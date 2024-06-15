package edu.poly.shop.controller.admin;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Product;
import edu.poly.shop.domain.ProductStatus;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;
import edu.poly.shop.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/product")
public class ProductsController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ParamService paramService;
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("product", new Product());
		
		List<Category> listcate = categoryService.findAll();
		model.addAttribute("categories", listcate);
		return "admin/product/addOrEdit";
	}

//	@RequestMapping("")
//	public String list(ModelMap model) {
//
//		List<Customer> list = customerService.findAll();
//
//		model.addAttribute("customers", list);
//		return "admin/customer/list";
//	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto,
	        BindingResult result) {
	    if (result.hasErrors()) {
	        System.out.println(result.toString());
	        return new ModelAndView("admin/product/addOrEdit");
	    }

	    Product entity;
	    if (dto.getIsEdit()) {
	        Optional<Product> opt = productService.findById(dto.getProductID());
	        if (opt.isPresent()) {
	            entity = opt.get();
	        } else {
	            model.addAttribute("message", "Product does not exist");
	            return new ModelAndView("admin/product/addOrEdit", model);
	        }
	    } else {
	        entity = new Product();
	    }

	    BeanUtils.copyProperties(dto, entity);

	    MultipartFile imageFile = dto.getImage();
	    if (imageFile != null && !imageFile.isEmpty()) {
	        File savedFile = paramService.save(imageFile, "/uploads/");
	        if (savedFile != null) {
	            entity.setImage(savedFile.getName());  // Lưu tên tệp hình ảnh vào cơ sở dữ liệu
	        }
	    }

	    productService.save(entity);
	    model.addAttribute("message", "Product is saved");

	    return new ModelAndView("redirect:/admin/product/search", model);
	}


	@GetMapping("/edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId") int productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		if (opt.isPresent()) {
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			dto.setImgurl(entity.getImage());
			
			
			model.addAttribute("statuses", ProductStatus.values());
			List<Category> listcate = categoryService.findAll();
			model.addAttribute("categories", listcate);
			model.addAttribute("product", dto);
			return new ModelAndView("admin/product/addOrEdit", model);
		}
		model.addAttribute("message", "Product is not existed");
		return new ModelAndView("redirect:/admin/product/search", model);
	}

	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId") int productId) {
		productService.deleteById(productId);
		model.addAttribute("message", "product is deleted");

		return new ModelAndView("forward:/admin/product/search", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "productName", required = false) String productname,
			@RequestParam("p") Optional<Integer> p) {
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				boolean isLogin = true;
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAll(pageable);
		model.addAttribute("page", page);

		Page<Product> resultPage;
		if (StringUtils.hasText(productname)) {
			resultPage = productService.findByproductNameContaining(productname, pageable);
			model.addAttribute("productName", productname);
		} else {
			resultPage = productService.findAll(pageable);
		}
		model.addAttribute("productPage", resultPage);
              
		return "admin/product/search";
	}

}

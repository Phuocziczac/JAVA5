package edu.poly.shop.controller.admin;

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
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.SessionService;
import edu.poly.shop.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;
	@GetMapping("/add")
	public String add(Model model) {
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
		model.addAttribute("customer", new CustomerDto());
		return "admin/customer/addOrEdit";
	}

	@RequestMapping("")
	public String list(ModelMap model) {

		List<Customer> list = customerService.findAll();

		model.addAttribute("customers", list);
		return "admin/customer/list";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("customer") CustomerDto dto,
			BindingResult result) {
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
		if (result.hasErrors()) {
                   System.out.println(result.toString());
                   System.out.println("sdad"+result.getFieldError());
			return new ModelAndView("admin/customer/addOrEdit");
		}
		Customer entity = new Customer();
		BeanUtils.copyProperties(dto, entity);
		
		customerService.save(entity);

		model.addAttribute("message", "Customer is save");
		return new ModelAndView("redirect:/admin/customer/search", model);
	}
	@GetMapping("/edit/{customerId}")
	public ModelAndView edit(ModelMap model,@PathVariable("customerId") Long customerId) {
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
	    Optional<Customer> opt = customerService.findById(customerId);
	   CustomerDto dto = new CustomerDto();
	    if(opt.isPresent()) {
	    	Customer entity = opt.get();
	    	BeanUtils.copyProperties(entity, dto);
	    	dto.setIsEdit(true);
	    	
	    	model.addAttribute("customer",dto);
	    	return new ModelAndView("admin/customer/addOrEdit",model) ;
	    }
	    	model.addAttribute("message","Category is not existed");
		return new ModelAndView("redirect:/admin/customer/search",model) ;
	}
	@GetMapping("delete/{customerId}")
	public ModelAndView delete (ModelMap model,@PathVariable("customerId") Long customerId) {
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
		customerService.deleteById(customerId);
		model.addAttribute("message","Customer is deleted");
		
		return new ModelAndView("forward:/admin/customer/search",model) ;
	}
	@GetMapping("search")
	public String search(ModelMap model,
	                     @RequestParam(name = "name", required = false) String name,
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
	    Page<Customer> page = customerService.findAll(pageable);
	    model.addAttribute("page", page);

	    Page<Customer> resultPage;
	    if (StringUtils.hasText(name)) {
	        resultPage = customerService.findByNameContaining(name, pageable);
	        model.addAttribute("name", name);
	    } else {
	        resultPage = customerService.findAll(pageable);
	    }
	    model.addAttribute("customerPage", resultPage);

	    return "admin/customer/search";
	}


}

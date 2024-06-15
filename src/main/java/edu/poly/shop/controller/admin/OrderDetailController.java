package edu.poly.shop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.OrderDetaildto;
import edu.poly.shop.model.OrderDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/orderDetail")
public class OrderDetailController {
	@Autowired
	ProductService productService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	OrderService orderService;
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;
	@GetMapping("/add")
	public String showOrderDetailForm(Model model) {
	    List<Product> products = productService.findAll();
	    List<Order> orders = orderService.findAll(); // Tải danh sách các Order
	    
	    model.addAttribute("products", products); // Đảm bảo tên thuộc tính là 'products'
	    model.addAttribute("orders", orders); // Thêm danh sách Order vào model
	    model.addAttribute("orderDetail", new OrderDetaildto()); // Tạo đối tượng OrderDetailDto mới
	    return "admin/orderdetail/addOrEdit"; // Tên của view
	}


	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("orderDetail") OrderDetaildto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("admin/orderdetail/addOrEdit");
		}
		OrderDetail entity = new OrderDetail();
		BeanUtils.copyProperties(dto, entity);
		Order order = orderService.findById(dto.getOrderId())
				.orElseThrow(() -> new RuntimeException("Order not found"));
		Product product = productService.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));
		entity.setOrders(order);
		entity.setProduct(product);
		orderDetailService.save(entity);

		model.addAttribute("message", "OrderDetail is save");
		return new ModelAndView("redirect:/admin/orderDetail/search", model);
	}

	@GetMapping("/edit/{orderDetailId}")
	public ModelAndView edit(ModelMap model, @PathVariable("orderDetailId") int orderDetailId) {
		Optional<OrderDetail> opt = orderDetailService.findById(orderDetailId);
		OrderDetaildto dto = new OrderDetaildto();
		if (opt.isPresent()) {
			OrderDetail entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			List<Product> listcus = productService.findAll();
			  List<Order> orders = orderService.findAll(); // Tải danh sách các Order
			    
			   // Đảm bảo tên thuộc tính là 'products'
			    model.addAttribute("orders", orders); // Thêm danh sách Order vào model
			model.addAttribute("products", listcus);
			model.addAttribute("orderDetail", dto);
			return new ModelAndView("admin/orderdetail/addOrEdit", model);
		}
		model.addAttribute("message", "OrderDetail is not existed");
		return new ModelAndView("redirect:/admin/order/search", model);
	}

	@GetMapping("delete/{orderDetailId}")
	public ModelAndView delete(ModelMap model, @PathVariable("orderDetailId") int orderDetailId) {
		orderDetailService.deleteById(orderDetailId);
		model.addAttribute("message", "OrderDetail is deleted");

		return new ModelAndView("redirect:/admin/orderDetail/search", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "orderDetailId", required = false) Integer orderDetailId,
			@RequestParam(name = "p", defaultValue = "0") Optional<Integer> p) {
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
		Page<OrderDetail> page = orderDetailService.findAll(pageable);
		model.addAttribute("page", page);

		Page<OrderDetail> resultPage;
		if (StringUtils.hasText(null)) {
			resultPage = orderDetailService.findByOrderDetailIdContaining(orderDetailId, pageable);
			model.addAttribute("orderDetailId", orderDetailId);
		} else {
			resultPage = orderDetailService.findAll(pageable);
		}
		model.addAttribute("orderDetailPage", resultPage);

		return "admin/orderdetail/search";
	}
}

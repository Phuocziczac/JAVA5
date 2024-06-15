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

import edu.poly.shop.model.OrderDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.SessionService;
import edu.poly.shop.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/order")
public class OrderController {
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;
	@Autowired
	OrderService orderService;
	@Autowired 
	CustomerService customerService;

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("order", new OrderDto());
	    List<Customer> listcus = customerService.findAll();
	    model.addAttribute("customers",listcus);
		return "admin/order/addOrEdit";
	}

	@RequestMapping("")
	public String list(ModelMap model) {

		List<Order> list = orderService.findAll();

		model.addAttribute("orders", list);
		return "admin/order/list";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("order") OrderDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.toString());
			return new ModelAndView("admin/order/addOrEdit");
		}
		Order entity = new Order();
		Customer customer = customerService.findById(dto.getCustomerId())
				.orElseThrow(() -> new RuntimeException("customer not found"));
		entity.setCustomer(customer);
		BeanUtils.copyProperties(dto, entity);
		
		orderService.save(entity);

		model.addAttribute("message", "Order is save");
		return new ModelAndView("redirect:/admin/order/search", model);
	}

	@GetMapping("/edit/{orderId}")
	public ModelAndView edit(ModelMap model, @PathVariable("orderId") Long orderId) {
		Optional<Order> opt = orderService.findById(orderId);
		OrderDto dto = new OrderDto();
		if (opt.isPresent()) {
			Order entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
		    List<Customer> listcus = customerService.findAll();
		    model.addAttribute("customers",listcus);
			model.addAttribute("order", dto);
			return new ModelAndView("admin/order/addOrEdit", model);
		}
		model.addAttribute("message", "Order is not existed");
		return new ModelAndView("redirect:/admin/order/search", model);
	}

	@GetMapping("delete/{orderId}")
	public ModelAndView delete(ModelMap model, @PathVariable("orderId") Long orderId) {
		orderService.deleteById(orderId);
		model.addAttribute("message", "Order is deleted");

		return new ModelAndView("forward:/admin/order/search", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "orderId", required = false) Long orderId,
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
		Page<Order> page = orderService.findAll(pageable);
		model.addAttribute("page", page);

		Page<Order> resultPage;
		   if (orderId != null) {
			resultPage = orderService.findByOrderId(orderId, pageable);
			model.addAttribute("orderId", orderId);
		} else {
			resultPage = orderService.findAll(pageable);
		}
		model.addAttribute("orderPage", resultPage);
	

		return "admin/order/search";
	}

}
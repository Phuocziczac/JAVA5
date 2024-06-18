package edu.poly.shop.controller.site;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Product;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;


@Controller
public class ProductControllersite {
	@Autowired
	private ProductService productService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	ParamService paramService;
	@Autowired
	OrderService orderService;
	@Autowired
	CustomerService customerService;
	@Autowired
	OrderDetailService detailService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping("")
	public String Product(Model model, @RequestParam(defaultValue = "0") int page) {
		boolean isLogin = true;
		String username = sessionService.get("account");

		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("imgurl", user.getImage());
				System.out.println("sss" + user.getImage());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		model.addAttribute("title", "Product");
		List<Category> category = categoryService.findAll();
		model.addAttribute("categories", category);
		  Pageable pageable = PageRequest.of(page, 10);
		    Page<Product> products = productService.findAvailableProductsPage(pageable);
		    model.addAttribute("productPage", products);

		return "site/product";

	}

}

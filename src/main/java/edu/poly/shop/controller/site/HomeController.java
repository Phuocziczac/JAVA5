package edu.poly.shop.controller.site;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.domain.Customer;
import edu.poly.shop.domain.Order;
import edu.poly.shop.domain.OrderDetail;
import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CartItem;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.model.OrderDto;
import edu.poly.shop.model.ProductDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CategoryService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.OrderDetailService;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;
import jakarta.persistence.Entity;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/site")
public class HomeController {
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

	@GetMapping("/logout")
	public String logout(Model model) {
		boolean isLogin = false;
		sessionService.remove("account"); // Xóa thuộc tính user khỏi session
		model.addAttribute("isLogin", isLogin);
		model.addAttribute("message", "please login ");
		return "redirect:/site/account/login"; // Chuyển hướng tới trang đăng nhập
	}

	@GetMapping("/home")
	public String home(Model model) {

		model.addAttribute("title", "HOME PAGE");
		boolean isLogin = true;
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
				
				return "forward:/site/product"; // Trả về trang chủ
			} else {
				return "redirect:/site/account/login"; // Chuyển hướng tới trang đăng nhập nếu người dùng không tồn tại
			}
		} else {
			return "redirect:/site/account/login"; // Chuyển hướng tới trang đăng nhập nếu chưa đăng nhập
		}
	}
	@RequestMapping("/default/homeadmin")
	public String admin() {
		return "site/default/homeadmin";
	}

	@RequestMapping("/product")
	public String Product(Model model, @RequestParam(defaultValue = "0") int page) {
	
	
		model.addAttribute("title", "Product");
		List<Category> category = categoryService.findAll();
		model.addAttribute("categories",category);
		List<Product> products = productService.findAll();
		model.addAttribute("product", products);
	
		 Pageable pageable = PageRequest.of(page, 10); // 10 sản phẩm mỗi trang
	        Page<Product> productPage = productService.findAll(pageable);
	        model.addAttribute("productPage", productPage);
	        return "site/product";
		
	}

	@RequestMapping("/order")
	public String order(Model model) {
		boolean isLogin = true;
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		List<Order> listorder = orderService.findAll();
		model.addAttribute("orders",listorder);
		return "site/order";
	}

	@RequestMapping("/detailproduct/{productID}")
	public String detail(Model model, @PathVariable("productID") int productID) {
		boolean isLogin = true;
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		Optional<Product> opt = productService.findById(productID);
		ProductDto dto = new ProductDto();
		if (opt.isPresent()) {
			Product entity = opt.get();

			BeanUtils.copyProperties(entity, dto);
			System.out.println();
			dto.setImgurl(entity.getImage());
			dto.setIsEdit(true);

			model.addAttribute("product", dto);
			return "site/detailproduct";
		}
		model.addAttribute("message", "Product is not existed");
		return "forward:/site/product";
	}

	@Autowired
	SessionService service;

	@RequestMapping("/cart")
	public String viewCart(Model model, @Valid @ModelAttribute("customer") CustomerDto cusdto, BindingResult result) {
		boolean isLogin = true;
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		List<CartItem> cartItems = (List<CartItem>) service.get("cart");
		if (cartItems == null || cartItems.isEmpty()) {
			model.addAttribute("error", "  Your cart is empty.");
			// Nếu giỏ hàng trống, chuyển hướng đến trang thông báo giỏ hàng trống
			return "site/cartitem";
		}
		double totalPrice = 0.0;

		for (CartItem item : cartItems) {
			totalPrice += item.getUnitPrice() * item.getQuantity();
		}
		List<Customer> customers = customerService.findAll(); // Phương thức để lấy danh sách khách hàng
	    model.addAttribute("customers", customers);
		// Truyền tổng giá trị đến view
		model.addAttribute("totalPrice", totalPrice);

		model.addAttribute("cartItems", cartItems);
		return "site/cartitem";
	}

	@RequestMapping("/cart/add/{productID}")
	public String addcart(Model model, @PathVariable("productID") int productId) {
		boolean isLogin = true;
		String username = sessionService.get("account");
		if (username != null) {
			Optional<Account> userOpt = accountService.findById(username);
			if (userOpt.isPresent()) {
				Account user = userOpt.get();
				model.addAttribute("account", user.getUsername());
				model.addAttribute("isLogin", isLogin);
				model.addAttribute("isAdmin", user.isRole());
			}
		}
		Optional<Product> entity = productService.findById(productId);
		if (entity.isPresent()) {
			Product product = entity.get();
			CartItem cartItem = new CartItem();
			cartItem.setProductId(product.getProductID());
			cartItem.setName(product.getProductName());
			cartItem.setQuantity(1); // default quantity is 1
			cartItem.setUnitPrice(product.getUnitPrice());

			// Get the cart from the session
			List<CartItem> cart = (List<CartItem>) service.get("cart");
			if (cart == null) {
				cart = new ArrayList<>();
				service.set("cart", cart);
			}
		
			// Add the cart item to the cart
			cart.add(cartItem);
			model.addAttribute("cartItems", cart);

			// Redirect to the cart page
			return "redirect:/site/cart";
		} else {
			// Product not found, redirect to error page
			return "redirect:/site/home";
		}
	}

	@RequestMapping("/cart/update/{productID}")
	public String updateCartItem(Model model, @PathVariable("productID") int productId,
			@RequestParam("quantity") int quantity) {
		// Kiểm tra xem sản phẩm có tồn tại trong giỏ hàng không
		List<CartItem> cartItems = (List<CartItem>) service.get("cart");
		if (cartItems != null) {
			for (CartItem item : cartItems) {
				if (item.getProductId() == productId) {
					// Cập nhật số lượng sản phẩm
					item.setQuantity(quantity);
					break;
				}
			}
			// Lưu lại giỏ hàng mới vào session
			service.set("cart", cartItems);
		}

		return "redirect:/site/cart";// Chuyển hướng đến trang giỏ hàng
	}

	@RequestMapping("/cart/delete/{productID}")
	public String deleteCartItem(Model model, @PathVariable("productID") int productId) {
		// Kiểm tra xem sản phẩm có tồn tại trong giỏ hàng không
		List<CartItem> cartItems = (List<CartItem>) service.get("cart");
		if (cartItems != null) {
			// Xóa sản phẩm khỏi giỏ hàng dựa vào productId
			cartItems.removeIf(item -> item.getProductId() == productId);
			// Lưu lại giỏ hàng mới vào session
			service.set("cart", cartItems);
		}

		return "redirect:/site/cart"; // Chuyển hướng đến trang giỏ hàng
	}
	
	@PostMapping("/order/add")
	public ModelAndView addOrder(Model model, @Valid @ModelAttribute("customer") CustomerDto cusdto, BindingResult result, @RequestParam("customerId") Long customerId) {
	    if (result.hasErrors()) {
	        System.out.println(result.toString());
	        return new ModelAndView("redirect:/site/cart");
	    }

	    List<CartItem> cartItems = (List<CartItem>) service.get("cart");
	    if (cartItems == null || cartItems.isEmpty()) {
	        return new ModelAndView("redirect:/site/cart");
	    }

	    Customer customer;
	    if (customerId != null && customerId > 0) {
	        // Use the selected customer
	        Optional<Customer> customerOpt = customerService.findById(customerId);
	        if (customerOpt.isPresent()) {
	            customer = customerOpt.get();
	        } else {
	            // Handle case where the selected customer does not exist
	            return new ModelAndView("redirect:/site/cart");
	        }
	    } else {
	        // Create a new customer
	        Customer cusentity = new Customer();
	        BeanUtils.copyProperties(cusdto, cusentity);
	        cusentity.setRegisterDate(new Date().toString());
	        cusentity.setStatus("available");
	        customer = customerService.save(cusentity);
	    }

	    // Create a new order
	    Order entity = new Order();
	    entity.setCustomer(customer);
	    entity.setAmount(calculateTotalPrice(cartItems));
	    entity.setOrderDate(new Date().toString());
	    entity.setStatus("available");
	    Order savedOrder = orderService.save(entity);

	    // Create order details
	    for (CartItem item : cartItems) {
	        OrderDetail detail = new OrderDetail();
	        detail.setOrders(savedOrder);
	        Optional<Product> product = productService.findById(item.getProductId());
	        if (product.isPresent()) {
	            detail.setProduct(product.get());
	            detail.setQuantity(item.getQuantity());
	            detail.setUnitPrice(item.getUnitPrice());
	            detailService.save(detail);
	        }
	    }

	    // Clear the cart
	    service.remove("cart");

	    // Redirect to the order page
	    return new ModelAndView("redirect:/site/order");
	}



	private double calculateTotalPrice(List<CartItem> cartItems) {
	    double totalPrice = 0.0;
	    for (CartItem item : cartItems) {
	        totalPrice += item.getUnitPrice() * item.getQuantity();
	    }
	    return totalPrice;
	}
	
	@RequestMapping("/orderdetail/{orderId}")
	public String orderdetail(Model model,@PathVariable("orderId")Long orderId) {
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
		Optional<Order> entity = orderService.findById(orderId);
		Order order = entity.get();
		model.addAttribute("order",order);
		
		System.out.println("cc"+order.getOrderDetails());
		
		List<OrderDetail> listdetail = orderService.findOrderDetailListByOrderId(orderId);
		System.out.println("fsf"+listdetail);
		return "site/orderdetail";
	}
	

    @PostMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, @RequestParam(required = false) Long category, Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Category> categoryid = categoryService.findAll();
		model.addAttribute("categories",categoryid);
        Page<Product> productPage = productService.searchProducts(keyword, category, pageable);
        model.addAttribute("productPage", productPage);
        return "redirect:/site/home";
    }

}

package edu.poly.shop.controller.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.poly.shop.domain.Product;
import edu.poly.shop.model.CartItem;
import edu.poly.shop.service.ProductService;
import edu.poly.shop.service.SessionService;

@Controller
@RequestMapping("/site")
public class CartController {
//	@Autowired
//	SessionService service;
//	@Autowired
//	ProductService productService;
//	
//	
//	@RequestMapping("/cart/add/{productID}")
//	public String addcart(Model model, @PathVariable("productID") int productId) {
//		   Optional<Product> entity = productService.findById(productId);
//		    if (entity.isPresent()) {
//		        Product product = entity.get();
//		        CartItem cartItem = new CartItem();
//		        cartItem.setProductId(product.getProductID());
//		        cartItem.setName(product.getProductName());
//		        cartItem.setQuantity(1); // default quantity is 1
//		        cartItem.setUnitPrice(product.getUnitPrice());
//		        
//		        // Get the cart from the session
//		        List<CartItem> cart = (List<CartItem>) service.get("cart");
//		        if (cart == null) {
//		            cart = new ArrayList<>();
//		            service.set("cart", cart);
//		        }
//		        
//		        // Add the cart item to the cart
//		        cart.add(cartItem);
//		        model.addAttribute("cartItems", cart);
//		        
//		        // Redirect to the cart page
//		        return "site/cartitem";
//		    } else {
//		        // Product not found, redirect to error page
//		        return "redirect:/site/home";
//		    }
//	}

}

package edu.poly.shop.controller.site;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import edu.poly.shop.domain.Category;
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.CategoryDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.MailerService;
import edu.poly.shop.service.ParamService;
import edu.poly.shop.service.SessionService;
import groovy.cli.Option;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/site")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private MailerService mailerService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private ParamService paramService;

	@RequestMapping("/account/login")
	public String login(@ModelAttribute("account") Account account, BindingResult result, Model model) {

		return "site/account/login";
	}

	@RequestMapping("/account/Register")
	public String register(@ModelAttribute("account") AccountDto accountdto, BindingResult result, Model model) {

		return "site/account/Register";
	}

	@RequestMapping("/account/changepassword")
	public String changepassword(Model model) {

		return "site/account/changepassword";
	}

	@RequestMapping("/account/fogotpassword")
	public String fogotpassword(Model model) {

		return "site/account/fogotpassword";
	}

	@PostMapping("/account/Register/add")
	public ModelAndView addUser(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
System.out.println(result.toString());
			return new ModelAndView("site/account/Register");
		}

		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		entity.setRole(false);
		  MultipartFile imageFile = dto.getImage();
		    if (imageFile != null && !imageFile.isEmpty()) {
		        File savedFile = paramService.save(imageFile, "/uploads/");
		        if (savedFile != null) {
		            entity.setImage(savedFile.getName());  // Lưu tên tệp hình ảnh vào cơ sở dữ liệu
		        }
		    }
		accountService.save(entity);

		model.addAttribute("message", "sucess , please login !!");
		return new ModelAndView("forward:/site/account/login", model);
	}

	@PostMapping("/account/login")
	public ModelAndView login(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
		Optional<Account> user = accountService.findById(dto.getUsername());
		if (user.isEmpty()) {
			model.addAttribute("error", "Account not found");
			return new ModelAndView("site/account/login", model);
		}
		Account entity = user.get();
		if (!entity.getPassword().equals(dto.getPassword())) {
			model.addAttribute("error", "Invalid credentials");
			return new ModelAndView("site/account/login", model); // Trả về trang đăng nhập nếu mật khẩu không khớp
		}

		boolean isAdmin = entity.isRole();
		
		sessionService.set("account", entity.getUsername());
		sessionService.set("isAdmin", isAdmin);
		if (isAdmin) {
			return new ModelAndView("redirect:/site/default/homeadmin", model);
		}

		return new ModelAndView("redirect:/site/home", model);
	}

	@PostMapping("/account/fogotpassword")
	public String sendPass(@RequestParam("username") String username, @RequestParam("email") String to, Model model) {
		Optional<Account> user = accountService.findById(username);
		if (user.isEmpty()) {
			model.addAttribute("error", "Account not found");
			return "site/account/fogotpassword";
		}
		mailerService.sendSimpleEmail(to, "Mail fogot", "Mật khẩu của bạn là:" + user.get().getPassword());
		model.addAttribute("message", "Zo check mail di ma con gui roi do ");
		return "site/account/fogotpassword";
	}

	@PostMapping("/account/changepassword")
	public String changepass(@ModelAttribute("newPassword") String newpassword,
			@ModelAttribute("confirmPassword") String confirmpassword,
			@ModelAttribute("oldPassword") String oldPassword, Model model) {
		String username = sessionService.get("account");

		// Retrieve the user from the database using the username
		Optional<Account> optionalUser = accountService.findById(username);

		Account user = optionalUser.get();

		if (!user.getPassword().equals(oldPassword)) {
			model.addAttribute("error", "Old password is incorrect");

		} else {
			if (!newpassword.equals(confirmpassword)) {
				model.addAttribute("error", "New password and confirm password do not match");
				return "site/account/changepassword";
			} else {
				user.setPassword(newpassword);
				accountService.save(user);
				model.addAttribute("message", "Password changed successfully");
			}
		}

		return "site/account/changepassword";
	}

}

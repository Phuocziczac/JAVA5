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
import edu.poly.shop.model.AccountDto;
import edu.poly.shop.model.CustomerDto;
import edu.poly.shop.service.AccountService;
import edu.poly.shop.service.CustomerService;
import edu.poly.shop.service.SessionService;
import jakarta.validation.Valid;



@Controller
@RequestMapping("admin/profile/")
public class ProfileController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;
	@GetMapping("/add")
	public String add(Model model) {
		  
		model.addAttribute("account", new AccountDto());
		return "admin/profile/addOrEdit";
	}

	

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto,
			BindingResult result) {
		if (result.hasErrors()) {
                   System.out.println(result.toString());
                   System.out.println("sdad"+result.getFieldError());
			return new ModelAndView("admin/profile/addOrEdit");
		}
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		
		accountService.save(entity);

		model.addAttribute("message", "account is save");
		return new ModelAndView("redirect:/admin/profile/search", model);
	}
	@GetMapping("/edit/{username}")
	public ModelAndView edit(ModelMap model,@PathVariable("username") String username) {
	    Optional<Account> opt = accountService.findById(username);
	   AccountDto dto = new AccountDto();
	    if(opt.isPresent()) {
	    	Account entity = opt.get();
	    	BeanUtils.copyProperties(entity, dto);
	    	dto.setIsEdit(true);
	    	
	    	model.addAttribute("account",dto);
	    	return new ModelAndView("admin/profile/addOrEdit",model) ;
	    }
	    	model.addAttribute("message","Account is not existed");
		return new ModelAndView("redirect:/admin/profile/search",model) ;
	}
	@GetMapping("delete/{username}")
	public ModelAndView delete (ModelMap model,@PathVariable("username") String username) {
		accountService.deleteById(username);
		model.addAttribute("message","Account is deleted");
		
		return new ModelAndView("forward:/admin/profile/search",model) ;
	}
	@GetMapping("search")
	public String search(ModelMap model,
	                     @RequestParam(name = "username", required = false) String name,
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
	    Page<Account> resultPage;
	    Page<Account> page = accountService.findAll(pageable);
	    model.addAttribute("page", page);

	 
	    if (StringUtils.hasText(name)) {
	        resultPage = accountService.findByUsernameContaining(name, pageable);
	        model.addAttribute("username", name);
	    } else {
	        resultPage = accountService.findAll(pageable);
	    }
	    model.addAttribute("profilePage", resultPage);

	    return "admin/profile/search";
	}
}

package edu.poly.shop.interceptor;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.poly.shop.domain.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private edu.poly.shop.service.SessionService sessionService;

    @Autowired
    private edu.poly.shop.service.AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = sessionService.get("account");
        if (username != null) {
            Optional<Account> userOpt = accountService.findById(username);
            if (userOpt.isPresent()) {
                Account user = userOpt.get();
                boolean isLogin = true;
                request.setAttribute("account", user.getUsername());
                request.setAttribute("isLogin", isLogin);
                request.setAttribute("isAdmin", user.isRole());
                request.setAttribute("imgurl", user.getImage());
            } else {
                request.setAttribute("isLogin", false);
            }
        } else {
            request.setAttribute("isLogin", false);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("isLogin", request.getAttribute("isLogin"));
            modelAndView.addObject("account", request.getAttribute("account"));
            modelAndView.addObject("isAdmin", request.getAttribute("isAdmin"));
        }
    }
}
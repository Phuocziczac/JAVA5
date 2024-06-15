package edu.poly.shop.controller.admin;

import edu.poly.shop.model.Report;
import edu.poly.shop.service.OrderService;
import edu.poly.shop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/report")
public class ReportController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping()
    public String getReport(Model model) {
        List<Report> revenueByMonth = orderService.getRevenueByMonth();
        model.addAttribute("revenueByMonth", revenueByMonth);
        String[] labels = revenueByMonth.stream().map(Report::getPeriod).toArray(String[]::new);
        double[] data = revenueByMonth.stream().mapToDouble(Report::getTotalRevenue).toArray();
       
        model.addAttribute("labels", labels);
        model.addAttribute("data", data);
        
        List<Object[]> CountProCate = productService.getTotalProductsAndCategories();
        int totalProducts = ((Number) CountProCate.get(0)[0]).intValue();
        int totalCategories = ((Number) CountProCate.get(0)[1]).intValue();

       
     model.addAttribute("totalpro",totalProducts);
     model.addAttribute("totalcate",totalCategories);
        return "admin/report";
    }
    @GetMapping("/product")
    public String getRePro(Model model) {
  
       
   
        return "admin/report";
    }
}

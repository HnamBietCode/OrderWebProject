package com.hnam.web_order.controller;

import com.hnam.web_order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model){
        //lấy ds sp
        var products = productRepository.findAll();
        //đóng gói ds với tên biến products để gửi ra giao diện
        model.addAttribute("products", products);

        //trả về file gdien "index.html"
        return "index";
    }
}

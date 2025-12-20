package com.hnam.web_order.controller;

import com.hnam.web_order.dto.OrderRequest;
import com.hnam.web_order.entity.Order;
import com.hnam.web_order.repository.ProductRepository;
import com.hnam.web_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String home(Model model){
        //lấy ds sp
        var products = productRepository.findAll();
        //đóng gói ds với tên biến products để gửi ra giao diện
        model.addAttribute("products", products);

        //trả về file gdien "index.html"
        return "index";
    }

    @GetMapping("/buy/{id}")
    public String showBuyForm(@PathVariable("id") Long id, Model model){
        var product = productRepository.findById(id).orElse(null);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("orderRequest", orderRequest);

        return "order-form";
    }

    @PostMapping("/buy")
    public String handleBuy(@ModelAttribute OrderRequest orderRequest, Model model){
        orderService.placeOrder(orderRequest);

        return "order-success";
    }

    @GetMapping("/orders")
    public String listOrders(Model model){
        //goi service lấy ds đơn hàng
        var orders = orderService.gettAllOrders();
        //gui ds lên gdien
        model.addAttribute("orders", orders);

        return "order-list";
    }
}

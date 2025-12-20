package com.hnam.web_order.controller;


import com.hnam.web_order.dto.OrderRequest;
import com.hnam.web_order.entity.Order;
import com.hnam.web_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping // đánh dấu API nhận POST request
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request){
        Order newOrder = orderService.placeOrder(request);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping
    public java.util.List<Order> getAllOrders(){
        return orderService.gettAllOrders();
    }
}

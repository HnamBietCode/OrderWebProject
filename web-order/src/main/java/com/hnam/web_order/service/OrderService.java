package com.hnam.web_order.service;


import com.hnam.web_order.dto.OrderRequest;
import com.hnam.web_order.entity.Order;
import com.hnam.web_order.entity.Product;
import com.hnam.web_order.repository.OrderRepository;
import com.hnam.web_order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order placeOrder(OrderRequest request){
        //tìm sp trong kho( check tồn )
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        //tạo đơn hàng mới từ request
        Order newOrder = new Order();
        newOrder.setCustomerName(request.getCustomerName());
        newOrder.setAddress(request.getAddress());
        newOrder.setPhoneNumber(request.getPhoneNumber());
        newOrder.setProduct(product);
        newOrder.setQuantity(request.getQuantity());

        //tự cộng tiền, tranh nhận gtri sai từ FE send lên
        newOrder.setTotalPrice(product.getPrice() * request.getQuantity());

        // sau cùng sẽ lưu tất cả vào DB
        return orderRepository.save(newOrder);
    }

    public java.util.List<Order> gettAllOrders(){
        return orderRepository.findAll();
    }
}

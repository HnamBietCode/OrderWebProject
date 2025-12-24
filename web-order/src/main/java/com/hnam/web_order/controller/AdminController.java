package com.hnam.web_order.controller;

import com.hnam.web_order.entity.Category;
import com.hnam.web_order.entity.Product;
import com.hnam.web_order.repository.CategoryRepository;
import com.hnam.web_order.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // --- 1. THÊM DANH MỤC ---
    @GetMapping("/category/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @PostMapping("/category/add")
    public String addCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/"; // Thêm xong về trang chủ
    }

    // --- 2. THÊM SẢN PHẨM ---
    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll()); // Để chọn danh mục
        return "admin/product-form";
    }

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/";
    }
}
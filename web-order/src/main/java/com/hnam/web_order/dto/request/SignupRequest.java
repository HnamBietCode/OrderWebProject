package com.hnam.web_order.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> role; // Người dùng muốn đăng ký quyền gì (admin/user)
}
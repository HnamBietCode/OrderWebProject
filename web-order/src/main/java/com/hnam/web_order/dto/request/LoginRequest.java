package com.hnam.web_order.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
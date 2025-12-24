package com.hnam.web_order.security;

import com.hnam.web_order.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;


    @Autowired
    private AuthEntryPointJwt unauthorizedHandler; // Xử lý lỗi khi chưa đăng nhập

    // Tạo Bean cho bộ lọc Token vừa viết ở trên
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // Tạo Bean để mã hóa mật khẩu (BCrypt là chuẩn xịn nhất hiện nay)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Tạo Bean quản lý xác thực
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    // CẤU HÌNH CÁC URL (QUAN TRỌNG NHẤT)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tắt CSRF để test API cho dễ
                // .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Tạm tắt dòng này để debug
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Không dùng Session, chỉ dùng Token
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()// Trang chủ: Ai cũng được vào
                                .requestMatchers("/api/auth/**").permitAll() // Đăng nhập/Đăng ký: Ai cũng được vào
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // File tĩnh: Ai cũng được vào
                                .requestMatchers("/api/products/**").permitAll() // Xem sản phẩm: Ai cũng được vào
                                .anyRequest().authenticated() // CÁC TRANG CÒN LẠI: PHẢI CÓ TOKEN MỚI ĐƯỢC VÀO
                )
                .formLogin(form -> form.loginPage("/login").permitAll()
                );

        // Thêm bộ lọc Token vào chuỗi bảo mật
        // Spring Security sẽ tự động sử dụng UserDetailsService và PasswordEncoder beans
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
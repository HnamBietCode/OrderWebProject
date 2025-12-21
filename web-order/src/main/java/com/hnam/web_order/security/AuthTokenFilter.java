package com.hnam.web_order.security;

import com.hnam.web_order.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// Class này sẽ chạy TRƯỚC khi request vào đến Controller
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Lấy token từ Header của request (Gửi lên dạng: Authorization: Bearer <token>)
            String jwt = parseJwt(request);

            // 2. Nếu có token và token hợp lệ
            if (jwt != null && jwtUtils.validateToken(jwt)) {

                // 3. Lấy tên user từ token
                String username = jwtUtils.getUsernameFromToken(jwt);

                // 4. Tìm thông tin user trong Database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Xác thực người dùng này với hệ thống Security (Cho phép đi qua)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Không thể xác thực user: {}", e);
        }

        // 6. Cho phép request đi tiếp (vào controller hoặc filter tiếp theo)
        filterChain.doFilter(request, response);
    }

    // Hàm phụ: Lấy token từ Header HOẶC Cookie
    private String parseJwt(HttpServletRequest request) {
        // 1. Tìm trong Header trước (Dành cho Postman / Mobile App)
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        // 2. Nếu không có ở Header thì tìm trong Cookie (Dành cho Web Thymeleaf)
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
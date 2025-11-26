package com.example.jwtcors.config;

import com.example.jwtcors.service.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtFilter implements Filter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException{

            HttpServletRequest request = (HttpServletRequest) req;

            String header = request.getHeader("Authorization");

            if(header != null && header.startsWith("Bearer ")){
                String token = header.substring(7);

                try {
                    String username = jwtUtil.getUsername(token);
                    String role = jwtUtil.getRole(token);

                    Authentication auth = new UsernamePasswordAuthenticationToken(username,null,
                            Set.of(new SimpleGrantedAuthority("ROLE_"+role)));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }catch (Exception e){
                    System.out.println("Token invalido");
                }
            }

            chain.doFilter(req, res);
        }
}

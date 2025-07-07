package com.ecommerce.config;

import com.ecommerce.service.JWTService;
import com.ecommerce.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZWphcyIsImlhdCI6MTc0MzQ0NjEwNywiZXhwIjoxNzQzNDQ2MjE1fQ.sR1hmhsiT05AfzcRPR8c0zwku3K6YKRVxELbbjCgsUs

        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            userName=jwtService.extractUsername(token);   //We need to extract username from token
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            //If username is not null and it is not already authenticated then validate the token
            UserDetails userDetails=context.getBean(UserService.class).loadUserByUsername(userName);
            if(jwtService.validateToken(token, userDetails))
            {
                //If validate token returns true create the authentication object and set it
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request,response);
    }
}

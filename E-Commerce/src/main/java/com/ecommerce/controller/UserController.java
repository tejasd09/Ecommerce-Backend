package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
     UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

            userService.registerUser(user.getUsername(), user.getPassword(), user.getRole(), user.getEmail());
            return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {
        return userService.verify(userDto);

    }

}

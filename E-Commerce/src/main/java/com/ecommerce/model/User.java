package com.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="users"
        ,uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))  // Prevents duplicate product entries for the same user)
public class User {                                                                       //Allows Multiple product for same user_id

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Email(message="Invalid email format")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



}

package com.ecommerce.dto;

import java.util.List;

public class CartSummaryDto {
    private List<CartResponseDto> cartResponseDto;
    private double totalCost;

    public CartSummaryDto(List<CartResponseDto> cartResponseDto,double totalCost)
    {
        this.cartResponseDto=cartResponseDto;
        this.totalCost=totalCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<CartResponseDto> getCartResponseDto() {
        return cartResponseDto;
    }

    public void setCartResponseDto(List<CartResponseDto> cartResponseDto) {
        this.cartResponseDto = cartResponseDto;
    }
}

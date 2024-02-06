package com.register.registration.dto;
import lombok.Data;
@Data
public class CartItemResponse {
    private Long productId;
    private Long cartItemId;
    private String productName;
    private int quantity;
    private double productCost ;
    private double totalCartCost;
    public double getTotalPrice() {
        return quantity * productCost ;
    }
    public CartItemResponse(Long productId, Long cartItemId, String productName, int quantity, double productCost, double v, double totalCartCost) {
        this.productId = productId;
        this.cartItemId = cartItemId;
        this.productName = productName;
        this.quantity = quantity;
        this.productCost =productCost ;

    }

}
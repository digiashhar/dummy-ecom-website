package com.register.registration.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Entity
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkoutId; // Unique ID for each checkout(my bill id)
   // private Long productId;
    private Long userId;
    private double totalPrice;

    //private boolean billingStatus; // true if billing is successful, false otherwise

}

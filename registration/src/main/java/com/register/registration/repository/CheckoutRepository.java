package com.register.registration.repository;

import com.register.registration.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    //Checkout findByUserId(Long userId);
    List<Checkout> findByUserId(Long userId);
}

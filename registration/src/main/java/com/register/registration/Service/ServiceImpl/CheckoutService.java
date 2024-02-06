package com.register.registration.Service.ServiceImpl;

import com.register.registration.entity.Checkout;
import com.register.registration.repository.CheckoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private CartService cartService;

    @Autowired
    private CheckoutRepository checkoutRepository;

//    public Checkout processCheckout(Long userId) {
//        Checkout checkout = new Checkout();
//        checkout.setUserId(userId);
//        //checkout.setTotalPrice(totalPrice);
//
//        // Additional logic if needed
//
//        return checkoutRepository.save(checkout);
//    }

public void performCheckout(Long userId) {
    double totalPrice = calculateTotalPrice(userId);

    // Create a new checkout entity
    Checkout checkout = new Checkout();
    checkout.setUserId(userId);
    checkout.setTotalPrice(totalPrice);

    // Save the checkout entity to the database
    checkoutRepository.save(checkout);

    // Display the checkout message
    displayCheckoutMessage(checkout);
}

    public Checkout processCheckout(Long userId) {
        // Check if checkout entry already exists for the userId
        List<Checkout> existingCheckouts = checkoutRepository.findByUserId(userId);
        //Checkout existingCheckout = checkoutRepository.findByUserId(userId);

        if (existingCheckouts!= null) {
            // If exists, update the total price
            double totalPrice = calculateTotalPrice(userId);

            Checkout existingCheckout = existingCheckouts.get(0);
            existingCheckout.setTotalPrice(totalPrice);

            // Save the updated checkout entity to the database
            checkoutRepository.save(existingCheckout);

            // Display the checkout message
            displayCheckoutMessage(existingCheckout);
            return existingCheckout;
        } else {
            // If not exists, create a new checkout entity
            double totalPrice = calculateTotalPrice(userId);

            Checkout newCheckout = new Checkout();
            newCheckout.setUserId(userId);
            newCheckout.setTotalPrice(totalPrice);

            // Save the new checkout entity to the database
            Checkout savedCheckout = checkoutRepository.save(newCheckout);

            // Display the checkout message
            displayCheckoutMessage(savedCheckout);
            return savedCheckout;
        }
    }


    // Display Checkout Message
    private void displayCheckoutMessage(Checkout checkout) {
        // You can use any logging mechanism or display on the UI
        System.out.println("Checkout details - User ID: " + checkout.getUserId() +
//                ", Product ID: " + checkout.getProductId() +
                ", Total Price: " + checkout.getTotalPrice());
    }
    public Checkout getCheckoutById(Long checkoutId) {
        return checkoutRepository.findById(checkoutId).orElse(null);
    }

    public double calculateTotalPrice(Long userId) {
        return cartService.getTotalCartCost(userId);
    }

    public Long getCheckoutIdByUserId(Long userId) {
        List<Checkout> checkouts = checkoutRepository.findByUserId(userId);

        if (!checkouts.isEmpty()) {
            // Handle the situation where multiple checkouts are found (e.g., log a warning)
            // You might also throw an exception if you want to enforce a unique result
            // For now, returning the checkout ID of the first item in the list
            return checkouts.get(0).getCheckoutId();
        } else {
            return null;
        }
    }
}
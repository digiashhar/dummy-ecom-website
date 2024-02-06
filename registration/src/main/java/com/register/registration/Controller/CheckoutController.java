package com.register.registration.Controller;

import com.register.registration.Service.ServiceImpl.CheckoutService;
import com.register.registration.dto.CheckoutResponse;
import com.register.registration.entity.Checkout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
//
//    @PostMapping("/processCheckout")
//    public ResponseEntity<String> processCheckout(@RequestParam Long userId, @RequestParam Long productId, @RequestParam double totalPrice) {
//        checkoutService.performCheckout(userId, productId, totalPrice);
//        return ResponseEntity.ok("Checkout successful!");  // You can customize the success message
//    }
// Updated endpoint to automatically calculate total price
@PostMapping("/processCheckout")
//public ResponseEntity<String> processCheckout(@RequestParam Long userId) {
public ResponseEntity<String> processCheckout(@RequestBody CheckoutResponse checkoutResponse) {
    checkoutService.performCheckout(checkoutResponse.getUserId());//did chng
    return ResponseEntity.ok("Checkout successful!");  // You can customize the success message
}

    @GetMapping("/totalPrice/user={userId}")
    public ResponseEntity<Double> calculateTotalPrice(@PathVariable Long userId) {
        double totalPrice = checkoutService.calculateTotalPrice(userId);
        return ResponseEntity.ok(totalPrice);
    }
//    }

    @GetMapping("/BillId/user={userId}")
    public ResponseEntity<Long> getCheckoutIdByUserId(@PathVariable Long userId) {
        Long checkoutId = checkoutService.getCheckoutIdByUserId(userId);

        if (checkoutId != null) {
            return ResponseEntity.ok(checkoutId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//@GetMapping("/totalPrice/user={userId}")
//public ResponseEntity<CheckoutResponse> calculateTotalPrice(@PathVariable Long userId) {
//    CheckoutResponse totalPrice = checkoutService.calculateTotalPrice(userId);
//    return ResponseEntity.ok(totalPrice);
//}
}

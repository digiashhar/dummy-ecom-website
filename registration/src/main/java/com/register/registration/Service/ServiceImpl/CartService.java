package com.register.registration.Service.ServiceImpl;

import com.register.registration.dto.CartItemResponse;
import com.register.registration.dto.CartRequest;
import com.register.registration.entity.Cart;
import com.register.registration.entity.Product;
import com.register.registration.entity.User;
import com.register.registration.repository.CartRepository;
import com.register.registration.repository.ProductRepository;
import com.register.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private Long userId;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean addProductToCart(Long userId, CartRequest cartRequest) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(cartRequest.getProductId()).orElse(null);

        if (user != null && product != null) {
            Cart existingCartItem = cartRepository.findByUserAndProduct(user, product);

            if (existingCartItem != null) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartRequest.getQuantity());
                cartRepository.save(existingCartItem);
            } else {
                Cart cartItem = new Cart();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setQuantity(cartRequest.getQuantity());
                cartRepository.save(cartItem);
            }
            return true;
        }

        return false;
    }

    public List<CartItemResponse> getCartItems(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<Cart> cartItems = cartRepository.findByUser(user);
            double totalCartCost = getTotalCartCost(userId);
            return mapCartItemsToResponse(userId, cartItems, totalCartCost);
        }

        return Collections.emptyList();
    }


    public boolean updateCartItemQuantity(Long userId, CartRequest cartRequest) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(cartRequest.getProductId()).orElse(null);

        if (user != null && product != null) {
            Cart cartItem = cartRepository.findByUserAndProduct(user, product);
            if (cartItem != null) {
                cartItem.setQuantity(cartRequest.getQuantity());
                cartRepository.save(cartItem);
                return true;
            }
        }
        return false;
    }

    public double getTotalCartCost(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<Cart> cartItems = cartRepository.findByUser(user);
            return cartItems.stream()
                    .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getProductCost())
                    .sum();
        }
        return 0;
    }

    public Long getProductId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<Cart> cartItems = cartRepository.findByUser(user);
            if (!cartItems.isEmpty()) {
                // Return the product ID of the first cart item
                return cartItems.get(0).getProduct().getProductId();
            }
        }

        return null;
    }

    private List<CartItemResponse> mapCartItemsToResponse(Long userId, List<Cart> cartItems, double totalCartCost) {
        return cartItems.stream()
                .map(cartItem -> new CartItemResponse(
                        cartItem.getProduct().getProductId(),
                        cartItem.getCartId(),
                        cartItem.getProduct().getProductName(),
                        cartItem.getQuantity(),
                        cartItem.getProduct().getProductCost(),
                        cartItem.getQuantity() * cartItem.getProduct().getProductCost(),
                        totalCartCost // Set the totalCartCost property
                ))
                .collect(Collectors.toList());
    }

}

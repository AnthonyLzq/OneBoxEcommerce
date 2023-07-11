package com.xcale.ecommerce.controller;

import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import com.xcale.ecommerce.dto.CreateCartRequest;
import com.xcale.ecommerce.dto.UpdateCartRequest;
import com.xcale.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  public ResponseEntity<String> createCart(@RequestBody CreateCartRequest createCartRequest) {
    List<CartItemDTO> items = createCartRequest.getItems();
    String cartId = cartService.createCart(items);

    return ResponseEntity.ok(cartId);
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<CartDTO> getCart(@PathVariable String cartId) {
    CartDTO cartDTO = cartService.getCart(cartId);

    return ResponseEntity.ok(cartDTO);
  }

  @PatchMapping
  public ResponseEntity<String> updateCart(@RequestBody UpdateCartRequest createCartRequest) {
    String updatedCartId = cartService.updateCart(createCartRequest.getCartId(), createCartRequest.getItems());

    return ResponseEntity.ok(updatedCartId);
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<String> deleteCart(@PathVariable String cartId) {
    String deletedCartId = cartService.deleteCart(cartId);

    return ResponseEntity.ok(deletedCartId);
  }
}

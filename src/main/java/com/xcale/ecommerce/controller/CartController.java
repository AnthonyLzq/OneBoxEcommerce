package com.xcale.ecommerce.controller;

import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import com.xcale.ecommerce.dto.CreateCartRequest;
import com.xcale.ecommerce.dto.OperationResult;
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
  public ResponseEntity<OperationResult<CartDTO>> createCart(@RequestBody CreateCartRequest createCartRequest) {
    OperationResult<CartDTO> result = cartService.createCart(createCartRequest.getItems());

    return ResponseEntity.status(result.getMessage().getCode()).body(result);
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<OperationResult<CartDTO>> getCart(@PathVariable String cartId) {
    OperationResult<CartDTO> result = cartService.getCart(cartId);

    return ResponseEntity.status(result.getMessage().getCode()).body(result);
  }

  @PatchMapping
  public ResponseEntity<OperationResult<CartDTO>> updateCart(@RequestBody UpdateCartRequest createCartRequest) {
    OperationResult<CartDTO> result = cartService.updateCart(createCartRequest.getCartId(), createCartRequest.getItems());

    return ResponseEntity.status(result.getMessage().getCode()).body(result);
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity<OperationResult<String>> deleteCart(@PathVariable String cartId) {
    OperationResult<String> result = cartService.deleteCart(cartId);

    return ResponseEntity.status(result.getMessage().getCode()).body(result);
  }
}

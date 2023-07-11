package com.xcale.ecommerce.repository;

import com.xcale.ecommerce.dbo.CartDBO;
import com.xcale.ecommerce.dbo.CartItemDBO;

import java.util.Optional;

public interface CartRepository {
  String save(CartDBO cart);
  Optional<CartDBO> get(String cartId);
  void delete(String cartId);
  String update(String cartId, CartDBO cartItem);
}

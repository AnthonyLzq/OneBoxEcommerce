package com.xcale.ecommerce.service;

import com.xcale.ecommerce.converter.CartConverter;
import com.xcale.ecommerce.dbo.CartDBO;
import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import com.xcale.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartService {
  private final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
  private final CartRepository cartRepository;
  private final CartConverter cartConverter;

  public String createCart(List<CartItemDTO> items) {
    CartDBO cartDBO = CartDBO.builder().items(cartConverter.convertFromDTOs(items)).build();

    try {
      String cartId = cartRepository.save(cartDBO);
      LOGGER.info("Cart created with id: {}", cartId);

      return cartId;
    } catch (Exception e) {
      LOGGER.error("Error creating cart", e);
      return null;
    }
  }

  public CartDTO getCart(String cartId) {
    try {
      CartDBO cartDBO = cartRepository.get(cartId).orElse(null);

      if (Objects.isNull(cartDBO)) return null;

      CartDTO cartDTO = cartConverter.convert(cartDBO);
      cartDTO.setId(cartId);

      return cartDTO;
    } catch (Exception e) {
      LOGGER.error("Error getting cart", e);
      return null;
    }
  }

  public String updateCart(String cartId, List<CartItemDTO> items) {
    CartDBO cartDBO = CartDBO.builder().items(cartConverter.convertFromDTOs(items)).build();

    try {
      cartRepository.update(cartId, cartDBO);
      LOGGER.info("Cart updated with id: {}", cartId);

      return cartId;
    } catch (Exception e) {
      LOGGER.error("Error updating cart", e);
      return null;
    }
  }

  public String deleteCart(String cartId) {
    try {
      cartRepository.delete(cartId);
      LOGGER.info("Cart deleted with id: {}", cartId);

      return cartId;
    } catch (Exception e) {
      LOGGER.error("Error deleting cart", e);
      return null;
    }
  }
}

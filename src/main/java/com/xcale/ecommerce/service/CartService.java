package com.xcale.ecommerce.service;

import com.xcale.ecommerce.converter.CartConverter;
import com.xcale.ecommerce.dbo.CartDBO;
import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import com.xcale.ecommerce.dto.OperationResult;
import com.xcale.ecommerce.dto.ResultMessage;
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

  public OperationResult<CartDTO> createCart(List<CartItemDTO> items) {
    CartDBO cartDBO = CartDBO.builder().items(cartConverter.convertFromDTOs(items)).build();

    try {
      String cartId = cartRepository.save(cartDBO);
      LOGGER.info("Cart created with id: {}", cartId);

      return OperationResult
        .<CartDTO>builder()
        .data("cart")
        .value(CartDTO.builder().id(cartId).items(items).build())
        .message(ResultMessage.CREATION_SUCCESS)
        .success(true)
        .build();
    } catch (Exception e) {
      return handleError("Error creating cart", e);
    }
  }

  public OperationResult<CartDTO> getCart(String cartId) {
    try {
      CartDBO cartDBO = cartRepository.get(cartId).orElse(null);

      if (Objects.isNull(cartDBO))
        return OperationResult
          .<CartDTO>builder()
          .data("cart")
          .value(null)
          .message(ResultMessage.NOT_FOUND)
          .success(false)
          .build();

      CartDTO cartDTO = cartConverter.convert(cartDBO);
      cartDTO.setId(cartId);

      return OperationResult
        .<CartDTO>builder()
        .data("cart")
        .value(cartDTO)
        .message(ResultMessage.FOUND)
        .success(true)
        .build();
    } catch (Exception e) {
      return handleError("Error getting cart", e);
    }
  }

  public OperationResult<CartDTO> updateCart(String cartId, List<CartItemDTO> items) {
    CartDBO cartDBO = CartDBO.builder().items(cartConverter.convertFromDTOs(items)).build();

    try {
      CartDBO cartFound = cartRepository.update(cartId, cartDBO);

      if (Objects.isNull(cartFound))
        return OperationResult
          .<CartDTO>builder()
          .data("cart")
          .value(null)
          .message(ResultMessage.NOT_FOUND)
          .success(false)
          .build();

      LOGGER.info("Cart updated with id: {}", cartId);

      return OperationResult
        .<CartDTO>builder()
        .data("cart")
        .value(cartConverter.convert(cartFound))
        .message(ResultMessage.UPDATE_SUCCESS)
        .success(true)
        .build();
    } catch (Exception e) {
      return handleError("Error updating cart", e);
    }
  }

  public OperationResult<String> deleteCart(String cartId) {
    try {
      Boolean deleteCartResult = cartRepository.delete(cartId);

      if (!deleteCartResult)
        return OperationResult
          .<String>builder()
          .data("cartId")
          .value(cartId)
          .message(ResultMessage.NOT_REMOVED)
          .success(false)
          .build();

      LOGGER.info("Cart deleted with id: {}", cartId);

      return OperationResult
        .<String>builder()
        .data("cartId")
        .value(cartId)
        .message(ResultMessage.DELETION_SUCCESS)
        .success(true)
        .build();
    } catch (Exception e) {
      return handleError("Error deleting cart", e);
    }
  }

  private <T> OperationResult<T> handleError(String message, Exception e) {
    LOGGER.error(message, e);

    return OperationResult
      .<T>builder()
      .data("cart")
      .data(null)
      .message(ResultMessage.INTERNAL_ERROR)
      .success(false)
      .build();
  }
}

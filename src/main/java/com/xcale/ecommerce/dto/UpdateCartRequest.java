package com.xcale.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCartRequest {
  private String cartId;
  private List<CartItemDTO> items;
}

package com.xcale.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateCartRequest {
  private List<CartItemDTO> items;
}

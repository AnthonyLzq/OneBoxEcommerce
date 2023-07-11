package com.xcale.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class CartDTO {
  private String id;
  @Singular
  private List<CartItemDTO> items;
}

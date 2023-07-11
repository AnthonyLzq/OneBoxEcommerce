package com.xcale.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDTO {
  private String id;
  private List<CartItemDTO> items;
}

package com.xcale.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDTO {
  private Integer id;
  private String description;
  private Integer amount;
}

package com.xcale.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
  private String id;
  private List<CartItemDTO> items;
}

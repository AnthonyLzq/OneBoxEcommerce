package com.xcale.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CartDTO {
  private String id;
  private List<CartItemDTO> items;
}

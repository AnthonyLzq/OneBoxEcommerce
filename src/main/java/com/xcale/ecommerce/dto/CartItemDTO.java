package com.xcale.ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CartItemDTO {
  private Integer id;
  private String description;
  private Integer amount;
}

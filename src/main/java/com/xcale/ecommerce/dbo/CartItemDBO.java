package com.xcale.ecommerce.dbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartItemDBO implements Serializable {
  private Integer id;
  private String description;
  private Integer amount;
}

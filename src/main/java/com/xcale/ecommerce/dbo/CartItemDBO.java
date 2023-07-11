package com.xcale.ecommerce.dbo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class CartItemDBO implements Serializable {
  private Integer id;
  private String description;
  private Integer amount;
}

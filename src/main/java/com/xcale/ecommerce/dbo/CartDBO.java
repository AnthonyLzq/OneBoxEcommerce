package com.xcale.ecommerce.dbo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@RedisHash("Cart")
public class CartDBO implements Serializable {
  private List<CartItemDBO> items;
}

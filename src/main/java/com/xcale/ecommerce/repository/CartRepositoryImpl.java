package com.xcale.ecommerce.repository;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.xcale.ecommerce.dbo.CartDBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class CartRepositoryImpl implements CartRepository {
  private RedisTemplate<String, CartDBO> redisTemplate;
  private ValueOperations<String, CartDBO> valueOperations;
  private Integer ttl;

  @Autowired
  public CartRepositoryImpl(
    RedisTemplate<String, CartDBO> redisTemplate,
    @Value("${spring.redis.ttl}") Integer ttl
  ) {
    this.redisTemplate = redisTemplate;
    this.valueOperations = redisTemplate.opsForValue();
    this.ttl = ttl;
  }

  @Override
  public String save(CartDBO cartDBO) {
    String cartId = NanoIdUtils.randomNanoId();
    valueOperations.set(cartId, cartDBO);
    redisTemplate.expire(cartId, ttl, TimeUnit.MINUTES);

    return cartId;
  }

  @Override
  public Optional<CartDBO> get(String cartId) {
    CartDBO cartDBO = valueOperations.get(cartId);
    redisTemplate.expire(cartId, ttl, TimeUnit.MINUTES);

    return Optional.ofNullable(cartDBO);
  }

  @Override
  public void delete(String cartId) {
    redisTemplate.delete(cartId);
  }

  @Override
  public String update(String cartId, CartDBO cartDBO) {
    Optional<CartDBO> optionalCart = get(cartId);

    if (optionalCart.isEmpty()) return null;

    CartDBO cartFound = optionalCart.get();
    cartDBO.getItems().addAll(cartFound.getItems());
    valueOperations.set(cartId, cartDBO);
    redisTemplate.expire(cartId, ttl, TimeUnit.MINUTES);

    return cartId;
  }
}

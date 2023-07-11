package com.xcale.ecommerce.converter;


import com.xcale.ecommerce.dbo.CartDBO;
import com.xcale.ecommerce.dbo.CartItemDBO;
import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartConverter {
  CartItemDTO convert(CartItemDBO cartItemDBO);
  CartItemDBO convert(CartItemDTO cartItemDTO);
  CartDBO convert(CartDTO cartDTO);
  CartDTO convert(CartDBO cartDBO);
  List<CartItemDBO> convertFromDTOs(List<CartItemDTO> cartDTOs);
  List<CartItemDTO> convertFromDBOs(List<CartItemDBO> cartDBOs);
}

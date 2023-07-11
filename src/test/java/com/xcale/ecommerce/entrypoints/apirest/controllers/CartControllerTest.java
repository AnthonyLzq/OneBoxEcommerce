package com.xcale.ecommerce.entrypoints.apirest.controllers;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.xcale.ecommerce.controller.CartController;
import com.xcale.ecommerce.dto.CartDTO;
import com.xcale.ecommerce.dto.CartItemDTO;
import com.xcale.ecommerce.dto.CreateCartRequest;
import com.xcale.ecommerce.dto.OperationResult;
import com.xcale.ecommerce.dto.ResultMessage;
import com.xcale.ecommerce.dto.UpdateCartRequest;
import com.xcale.ecommerce.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {
  private MockMvc mockMvc;

  @Mock
  private CartService cartService;

  @InjectMocks
  private CartController cartController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
  }

  @Test
  void shouldCreateCart() throws Exception {
    // given
    Random random = new Random();
    List<CartItemDTO> items = Collections.singletonList(
      CartItemDTO
        .builder()
        .id(random.nextInt())
        .description("Product description")
        .amount(random.nextInt())
        .build()
    );
    CreateCartRequest createCartRequest = CreateCartRequest.builder().items(items).build();
    String id = NanoIdUtils.randomNanoId();

    CartDTO cartDTO = CartDTO.builder().id(id).items(items).build();
    OperationResult<CartDTO> result = OperationResult
      .<CartDTO>builder()
      .data("cart")
      .value(cartDTO)
      .message(ResultMessage.CREATION_SUCCESS)
      .success(true)
      .build();

    // when
    when(cartService.createCart(items)).thenReturn(result);
    String body = asJsonString(createCartRequest);

    // then
    mockMvc.perform(post("/api/cart").content(body).contentType("application/json"))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value(ResultMessage.CREATION_SUCCESS.name()))
      .andExpect(jsonPath("$.value.id").value(id))
      .andExpect(jsonPath("$.value.items[0]").value(items.get(0)));
  }

  @Test
  void shouldGetCart() throws Exception {
    // given
    Random random = new Random();
    List<CartItemDTO> items = Collections.singletonList(
      CartItemDTO
        .builder()
        .id(random.nextInt())
        .description("Product description")
        .amount(random.nextInt())
        .build()
    );
    String id = NanoIdUtils.randomNanoId();

    CartDTO cartDTO = CartDTO.builder().id(id).items(items).build();
    OperationResult<CartDTO> result = OperationResult
      .<CartDTO>builder()
      .data("cart")
      .value(cartDTO)
      .message(ResultMessage.FOUND)
      .success(true)
      .build();

    // when
    when(cartService.getCart(id)).thenReturn(result);

    // then
    mockMvc.perform(get("/api/cart/" + id))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value(ResultMessage.FOUND.name()))
      .andExpect(jsonPath("$.value.id").value(id))
      .andExpect(jsonPath("$.value.items[0]").value(items.get(0)));
  }

  @Test
  void shouldUpdateCart() throws Exception {
    // given
    Random random = new Random();
    List<CartItemDTO> items = Arrays.asList(
      CartItemDTO.builder().id(random.nextInt()).description("Product description 1").amount(random.nextInt()).build(),
      CartItemDTO.builder().id(random.nextInt()).description("Product description 2").amount(random.nextInt()).build()
    );
    String id = NanoIdUtils.randomNanoId();
    UpdateCartRequest updateCartRequest = UpdateCartRequest.builder().cartId(id).items(items).build();

    CartDTO cartDTO = CartDTO.builder().id(id).items(items).build();
    OperationResult<CartDTO> result = OperationResult
      .<CartDTO>builder()
      .data("cart")
      .value(cartDTO)
      .message(ResultMessage.UPDATE_SUCCESS)
      .success(true)
      .build();

    // when
    when(cartService.updateCart(id, items)).thenReturn(result);
    String body = asJsonString(updateCartRequest);

    // then
    mockMvc.perform(patch("/api/cart").content(body).contentType("application/json"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.success").value(true))
      .andExpect(jsonPath("$.message").value(ResultMessage.UPDATE_SUCCESS.name()))
      .andExpect(jsonPath("$.value.id").value(id))
      .andExpect(jsonPath("$.value.items[0]").value(items.get(0)))
      .andExpect(jsonPath("$.value.items[1]").value(items.get(1)));
  }

  private static String asJsonString(final Object obj) {
    return new Gson().toJson(obj);
  }
}

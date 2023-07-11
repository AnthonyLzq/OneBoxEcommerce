package com.xcale.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationResult<T> {
  private String data;
  private T value;
  private ResultMessage message;
  private Boolean success;
}

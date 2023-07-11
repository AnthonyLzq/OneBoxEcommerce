package com.xcale.ecommerce.dto;

import lombok.Getter;

public enum ResultMessage {
  FOUND(200),
  CREATION_SUCCESS(201),
  UPDATE_SUCCESS(200),
  DELETION_SUCCESS(200),
  INTERNAL_ERROR(500),
  NOT_FOUND(404),
  NOT_REMOVED(409);

  @Getter
  private final int code;

  ResultMessage(int i) {
    this.code = i;
  }
}

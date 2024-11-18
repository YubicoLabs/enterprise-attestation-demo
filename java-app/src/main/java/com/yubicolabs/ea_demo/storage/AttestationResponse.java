package com.yubicolabs.ea_demo.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@AllArgsConstructor
@Value
public class AttestationResponse {
  String serialNumber;
}

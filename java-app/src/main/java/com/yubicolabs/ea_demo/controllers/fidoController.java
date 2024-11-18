package com.yubicolabs.ea_demo.controllers;

import com.yubicolabs.ea_demo.services.fidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class fidoController {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/v1/attestation/options",
      produces = {"application/json"})
  public ResponseEntity attestationOptions() {

    try {
      fidoService fs = new fidoService();

      return ResponseEntity.status(HttpStatus.OK).body(fs.attestationOptions());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/v1/attestation/result",
      produces = {"application/json"})
  public ResponseEntity attestationResult(@RequestBody String requestBody) {
    try {
      fidoService fs = new fidoService();
      String response = fs.attestationResult(requestBody);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (NullPointerException e) {
      // EA not found
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    } catch (Exception e) {
      // Generic error
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}

package com.yubicolabs.ea_demo.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.util.X509CertUtils;
import com.yubico.webauthn.RelyingParty;
import com.yubico.webauthn.StartRegistrationOptions;
import com.yubico.webauthn.data.AttestationConveyancePreference;
import com.yubico.webauthn.data.AttestationObject;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.AuthenticatorSelectionCriteria;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;
import com.yubico.webauthn.data.PublicKeyCredentialCreationOptions;
import com.yubico.webauthn.data.RelyingPartyIdentity;
import com.yubico.webauthn.data.ResidentKeyRequirement;
import com.yubico.webauthn.data.UserIdentity;
import com.yubico.webauthn.data.UserVerificationRequirement;
import com.yubicolabs.ea_demo.storage.AttestationResponse;
import com.yubicolabs.ea_demo.storage.CredentialRegistrationStorage;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

public class fidoService {

  private SecureRandom random = new SecureRandom();

  ObjectMapper mapper = new ObjectMapper();
  CredentialRegistrationStorage credentialRegistrationStorage;
  RelyingParty rp;

  public fidoService() {
    this.credentialRegistrationStorage = new CredentialRegistrationStorage();

    Set<String> origins = new HashSet<String>();
    origins.add("http://localhost:3000");
    this.rp =
        RelyingParty.builder()
            .identity(
                RelyingPartyIdentity.builder().id("localhost").name("My app").build())
            .credentialRepository(credentialRegistrationStorage)
            .origins(origins)
            .attestationConveyancePreference(AttestationConveyancePreference.ENTERPRISE)
            .build();
  }

  public String attestationOptions() throws Exception {

    /*
     * Create a generic user for the example
     */
    UserIdentity userIdentity =
        UserIdentity.builder()
            .name("Default User")
            .displayName("Default User")
            .id(generateRandom(32))
            .build();

    /*
     * Create PublicKeyCredentialCreationOptions Note, the attestation preference was set in the RP
     * configs
     */
    PublicKeyCredentialCreationOptions assertionOptions =
        this.rp.startRegistration(
            StartRegistrationOptions.builder()
                .user(userIdentity)
                .timeout(180000)
                .authenticatorSelection(
                    AuthenticatorSelectionCriteria.builder()
                        .residentKey(ResidentKeyRequirement.DISCOURAGED)
                        .userVerification(UserVerificationRequirement.DISCOURAGED)
                        .build())
                .build());

    /*
     * Return the JSON object to the client
     */
    return assertionOptions.toCredentialsCreateJson();
  }

  /*
   * Request structure { type: "public-key", id: "base64url credential ID", clientExtensionResults:
   * {}, response: { clientDataJSON: "base64url string", attestationObject: "CBOR encoded object" }
   * }
   */
  public String attestationResult(String request) throws Exception {
    /*
     * Parse the JSON request
     */
    PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> parsedRequest =
        PublicKeyCredential.parseRegistrationResponseJson(request);

    /*
     * Read the attestationObject
     */
    AttestationObject attestationObject =
        new AttestationObject(parsedRequest.getResponse().getAttestationObject());

    /*
     * Parse the attestation statement for the x5c cert
     */
    ObjectNode attestationStatement = attestationObject.getAttestationStatement();
    JsonNode x5cNode = attestationStatement.get("x5c");
    // Encode the x5c value to a String array (this is how the entry is represented)
    String[] x5cArray = mapper.readValue(x5cNode.toString(), String[].class);

    // Assume the first entry (it's the only entry)
    String firstX5c = x5cArray[0];
    byte[] bytes = ByteArray.fromBase64(firstX5c).getBytes();
    X509Certificate cert = X509CertUtils.parse(bytes);

    System.out.println("\nPrinting entire cert for analysis");
    System.out.println(cert.toString());

    /*
     * Options for reading the serial number:
     * 1) Read and parse the serial number from the cert subject name
     * 2) Read the serial number from the cert extension (1.3.6.1.4.1.45724.1.1.2)
     * 3) Optional, read both and compare to ensure that they're similar
     */

    // 1) Read serial from cert subject name
    String subjectName = cert.getSubjectX500Principal().getName();
    System.out.println("\nValue from cert subject name:");
    System.out.println(subjectName);

    // 2) Read the serial from the cert extension
    byte[] extensionValuBytes = cert.getExtensionValue("1.3.6.1.4.1.45724.1.1.2");
    // Convert extension value to int
    if (extensionValuBytes == null) {
      String errorMessage = "Serial number extension not present";
      System.out.println("\n" + errorMessage + "\nEnterprise Attestation not found");
      throw new NullPointerException(errorMessage);
    }
    int extensionValueInts = new BigInteger(1, extensionValuBytes).intValue();
    String serialNumber = String.valueOf(extensionValueInts);
    System.out.println("\nValue from cert extension value:");
    System.out.println(serialNumber);

    // 3) Compare both values to see if they match
    boolean doesEqual = subjectName.contains(serialNumber);
    System.out.println("\nDo serial numbers match: " + doesEqual);

    AttestationResponse response = new AttestationResponse(serialNumber);
    return mapper.writeValueAsString(response);
  }

  private ByteArray generateRandom(int length) {
    byte[] bytes = new byte[length];
    random.nextBytes(bytes);
    return new ByteArray(bytes);
  }
}

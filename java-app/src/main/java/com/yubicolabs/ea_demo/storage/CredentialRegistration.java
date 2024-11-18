package com.yubicolabs.ea_demo.storage;

import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.UserIdentity;
import java.util.Optional;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CredentialRegistration {

  UserIdentity userIdentity;
  Optional<String> credentialNickname;
  RegisteredCredential credential;
  long signatureCount;

  public void printItem() {
    System.out.println(
        "TsvItem:"
            + "\n\tuserIdentity.name: "
            + this.userIdentity.getName()
            + "\n\tuserIdentity.id: "
            + this.userIdentity.getId().getBase64()
            + "\n\tcredential_nickname: "
            + this.credentialNickname.get()
            + "\n\tcredential_id: "
            + this.credential.getCredentialId());
  }
}

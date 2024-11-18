package com.yubicolabs.ea_demo.storage;

// Copyright (c) 2018, Yubico AB
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this
// list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

import com.yubico.webauthn.CredentialRepository;
import com.yubico.webauthn.RegisteredCredential;
import com.yubico.webauthn.data.ByteArray;
import com.yubico.webauthn.data.PublicKeyCredentialDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CredentialRegistrationStorage implements CredentialRepository {

  Collection<CredentialRegistration> items = new HashSet<CredentialRegistration>();

  public boolean insert(CredentialRegistration newItem) {
    return items.add(newItem);
  }

  @Override
  public Set<PublicKeyCredentialDescriptor> getCredentialIdsForUsername(String username) {
    return items.stream()
        .filter(item -> item.getUserIdentity().getName().equals(username))
        .map(
            reg ->
                PublicKeyCredentialDescriptor.builder()
                    .id(reg.getCredential().getCredentialId())
                    .build())
        .collect(Collectors.toSet());
  }

  @Override
  public Optional<ByteArray> getUserHandleForUsername(String username) {
    return Optional.ofNullable(
        items.stream()
            .filter(item -> item.getUserIdentity().getName().equals(username))
            .findFirst()
            .get()
            .getUserIdentity()
            .getId());
  }

  public Optional<CredentialRegistration> getIfPresentByCredentialId(String credentialId) {
    return items.stream()
        .filter(item -> item.getCredential().getCredentialId().getBase64Url().equals(credentialId))
        .findFirst();
  }

  @Override
  public Optional<String> getUsernameForUserHandle(ByteArray userHandle) {
    return Optional.ofNullable(
        items.stream()
            .filter(
                item ->
                    item.getUserIdentity().getId().getBase64Url().equals(userHandle.getBase64Url()))
            .findFirst()
            .get()
            .getUserIdentity()
            .getName());
  }

  @Override
  public Optional<RegisteredCredential> lookup(ByteArray credentialId, ByteArray userHandle) {
    Optional<CredentialRegistration> regMaybe =
        items.stream()
            .filter(credReg -> credentialId.equals(credReg.getCredential().getCredentialId()))
            .findFirst();

    return regMaybe.flatMap(
        registration ->
            Optional.of(
                RegisteredCredential.builder()
                    .credentialId(registration.getCredential().getCredentialId())
                    .userHandle(registration.getUserIdentity().getId())
                    .publicKeyCose(registration.getCredential().getPublicKeyCose())
                    .signatureCount(registration.getSignatureCount())
                    .build()));
  }

  public Set<RegisteredCredential> lookupAll(ByteArray credentialId) {
    return null;
  }
}

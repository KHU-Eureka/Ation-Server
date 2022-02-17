package com.eureka.ationserver.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
  kakao("kakao"),
  google("google");

  private final String value;
}

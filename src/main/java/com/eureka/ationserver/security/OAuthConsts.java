package com.eureka.ationserver.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OAuthConsts {

  @Value("${eureka.app.oauth.redirectURL}")
  private String redirectUrl;

}

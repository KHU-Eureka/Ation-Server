package com.eureka.ationserver.config.security;

import com.eureka.ationserver.config.security.jwt.JwtAuthProvider;
import com.eureka.ationserver.security.OAuthConsts;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtAuthProvider jwtAuthProvider;

  private final OAuthConsts oAuthConsts;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String token = jwtAuthProvider.generateJwtToken(authentication);

    String targetUrl = UriComponentsBuilder.fromUriString(oAuthConsts.getRedirectUrl())
        .queryParam("token", token).build().toUriString();
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
    this.clearAuthenticationAttributes(request);
  }

}


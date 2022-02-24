package com.eureka.ationserver.security;

import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  @SneakyThrows
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
    OAuthUserInfo oAuthUserInfo = OAuthUserInfo.of(
        oAuth2UserRequest.getClientRegistration().getRegistrationId(),
        oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
            .getUserNameAttributeName(),
        oAuth2User.getAttributes()
    );

    User user = this.saveOrUpdate(oAuthUserInfo);

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }


  private User saveOrUpdate(OAuthUserInfo oAuthUserInfo) {

    User user = userRepository.findByIdentifyId(oAuthUserInfo.getIdentifyId())
        .map(entity -> entity.update(oAuthUserInfo.getName(), oAuthUserInfo.getEmail(),
            oAuthUserInfo.getPicture()))
        .orElse(oAuthUserInfo.toEntity());

    return userRepository.save(user);
  }
}

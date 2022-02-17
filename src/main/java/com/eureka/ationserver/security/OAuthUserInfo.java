package com.eureka.ationserver.security;

import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUserInfo {

  private Map<String, Object> attributes;
  private String id;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String picture;
  private OAuthProvider provider;
  private String identifyId;

  public static OAuthUserInfo of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {

    // kakao
    if (OAuthProvider.kakao.getValue().equals(registrationId)) {
      return ofKakao(userNameAttributeName, attributes, OAuthProvider.kakao);
    }
    // google
    return ofGoogle(userNameAttributeName, attributes, OAuthProvider.google);
  }

  private static OAuthUserInfo ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes, OAuthProvider oAuthProvider) {

    return OAuthUserInfo.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .provider(oAuthProvider)
        .id((String) attributes.get(userNameAttributeName))
        .identifyId(String.format("%s_%s", (String) attributes.get(userNameAttributeName),
            oAuthProvider.getValue()))
        .build();
  }

  private static OAuthUserInfo ofKakao(String userNameAttributeName,
      Map<String, Object> attributes, OAuthProvider oAuthProvider) {
    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

    OAuthUserInfo oAuthUserInfo = OAuthUserInfo.builder()
        .name((String) kakaoProfile.get("nickname"))
        .picture((String) kakaoProfile.get("profile_image_url"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .provider(oAuthProvider)
        .id(String.valueOf(attributes.get(userNameAttributeName)))
        .identifyId(String.format("%s_%s", String.valueOf(attributes.get(userNameAttributeName)),
            oAuthProvider.getValue()))
        .build();

    try {
      oAuthUserInfo.setEmail((String) kakaoAccount.get("email"));
    } catch (Exception e) {
      oAuthUserInfo.setEmail(
          String.format("%s@%s", oAuthUserInfo.getId(), oAuthUserInfo.getProvider()));
    }
    return oAuthUserInfo;
  }


  public User toEntity() {
    return User.builder()
        .name(name)
        .email(email)
        .profileImg(picture)
        .provider(provider)
        .identifyId(identifyId)
        .myPageImgPath(ImageUtil.getDefaultImagePath(User.MYPAGE_PREFIX))
        .build();
  }
}
package com.eureka.ationserver.security;

import com.eureka.ationserver.model.user.User;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class UserPrincipal implements OAuth2User, UserDetails {

  private Long id;
  private String username;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  public UserPrincipal(Long id, String identifyId, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = identifyId;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = Collections.singletonList(
        new SimpleGrantedAuthority("ROLE_USER"));

    return new UserPrincipal(
        user.getId(),
        user.getIdentifyId(),
        user.getPassword(),
        authorities
    );
  }

  public static UserPrincipal create(User user, Map<String, Object> attributes) {
    UserPrincipal userPrincipal = UserPrincipal.create(user);
    userPrincipal.setAttributes(attributes);
    return userPrincipal;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserPrincipal user = (UserPrincipal) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
}

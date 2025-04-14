package com.business.userservice.infrastructure.auth;

import com.business.userservice.domain.user.entity.UserEntity;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username;
    private final String role;
    private final Boolean isBlacklisted;
    private final String password;

    // JWT 인증용
    public UserDetailsImpl(Long id, String username, String role, Boolean isBlacklisted) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.isBlacklisted = isBlacklisted;
        this.password = null;
    }

    // DB 인증용
    public UserDetailsImpl(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole().toString();
        this.isBlacklisted = user.getIsBlacklisted();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return !isBlacklisted;
    }

}

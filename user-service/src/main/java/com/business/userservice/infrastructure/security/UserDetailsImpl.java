package com.business.userservice.infrastructure.security;

import com.business.userservice.domain.user.entity.UserEntity;
import com.business.userservice.domain.user.vo.RoleType;
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
    private final RoleType role;
    private final Boolean isBlacklisted;
    private final String password;
    private final String slackId;
    private final boolean isDeleted;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
        this.isBlacklisted = user.getIsBlacklisted();
        this.password = user.getPassword();
        this.slackId = user.getSlackId();
        this.isDeleted = user.getDeletedAt() != null;
        this.authorities = generateAuthorities(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return !isBlacklisted && !isDeleted;
    }

    private Collection<GrantedAuthority> generateAuthorities(RoleType role) {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }
}

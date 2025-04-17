package com.business.userservice.domain.user.entity;

import com.business.userservice.domain.user.vo.RoleType;
import com.github.themepark.common.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "p_users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String slackId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(name = "is_blacklisted")
    private Boolean isBlacklisted = Boolean.FALSE;

    @Builder(access = AccessLevel.PRIVATE)
    private UserEntity(
        String username,
        String password,
        String slackId,
        RoleType role
    ) {
        this.username = username;
        this.password = password;
        this.slackId = slackId;
        this.role = role;
    }

    public static UserEntity of(
        String username,
        String password,
        String slackId,
        RoleType role
    ) {
        return UserEntity.builder()
            .username(username)
            .password(password)
            .slackId(slackId)
            .role(role)
            .build();
    }

    public void update(String username, String password, String slackId) {
        if (username != null) this.username = username;
        if (password != null) this.password = password;
        if (slackId != null) this.slackId = slackId;
    }

    public boolean isPasswordMatch(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.password);
    }

    public boolean blacklist() {
        isBlacklisted = !isBlacklisted;
        return isBlacklisted;
    }
}

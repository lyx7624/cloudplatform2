package com.zcyk.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zcyk.entity.Permission;
import com.zcyk.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 功能描述: 登录用户
 * 开发人员: xlyx
 * 创建日期: 2020-4-15 10:05
 */
@Data
@Accessors(chain = true)
public class LoginUser extends User implements UserDetails {
    private String company_id;

    private static final long serialVersionUID = -1379274258881257107L;

    private List<Permission> permissions;

    private String token;
    /** 登陆时间戳（毫秒） */
    private Long loginTime;
    /** 过期时间戳 */
    private Long expireTime;

    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账户是否未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
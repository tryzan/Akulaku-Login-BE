package com.akulaku.config;

import com.akulaku.entities.User;
import com.akulaku.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ApplicationUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public ApplicationUserDetails(User user, Set<Role> roles) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        roles.forEach(role ->{
            this.authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return true;
    }
}

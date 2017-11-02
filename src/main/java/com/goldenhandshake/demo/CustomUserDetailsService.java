/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldenhandshake.demo;

import com.goldenhandshake.demo.entities.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author binod
 */
public class CustomUserDetailsService implements  UserDetails{
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetailsService() {
    }
    
    public CustomUserDetailsService(User user) {
        this.username=user.getUsername();
        this.password=user.getPassword();
        List<GrantedAuthority> auths=new ArrayList<>();
        user.getRoles().forEach((role) -> {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        });
       this.authorities=auths;
    }
    
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return this.authorities;
    }

    @Override
    public String getPassword() {
return this.password;    }

    @Override
    public String getUsername() {
return this.username;    }

    @Override
    public boolean isAccountNonExpired() {
       return true;
       
    }

    @Override
    public boolean isAccountNonLocked() {

return true;    }

    @Override
    public boolean isCredentialsNonExpired() {
return true;
    }

    @Override
    public boolean isEnabled() {
return true;
    }
    
}

package com.cms.config.jwt;

import com.cms.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
//
//    private Long id;
//
//    private String userName;
//
//    private String email;
//
//    @JsonIgnore
//    private String password;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public UserDetailsImpl(Long id, String userName, String email, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.userName = userName;
//        this.password = password;
//        this.email = email;
//        this.authorities = authorities;
//    }
//
//    public static UserDetailsImpl build (User user){
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//                .collect(Collectors.toList());
//        return new UserDetailsImpl(
//                user.getId(),
//                user.getUserName(),
//                user.getPassword(),
//                user.getEmail(),
//                authorities
//        );
//    }
//
    User user;
    public UserDetailsImpl(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
//                List<GrantedAuthority> authorities = new SimpleGrantedAuthority();

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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

//    @Override
//    public boolean equals(Object o){
//        if(this == o){
//            return true;
//        }
//        if(o == null || getClass() != o.getClass()){
//            return false;
//        }
//        UserDetailsImpl user = (UserDetailsImpl) o;
//        return Objects.equals(id, user.id);
//    }
//
    public String getEmail() {return user.getEmail();}
}

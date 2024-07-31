package org.githubwuzupkev.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.githubwuzupkev.models.auth.UserEntity;
import org.githubwuzupkev.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailImp implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(username+" not found" ));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        user.getRoles().stream().flatMap(roleEntity -> roleEntity.getPermissionList().stream())
                .forEach(permissionEntity -> authorities.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return new User(user.getUsername(), user.getPassword(),user.isEnabled()
                ,user.isAccountNonExpired(),user.isCredentialsNonExpired(),
                user.isAccountNonLocked() ,authorities);
    }
}

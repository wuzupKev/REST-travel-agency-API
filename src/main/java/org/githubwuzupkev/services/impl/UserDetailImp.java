package org.githubwuzupkev.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.githubwuzupkev.models.auth.RoleEntity;
import org.githubwuzupkev.models.auth.UserEntity;
import org.githubwuzupkev.models.entities.EmployeeEntity;
import org.githubwuzupkev.models.requests.EmployeeRequest;
import org.githubwuzupkev.models.requests.auth.UserLoginRequest;
import org.githubwuzupkev.models.responses.EmployeeResponse;
import org.githubwuzupkev.models.responses.auth.UserResponse;
import org.githubwuzupkev.repositories.EmployeeRepository;
import org.githubwuzupkev.repositories.RoleRepository;
import org.githubwuzupkev.repositories.UserRepository;
import org.githubwuzupkev.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailImp implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;
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

    public UserResponse createUser(EmployeeRequest employeeRequest) throws Exception {
        String username = employeeRequest.email();
        String password = employeeRequest.password();
        List<String> rolesRequest = employeeRequest.roles();

        Set<RoleEntity> roleEntityList = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest)
                .stream().collect(Collectors.toSet());

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity user= UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isAccountNonLocked(true)
                .roles(roleEntityList)
                .build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName(employeeRequest.firstName())
                .lastName(employeeRequest.lastName())
                .homeAddress(employeeRequest.homeAddress())
                .identityCardNumber(employeeRequest.identityCardNumber())
                .dateBirth(employeeRequest.dateBirth())
                .phoneNumber(employeeRequest.phoneNumber())
                .professionalPosition(employeeRequest.professionalPosition())
                .salary(employeeRequest.salary())
                .credential(user)

                .build();

        EmployeeEntity userSaved = employeeRepository.save(employeeEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getCredential().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userSaved.getCredential().getRoles().stream().flatMap(role -> role.getPermissionList().stream()).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.generateToken(authentication);

       UserResponse authResponse = new UserResponse(username, "User created successfully", accessToken, true);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public UserResponse loginUser(UserLoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateToken(authentication);
        UserResponse authResponse = new UserResponse(username, "User loged succesfully", accessToken, true);
        return authResponse;
    }

}

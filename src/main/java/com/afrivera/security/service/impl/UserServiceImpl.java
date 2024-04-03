package com.afrivera.security.service.impl;

import com.afrivera.security.controller.dto.AuthLogin;
import com.afrivera.security.controller.dto.AuthResponse;
import com.afrivera.security.controller.dto.RegisterUser;
import com.afrivera.security.persistence.entity.RoleEntity;
import com.afrivera.security.persistence.entity.UserEntity;
import com.afrivera.security.persistence.repository.RoleRepository;
import com.afrivera.security.persistence.repository.UserRepository;
import com.afrivera.security.service.IUserService;
import com.afrivera.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username: " + username + " doesn't exist!"));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions()
                        .stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(username, userEntity.getPassword(), userEntity.isEnabled(), userEntity.isAccountNoExpired(), userEntity.isCredentialsNoExpired(), userEntity.isAccountNoLocked(), authorities);
    }

    @Override
    public void saveInitUsers(List<UserEntity> users) {
        userRepository.saveAll(users);
    }

    @Override
    public AuthResponse login(AuthLogin authLogin) {
        Authentication authentication = authentication(authLogin.getUsername(), authLogin.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateToken(authentication);

        return new AuthResponse(authLogin.getUsername(), "User Logged!", accessToken, true);
    }

    @Override
    public AuthResponse register(RegisterUser registerUser) {

        Set<RoleEntity> roleEntities = roleRepository.findRoleEntitiesByRoleEnumIn(registerUser.getRoles().getRoles())
                .stream().collect(Collectors.toSet());
        if (roleEntities.isEmpty()) {
            throw new IllegalArgumentException("the roles specified doesn't exist");
        }
        UserEntity userEntity = UserEntity.
                builder()
                .username(registerUser.getUsername())
                .password(passwordEncoder.encode(registerUser.getPassword()))
                .roles(roleEntities)
                .isEnabled(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialsNoExpired(true)
                .build();
        UserEntity userSave = userRepository.save(userEntity);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userSave.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())));
        userSave.getRoles().stream().flatMap(role -> role.getPermissions().stream()).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSave.getUsername(), null, authorities);
        String accessToken = jwtUtils.generateToken(authentication);

        return new AuthResponse(userSave.getUsername(), "user created successfully", accessToken, true);
    }

    public Authentication authentication(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

    }

}

package com.afrivera.security.service;

import com.afrivera.security.controller.dto.AuthLogin;
import com.afrivera.security.controller.dto.AuthResponse;
import com.afrivera.security.controller.dto.RegisterUser;
import com.afrivera.security.persistence.entity.UserEntity;
import com.afrivera.security.persistence.repository.UserRepository;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface IUserService {
    void saveInitUsers(List<UserEntity> users);

    AuthResponse login(AuthLogin authLogin);

    AuthResponse register(RegisterUser registerUser);
}

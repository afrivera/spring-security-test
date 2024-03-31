package com.afrivera.security.service;

import com.afrivera.security.persistence.entity.UserEntity;
import com.afrivera.security.persistence.repository.UserRepository;

import java.util.List;

public interface IUserService {
    void saveInitUsers(List<UserEntity> users);
}

package com.afrivera.security.service.impl;

import com.afrivera.security.persistence.entity.UserEntity;
import com.afrivera.security.persistence.repository.UserRepository;
import com.afrivera.security.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public void saveInitUsers(List<UserEntity> users){
        userRepository.saveAll(users);
    }

}

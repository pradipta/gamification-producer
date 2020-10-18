package com.pradipta.gamificationproducer.models.user;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

//@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
     public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
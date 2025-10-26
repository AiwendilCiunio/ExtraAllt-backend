package com.stocksimulator.stocksimulator.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Just for MVP, to be removed after implementing login
    public Optional<User> getDemoUser() {
        return userRepository.findByUsername("demo");
    }
}

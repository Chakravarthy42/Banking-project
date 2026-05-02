package com.bank.user_service.service;



import com.bank.user_service.entity.User;
import com.bank.user_service.repository.UserRepository;
import com.bank.user_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    public User register(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ set default role
        user.setRole("USER");
        user.setRole("ADMIN");
        
        return userRepository.save(user);
    }

    // LOGIN (ONLY ONE METHOD ✅)
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // generate JWT token
        return jwtUtil.generateToken(email, user.getRole());
    }
}
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

    // ✅ REGISTER
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // ✅ set only ONE role
        user.setRole("USER");

        return userRepository.save(user);
    }

    // ✅ LOGIN
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ CORRECT PASSWORD CHECK
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(email, user.getRole());
    }
}

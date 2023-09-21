package com.example.app.seed;

import com.example.app.model.Profile;
import com.example.app.model.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * For testing purpose, this class injects data into our application.
 */
@Component
public class SeedData implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Autowired
    public SeedData(@Lazy PasswordEncoder passwordEncoder, //loads on-demand
                    UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Everytime the application starts, this method will be invoked
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmailAddress("gabrielleynara@ymail.com");
        user.setPassword(passwordEncoder.encode("gaby1234"));
        user.setProfile(new Profile());
        userRepository.save(user);
    }

}

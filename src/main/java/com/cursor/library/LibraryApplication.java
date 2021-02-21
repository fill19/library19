package com.cursor.library;

import com.cursor.library.models.User;
import com.cursor.library.models.enums.UserPermissions;
import com.cursor.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
public class LibraryApplication {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public LibraryApplication(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @PostConstruct
    public void addUsers() {
        User user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .permissions(Set.of(UserPermissions.ROLE_READ))
                .build();
        userRepository.save(user);

        User admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .permissions(Set.of(UserPermissions.ROLE_READ, UserPermissions.ROLE_DELETE, UserPermissions.ROLE_ADD))
                .build();
        userRepository.save(admin);
    }
}

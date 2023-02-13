package org.dyson.chat;

import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import org.dyson.chat.entities.Authority;
import org.dyson.chat.entities.User;
import org.dyson.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var user = new User(
                "root",
                passwordEncoder.encode("root"),
                Set.of(new Authority("ROLE_ROOT"))
        );
        userService.createUser(user);
    }
}

package org.dyson.chat;

import org.dyson.chat.entities.Authority;
import org.dyson.chat.entities.User;
import org.dyson.chat.repositories.MessageRepository;
import org.dyson.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class ChatApplication implements CommandLineRunner {
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var user = new User(
                "root",
                new BCryptPasswordEncoder().encode("root"),
                Set.of(new Authority("ROLE_ROOT"))
        );
        userService.createUser(user);
    }
}

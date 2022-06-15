package ru.tigran.authorizationservice.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.tigran.authorizationservice.database.entity.Role;
import ru.tigran.authorizationservice.database.entity.User;
import ru.tigran.authorizationservice.database.repository.RoleRepository;
import ru.tigran.authorizationservice.database.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class InitDatabase {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder encoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(new ArrayList<>() {{
                add(Role.of("USER"));
                add(Role.of("ADMIN"));
            }});
        }
        if (userRepository.count() == 0) {
            userRepository.saveAll(new ArrayList<>() {{
                add(User.of("admin", encoder.encode("password"), new HashSet<>(roleRepository.findAll())));
                add(User.of("user", encoder.encode("password"), roleRepository.findByName("USER")));
            }});
        }
    }
}

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
import java.util.Collections;
import java.util.HashSet;

@Component
public class InitDatabase {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(new ArrayList<>(){{
                add(Role.of(1, "ROLE_USER"));
                add(Role.of(2, "ROLE_ADMIN"));
            }});
        }
        if (userRepository.count() == 0) {
            userRepository.saveAll(new ArrayList<>(){{
                    add(User.of("admin", bCryptPasswordEncoder.encode("password"),
                            new HashSet<>(roleRepository.findAll())));
                    add(User.of("user", bCryptPasswordEncoder.encode("password"),
                            new HashSet<>(Collections.singleton(roleRepository.findById(1).get()))));
            }});
        }
    }
}

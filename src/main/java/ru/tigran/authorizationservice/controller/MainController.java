package ru.tigran.authorizationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.authorizationservice.database.entity.Role;
import ru.tigran.authorizationservice.database.entity.User;
import ru.tigran.authorizationservice.database.repository.RoleRepository;
import ru.tigran.authorizationservice.database.repository.UserRepository;
import ru.tigran.authorizationservice.security.JwtClaims;
import ru.tigran.authorizationservice.security.JwtUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping("/login")
    public ResponseEntity<String> login(String username, String password) throws JsonProcessingException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            return new ResponseEntity<>("Username not found!", HttpStatus.FORBIDDEN);

        if (!passwordEncoder.matches(password, user.getPassword()))
            return new ResponseEntity<>("Incorrect password!", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    @RequestMapping("/refresh")
    public ResponseEntity<String> refresh(String jrt) throws JsonProcessingException {
        User user = userRepository.findByRefreshToken(jrt);
        if (user == null)
            return new ResponseEntity<>("Refresh token not found!", HttpStatus.FORBIDDEN);

        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    @RequestMapping("/validate")
    public ResponseEntity<String> validate(String username, String jwt) throws JsonProcessingException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            return new ResponseEntity<>("User not found!", HttpStatus.FORBIDDEN);

        try{
            jwtUtil.TryGetClaims(jwt, user.getSecret());
            return new ResponseEntity<>("True", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/register")
    public ResponseEntity<String> register(String username, String password) throws JsonProcessingException {
        if (userRepository.existsByUsername(username))
            return new ResponseEntity<>("Username is already exists!", HttpStatus.CONFLICT);

        Role role = roleRepository.findById(1).get();
        User user = User.of(username, passwordEncoder.encode(password), new HashSet<>(Collections.singleton(role)));
        userRepository.save(user);

        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    private String generateJWT(User user) throws JsonProcessingException {
        user.setRefreshToken(UUID.randomUUID().toString().replace("-", ""));
        userRepository.save(user);

        JwtClaims claims = new JwtClaims()
                .setUsername(user.getUsername())
                .setRefreshToken(user.getRefreshToken())
                .setAuthorities(user.getAuthorities());

        Map<String, Object> values = Map.of(
                "token", jwtUtil.randomJWT(user, claims),
                "claims", claims);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(values);
    }
}

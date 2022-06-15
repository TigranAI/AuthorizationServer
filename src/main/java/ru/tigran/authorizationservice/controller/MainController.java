package ru.tigran.authorizationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tigran.authorizationservice.controller.dto.LoginDto;
import ru.tigran.authorizationservice.controller.dto.RefreshTokenDto;
import ru.tigran.authorizationservice.database.entity.User;
import ru.tigran.authorizationservice.database.repository.RoleRepository;
import ru.tigran.authorizationservice.database.repository.UserRepository;
import ru.tigran.authorizationservice.security.JwtClaims;
import ru.tigran.authorizationservice.security.JwtProperties;
import ru.tigran.authorizationservice.security.JwtToken;

import java.util.UUID;

@RestController
@RequestMapping("/authorize")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties properties;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto form) {
        if (userRepository.existsByUsername(form.username()))
            return new ResponseEntity<>("Пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);

        User user = User.of(form.username(), passwordEncoder.encode(form.password()), roleRepository.findByName("USER"));
        userRepository.save(user);

        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto form) {
        User user = userRepository.findByUsername(form.username());
        if (user == null)
            return new ResponseEntity<>("Пользователь с таким именем не найден", HttpStatus.BAD_REQUEST);
        if (!passwordEncoder.matches(form.password(), user.getPassword()))
            return new ResponseEntity<>("Неправильная пара логин пароль", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    @RequestMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshTokenDto token) {
        User user = userRepository.findByRefreshToken(token.jrt());
        if (user == null)
            return new ResponseEntity<>("Refresh token not found!", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(generateJWT(user), HttpStatus.OK);
    }

    private String generateJWT(User user) {
        user.setRefreshToken(UUID.randomUUID().toString().replace("-", ""));
        userRepository.save(user);

        JwtClaims claims = new JwtClaims()
                .setUsername(user.getUsername())
                .setRefreshToken(user.getRefreshToken())
                .setAuthorities(user.getAuthorities());

        return String.format("\"%s\"", JwtToken.of(claims, properties).get());
    }
}

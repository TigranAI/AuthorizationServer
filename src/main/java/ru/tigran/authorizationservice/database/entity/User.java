package ru.tigran.authorizationservice.database.entity;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID uuid;
    private String username;
    private String password;
    private String refreshToken;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public static User of(String username, String encodedPassword){
        User result = new User();
        result.setUuid(UUID.randomUUID());
        result.setUsername(username);
        result.setPassword(encodedPassword);
        return result;
    }

    public static User of(String username, String encodedPassword, HashSet<Role> roles){
        User result = of(username, encodedPassword);
        result.setRoles(roles);
        return result;
    }

    public static User of(String username, String encodedPassword, Role role){
        User result = of(username, encodedPassword);
        result.setRoles(new HashSet<>(List.of(role)));
        return result;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public List<String> getAuthorities(){
        if (roles == null) return Collections.emptyList();
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}

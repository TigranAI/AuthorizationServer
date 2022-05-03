package ru.tigran.authorizationservice.database.entity;

import ru.tigran.authorizationservice.security.JwtUtil;

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
    private String secret;
    private String refreshToken;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    private Boolean isAccountNonExpired = true;
    private Boolean isAccountNonLocked = true;
    private Boolean isCredentialsNonExpired = true;
    private Boolean isEnabled = true;

    public static User of(String username, String encodedPassword){
        User result = new User();
        result.setUuid(UUID.randomUUID());
        result.setSecret(JwtUtil.randomSecret());
        result.setUsername(username);
        result.setPassword(encodedPassword);
        return result;
    }

    public static User of(String username, String encodedPassword, HashSet<Role> roles){
        User result = of(username, encodedPassword);
        result.setRoles(roles);
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

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String tokenSecret) {
        this.secret = tokenSecret;
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

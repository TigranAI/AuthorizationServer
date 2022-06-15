package ru.tigran.authorizationservice.database.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new java.util.LinkedHashSet<>();

    public static Role of(String name){
        Role result = new Role();
        result.setName(name);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

package org.cargo.bean.user;

import org.cargo.bean.transportation.Transportation;

import java.util.Objects;
import java.util.Set;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private Role userRole;
    private Set<Transportation> userTransportations;


    public User() {
    }

    public User(int id, String username, String password, String email, Role userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public Set<Transportation> getUserTransportations() {
        return userTransportations;
    }

    public void setUserTransportations(Set<Transportation> userTransportations) {
        this.userTransportations = userTransportations;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userRole=" + userRole +
                ", userTransportations=" + userTransportations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email) && userRole == user.userRole
                && Objects.equals(userTransportations, user.userTransportations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, userRole, userTransportations);
    }
}




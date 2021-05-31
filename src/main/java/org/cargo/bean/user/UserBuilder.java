package org.cargo.bean.user;

public class UserBuilder {

     int id;
     String username;
     String password;
     String email;
     Role userRole;

    public UserBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setUserRole(Role userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public User build(){
        return new User (id, username, password, email, userRole);
    }
}

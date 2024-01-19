package org.launchcode.tutorconnector.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String role;

    public Login(String email) {}

    public Login(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return id == login.id && Objects.equals(email, login.email) && Objects.equals(role, login.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, role);
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

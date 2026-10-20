package com.fitnessclub.fitnessclubbackend.model.user;

public abstract class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;

    public User(Long id, String firstName, String lastName, String email, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    // Абстрактный метод (требование методички: разделяем логику)
    public abstract String getAccessDescription();

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + role + ")";
    }
}

package com.fitnessclub.fitnessclubbackend.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CUSTOMER")
@NoArgsConstructor
public class CustomerUser extends User {

    public CustomerUser(Long id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email, UserRole.CUSTOMER);
    }

    @Override
    public String getAccessDescription() {
        return "Доступ к просмотру расписания и своего абонемента";
    }
}
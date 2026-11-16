package com.fitnessclub.fitnessclubbackend.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("MANAGER")
@NoArgsConstructor
public class ManagerUser extends User {

    public ManagerUser(Long id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email, UserRole.MANAGER);
    }

    @Override
    public String getAccessDescription() {
        return "Доступ к управлению абонементами и клиентами";
    }
}
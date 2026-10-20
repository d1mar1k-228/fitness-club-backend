package com.fitnessclub.fitnessclubbackend.model.user;

public class ManagerUser extends User {
    public ManagerUser(Long id, String firstName, String lastName, String email) {
        super(id, firstName, lastName, email, UserRole.MANAGER);
    }

    @Override
    public String getAccessDescription() {
        return "Доступ к управлению абонементами и клиентами";
    }
}
package com.fitnessclub.fitnessclubbackend.model.membership;

public enum MembershipType {
    YEARLY("Годовой"),
    MONTHLY("Месячный");

    private final String displayName;

    MembershipType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

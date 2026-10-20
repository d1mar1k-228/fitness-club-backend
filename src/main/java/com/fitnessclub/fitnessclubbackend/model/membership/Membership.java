package com.fitnessclub.fitnessclubbackend.model.membership;

import java.time.LocalDate;

public class Membership {
    private Long id;
    private String clientName;
    private String membershipType;
    private int visitsLeft;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership(String clientName, String membershipType, int visitsLeft,
                      LocalDate startDate, LocalDate endDate) {
        this.clientName = clientName;
        this.membershipType = membershipType;
        this.visitsLeft = visitsLeft;
        this.isActive = true; // По умолчанию абонемент активен
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public int getVisitsLeft() { return visitsLeft; }
    public void setVisitsLeft(int visitsLeft) { this.visitsLeft = visitsLeft; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return "Membership{id=" + id + ", client='" + clientName + "', type='" +
                membershipType + "', visitsLeft=" + visitsLeft + ", active=" + isActive + "}";
    }
}

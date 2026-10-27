package com.fitnessclub.fitnessclubbackend.model.membership;

import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import java.time.LocalDate;

public class Membership {
    private Long id;
    private CustomerUser client;
    private MembershipType membershipType;
    private int visitsLeft;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate endDate;

    public Membership(CustomerUser client, MembershipType membershipType, int visitsLeft,
                      LocalDate startDate, LocalDate endDate) {
        this.client = client;
        this.membershipType = membershipType;
        this.visitsLeft = visitsLeft;
        this.isActive = true;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CustomerUser getClient() { return client; }
    public void setClient(CustomerUser client) { this.client = client; }

    public MembershipType getMembershipType() { return membershipType; }
    public void setMembershipType(MembershipType membershipType) { this.membershipType = membershipType; }

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
        String clientName = (client != null) ? (client.getFirstName() + " " + client.getLastName()) : "Неизвестный клиент";
        String typeName = (membershipType != null) ? membershipType.getDisplayName() : "Неизвестный тип";
        
        return "Membership{id=" + id + ", client='" + clientName + "', type='" + typeName +
                "', visitsLeft=" + visitsLeft + ", active=" + isActive + "}";
    }
}
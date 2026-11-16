package com.fitnessclub.fitnessclubbackend.model.membership;

import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "memberships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private CustomerUser client;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", nullable = false)
    private MembershipType membershipType;

    @Column(name = "visits_left", nullable = false)
    private int visitsLeft;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
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

    @Override
    public String toString() {
        String clientName = (client != null) ? (client.getFirstName() + " " + client.getLastName()) : "Неизвестный клиент";
        String typeName = (membershipType != null) ? membershipType.getDisplayName() : "Неизвестный тип";
        
        return "Membership{id=" + id + ", client='" + clientName + "', type='" + typeName +
                "', visitsLeft=" + visitsLeft + ", active=" + isActive + "}";
    }
}
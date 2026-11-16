package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import java.util.List;
import java.util.Optional;

public interface MembershipService {
    List<Membership> getAllMemberships();
    Optional<Membership> getMembershipById(Long id);
    Membership createMembership(Membership membership);
    Membership updateMembership(Long id, Membership membership);
    void deleteMembership(Long id);
    List<Membership> getMembershipsByVisitsLeftLessThan(int threshold);
    void processMembershipsMultithreaded(List<Membership> memberships);
    void notifyExpiringMemberships(List<Membership> memberships);
}
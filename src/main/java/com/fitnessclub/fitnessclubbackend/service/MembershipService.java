package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import java.util.List;

public interface MembershipService {
    void processMembershipsMultithreaded(List<Membership> memberships);
    void notifyExpiringMemberships(List<Membership> memberships);
}
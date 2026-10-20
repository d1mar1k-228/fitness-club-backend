package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import java.util.List;
import java.util.Optional;

public interface MembershipRepository {
    void save(Membership membership);
    Optional<Membership> findById(Long id);
    List<Membership> findAll();
    void deleteById(Long id);
    List<Membership> findByVisitsLeftLessThan(int threshold);
}
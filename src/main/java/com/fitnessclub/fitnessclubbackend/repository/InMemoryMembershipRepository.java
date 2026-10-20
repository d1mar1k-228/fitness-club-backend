package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryMembershipRepository implements MembershipRepository {
    // Используем разные структуры данных (требование методички)
    private final List<Membership> membershipList = new ArrayList<>();
    private final Map<Long, Membership> membershipMap = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public void save(Membership membership) {
        membership.setId(currentId++);
        membershipList.add(membership);
        membershipMap.put(membership.getId(), membership);
    }

    @Override
    public Optional<Membership> findById(Long id) {
        return Optional.ofNullable(membershipMap.get(id));
    }

    @Override
    public List<Membership> findAll() {
        return new ArrayList<>(membershipList);
    }

    @Override
    public void deleteById(Long id) {
        Membership removed = membershipMap.remove(id);
        if (removed != null) {
            membershipList.remove(removed);
        }
    }

    @Override
    public List<Membership> findByVisitsLeftLessThan(int threshold) {
        List<Membership> result = new ArrayList<>();
        for (Membership m : membershipList) {
            if (m.getVisitsLeft() < threshold && m.isActive()) {
                result.add(m);
            }
        }
        return result;
    }
}
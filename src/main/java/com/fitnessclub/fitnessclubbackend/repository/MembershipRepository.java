package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    /**
     * Spring Data JPA автоматически сгенерирует SQL-запрос по имени метода:
     * SELECT * FROM memberships WHERE visits_left < ?1
     */
    List<Membership> findByVisitsLeftLessThan(int threshold);

    /**
     * Найти все абонементы определённого типа
     */
    List<Membership> findByMembershipType(com.fitnessclub.fitnessclubbackend.model.membership.MembershipType type);

    /**
     * Найти все активные абонементы
     */
    List<Membership> findByIsActiveTrue();
}
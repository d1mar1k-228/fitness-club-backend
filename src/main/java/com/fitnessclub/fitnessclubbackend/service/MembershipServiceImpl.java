package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.exception.ResourceNotFoundException;
import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    @Override
    public Optional<Membership> getMembershipById(Long id) {
        Optional<Membership> founded = membershipRepository.findById(id);
        
        if (founded.isEmpty()) {
            throw new ResourceNotFoundException("Абонемент с ID " + id + " не найден");
        } 
        
        return founded;
    }

    @Override
    public Membership createMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public Membership updateMembership(Long id, Membership membership) {
        return membershipRepository.findById(id)
                .map(existing -> {
                    existing.setClient(membership.getClient());
                    existing.setMembershipType(membership.getMembershipType());
                    existing.setVisitsLeft(membership.getVisitsLeft());
                    existing.setStartDate(membership.getStartDate());
                    existing.setEndDate(membership.getEndDate());
                    existing.setActive(membership.isActive());
                    return membershipRepository.save(existing);
                })
                   .orElseThrow(() -> new ResourceNotFoundException("Абонемент с ID " + id + " не найден"));
    }

    @Override
    public void deleteMembership(Long id) {
        if (!membershipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Абонемент с ID " + id + " не найден для удаления");
        }
        
        membershipRepository.deleteById(id);
    }

    @Override
    public List<Membership> getMembershipsByVisitsLeftLessThan(int threshold) {
        return membershipRepository.findByVisitsLeftLessThan(threshold);
    }

    @Override
    public void processMembershipsMultithreaded(List<Membership> memberships) {
        ThreadFactory namedThreadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName("Fitness-Worker-" + thread.getId());
            return thread;
        };

        ExecutorService executor = Executors.newFixedThreadPool(3, namedThreadFactory);

        for (Membership membership : memberships) {
            executor.execute(() -> {
                String threadName = Thread.currentThread().getName();
                String clientName = (membership.getClient() != null)
                        ? (membership.getClient().getFirstName() + " " + membership.getClient().getLastName())
                        : "Неизвестный клиент";

                System.out.println("[" + threadName + "] Обработка абонемента ID: "
                        + membership.getId() + " | Клиент: " + clientName);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
    }

    @Override
    public void notifyExpiringMemberships(List<Membership> memberships) {
        for (Membership m : memberships) {
            if (m.getVisitsLeft() < 5 && m.isActive()) {
                String clientName = (m.getClient() != null)
                        ? (m.getClient().getFirstName() + " " + m.getClient().getLastName())
                        : "Неизвестный клиент";

                System.out.println("Уведомление: У клиента " + clientName +
                        " осталось " + m.getVisitsLeft() + " посещений. Пора продлить!");
            }
        }
    }
}
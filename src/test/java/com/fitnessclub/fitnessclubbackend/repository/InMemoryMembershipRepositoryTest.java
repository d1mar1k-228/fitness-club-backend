package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.model.membership.MembershipType;
import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMembershipRepositoryTest {

    private InMemoryMembershipRepository repository;
    private CustomerUser testClient;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMembershipRepository();
        testClient = new CustomerUser(1L, "Тест", "Клиент", "test@mail.ru");
    }

    @Test
    void testSaveAndFindAll() {
        Membership m1 = new Membership(testClient, MembershipType.YEARLY, 50,
                LocalDate.now(), LocalDate.now().plusYears(1));
        Membership m2 = new Membership(testClient, MembershipType.MONTHLY, 3,
                LocalDate.now(), LocalDate.now().plusMonths(1));

        repository.save(m1);
        repository.save(m2);

        List<Membership> all = repository.findAll();
        assertEquals(2, all.size(), "Должно быть 2 абонемента");
    }

    @Test
    void testFindByVisitsLeftLessThan() {
        repository.save(new Membership(testClient, MembershipType.YEARLY, 50, LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership(testClient, MembershipType.MONTHLY, 3, LocalDate.now(), LocalDate.now().plusMonths(1)));
        repository.save(new Membership(testClient, MembershipType.YEARLY, 8, LocalDate.now(), LocalDate.now().plusYears(1)));

        List<Membership> result = repository.findByVisitsLeftLessThan(10);
        assertEquals(2, result.size(), "Должно быть 2 абонемента с остатком < 10");
        
        for (Membership m : result) {
            assertTrue(m.getVisitsLeft() < 10, "Остаток должен быть меньше 10");
        }
    }

    @Test
    void testFindByVisitsLeftLessThan_NoResults() {
        repository.save(new Membership(testClient, MembershipType.YEARLY, 50, LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership(testClient, MembershipType.YEARLY, 100, LocalDate.now(), LocalDate.now().plusYears(1)));

        List<Membership> result = repository.findByVisitsLeftLessThan(10);
        assertEquals(0, result.size(), "Должно быть 0 абонементов с остатком < 10");
    }
}
package com.fitnessclub.fitnessclubbackend.repository;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryMembershipRepositoryTest {

    private InMemoryMembershipRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMembershipRepository();
    }

    @Test
    void testSaveAndFindAll() {
        // Создаем абонементы
        Membership m1 = new Membership("Иван Иванов", "Годовой", 50,
                LocalDate.now(), LocalDate.now().plusYears(1));
        Membership m2 = new Membership("Петр Петров", "Месячный", 3,
                LocalDate.now(), LocalDate.now().plusMonths(1));

        repository.save(m1);
        repository.save(m2);

        // Проверяем, что все абонементы сохранены
        List<Membership> all = repository.findAll();
        assertEquals(2, all.size(), "Должно быть 2 абонемента");
    }

    @Test
    void testFindByVisitsLeftLessThan() {
        // Создаем абонементы с разным остатком
        repository.save(new Membership("Иван", "Годовой", 50,
                LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership("Петр", "Месячный", 3,
                LocalDate.now(), LocalDate.now().plusMonths(1)));
        repository.save(new Membership("Анна", "Годовой", 8,
                LocalDate.now(), LocalDate.now().plusYears(1)));

        // Ищем абонементы с остатком < 10
        List<Membership> result = repository.findByVisitsLeftLessThan(10);
        
        // Должно найти 2 абонемента (Петр и Анна)
        assertEquals(2, result.size(), "Должно быть 2 абонемента с остатком < 10");
        
        // Проверяем, что найденные абонементы действительно имеют остаток < 10
        for (Membership m : result) {
            assertTrue(m.getVisitsLeft() < 10, "Остаток должен быть меньше 10");
        }
    }

    @Test
    void testFindByVisitsLeftLessThan_NoResults() {
        // Создаем абонементы с большим остатком
        repository.save(new Membership("Иван", "Годовой", 50,
                LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership("Петр", "Годовой", 100,
                LocalDate.now(), LocalDate.now().plusYears(1)));

        // Ищем абонементы с остатком < 10
        List<Membership> result = repository.findByVisitsLeftLessThan(10);
        
        // Должно найти 0 абонементов
        assertEquals(0, result.size(), "Должно быть 0 абонементов с остатком < 10");
    }
}
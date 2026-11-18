package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.model.membership.MembershipType;
import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import com.fitnessclub.fitnessclubbackend.repository.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MembershipServiceTest {

    // Внедряем наш сервис (Spring сам его создаст)
    @Autowired
    private MembershipService membershipService;

    // А вот репозиторий мы ПОДМЕНЯЕМ на "мок" (пустышку). 
    // Благодаря этому тесты НЕ будут лезть в реальную базу данных!
    @MockBean
    private MembershipRepository membershipRepository;

    private Membership testMembership;

    @BeforeEach
    void setUp() {
        // Создаем тестового клиента и абонемент
        CustomerUser client = new CustomerUser();
        client.setFirstName("Тест");
        client.setLastName("Клиент");
        client.setEmail("test@mail.ru");

        testMembership = new Membership(client, MembershipType.YEARLY, 50, LocalDate.now(), LocalDate.now().plusYears(1));
        testMembership.setId(1L);
    }

    @Test
    void testGetAllMemberships() {
        // Настраиваем "мок": когда вызовут findAll(), верни наш тестовый список
        when(membershipRepository.findAll()).thenReturn(Arrays.asList(testMembership));

        List<Membership> result = membershipService.getAllMemberships();

        assertEquals(1, result.size());
        // Проверяем, что метод репозитория действительно вызвался 1 раз
        verify(membershipRepository, times(1)).findAll();
    }

    @Test
    void testGetMembershipById_Found() {
        when(membershipRepository.findById(1L)).thenReturn(Optional.of(testMembership));

        Optional<Membership> result = membershipService.getMembershipById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetMembershipById_NotFound() {
        when(membershipRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Membership> result = membershipService.getMembershipById(99L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateMembership() {
        // Когда вызовут save(), верни тот же объект (как будто сохранился)
        when(membershipRepository.save(any(Membership.class))).thenReturn(testMembership);

        Membership created = membershipService.createMembership(testMembership);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        verify(membershipRepository, times(1)).save(testMembership);
    }

    @Test
    void testGetMembershipsByVisitsLeftLessThan() {
        when(membershipRepository.findByVisitsLeftLessThan(10)).thenReturn(Arrays.asList(testMembership));

        List<Membership> result = membershipService.getMembershipsByVisitsLeftLessThan(10);

        assertEquals(1, result.size());
        verify(membershipRepository).findByVisitsLeftLessThan(10);
    }
}
package com.fitnessclub.fitnessclubbackend;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.model.membership.MembershipType;
import com.fitnessclub.fitnessclubbackend.model.user.AdminUser;
import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import com.fitnessclub.fitnessclubbackend.model.user.ManagerUser;
import com.fitnessclub.fitnessclubbackend.model.user.User;
import com.fitnessclub.fitnessclubbackend.repository.InMemoryMembershipRepository;
import com.fitnessclub.fitnessclubbackend.repository.MembershipRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Демонстрация Лабораторной работы №1 ===\n");

        // 1. Иерархия пользователей
        System.out.println("--- Иерархия пользователей ---");
        User admin = new AdminUser(1L, "Иван", "Администраторов", "admin@fitness.ru");
        User manager = new ManagerUser(2L, "Петр", "Менеджеров", "manager@fitness.ru");
        User customer = new CustomerUser(3L, "Анна", "Клиентова", "client@mail.ru");

        List<User> users = List.of(admin, manager, customer);
        for (User user : users) {
            System.out.println(user + " - " + user.getAccessDescription());
        }

        // 2. Работа с коллекциями
        System.out.println("\n--- Работа с абонементами ---");
        MembershipRepository repository = new InMemoryMembershipRepository();

        repository.save(new Membership((CustomerUser) customer, MembershipType.YEARLY, 50,
                LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership(new CustomerUser(4L, "Петр", "Петров", "peter@mail.ru"), MembershipType.MONTHLY, 3,
                LocalDate.now(), LocalDate.now().plusMonths(1)));
        repository.save(new Membership(new CustomerUser(5L, "Анна", "Сидорова", "anna@mail.ru"), MembershipType.YEARLY, 8,
                LocalDate.now(), LocalDate.now().plusYears(1)));
        repository.save(new Membership(new CustomerUser(6L, "Мария", "Кузнецова", "maria@mail.ru"), MembershipType.MONTHLY, 2,
                LocalDate.now(), LocalDate.now().plusMonths(1)));
        repository.save(new Membership(new CustomerUser(7L, "Сергей", "Волков", "sergey@mail.ru"), MembershipType.YEARLY, 15,
                LocalDate.now(), LocalDate.now().plusYears(1)));

        System.out.println("Все абонементы:");
        for (Membership m : repository.findAll()) {
            System.out.println(m);
        }

        System.out.println("\nАбонементы с малым остатком посещений (<10):");
        List<Membership> expiring = repository.findByVisitsLeftLessThan(10);
        for (Membership m : expiring) {
            System.out.println(m);
        }

        // 3. Многопоточная обработка (требование методички)
        System.out.println("\n--- Многопоточная обработка абонементов ---");
        List<Membership> allMemberships = repository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < allMemberships.size(); i++) {
            final Membership membership = allMemberships.get(i);
            final int index = i;

            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Поток [" + threadName + "] обрабатывает абонемент #" +
                        (index + 1) + ": " + membership.getClient().getFirstName() +
                        " - " + membership.getMembershipType().getDisplayName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=== Демонстрация завершена ===");
    }
}
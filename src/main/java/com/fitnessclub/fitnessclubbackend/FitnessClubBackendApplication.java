package com.fitnessclub.fitnessclubbackend;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import com.fitnessclub.fitnessclubbackend.model.user.AdminUser;
import com.fitnessclub.fitnessclubbackend.model.user.CustomerUser;
import com.fitnessclub.fitnessclubbackend.model.user.ManagerUser;
import com.fitnessclub.fitnessclubbackend.model.user.User;
import com.fitnessclub.fitnessclubbackend.repository.InMemoryMembershipRepository;
import com.fitnessclub.fitnessclubbackend.repository.MembershipRepository;
import com.fitnessclub.fitnessclubbackend.service.MembershipService;
import com.fitnessclub.fitnessclubbackend.service.MembershipServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class FitnessClubBackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FitnessClubBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("=== Демонстрация Лабораторной работы №1 ===\n");

		// 1. Демонстрация иерархии пользователей (требование методички)
		System.out.println("--- Иерархия пользователей ---");
		User admin = new AdminUser(1L, "Иван", "Администраторов", "admin@fitness.ru");
		User manager = new ManagerUser(2L, "Петр", "Менеджеров", "manager@fitness.ru");
		User customer = new CustomerUser(3L, "Анна", "Клиентова", "client@mail.ru");

		List<User> users = List.of(admin, manager, customer);
		for (User user : users) {
			System.out.println(user + " - " + user.getAccessDescription());
		}

		// 2. Демонстрация работы с коллекциями (алгоритмы обработки)
		System.out.println("\n--- Работа с абонементами ---");
		MembershipRepository repository = new InMemoryMembershipRepository();

		repository.save(new Membership("Иван Иванов", "Годовой", 50,
				LocalDate.now(), LocalDate.now().plusYears(1)));
		repository.save(new Membership("Петр Петров", "Месячный", 3,
				LocalDate.now(), LocalDate.now().plusMonths(1)));
		repository.save(new Membership("Анна Сидорова", "Годовой", 8,
				LocalDate.now(), LocalDate.now().plusYears(1)));
		repository.save(new Membership("Мария Кузнецова", "Месячный", 2,
				LocalDate.now(), LocalDate.now().plusMonths(1)));
		repository.save(new Membership("Сергей Волков", "Годовой", 15,
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

		// 3. Демонстрация многопоточной обработки (требование методички)
		System.out.println("\n--- Многопоточная обработка ---");
		MembershipService service = new MembershipServiceImpl();
		service.processMembershipsMultithreaded(repository.findAll());

		// Ждем завершения потоков
		Thread.sleep(2000);
		System.out.println("\n=== Демонстрация завершена ===");
	}
}

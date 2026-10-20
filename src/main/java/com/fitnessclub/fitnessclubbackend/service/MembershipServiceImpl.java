package com.fitnessclub.fitnessclubbackend.service;

import com.fitnessclub.fitnessclubbackend.model.membership.Membership;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class MembershipServiceImpl implements MembershipService {

    @Override
    public void processMembershipsMultithreaded(List<Membership> memberships) {
        // Создаем фабрику потоков с кастомными именами (требование методички)
        ThreadFactory namedThreadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setName("Fitness-Worker-" + thread.getId());
            return thread;
        };

        // Создаем пул из 3 потоков
        ExecutorService executor = Executors.newFixedThreadPool(3, namedThreadFactory);

        for (Membership membership : memberships) {
            executor.execute(() -> {
                // Получаем имя текущего потока для доказательства многопоточности
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] Обработка абонемента ID: "
                        + membership.getId() + " | Клиент: " + membership.getClientName());

                // Имитация сложной бизнес-логики (например, расчет бонусов или запрос к БД)
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Корректное завершение работы пула потоков
        executor.shutdown();
    }

    @Override
    public void notifyExpiringMemberships(List<Membership> memberships) {
        for (Membership m : memberships) {
            if (m.getVisitsLeft() < 5 && m.isActive()) {
                System.out.println("Уведомление: У клиента " + m.getClientName() +
                        " осталось " + m.getVisitsLeft() + " посещений. Пора продлить!");
            }
        }
    }
}
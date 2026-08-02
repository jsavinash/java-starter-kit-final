package com.javastarterkit.patterns.eventdrivenarchitecture;

import static org.assertj.core.api.Assertions.assertThat;

import com.javastarterkit.patterns.eventdrivenarchitecture.core.EventBus;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.EmailService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.InventoryService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.OrderService;
import com.javastarterkit.patterns.eventdrivenarchitecture.service.ShippingService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * Concurrency tests verifying thread-safety of the event bus under load.
 */
class EventBusConcurrencyTest {

    @Test
    void concurrentPublishesAreThreadSafe() throws InterruptedException {
        EventBus eventBus = new EventBus();
        EmailService emailService = new EmailService(eventBus);
        InventoryService inventoryService = new InventoryService(eventBus);
        ShippingService shippingService = new ShippingService(eventBus);
        OrderService orderService = new OrderService(eventBus);

        int threadCount = 16;
        int ordersPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threadCount);

        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            executor.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                    for (int i = 0; i < ordersPerThread; i++) {
                        orderService.placeOrder("ORD-" + threadId + "-" + i,
                                "user" + threadId + "@example.com", 1);
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }

        ready.await();
        start.countDown();
        assertThat(done.await(30, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();

        int totalOrders = threadCount * ordersPerThread;
        assertThat(emailService.getConfirmationCount()).isEqualTo(totalOrders);
        assertThat(emailService.getShippingCount()).isEqualTo(totalOrders);
        assertThat(inventoryService.getTotalReserved()).isEqualTo(totalOrders);
        assertThat(shippingService.getShippedCount()).isEqualTo(totalOrders);

        eventBus.shutdown();
    }
}
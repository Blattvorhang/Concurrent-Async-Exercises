package de.tum.in.ase.eist;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        detectDeadlock(new SwimmingPool());
    }

    public static void detectDeadlock(SwimmingPool swimmingPool) {
        // TODO 2
        // Create a new thread for each swimmer and start it
        // Wait for all threads to finish
        // Print the total number of visitors
        List<Thread> threads = List.of(
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.CHANGING_ROOM_BEFORE_LOCKER)),
                new Thread(() -> new Swimmer().goToSwimmingPool(swimmingPool, SwimmingPoolActionOrder.LOCKER_BEFORE_CHANGING_ROOM))
        );
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException("A thread threw an error when trying to join: " + e);
            }
        });
        System.out.println("Total visitors: " + swimmingPool.getTotalVisitors());
    }
}

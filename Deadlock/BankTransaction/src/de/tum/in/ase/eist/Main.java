package de.tum.in.ase.eist;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final long NUM_THREAD_PAIRS = 100;
    private static final long OPERATIONS_PER_THREAD = 100_000;

    public static void main(String[] args) throws InterruptedException {
        // === Test for Race Condition
        System.out.println("=== Test for Race Condition:");

        BankAccount account = new BankAccount(42);
        long originalBalance = account.getBalance();

        // Create many threads, half of which constantly deposit and the other half constantly withdraw
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREAD_PAIRS; i++) {
            Thread depositThread = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    account.deposit(1);
                }
            });
            threads.add(depositThread);

            Thread withdrawThread = new Thread(() -> {
                for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                    account.withdraw(1);
                }
            });
            threads.add(withdrawThread);
        }

        // Start and join threads
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        // Compare the original and new balances
        long newBalance = account.getBalance();
        System.out.println("Original balance: " + originalBalance);
        System.out.println("New balance:      " + newBalance);
        System.out.println();


        // === Test for Deadlock
        System.out.println("=== Test for Deadlock:");

        BankAccount account1 = new BankAccount(10);
        BankAccount account2 = new BankAccount(20);

        // Start two threads, transferring between two accounts
        threads = List.of(
                new Thread(() -> account1.transfer(account2,5)),
                new Thread(() -> account2.transfer(account1,8))
        );
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Both threads have terminated, so it seems like the deadlock has been eliminated!");
    }
}

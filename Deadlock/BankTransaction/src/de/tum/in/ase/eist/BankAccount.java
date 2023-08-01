package de.tum.in.ase.eist;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private static final long WAIT_BEFORE_OP_TIMEOUT_MS = 50;
    private static int uniqueIdCounter = 0;
    private final int accountId; // this value will be unique among all instances of this class
    private final ReentrantLock securityMutex;
    private long balance;

    public BankAccount(long initialBalance) {
        this.accountId = uniqueIdCounter++;
        this.balance = initialBalance;
        this.securityMutex = new ReentrantLock();
    }

    // TODO 1: Add synchronization mechanisms to prevent Race Condition
    // Deposits the given amount to this bank account
    public void deposit(long amount) {
        securityMutex.lock();
        balance += amount;
        securityMutex.unlock();
    }

    // TODO 1: Add synchronization mechanisms to prevent Race Condition
    // Withdraws the given amount from this bank account
    public void withdraw(long amount) {
        securityMutex.lock();
        balance -= amount;
        securityMutex.unlock();
    }

    // DO NOT CHANGE THIS METHOD!
    // Sleep before locking so one thread doesn't perform all of its operations before the other thread can even start locking
    private void artificialSleep() {
        try {
            Thread.sleep(WAIT_BEFORE_OP_TIMEOUT_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException("A thread threw an error when trying to sleep: " + e);
        }
    }

    // DO NOT CHANGE THIS METHOD!
    // Performs a transfer with enhanced security, which locks/unlocks the mutexes from this bank account and
    // the destination bank account in the specified order.
    private void secureTransfer(BankAccount destination, long amount, ReentrantLock firstMutex, ReentrantLock secondMutex) {
        // Lock the mutexes of this bank account and that of the destination
        firstMutex.lock();
        artificialSleep(); // necessary (for testing purposes) so threads synchronize
        secondMutex.lock();

        // Perform funds transfer
        withdraw(amount);
        destination.deposit(amount);

        // Unlock the mutexes of this bank account and that of the destination
        secondMutex.unlock();
        firstMutex.unlock();
    }

    // The given amount is transferred from this bank account to the destination bank account.
    public void transfer(BankAccount destination, long amount) {
        // TODO 2: Prevent the deadlock
        if (this.accountId < destination.accountId)
            secureTransfer(destination, amount, this.securityMutex, destination.securityMutex);
        else
            secureTransfer(destination, amount, destination.securityMutex, this.securityMutex);
    }

    public long getBalance() {
        return this.balance;
    }
}

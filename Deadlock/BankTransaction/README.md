# Parallelism: A Multi-threaded Saga

Due to a recent economic stimulus package for university students announced by the German government, students are rushing to the bank to take advantage of the financial benefits provided.
However, this causes internal chaos at the bank, because the `BankAccount`s are unprepared for the overwhelming activity, which can lead to potential race conditions.

**Note**: For the sake of simplicity, the `balance` of a `BankAccount` may also be negative.

## Part 1: Race Conditions

**You have the following task:**

1. **Fix the Race Conditions**

Your first task is to ensure the integrity of the banking system by preventing race conditions in the face of countless `deposit` and `withdraw` requests made on a single `BankAccount`.
Implement synchronization mechanisms that ensure consistent execution of `deposit` and `withdraw` transactions, even when the system is faced with many such transactions executed in parallel.

Note that we have already provided a `main()` function in the `Main` class that allows you to test your solution for race conditions.

## Part 2: Deadlocks

The bank also supports transferring money from one `BankAccount` to another using the `transfer()` method.
Furthermore, the bank has recently introduced a new security feature:
When transferring funds between two accounts, the `securityMutex` from each of the bank accounts involved in the transaction must be locked before, and unlocked after, the actual transfer of money.
However, this creates a problem in an edge case that the programmers of the banking system have not noticed.
For a certain multi-threaded combination of transfer requests (i.e. when multiple transfer requests are executed at the same time on different threads), the system will encounter a deadlock.

**You have the following task:**

1. **Fix the Deadlock**

It is now your job to find this deadlock, and prevent it.

The `Main` class already contains some code that will help you test whether the deadlock is (still) present.

As indicated in the todos in the code, you will have to make changes to the `transfer()` method.
Importantly, you are **not** allowed to change any of the code in the `secureTransfer()` method.
If you change anything in the `secureTransfer()` method, you will automatically receive 0 points for this part.

**Hint**: The `accountId` in `BankAccount` might be helpful.

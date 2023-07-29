# Deadlock
This exercise was originally part of a homework assignment from the course *Introduction to Software Engineering* at TUM during the Summer Semester of 2023.

In this exercise, we want to explore deadlocks using a swimming pool facility.

Here is an overview of the system: We have a `SwimmingPool`, which contains exactly one `ChangingRoom` and one `Locker` (for the sake of simplicity; a real swimming pool would obviously have more of each). Before entering the `SwimmingPool`, `Swimmer`s must change their clothes in the changing room and lock their clothes in the locker. The locker should stay locked until the swimmer is done with their swimming session, and the key to the changing room should also be kept until the swimmer leaves the facility (this implies that only one swimmer at a time can swim in our pool). Different swimmers should be able to request entry to the swimming pool (using `goToSwimmingPool`) from multiple different threads simultaneously.

## Part 1: Create the deadlock
Your first task is to use the mutexes provided in the `ChangingRoom` and `Locker` classes to lock and unlock accordingly. Make sure to also set the new `occupant`s of the changing room and locker. Since we expect `acquireKey()` to be called before `releaseKey()` in `ChangingRoom`, the tests assume you implement them in this order (the same goes for `Locker`). We will learn later on that this will (purposefully for this exercise) create a deadlock in a specific situation. Do not remove the `takeSomeTime()` calls from the methods, as they are required to increase the likelihood of deadlocks (significantly).

**Note**: The tests for part 2 and part 3 expect part 1 to be implemented correctly.

## Part 2: Detect the deadlock
We will now explore why a deadlock can occur here: The `SwimmingPoolActionOrder` specifies the two possible orders for entering the swimming pool facility: A `Swimmer` can either use the `ChangingRoom` before the `Locker`, or the `Locker` before the `ChangingRoom`. However, our current system allows the multi-threaded situation where one swimmer on one thread wants to use one of the orders and another swimmer from another thread wants to use the other order. You should complete the `detectDeadlock` function in `Main`, that creates this situation. If you implemented task 1 correctly (i.e. you created the environment required for the deadlock to occur), then executing the main function should lead to it "getting stuck"/blocking/not terminating, as this is what deadlocks do.

**Hint**: You will have to spawn multiple threads and create the exact interleaving that leads to the deadlock.

**Hint**: An easy way to test that your `detectDeadlock` function is actually blocking is to add a print statement under the `detectDeadlock` call in the main function. If said print statement is actually called, you know that `detectDeadlock` is not actually blocking (even though it might look like it is).

## Part 3: Prevent the deadlock
Finally, we want to remove the deadlock (without simply removing what we added in task 1). We can achieve this by enforcing a global order (consistent across all threads) of acquiring the `ChangingRoom` and `Locker`. This means that we will reject any entry request by a swimmer that contains an order different to our prescribed order. You may print a meaningful message to stdout when rejecting such entry requests. Make your changes to `handleEntryRequestDeadlockFree`, which currently just forwards to `handleEntryRequest` in `SwimmingPool`. You may reuse as much code from `handleEntryRequest` as you see fit.

If you implemented this part correctly, you should be able to run your main function from part 2 and see that executing it actually terminates now. You might have to replace the `handleEntryRequest()` call with a `handleEntryRequestDeadlockFree` call in `goToSwimmingPool` in `Swimmer` to use our updated deadlock free implementation, depending on your implementation of part 2.
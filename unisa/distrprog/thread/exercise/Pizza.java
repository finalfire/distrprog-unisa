package unisa.distrprog.thread.exercise;

import java.util.LinkedList;
import java.util.Queue;

class Counter {
    private Queue<Integer> queue = new LinkedList<>();
    private final int MAX_PIZZAS = 10;

    // this method will be called from the producer (e.g., the pizzaiolo)
    public synchronized void produce(int pizza) throws InterruptedException {
        while (queue.size() == MAX_PIZZAS) {
            wait();  // the pizzaiolo cannot insert any more pizzas
        }
        queue.add(pizza);
        System.out.println("unisa.distrprog.thread.exercise.Pizza " + pizza + " sfornata!");
        notify();
    }

    // this method will be called from the consumer (e.g., the waiter)
    public synchronized void consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();  // we wait until at least one pizza is placed on the counter
        }
        int pizza = queue.poll();
        System.out.println("unisa.distrprog.thread.exercise.Pizza " + pizza + " portata al tavolo!");
        notify();
    }
}

public class Pizza {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // this is the thread of the producer
        // we use a lambda function to define it quickly
        Thread pizzaiolo = new Thread(() -> {
            int currentPizza = 0;
            try {
                while (true) {
                    counter.produce(++currentPizza);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Pizzaiolo");

        // this is the thread of the consumer
        Thread waiter = new Thread(() -> {
            try {
                while (true) {
                    counter.consume();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Cameriere");

        pizzaiolo.start();
        waiter.start();
    }
}

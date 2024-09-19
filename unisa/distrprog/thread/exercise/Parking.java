package unisa.distrprog.thread.exercise;

import java.util.*;

class ParkingLot {
    private final int MAX_PLACES = 5;
    private Queue<Integer> places = new LinkedList<>();

    private boolean isFull() {
        return this.places.size() == this.MAX_PLACES;
    }

    public synchronized boolean park(int car) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while(this.isFull()) {
            System.out.println("unisa.distrprog.thread.exercise.Car " + car + " is waiting...");
            wait(20000);
        }
        this.places.add(car);
        System.out.println("unisa.distrprog.thread.exercise.Car " + car + " found parking after " +
                (System.currentTimeMillis() - startTime) + " seconds.");
        return true;
    }

    public synchronized void leave(int car) {
        this.places.remove(car);
        System.out.println("unisa.distrprog.thread.exercise.Car " + car + " left. We have now " + this.places.size() + " places occupied.");
        notify();
    }

}

class Car extends Thread {
    private final int car;
    private final ParkingLot parkingLot;

    public Car (int car, ParkingLot parkingLot) {
        this.car = car;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        try {
            if (this.parkingLot.park(this.car)) {
                Thread.sleep(10000);  // here we simulate the 10 seconds parking
                this.parkingLot.leave(this.car);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("unisa.distrprog.thread.exercise.Car " + this.car + " had a problem in parking.");
        }
    }
}

public class Parking {
    public static void main(String[] args) throws InterruptedException {
        Random rng = new Random();

        ParkingLot parkingLot = new ParkingLot();
        ArrayList<Car> cars = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cars.add(new Car(i, parkingLot));
            cars.get(i).start();
            Thread.sleep(rng.nextInt((1000 - 500) + 1) + 500);  // random arrival time
        }
    }
}

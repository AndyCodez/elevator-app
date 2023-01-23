package org.example;

public class Main {
    public static void main(String[] args) {

        Elevator elevator1 = new Elevator('A', 3, 0);
        Runnable r = new ElevatorCaller(elevator1, 1);
        new Thread(r).start();

        Elevator elevator2 = new Elevator('B', 2, 0);
        Runnable r2 = new ElevatorCaller(elevator2, 5);
        new Thread(r2).start();
    }
}
package org.example;

public class Main {
    public static void main(String[] args) {

        Building building = new Building(5);

        Elevator elevator1 = new Elevator(building,'A', 6, 0);
        Runnable r = new ElevatorCaller(elevator1, 1);
        new Thread(r).start();

        Elevator elevator2 = new Elevator(building,'B', 2, 0);
        Runnable r2 = new ElevatorCaller(elevator2, 5);
        new Thread(r2).start();
    }
}
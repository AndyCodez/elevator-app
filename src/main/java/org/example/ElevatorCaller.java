package org.example;

public class ElevatorCaller implements Runnable {

    private Elevator elevator;
    private int secondsPerFloor;

    public ElevatorCaller(Elevator elevator, int secondsPerFloor) {
        this.elevator = elevator;
        this.secondsPerFloor = secondsPerFloor;
    }


    @Override
    public void run() {
        try {
            elevator.callElevator(elevator.getDestinationFloor(), this.secondsPerFloor);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

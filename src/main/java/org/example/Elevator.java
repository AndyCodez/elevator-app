package org.example;

import java.util.concurrent.TimeUnit;

public class Elevator {
    private Building building;
    private char elevatorLetter;
    private int currentFloor;
    private String state; // "idle" or "moving"
    private String direction; // "up" or "down"
    private String doors; // "open" or "closed"
    private int destinationFloor;

    public Elevator(Building building, char elevatorLetter, int currentFloor, int destinationFloor) {
        this.building = building;
        this.elevatorLetter = elevatorLetter;
        this.currentFloor = currentFloor;
        this.state = "idle";
        this.direction = "";
        this.doors = "closed";
        this.destinationFloor = destinationFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void callElevator(int destinationFloor, int secondsPerFloor) throws InterruptedException {
        if (currentFloor > this.building.getNumberOfFloors() || destinationFloor > this.building.getNumberOfFloors()) {
            throw new RuntimeException("Floor number is invalid.");
        }
        System.out.println("Elevator " + this.elevatorLetter + " moving from current Floor " + this.currentFloor + " at " + secondsPerFloor + " secondsPerFloor");
        ElevatorStatus status = this.getStatus();

        closeDoors();
        this.destinationFloor = destinationFloor;
        this.state = "moving";
        int floorDifference = Math.abs(this.currentFloor - destinationFloor);

        for (int i=1; i <= floorDifference; i++) {
            if (this.currentFloor > destinationFloor) {
                this.direction = "down";
                TimeUnit.SECONDS.sleep(secondsPerFloor);
                this.currentFloor -= 1;

                status.setCurrentFloor(this.currentFloor);

                if (this.currentFloor == 0) {
                    this.state = "idle";
                    this.direction = "";
                }
                status.setDirection(this.direction);
                status.setState(this.state);

                System.out.println("Elevator " + this.elevatorLetter + " is at floor " + status.getCurrentFloor() + " and is " + status.getState() + " " + status.getDirection());

            } else {
                this.direction = "up";
                TimeUnit.SECONDS.sleep(secondsPerFloor);
                this.currentFloor += 1;

                status.setCurrentFloor(this.currentFloor);

                if (this.currentFloor == destinationFloor) {
                    this.state = "idle";
                    this.direction = "";
                }
                status.setDirection(this.direction);
                status.setState(this.state);

                System.out.println("Elevator " + this.elevatorLetter + " is at floor " + status.getCurrentFloor() + " and is " + status.getState() + " " + status.getDirection());
            }
        }

        openDoors();
    }

    public ElevatorStatus getStatus() {
        ElevatorStatus elevatorStatus = new ElevatorStatus(this.currentFloor, this.state, this.direction);
        return elevatorStatus;
    }

    public void openDoors() throws InterruptedException {
        if (this.doors.equals("closed")) {
            TimeUnit.SECONDS.sleep(2);
            this.doors = "open";
        }
    }

    public void closeDoors() throws InterruptedException {
        if (this.doors.equals("open")) {
            TimeUnit.SECONDS.sleep(2);
            this.doors = "closed";
        }
    }
}

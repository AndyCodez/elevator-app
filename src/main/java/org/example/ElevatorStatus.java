package org.example;

public class ElevatorStatus {
    private int currentFloor;
    private String state; // "idle" or "moving"
    private String direction;

    public ElevatorStatus(int currentFloor, String state, String direction) {
        this.currentFloor = currentFloor;
        this.state = state;
        this.direction = direction;
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
}

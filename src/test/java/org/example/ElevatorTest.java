package org.example;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorTest {

    @Test
    void callElevatorToALowerFloor() throws InterruptedException {
        Building building = new Building(6);
        int destinationFloor = 0;

        Elevator elevator = new Elevator(building, 'A', 3, destinationFloor);

        int secondsPerFloor = 1;
        elevator.callElevator(destinationFloor, secondsPerFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

    @Test
    void callElevatorToAHigherFloor() throws InterruptedException {
        Building building = new Building(6);
        int destinationFloor = 3;

        Elevator elevator = new Elevator(building, 'B', 1, destinationFloor);

        int secondsPerFloor = 1;

        elevator.callElevator(destinationFloor, secondsPerFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

}
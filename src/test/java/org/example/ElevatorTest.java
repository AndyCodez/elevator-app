package org.example;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorTest {

    @Test
    void callElevatorToALowerFloor() throws InterruptedException {
        Elevator elevator = new Elevator(3);
        int destinationFloor = 0;
        int secondsPerFloor = 1;
        elevator.callElevator(destinationFloor, secondsPerFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

    @Test
    void callElevatorToAHigherFloor() throws InterruptedException {
        Elevator elevator = new Elevator(1);
        int destinationFloor = 3;
        int secondsPerFloor = 1;

        elevator.callElevator(destinationFloor, secondsPerFloor);

        assertEquals(destinationFloor, elevator.getCurrentFloor());
    }

}
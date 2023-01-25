package org.example;

import java.sql.*;
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

        ElevatorStatus status = this.getStatus();

        String log = "Elevator " + this.elevatorLetter + " is at floor " + status.getCurrentFloor() + " and is " + status.getState() + " " + status.getDirection();
        System.out.println(log);
        persistLog(log, "Floor " + destinationFloor);

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

                log = "Elevator " + this.elevatorLetter + " is at floor " + status.getCurrentFloor() + " and is " + status.getState() + " " + status.getDirection();
                System.out.println(log);
                persistLog(log, "Floor " + destinationFloor);

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

                log = "Elevator " + this.elevatorLetter + " is at floor " + status.getCurrentFloor() + " and is " + status.getState() + " " + status.getDirection();
                System.out.println(log);
                persistLog(log, "Floor " + destinationFloor);

            }
        }

        openDoors();
        closeDoors();
    }

    public ElevatorStatus getStatus() {
        return new ElevatorStatus(this.currentFloor, this.state, this.direction);
    }

    public void openDoors() throws InterruptedException {
        if (this.doors.equals("closed")) {
            String log = "Elevator " + this.elevatorLetter + " door opening...";
            ElevatorStatus status = this.getStatus();

            persistLog(log, "Floor " + status.getCurrentFloor());

            TimeUnit.SECONDS.sleep(2);
            this.doors = "open";
        }
    }

    public void closeDoors() throws InterruptedException {
        if (this.doors.equals("open")) {
            String log = "Elevator " + this.elevatorLetter + " door closing...";
            ElevatorStatus status = this.getStatus();

            persistLog(log, "Floor " + status.getCurrentFloor());

            TimeUnit.SECONDS.sleep(2);
            this.doors = "closed";
        }
    }

    public void persistLog(String log, String madeFrom) {
        String username = Config.loadDBCredentials().get("username");
        String password = Config.loadDBCredentials().get("password");
        String database_url = Config.loadDBCredentials().get("database_url");
        String database_driver = Config.loadDBCredentials().get("database_driver");

        Connection connection;

        try {
            Class.forName(database_driver);
            connection = DriverManager.getConnection(database_url, username, password);

            if (connection != null) {
                String sql = "INSERT INTO query_log (log, made_from, made_at) VALUES (?,?,?)";

                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, log);
                statement.setString(2, madeFrom);
                statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                statement.executeUpdate();
            } else {
                System.out.println("Connection Failed!");
            }

            assert connection != null;
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example;

public class AdventureSetup {

    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;

    public String getStartingRoom() {
        return startingRoom;
    }

    public void setStartingRoom(String room) {
        startingRoom = room;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }
}

package com.example;

public class Layout {

    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Player player;
    private Monster[] monsters;

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

    public Player getPlayer() {
        return player;
    }

    public Monster[] getMonsters() {
        return monsters;
    }

    public void getPlayerInfo() {
        System.out.println("Player Level: " + player.getLevel());
        System.out.println("Player Attack: " + player.getAttack());
        System.out.println("Player Defense: " + player.getDefense());
        System.out.println("Player Health: " + player.getHealth());
    }
}

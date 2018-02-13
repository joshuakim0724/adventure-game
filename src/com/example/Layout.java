package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Layout {

    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Player player;
    private Monster[] monsters;
//    private Map<String, Item> allItemsInGame = new HashMap();

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

    //    public void setUpHashMap() {
//        for (Room room : rooms) {
//            Item[] items = room.getItems();
//            for (Item item : items) {
//                String itemName = item.getName();
//                allItemsInGame.put(itemName, item);
//            }
//        }
//    }
    public void getPlayerInfo() {
        System.out.println("Player Level: " + player.getLevel());
        System.out.println("Player Attack: " + player.getAttack());
        System.out.println("Player Defense: " + player.getDefense());
        System.out.println("Player Health: " + player.getHealth());
    }
}
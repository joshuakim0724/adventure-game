package com.example;
import java.lang.StringBuffer;

public class Layout {

    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;
    private Player player;
    private Monster[] monsters;
    private boolean monstersExist;

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

    public void printListOfMonsters() {
        StringBuffer monsterOutput;
        monsterOutput = new StringBuffer("Monsters ");
        if (monsters == null || monsters.length == 0) {
            System.out.println("There are no monsters in this room");
            monstersExist = false;
            return;
        }
        for (int i = 0; i < monsters.length; i++) {
            if (i == monsters.length - 1) {
                monsterOutput.append(monsters[i]);
                monsterOutput.append("\n");
            } else {
                monsterOutput.append(monsters[i]);
                monsterOutput.append(", ");
            }
        }
        monstersExist = true;
    }
}

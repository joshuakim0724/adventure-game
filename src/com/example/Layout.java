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

    public void setArrayList() {
        player.setUpArrayList();
        for (Room room : rooms) {
            room.setUpArrayList();
        }
    }

    public void setMonsterMaxHealth() {
        for (Monster monster : monsters) {
            monster.setMaxHealth(monster.getHealth());
        }
    }

    /**
     * Helper Method
     * @param monsterInput String input that is name of the monster
     * @return monster from a string input
     */
    public Monster getMonster(String monsterInput) {
        for (Monster monster : monsters) {
            String monsterName = monster.getName();
            if (monsterInput.equalsIgnoreCase(monsterName)) {
                return monster;
            }
        }
        return null;
    }

    public Room getRoomFromDirection(Direction direction) {
        for (Room room : rooms) {
            if (room.getName().equals(direction.getRoom())) {
                return room;
            }
        }
        return null;
    }

    public Room getRoomFromName(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }
}
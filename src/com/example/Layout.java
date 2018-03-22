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

    /**
     * Sets up all the arrayList for Rooms and Player
     */
    public void setupArrayList() {
        player.setUpArrayList();
        for (Room room : rooms) {
            room.setUpArrayList();
        }
    }

    /**
     * Used to set the max health of all the monsters
     */
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

    /**
     * Gets the room from the direction given
     * @param direction that user will input
     * @return Room that is found from the given direction, null otherwise
     */
    public Room getRoomFromDirection(Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DIRECTION);
        }
        for (Room room : rooms) {
            if (room.getName().equals(direction.getRoom())) {
                return room;
            }
        }
        return null;
    }

    /**
     * Gets the room from the direction given
     * @param roomName that user will input
     * @return Room that is found from the given name, null otherwise
     */
    public Room getRoomFromName(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }
}
package com.example;
import java.util.*;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private Item[] items;
    private String[] monstersInRoom;

    private ArrayList<Item> itemsArrayList = new ArrayList<Item>();
    private ArrayList<String> monstersArrayList = new ArrayList<String>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public Item[] getItems() {
        return items;
    }

    public String[] getMonstersInRoom() {
        return monstersInRoom;
    }

    /**
     * This will setup the Items ArrayList and Monsters ArrayList
     */
    public void setUpArrayList() {
        if (items != null) {
            Collections.addAll(itemsArrayList, items);
        }
        if (monstersInRoom != null) {
            Collections.addAll(monstersArrayList, monstersInRoom);
        }
    }

    /**
     * @return all the available directions from this room
     * Will return Empty string if monsters exist
     */
    public String getDirectionsAvailable() {
        if (monstersExist()) {
            return "";
        }
        StringBuilder directionList = new StringBuilder(GameConstants.CAN_GO_TO);

        for (int i = 0; i < directions.length; i ++) {
            if (i != directions.length - 1) {
                directionList.append(directions[i].getDirectionName()).append(", ");
            }
            else {
                directionList.append(directions[i].getDirectionName());
            }
        }
        return directionList.toString();
    }

    /**
     * @return all the available Items in this room
     * Will return Empty string if monsters exist
     */
    public String getItemsAvailable() {
        StringBuilder itemList = new StringBuilder(GameConstants.ROOM_CONTAINS);

        if (itemsArrayList.size() == 0) {
            return GameConstants.NOTHING_IN_ROOM;
        }

        for (int i = 0; i < itemsArrayList.size(); i ++) {
            if (i != itemsArrayList.size() - 1) {
                itemList.append(itemsArrayList.get(i).getName()).append(", ");
            }
            else {
                itemList.append(itemsArrayList.get(i).getName());
            }
        }
        return itemList.toString();
    }

    /**
     * Helper method used to see if monsters exist
     * @return true if monsters exist (arrayList.size() is greater than 0)
     */
    public boolean monstersExist() {
        return monstersArrayList.size() != 0;
    }

    /**
     * @return all the monsters in the room
     */
    public String getMonstersAvailable() {
        StringBuilder monsterList = new StringBuilder(GameConstants.ROOM_CONTAINS_MONSTERS);

        if (!monstersExist()) {
            return GameConstants.NO_MONSTERS;
        }

        for (int i = 0; i < monstersArrayList.size(); i ++) {
            if (i != monstersArrayList.size() - 1) {
                monsterList.append(monstersArrayList.get(i)).append(", ");
            }
            else {
                monsterList.append(monstersArrayList.get(i));
            }
        }
        return monsterList.toString();
    }
    /**
     * This method makes sure the direction is a valid direction
     * @param directionInput This is the direction user has inputed
     * @return true if it is valid, false if it is not
     */
    public Direction getDirection(String directionInput) {
        if (directionInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DIRECTION);
        }
        if (monstersExist()) {
            System.out.println(GameConstants.MONSTERS_EXIST + GameConstants.CANT_MOVE);
            return null;
        }
        for (Direction direction : directions) {
            String directionName = direction.getDirectionName();
            if (directionName.equalsIgnoreCase(directionInput)) {
                return direction;
            }
        }
        return null;
    }

    /**
     * Method will put user in "Duel" state if it is a valid duel
     * @param monsterName Monster name that user wants to duel
     * @return true if a valid duel, false if not
     */
    public boolean validDuel(String monsterName) {
        for (String aMonstersInRoom : monstersInRoom) {
            if (monsterName.equalsIgnoreCase(aMonstersInRoom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method makes sure the item is a valid pickup
     * @param itemInput This is the String input that the user will enter into the scanner
     * @return true if it is valid, false if it is not. Boolean only for testing purposes
     */
    public boolean takeItem(Player player, String itemInput) {
        if (itemInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        if (player == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_PLAYER);
        }
        if (monstersExist()) {
            System.out.println(GameConstants.MONSTERS_EXIST + GameConstants.CANT_TAKE);
            return false;
        }
        for (int i = 0; i < itemsArrayList.size(); i++) {
            String itemName = itemsArrayList.get(i).getName();
            Item item = itemsArrayList.get(i);
            if (itemInput.equalsIgnoreCase(itemName)) {
                player.addItem(item);
                itemsArrayList.remove(item);
                return true;
            }
        }
        return false;
    }

    /**
     * This method makes sure item is a valid drop
     * @param player Where the item is dropping from
     * @param itemInput This is the String input user will enter for which item to drop
     * @return true if is valid drop, false if it is not
     */
    public boolean dropItem(Player player, String itemInput) {
        if (player == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_PLAYER);
        }
        if (itemInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }

        for (Item anItemList : player.getItemsArray()) {
            String itemName = anItemList.getName();

            if (itemInput.equalsIgnoreCase(itemName)) {
                player.removeItem(anItemList);
                itemsArrayList.add(anItemList);
                return true;
            }
        }
        return false;
    }

    /**
     * Will remove monster from the room
     * @param monsterName monster name
     */
    public boolean removeMonsterFromRoom(String monsterName) {
        if (monsterName == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        return monstersArrayList.remove(monsterName);
    }
}

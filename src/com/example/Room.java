package com.example;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private Item[] items;
    private ArrayList<Item> itemsList;
    private String[] monstersInRoom;
    private Map<String, Item> itemMap = new HashMap();
    private boolean mapIsSetup = false;

    private static final String ROOM_CONTAINS = "This room contains monsters: ";
    private static final String NO_MONSTERS = "There are no monsters in this room";

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

    public boolean printListOfMonsters() {
        StringBuffer monsterOutput;
        monsterOutput = new StringBuffer(ROOM_CONTAINS);
        if (monstersInRoom == null || monstersInRoom.length == 0) {
            System.out.println(NO_MONSTERS);
            return false;
        }
        for (int i = 0; i < monstersInRoom.length; i++) {
            if (i == monstersInRoom.length - 1) {
                monsterOutput.append(monstersInRoom[i]);
            } else {
                monsterOutput.append(monstersInRoom[i]);
                monsterOutput.append(", ");
            }
        }

        System.out.println(monsterOutput);
        return true;
    }

    public boolean addRoomItemsToMap() {
        if (!mapIsSetup) {
            for (Item item : items) {
                String itemName = item.getName();
                addToMap(itemName, item);
            }
            mapIsSetup = true;
        }
        return true;
    }

    private void addToMap(String itemName, Item item) {
        itemMap.put(itemName, item);
    }

    public Item getItemFromMap(String itemName) {
        return itemMap.get(itemName);
    }

    /**
     * This method will add an item to the Item List
     * @param userInput This is the String input user will enter for which item to add
     */
    public void addItemToRoom(Item userInput) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        String itemName = userInput.getName();
        itemMap.put(itemName, userInput);
        itemsList = new ArrayList<Item>(Arrays.asList(items));

        itemsList.add(userInput);

        items = itemsList.toArray(new Item[itemsList.size()]);
    }

    /**
     * Will remove item from the room
     * @param userInput item name
     */
    public void removeItemFromRoom(String userInput) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        itemsList = new ArrayList<Item>(Arrays.asList(items));

        Item newItem = itemMap.get(userInput);
        itemsList.remove(newItem);

        items = itemsList.toArray(new Item[itemsList.size()]);

    }

    /**
     * Will remove monster from the room
     * @param userInput monster name
     */
    public void removeMonsterFromRoom(String userInput) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        ArrayList<String> monstersList = new ArrayList<String>(Arrays.asList(monstersInRoom));

        monstersList.remove(userInput);

        monstersInRoom = monstersList.toArray(new String[monstersList.size()]);
    }
    /* No longer used because Items is now Item[] and not a String
    //https://stackoverflow.com/questions/157944/create-arraylist-from-array
    //Converted Array to an ArrayList
//        itemsList = new ArrayList<String>(Arrays.asList(items));
//        for (int j = 0; j < itemsList.size(); j++) {
//            if (itemsList.get(j) == null) {
//                itemsList.set(j, userInput);
//                wasAdded = true;
//                break;
//        if (!wasAdded) {
//            itemsList.add(userInput);
//        }
//
//        //https://stackoverflow.com/questions/9929321/converting-arraylist-to-array-in-java
//        //Converted ArrayList back to an Array
//        items = itemsList.toArray(new String[itemsList.size()]);
    /**
     * This method will remove an item from the Item List
     * @param userInput This is the String input user will enter for which item to remove
     */
//    public void removeItem(String userInput) {
//        if (userInput == null) {
//            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
//        }
//        itemsList = new ArrayList<String>(Arrays.asList(items));
//        for (int i = 0; i < itemsList.size(); i++) {
//            if (itemsList.get(i).equalsIgnoreCase(userInput)) {
//                itemsList.remove(i);
//                break;
//            }
//        }
//        items = itemsList.toArray(new String[itemsList.size()]);
//    }
}

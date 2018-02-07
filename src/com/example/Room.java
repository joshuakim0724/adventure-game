package com.example;
import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private String[] items;
    private ArrayList<String> itemsList;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public String[] getItems() {
        return items;
    }

    public void addItem(String userInput) {
        boolean wasAdded = false;
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        //https://stackoverflow.com/questions/157944/create-arraylist-from-array
        //Converted Array to an ArrayList
        itemsList = new ArrayList<String>(Arrays.asList(items));
        for (int j = 0; j < itemsList.size(); j++) {
            if (itemsList.get(j) == null) {
                itemsList.set(j, userInput);
                wasAdded = true;
                break;
            }
        }
        if (!wasAdded) {
            itemsList.add(userInput);
        }
        //https://stackoverflow.com/questions/9929321/converting-arraylist-to-array-in-java
        //Converted ArrayList back to an Array
        itemsList.toArray(items);
    }

    public void removeItem(String userInput) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        itemsList = new ArrayList<String>(Arrays.asList(items));
        for (int i = 0; i < itemsList.size(); i++) {
            if (itemsList.get(i).equalsIgnoreCase(userInput)) {
                itemsList.remove(i);
                break;
            }
        }
        itemsList.toArray(items);
    }
}

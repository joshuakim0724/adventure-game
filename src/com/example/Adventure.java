package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {
    private static URL url;
    private static AdventureSetup adventureSetup;

    private static final int OK_STATUS = 200;
    private static final String QUIT_GAME = "quit";
    private static final String EXIT_GAME = "exit";

    private static int itemIndex;
    private static boolean isFinished = false;
    private static boolean passedFirstRoom = false;
    private static ArrayList<String> carryingItems;
    private static Room[] rooms;

    //Main method that will run the actual game

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();
        carryingItems = new ArrayList<String>();
        /*
          https://github.com/zillesc/WashingtonPost
          https://echo360.org/lesson/G_0bf45e48-dfa7-488a-88db-4699e6468c8d_b0113c19
          -4871-4da9-bad8-9a5f6b017a2f_2018-02-01T12:30:00.000_2018-02-01T13:57:00.000/classroom#sortDirection=desc
          Classroom video link
          Looked at the lecture in class to figure out how to get a Json from a URL

          //https://stackoverflow.com/questions/31504123/unhandled-exception-java-net-malformedurlexception
         */
        try {
            final HttpResponse<String> stringHttpResponse;

            url = new URL(AdventureURL.JSON_LINK);

            stringHttpResponse = Unirest.get(AdventureURL.JSON_LINK).asString();
            // Check to see if the request was successful; if so, convert the payload JSON into Java objects
            if (stringHttpResponse.getStatus() == OK_STATUS) {
                String json = stringHttpResponse.getBody();
                AdventureSetup adventureSetup = gson.fromJson(json, AdventureSetup.class);
                String orginalStartingRoom = adventureSetup.getStartingRoom();
                while (!isFinished) {
                    if (!passedFirstRoom) {
                        System.out.println("Your starting room is: " + adventureSetup.getStartingRoom());
                    }
                    rooms = adventureSetup.getRooms();
                    for (Room room : rooms) {
                        if (room.getName().equals(adventureSetup.getStartingRoom())) {
                            System.out.println(room.getDescription());
                            if (!passedFirstRoom) {
                                System.out.println("Your journey beings here");
                            }
                            getItemsInRoom(room);
                            getAvailableDirections(room);
                            passedFirstRoom = true;


                            String originalInput = scan.nextLine();
                            String input = originalInput.toLowerCase();
                        /*
                        https://stackoverflow.com/questions/2932392/
                        java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
                        Used this to learn how to remove excess spaces for my input
                         */
                            input = input.trim().replaceAll(" +", " ");

                            if (input.equals(QUIT_GAME) || input.equals(EXIT_GAME)) {
                                System.out.println("Exiting Game");
                                return;
                            }
                            if (input.equals("list")) {
                                System.out.print("You are carrying ");
                                if (carryingItems.size() == 0 || carryingItems == null) {
                                    System.out.println("nothing");
                                }
                                for (int k = 0; k < carryingItems.size(); k++) {
                                    if (k == carryingItems.size() - 1) {
                                        System.out.println(carryingItems.get(k));
                                    } else {
                                        System.out.print(carryingItems.get(k) + ", ");
                                    }
                                }
                            }
                            //Allowing steal input :)
                            if (input.contains("take ") || input.contains("steal ")) {
                                if (validItemPickup(originalInput, room)) {
                                    itemIndex = input.indexOf(" ") + 1;
                                    carryingItems.add(input.substring(itemIndex));
                                    room.removeItem(input.substring(itemIndex));
                                }
                            }
                            if (input.contains("drop ")) {
                                if (isValidDrop(carryingItems, originalInput)) {
                                    itemIndex = input.indexOf(" ") + 1;
                                    for (int l = 0; l < carryingItems.size(); l++) {
                                        if (carryingItems.get(l).equalsIgnoreCase(input.substring(itemIndex))) {
                                            carryingItems.remove(l);
                                            room.addItem(input.substring(itemIndex));
                                            break;
                                        }
                                    }
                                }
                            }
                            //Allowing walk and run inputs for optional features
                            if (input.contains("go ") || input.contains("walk ") || input.contains("run ")) {
                                if (validDirection(originalInput, room)) {
                                    itemIndex = input.indexOf(" ") + 1;
                                    String directionInput = input.substring(itemIndex);
                                    for (int i = 0; i < room.getDirections().length; i++) {
                                        String directionName = room.getDirections()[i].getDirectionName();
                                        if (directionName.equalsIgnoreCase(directionInput)) {
                                            adventureSetup.setStartingRoom(room.getDirections()[i].getRoom());
                                            break;
                                        }
                                    }
                                }
                                break;
                            }

                            if (!input.contains("go ") && !input.contains("take ") && !input.contains("drop ")
                                    && !input.equals("list")) {
                                System.out.println("I don't understand '" + originalInput + "'");
                            }
                        }
                    }
                    if (adventureSetup.getEndingRoom().equals(adventureSetup.getStartingRoom())) {
                        isFinished = true;
                        System.out.println("You have reached your final destination!");
                    }
                    System.out.println(); //To separate the paragraphs of text
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }

    /**
     * This method prints out all the items available in the room
     * @param room is the room you are getting the items from
     */
    private static void getItemsInRoom(Room room) {
        boolean allValuesNull = true;
        if (room.getItems() == null || room.getItems().length == 0) {
            System.out.println("This room contains nothing");
        } else {
            System.out.print("This room contains ");
            for (int i = 0; i < room.getItems().length; i++) {
                if (room.getItems()[i] != null) {
                    allValuesNull = false;
                    if (i == room.getItems().length - 1) {
                        System.out.println(room.getItems()[i]);
                    } else {
                        System.out.print(room.getItems()[i]);
                        if (i + 1 < room.getItems().length && room.getItems()[i+1] != null) {
                            System.out.print(", ");
                        } else {
                            System.out.println();
                        }
                    }
                }
            }
            if (allValuesNull) {
                System.out.println("nothing");
            }
        }
    }

    /** +
     * This method prints out all the directions available from the room
     * @param room is the room you are getting available directions from
     */
    private static void getAvailableDirections(Room room) {
        System.out.print("From here, you can go: ");
        for (int j = 0; j < room.getDirections().length; j++) {
            if (j == room.getDirections().length - 1) {
                System.out.println(room.getDirections()[j].getDirectionName());
            } else if (j == room.getDirections().length - 2) {
                System.out.print(room.getDirections()[j].getDirectionName() + " or ");
            } else {
                System.out.print(room.getDirections()[j].getDirectionName() + ", ");
            }
        }
    }

    /**
     * This method makes sure the direction is a valid direction
     * @param userInput This is the String input that the user will enter into the scanner
     * @return true if it is valid, false if it is not
     */
    public static boolean validDirection(String userInput, Room room) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DIRECTION);
        }
        if (room == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ROOM);
        }
        String inputLowerCase = userInput.toLowerCase();
        /*
        https://stackoverflow.com/questions/2932392/
        java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        Used this to learn how to remove excess spaces for my input
         */
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        itemIndex = inputLowerCase.indexOf(" ") + 1;
        String directionInput = inputLowerCase.substring(itemIndex);

        //For optional features, allowing run and walk inputs
        if (!inputLowerCase.contains("go ") && !inputLowerCase.contains("run ") &&
                !inputLowerCase.contains("walk ")) {
            return false;
        }
        for (int i = 0; i < room.getDirections().length; i++) {
            String directionName = room.getDirections()[i].getDirectionName();
            if (directionName.equalsIgnoreCase(directionInput)) {
                return true;
            }
        }
        System.out.println("I can't " + userInput);
        return false;
    }

    /** +
     * This method makes sure the item is a valid pickup
     * @param userInput This is the String input that the user will enter into the scanner
     * @return true if it is valid, false if it is not
     */
    public static boolean validItemPickup(String userInput, Room room) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        if (room == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ROOM);
        }
        String inputLowerCase = userInput.toLowerCase();
        //See comment in validDirection
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        itemIndex = inputLowerCase.indexOf(" ") + 1;
        String itemInput = inputLowerCase.substring(itemIndex);

        if (!inputLowerCase.contains("take ") && !inputLowerCase.contains("steal ")) {
            return false;
        }
        for (int i = 0; i < room.getItems().length; i++) {
            String directionName = room.getItems()[i];
            if (directionName.equalsIgnoreCase(itemInput)) {
                return true;
            }
        }
        System.out.println("I can't " + userInput);
        return false;
    }

    /** +
     * This method makes sure item is a valid drop
     * @param list ArrayList that item is dropping from
     * @param userInput This is the String input user will enter for which item to drop
     * @return true if is valid drop, false if it is not
     */
    public static boolean isValidDrop(ArrayList<String> list, String userInput) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        if (list == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ARRAY);

        }
        userInput = userInput.toLowerCase().trim().replaceAll(" +", " ");

        if(!userInput.contains("drop ")) {
            return false;
        }
        int itemIndex = userInput.indexOf(" ") + 1;
        String newInput = userInput.substring(itemIndex);
        for (String aList : list) {
            if (aList.equalsIgnoreCase(newInput)) {
                return true;
            }
        }
        System.out.println("I can't " + userInput);
        return false;
    }
}
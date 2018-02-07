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
    private static boolean isFinished = false;
    private static boolean passedFirstRoom = false;
    private static Room[] rooms;
    private static final int OK_STATUS = 200;
    private static final String QUIT_GAME = "quit";
    private static final String EXIT_GAME = "exit";
    private static ArrayList<String> carryingItems;
    private static int itemIndex;


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();
        carryingItems = new ArrayList<String>();
        /**
         * https://github.com/zillesc/WashingtonPost
         * https://echo360.org/lesson/G_0bf45e48-dfa7-488a-88db-4699e6468c8d_b0113c19
         * -4871-4da9-bad8-9a5f6b017a2f_2018-02-01T12:30:00.000_2018-02-01T13:57:00.000/classroom#sortDirection=desc
         * Classroom video link
         * Looked at the lecture in class to figure out how to get a Json from a URL
         *
         * //https://stackoverflow.com/questions/31504123/unhandled-exception-java-net-malformedurlexception
         */
        try {
            final HttpResponse<String> stringHttpResponse;

            url = new URL(AdventureURL.JSON_LINK);

            stringHttpResponse = Unirest.get(AdventureURL.JSON_LINK).asString();
            // Check to see if the request was successful; if so, convert the payload JSON into Java objects
            if (stringHttpResponse.getStatus() == OK_STATUS) {
                String json = stringHttpResponse.getBody();
                AdventureSetup adventureSetup = gson.fromJson(json, AdventureSetup.class);

                while (!isFinished) {
                    System.out.println("Your starting room is: " + adventureSetup.getStartingRoom());
                    rooms = adventureSetup.getRooms();
                    for (Room room : rooms) {
                        if (room.getName().equals(adventureSetup.getStartingRoom())) {
                            System.out.println(room.getDescription());
                            if (!passedFirstRoom) {
                                System.out.println("Your journey beings here");
                            }
                            passedFirstRoom = true;
                            getItemsInRoom(room);
                            getAvailableDirections(room);

                            String input = scan.nextLine().toLowerCase();
                        /*
                        https://stackoverflow.com/questions/2932392/
                        java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
                        Used this to learn how to remove excess spaces for my input
                         */
                            input = input.trim().replaceAll(" +", " ");
                            System.out.println(input);
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
                            if (input.contains("take ")) {
                                if (validItemPickup(input, room)) {
                                    itemIndex = input.indexOf(" ") + 1;
                                    carryingItems.add(input.substring(itemIndex));
                                    room.removeItem(input.substring(itemIndex));
                                }
                            }
                            if (input.contains("drop ")) {
                                if (isValidDrop(carryingItems, input)) {
                                    itemIndex = input.indexOf(" ") + 1;
                                    for (int l = 0; l < carryingItems.size(); l++) {
                                        if (carryingItems.get(l).equalsIgnoreCase(input.substring(itemIndex))) {
                                            carryingItems.remove(l);
                                            room.addItem(input.substring(itemIndex));
                                            break;
                                        }
                                    }
                                    room.addItem(input.substring(itemIndex));
                                }
                            }
                            if (input.contains("go ")) {
                                if (validDirection(input, room)) {
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
                                System.out.println("I don't understand '" + input + "'");
                            }
                        }
                    }
                    if (adventureSetup.getEndingRoom().equals(adventureSetup.getStartingRoom())) {
                        isFinished = true;
                        System.out.println("You reached the end room!");
                    }
                    System.out.println();
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }


    public static void getItemsInRoom(Room room) {
        if (room.getItems().length == 0) {
            System.out.println("This room contains nothing");
        } else {
            System.out.print("This room contains ");
            for (int i = 0; i < room.getItems().length; i++) {
                if (room.getItems()[i] != null) {
                    if (i == room.getItems().length - 1) {
                        System.out.println(room.getItems()[i]);
                    } else {
                        System.out.print(room.getItems()[i] + ", ");
                    }
                } else {
                    System.out.println("nothing");
                }
            }
        }
    }

    public static void getAvailableDirections(Room room) {
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
        String inputLowerCase = userInput.toLowerCase();
        /*
        https://stackoverflow.com/questions/2932392/
        java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        Used this to learn how to remove excess spaces for my input
         */
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        itemIndex = inputLowerCase.indexOf(" ") + 1;
        String directionInput = inputLowerCase.substring(itemIndex);

        if (!inputLowerCase.contains("go ")) {
            return false;
        }
        for (int i = 0; i < room.getDirections().length; i++) {
            String directionName = room.getDirections()[i].getDirectionName();
            if (directionName.equalsIgnoreCase(directionInput)) {
                return true;
            }
        }
        System.out.println("I can't go " + userInput);
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
        String inputLowerCase = userInput.toLowerCase();
        //See comment in validDirection
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        itemIndex = inputLowerCase.indexOf(" ") + 1;
        String itemInput = inputLowerCase.substring(itemIndex);

        if (!inputLowerCase.contains("take ")) {
            return false;
        }
        for (int i = 0; i < room.getItems().length; i++) {
            String directionName = room.getItems()[i];
            if (directionName.equalsIgnoreCase(itemInput)) {
                return true;
            }
        }
        System.out.println("I can't take " + userInput);
        return false;
    }
//        if (inputLowerCase.equals("take coin") || inputLowerCase.equals("take sweatshirt") ||
//                inputLowerCase.equals("take key") || inputLowerCase.equals("take pizza") ||
//                inputLowerCase.equals("take usb-c connector") ||
//                inputLowerCase.equals("take grading rubric") || inputLowerCase.equals("take bagel") ||
//                inputLowerCase.equals("take coffee") || inputLowerCase.equals("take pencil")) {
//            return true;
//        }

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
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(newInput)) {
                return true;
            }
        }
        System.out.println("I can't drop " + userInput);
        return false;
    }
}
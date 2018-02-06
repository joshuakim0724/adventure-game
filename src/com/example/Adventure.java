package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

public class Adventure {
    private static URL url;
    private static AdventureSetup adventureSetup;
    private static boolean isFinished = false;
    private static Room[] rooms;
    private static final int OK_STATUS = 200;
    private static final String QUIT_GAME = "quit";
    private static final String EXIT_GAME = "exit";
    private static ArrayList<String> carryingItems = new ArrayList<String>();
    private static int itemIndex;


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();
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

//                while (!isFinished) {
                System.out.println("Your starting room is: " + adventureSetup.getStartingRoom());
                rooms = adventureSetup.getRooms();
                for (Room name : rooms) {
                    if (name.getName().equals(adventureSetup.getStartingRoom())) {
                        System.out.println(name.getDescription());
                        System.out.println("Your journey beings here");

                        getItemsInRoom(name);
                        getAvailableDirections(name);

                        String input = scan.next().toLowerCase();
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
                            for (int k = 0; k < carryingItems.size(); k ++) {
                                if (k == carryingItems.size()- 1) {
                                    System.out.println(carryingItems.get(k));
                                } else {
                                    System.out.print(carryingItems.get(k) + ", ");
                                }
                            }
                        }
                        whichUserInput(input);
                    }
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }

    public static void whichUserInput(String input) {
        input = input.toLowerCase();
        if (input.contains("take ")) {
            if (validItemPickup(input)) {
                itemIndex = input.indexOf(" ") + 1;
                carryingItems.add(input.substring(itemIndex));
            }
        }
        if (input.contains("drop ")) {
            if (isValidDrop(carryingItems, input)) {
                itemIndex = input.indexOf(" ") + 1;
                carryingItems.add(input.substring(itemIndex));
                for (int l = 0; l < carryingItems.size(); l++) {
                    if (carryingItems.get(l).toLowerCase().equals(input.substring(itemIndex))) {
                        carryingItems.remove(l);
                        break;
                    }
                }
            }
        }
        if (input.contains("go ")) {

        }
    }
    public static void getItemsInRoom(Room room) {
        if (room.getItems().length == 0) {
            System.out.println("This room contains nothing");
        } else {
            System.out.print("This room contains ");
            for (int i = 0; i < room.getItems().length; i++) {
                if (i == room.getItems().length - 1) {
                    System.out.println(room.getItems()[i]);
                } else {
                    System.out.print(room.getItems()[i] + ", ");
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
    public static boolean validDirection(String userInput) {
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

        if (!inputLowerCase.contains("go ")) {
//            System.out.println("Please remember to put the word 'go' before the direction");
            return false;
        }
        //Note this part only works since I hard coded the options from the file
        if (inputLowerCase.equals("go north") || inputLowerCase.equals("go east") ||
                inputLowerCase.equals("go south") || inputLowerCase.equals("go west") ||
                inputLowerCase.equals("go northeast") ||
                inputLowerCase.equals("go up") || inputLowerCase.equals("go down")) {
            return true;
        } else {
            System.out.println("I can't go " + userInput);
            return false;
        }
    }

    /** +
     * This method makes sure the item is a valid pickup
     * @param userInput This is the String input that the user will enter into the scanner
     * @return true if it is valid, false if it is not
     */
    public static boolean validItemPickup(String userInput) {
        String inputLowerCase = userInput.toLowerCase();
        //See comment in validDirection
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");

        if (!inputLowerCase.contains("take ")) {
//            System.out.println("Please remember to put the word 'take' before the item");
            return false;
        }
        //Note this part only works since I hard coded the options from the file
        if (inputLowerCase.equals("take coin") || inputLowerCase.equals("take sweatshirt") ||
                inputLowerCase.equals("take key") || inputLowerCase.equals("take pizza") ||
                inputLowerCase.equals("take usb-c connector") ||
                inputLowerCase.equals("take grading rubric") || inputLowerCase.equals("take bagel") ||
                inputLowerCase.equals("take coffee") || inputLowerCase.equals("take pencil")) {
            return true;
        } else {
            System.out.println("I can't take " + userInput);
            return false;
        }
    }

    public static boolean isValidDrop(ArrayList<String> list, String input) {
        input = input.toLowerCase().trim().replaceAll(" +", " ");

        if(!input.contains("take ")) {
            return false;
        }
        int itemIndex = input.indexOf(" ") + 1;
        String newInput = input.substring(itemIndex);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toLowerCase().equals(newInput)) {
                return true;
            }
        }
        System.out.println("I can't drop " + input);
        return false;
    }
    //Don't actually use this, but have this to reference Zillecs code that he showed
//    static void makeApiRequest(String url) throws UnirestException, MalformedURLException {
//        final HttpResponse<String> stringHttpResponse;
//
//        // This will throw MalformedURLException if the url is malformed.
//        new URL(AdventureURL.JSON_LINK);
//
//        stringHttpResponse = Unirest.get(url).asString();
//        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
//        if (stringHttpResponse.getStatus() == 200) {
//            String json = stringHttpResponse.getBody();
//            Gson gson = new Gson();
//            final AdventureSetup adventureSetup = gson.fromJson(json, AdventureSetup.class);
//        }
//    }
}
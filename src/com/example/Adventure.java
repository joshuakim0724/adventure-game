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
    private static Layout adventureSetup;
    private static Gson gson = new Gson();

    private static final int OK_STATUS = 200;
    private static final int healthBars = 20;
    private static final String QUIT_GAME = "quit";
    private static final String EXIT_GAME = "exit";
    private static final String TAKE = "take ";
    private static final String STEAL = "steal ";
    private static final String LIST = "list";
    private static final String DROP = "drop ";
    private static final String GO = "go ";
    private static final String WALK = "walk ";
    private static final String RUN = "run ";
    private static final String CANT = "I can't ";
    private static final String NOTHING = "nothing";

    private static int inputIndex;
    private static boolean isFinished = false;
    private static boolean passedFirstRoom = false;
    private static ArrayList<String> carryingItems = new ArrayList<String>();
    private static boolean goInputed;
    private static boolean monstersExist;
    private static boolean inDuel;

    //Main method that will run the actual game

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        adventureSetup(AdventureURL.JSON_LINK);

                while (!isFinished) {

                    if (!passedFirstRoom) {
                        System.out.println(GameConstants.STARTING_ROOM + adventureSetup.getStartingRoom());
                    }

                    Room[] rooms = adventureSetup.getRooms();

                    for (Room room : rooms) {
                        if (room.getName().equals(adventureSetup.getStartingRoom())) {
                            System.out.println(room.getDescription());
                            if (!passedFirstRoom) {
                                System.out.println(GameConstants.JOURNEY_BEGINS);
                            }
                            getItemsInRoom(room);
                            monstersExist = room.printListOfMonsters();
                            getAvailableDirections(room);
                            passedFirstRoom = true;

                            String originalInput = scan.nextLine();

                            userInput(originalInput, room);
                            if (goInputed) {
                                break;
                            }
                        }
                    }
                    isGameOver();

                    System.out.println(); //To separate the paragraphs of text
                }
            }

    /**
     * Initializer
     * @param jsonLink The Json file link that the user wants to read
     */
    private static void adventureSetup (String jsonLink) {
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

            url = new URL(jsonLink);

            stringHttpResponse = Unirest.get(jsonLink).asString();
            // Check to see if the request was successful; if so, convert the payload JSON into Java objects
            if (stringHttpResponse.getStatus() == OK_STATUS) {
                String json = stringHttpResponse.getBody();
                adventureSetup = gson.fromJson(json, Layout.class);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println(ErrorConstants.NO_NETWORK_RESPONSE);
        } catch (MalformedURLException e) {
            System.out.println(ErrorConstants.BAD_URL + url);
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorConstants.INVALID_INPUT);
        }
    }

    /**
     * This method takes the user input and returns statement depending on the input
     * @param input User input that is go, take, drop etc.
     * @param room The room that the user is inputting for
     */
    private static void userInput(String input, Room room) {
        if (input == null) {
            throw new IllegalArgumentException(ErrorConstants.INVALID_INPUT);
        }
        if (room == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ROOM);
        }
        String originalInput = input;
        //
        //https://stackoverflow.com/questions/2932392/
        //java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        //Used this to learn how to remove excess spaces for my input
        //
        input = input.toLowerCase().replaceAll(" +", " ");
        goInputed = false;
        boolean invalidInput = true;

        if (input.equals(QUIT_GAME) || input.equals(EXIT_GAME)) {
            System.out.println(GameConstants.EXITING_GAME);
            return;
        }
        if (input.equals("playerinfo")) {
            adventureSetup.getPlayerInfo();
        }
        if (input.startsWith("duel ")) {
            inputIndex = input.indexOf(" ") + 1;
            String monsterInput = input.substring(inputIndex);
            Monster currentMonster = getMonster(monsterInput);
            if (currentMonster != null) {
                inDuel = true;
            } else {
                System.out.println(CANT + input);
            }
        }
        if (!inDuel) { //If in duel state can't use these commands
            if (input.equals(LIST)) {
                System.out.print(GameConstants.CARRYING);
                if (carryingItems == null || carryingItems.size() == 0) {
                    System.out.println(NOTHING);
                }
                for (int k = 0; k < carryingItems.size(); k++) {
                    if (k == carryingItems.size() - 1) {
                        System.out.println(carryingItems.get(k));
                    } else {
                        System.out.print(carryingItems.get(k) + ", ");
                    }
                }
                invalidInput = false;
            }
            //Allowing steal input
            if (input.startsWith(TAKE) || input.startsWith(STEAL)) {
                if (validItemPickup(originalInput, room)) {
                    inputIndex = input.indexOf(" ") + 1;
                    carryingItems.add(input.substring(inputIndex));
                    room.removeItem(input.substring(inputIndex));
                }
                invalidInput = false;
            }
            if (input.startsWith(DROP)) {
                if (isValidDrop(carryingItems, originalInput)) {
                    inputIndex = input.indexOf(" ") + 1;
                    for (int l = 0; l < carryingItems.size(); l++) {
                        if (carryingItems.get(l).equalsIgnoreCase(input.substring(inputIndex))) {
                            carryingItems.remove(l);
                            room.addItem(input.substring(inputIndex));
                            break;
                        }
                    }
                }
                invalidInput = false;
            }
            //Allowing walk and run inputs for optional features
            if (input.startsWith(GO) || input.startsWith(WALK) || input.startsWith(RUN)) {
                if (validDirection(originalInput, room)) {
                    inputIndex = input.indexOf(" ") + 1;
                    String directionInput = input.substring(inputIndex);
                    for (int i = 0; i < room.getDirections().length; i++) {
                        String directionName = room.getDirections()[i].getDirectionName();
                        if (directionName.equalsIgnoreCase(directionInput)) {
                            adventureSetup.setStartingRoom(room.getDirections()[i].getRoom());
                            break;
                        }
                    }
                }
                goInputed = true;
                invalidInput = false;
            }
        }

        if (invalidInput) {
            System.out.println(GameConstants.CANT_UNDERSTAND + originalInput);
        }
    }
    /**
     * This method prints out all the items available in the room
     * @param room is the room you are getting the items from
     */
    private static void getItemsInRoom(Room room) {
        boolean allValuesNull = true;
        if (room.getItems() == null || room.getItems().length == 0) {
            System.out.println(GameConstants.NOTHING_IN_ROOM);
        } else {
            System.out.print(GameConstants.ROOM_CONTAINS);
            for (int i = 0; i < room.getItems().length; i++) {
                if (i == room.getItems().length - 1) {
                    System.out.println(room.getItems()[i]);
                } else {
                    System.out.print(room.getItems()[i] + ", ");
                }
            }
        }
    }

    /** +
     * This method prints out all the directions available from the room
     * @param room is the room you are getting available directions from
     */
    private static void getAvailableDirections(Room room) {
        System.out.print(GameConstants.CAN_GO_TO);
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
        inputIndex = inputLowerCase.indexOf(" ") + 1;
        String directionInput = inputLowerCase.substring(inputIndex);

        //For optional features, allowing run and walk inputs
        if (!inputLowerCase.startsWith(GO) && !inputLowerCase.startsWith(RUN) &&
                !inputLowerCase.startsWith(WALK)) {
            return false;
        }
        for (int i = 0; i < room.getDirections().length; i++) {
            String directionName = room.getDirections()[i].getDirectionName();
            if (directionName.equalsIgnoreCase(directionInput)) {
                return true;
            }
        }
        System.out.println(CANT + userInput);
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
        if (monstersExist) {
            System.out.println(GameConstants.MONSTERS_EXIST);
            return false;
        }
        String inputLowerCase = userInput.toLowerCase();
        //See comment in validDirection
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        inputIndex = inputLowerCase.indexOf(" ") + 1;
        String itemInput = inputLowerCase.substring(inputIndex);

        if (!inputLowerCase.startsWith(TAKE) && !inputLowerCase.startsWith(STEAL)) {
            return false;
        }
        for (int i = 0; i < room.getItems().length; i++) {
            String directionName = room.getItems()[i];
            if (directionName.equalsIgnoreCase(itemInput)) {
                return true;
            }
        }
        System.out.println(CANT + userInput);
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

        if(!userInput.startsWith(DROP)) {
            return false;
        }
        int itemIndex = userInput.indexOf(" ") + 1;
        String newInput = userInput.substring(itemIndex);
        for (String aList : list) {
            if (aList.equalsIgnoreCase(newInput)) {
                return true;
            }
        }
        System.out.println(CANT + userInput);
        return false;
    }

    /**
     * Checks to see if the game is over by checking if the current room matches the end room
     */
    private static void isGameOver() { //boolean for testing purposes only
        String startingRoom = adventureSetup.getStartingRoom();
        String endingRoom = adventureSetup.getEndingRoom();
        if (startingRoom.equals(endingRoom)) {
            isFinished = true;
            System.out.println(GameConstants.FINAL_DESTINATION);
        }
    }

    private static boolean duel(String monster, Room room) {
        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }
        if (room == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ROOM);
        }
        for (int i = 0; i < room.getMonstersInRoom().length; i++) {
            String monsterName = room.getMonstersInRoom()[i];
            if (monsterName.equalsIgnoreCase((monster))) {
                inDuel = true;
                return true;
            }
        }
        return false;
    }

    private static boolean attack(Monster monster) {
        if (!inDuel) {
            return false;
        }

        double damageDone = adventureSetup.getPlayer().getAttack() - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;

        if (monsterHealth <= 0) {
            inDuel = false;
            return true;
        } else {
            monster.setHealth(monster.getHealth() - damageDone);
        }

        double monsterDamageDone = monster.getAttack() - adventureSetup.getPlayer().getDefense();
        double playerHealth = adventureSetup.getPlayer().getHealth() - monsterDamageDone;
        if (playerHealth > 0) {
            adventureSetup.getPlayer().setHealth(playerHealth);
        } else {
            System.out.println("You have died");
            System.exit(0);
        }

        return true;
    }

    private static boolean attackWithItem(Monster monster, String item) {
        if (!inDuel) {
            return false;
        }
        double damageDone = adventureSetup.getPlayer().getAttack() + - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;

        if (monsterHealth <= 0) {
            inDuel = false;
            return true;
        } else {
            monster.setHealth(monster.getHealth() - damageDone);
        }

        double monsterDamageDone = monster.getAttack() - adventureSetup.getPlayer().getDefense();
        double playerHealth = adventureSetup.getPlayer().getHealth() - monsterDamageDone;
        if (playerHealth > 0) {
            adventureSetup.getPlayer().setHealth(playerHealth);
        } else {
            System.out.println("You have died");
            System.exit(0);
        }
        return true;
    }

    public static void disegage() {
        inDuel = false;
    }

    public static void displayStatus(Monster monster) {
        double playerHealth = adventureSetup.getPlayer().getHealth();
        StringBuilder playerHealthOutput = new StringBuilder("Player: ");

        for (int i = 0; i < healthBars; i++) {
            if (playerHealth - 5 > 0) {
                playerHealth -= 5;
                playerHealthOutput.append("#");
            } else {
                playerHealthOutput.append("_");
            }
        }
        System.out.println(playerHealthOutput);

        double monsterHealth = monster.getHealth();
        StringBuilder monsterHealthOutput = new StringBuilder("Monster: ");

        for (int j = 0; j < healthBars; j++) {
            if (monsterHealth - 5 > 0) {
                monsterHealth -= 5;
                monsterHealthOutput.append("#");
            } else {
                monsterHealthOutput.append("_");
            }
        }
        System.out.println(monsterHealthOutput);
    }

    public static Monster getMonster(String monsterInput) {
        for (int i = 0; i < adventureSetup.getMonsters().length; i++) {
            String monsterName = adventureSetup.getMonsters()[i].getName();
            if (monsterInput.equalsIgnoreCase(monsterName)) {
                return adventureSetup.getMonsters()[i];
            }
        }
        return null;
    }
}
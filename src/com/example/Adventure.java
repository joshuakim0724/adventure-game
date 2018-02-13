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
    private static Monster currentMonster;
    private static Player player;

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

    private static int inputIndex;
    private static boolean isFinished = false;
    private static boolean passedFirstRoom = false;
//    private static ArrayList<String> carryingItems = new ArrayList<String>();
    private static boolean goInputed;
    private static boolean monstersExist;
    private static boolean inDuel;
    private static boolean wonDuel;

    //Main method that will run the actual game

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
//        adventureSetup(AdventureURL.JSON_LINK);
        adventureSetup = gson.fromJson(AdventureURL.SIEBEL, Layout.class);
        player = adventureSetup.getPlayer();
        try {
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
                        room.addRoomItemsToMap();
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
        } catch (IllegalArgumentException e) {
            System.out.println(ErrorConstants.INVALID_INPUT);
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
            invalidInput = false;
        }
        if (input.startsWith("duel ")) {
            inputIndex = input.indexOf(" ") + 1;
            String monsterInput = input.substring(inputIndex);
            currentMonster = getMonster(monsterInput);
            if (currentMonster != null) {
                inDuel = true;
            } else {
                System.out.println(CANT + input);
            }
            invalidInput = false;
        }
        if (!inDuel) { //If in duel state can't use these commands
            if (input.equals(LIST)) {
                player.printItems();
                invalidInput = false;
            }
            //Allowing steal input
            if (input.startsWith(TAKE) || input.startsWith(STEAL)) {
                if (itemPickup(originalInput, room)) {
                    invalidInput = false;
                }
            }
            if (input.startsWith(DROP)) {
                if (itemDrop(player.getItems(), originalInput, room)) {
                    invalidInput = false;
                }
            }
            //Allowing walk and run inputs for optional features
            if (input.startsWith(GO) || input.startsWith(WALK) || input.startsWith(RUN)) {
                if(validDirection(originalInput, room)) {
                    invalidInput = false;
                }
            }
        }
        if (inDuel) {
            if (input.startsWith("attack ") && !input.contains(" with ")) {
                attack(currentMonster);
                displayStatus(currentMonster);
            }
            if (input.startsWith("attack with ")) {
                int itemIndex = input.indexOf(" ") + 1;
                String itemName = input.substring(itemIndex);
                //Since there are two spaces, need to repeat twice to get the item name
                itemIndex = input.indexOf(" ") + 1;
                itemName = input.substring(itemIndex);
                attackWithItem(currentMonster, itemName);
                displayStatus(currentMonster);
            }
            if (input.equals("disengage")) {
                disengage();
            }
            if (input.equals("status")) {
                displayStatus(currentMonster);
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
                adventureSetup.setStartingRoom(room.getDirections()[i].getRoom());
                goInputed = true;
                return true;
            }
        }
        System.out.println(CANT + userInput);
        return false;
    }

    /** +
     * This method makes sure the item is a valid pickup
     * @param userInput This is the String input that the user will enter into the scanner
     * @return true if it is valid, false if it is not. Boolean only for testing purposes
     */
    public static boolean itemPickup(String userInput, Room room) {
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
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        inputIndex = inputLowerCase.indexOf(" ") + 1;
        String userItemName = userInput.substring(inputIndex);

        if (!inputLowerCase.startsWith(TAKE) && !inputLowerCase.startsWith(STEAL)) {
            return false;
        }
        for (int i = 0; i < room.getItems().length; i++) {
            String itemName = room.getItems()[i].getName();
            Item item = room.getItemFromMap(itemName);

            if (userItemName.equalsIgnoreCase(itemName)) {
                player.addItem(item);
                room.removeItemFromRoom(itemName);
                return true;
            }
        }
        System.out.println(CANT + userInput);
        return false;
    }

    /** +
     * This method makes sure item is a valid drop
     * @param itemList Item array that item is dropping from
     * @param userInput This is the String input user will enter for which item to drop
     * @return true if is valid drop, false if it is not
     */
    public static boolean itemDrop(Item[] itemList, String userInput, Room room) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_DROP);
        }
        if (itemList == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ARRAY);

        }

        String inputLowerCase = userInput.toLowerCase();
        inputLowerCase = inputLowerCase.trim().replaceAll(" +", " ");
        inputIndex = inputLowerCase.indexOf(" ") + 1;
        String userItemName = userInput.substring(inputIndex);

        if(!userInput.startsWith(DROP)) {
            return false;
        }

        for (Item anItemList : itemList) {
            String itemName = anItemList.getName();

            if (userItemName.equalsIgnoreCase(itemName)) {
                player.removeItem(anItemList);
                room.addItemToRoom(anItemList);
                return true;
            }
        }
        System.out.println(CANT + userInput);
        return false;
    }

    /**
     * Checks to see if the game is over by checking if the current room matches the end room
     */
    public static void isGameOver() { //boolean for testing purposes only
        String startingRoom = adventureSetup.getStartingRoom();
        String endingRoom = adventureSetup.getEndingRoom();
        if (startingRoom.equals(endingRoom)) {
            isFinished = true;
            System.out.println(GameConstants.FINAL_DESTINATION);
        }
    }

    public static boolean duel(String monster, Room room) {
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

    public static boolean attack(Monster monster) {
        if (!inDuel) {
            return false;
        }

        double damageDone = adventureSetup.getPlayer().getAttack() - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;
        System.out.println("You did " + damageDone + " damage");

        if (monsterHealth <= 0) {
            inDuel = false;
            wonDuel = true;
            return true;
        } else {
            monster.setHealth(monster.getHealth() - damageDone);
        }

        double monsterDamageDone = monster.getAttack() - adventureSetup.getPlayer().getDefense();
        double playerHealth = adventureSetup.getPlayer().getHealth() - monsterDamageDone;
        System.out.println("You received " + monsterDamageDone + " damage");

        if (playerHealth > 0) {
            adventureSetup.getPlayer().setHealth(playerHealth);
        } else {
            System.out.println("You have died");
            System.exit(0);
        }

        return true;
    }

    public static boolean attackWithItem(Monster monster, String item) {
        if (!inDuel) {
            return false;
        }
        double damageDone = adventureSetup.getPlayer().getAttack() + - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;
        System.out.println("You did " + damageDone + " damage");

        if (monsterHealth <= 0) {
            inDuel = false;
            wonDuel = true;
            giveEXP(monster);
            return true;
        } else {
            monster.setHealth(monster.getHealth() - damageDone);
        }

        double monsterDamageDone = monster.getAttack() - adventureSetup.getPlayer().getDefense();
        double playerHealth = adventureSetup.getPlayer().getHealth() - monsterDamageDone;
        System.out.println("You received " + monsterDamageDone + " damage");

        if (playerHealth > 0) {
            adventureSetup.getPlayer().setHealth(playerHealth);
        } else {
            System.out.println("You have died");
            System.exit(0);
        }
        return true;
    }

    public static boolean isValidItem(String itemInput) {
        for (int i = 0; i < adventureSetup.getPlayer().getItems().length; i++) {
            String itemName = adventureSetup.getPlayer().getItems()[i].getName();
            if (itemInput.equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public static void disengage() {
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

    public static void giveEXP(Monster monster) {
        int playerLevel = adventureSetup.getPlayer().getLevel();
        double currentPlayerExp = adventureSetup.getPlayer().getExp();
        double totalExpGained = currentPlayerExp +
                (monster.getAttack() + monster.getDefense()) / 2 + monster.getHealth() * 20; //formula for exp
        double leftOverExp;
        if (wonDuel) {
            while (totalExpGained > experienceNeeded(playerLevel)) {
                totalExpGained = totalExpGained - experienceNeeded(playerLevel);

                if (totalExpGained > 0) {
                    System.out.println("You leveled up!");
                    player.setLevel(playerLevel + 1); //level up
                    levelUp();
                    player.setHealth(player.getMaxHealth());
                } else {
                    leftOverExp = totalExpGained;
                    player.setExp(leftOverExp);
                }
            }
        }
        wonDuel = false;
    }

    private static double experienceNeeded(int playerLevel) {
        double expNeeded;

        if (playerLevel == 1) {
            expNeeded = 25;
            return expNeeded;
        }
        if (playerLevel == 2) {
            expNeeded = 50;
            return expNeeded;
        }
        return expNeeded = (experienceNeeded(playerLevel - 1) +
                experienceNeeded(playerLevel - 2)) * 1.1;
    }

    public static void levelUp() {
        player.setAttack(player.getAttack() * 1.5);
        player.setDefense(player.getDefense() * 1.5);
        player.setMaxHealth(player.getMaxHealth() * 1.3);
    }
}
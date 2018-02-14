package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

public class Adventure {
    private static URL url;
    private static Layout adventureSetup;
    private static Gson gson = new Gson();
    private static Monster currentMonster;
    private static Player player;

    private static final int OK_STATUS = 200;
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
    private static final int healthPerBar = 5;

    private static int inputIndex;
    private static boolean isFinished = false;
    private static boolean passedFirstRoom = false;
    private static boolean goInputed;
    private static boolean monstersExist;
    private static boolean inDuel = false;
    private static boolean wonDuel;

    //Main method that will run the actual game

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
//        adventureSetup(AdventureFilesAndURL.JSON_LINK); This is used to call Json from a link
        adventureSetup = gson.fromJson(AdventureFilesAndURL.SIEBEL, Layout.class); //Calls json from String
        player = adventureSetup.getPlayer();
        player.setMaxHealth(player.getHealth());
        adventureSetup.setMonsterMaxHealth();

        try {
            while (!isFinished) {

                if (!passedFirstRoom) {
                    System.out.println(GameConstants.STARTING_ROOM + adventureSetup.getStartingRoom());
                }

                Room[] rooms = adventureSetup.getRooms();

                for (Room room : rooms) {

                    if (room.getName().equals(adventureSetup.getStartingRoom())) {
                        if(!inDuel) {
                            System.out.println(room.getDescription());
                        }
                        if (!passedFirstRoom) {
                            System.out.println(GameConstants.JOURNEY_BEGINS);
                        }
                        room.addRoomItemsToMap();
                        if (!inDuel) {
                            getItemsInRoom(room);
                            monstersExist = room.printListOfMonsters();
                            getAvailableDirections(room);
                        }
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
        input = input.toLowerCase().trim().replaceAll(" +", " ");
        goInputed = false;
        boolean invalidInput = true;

        if (input.equals(QUIT_GAME) || input.equals(EXIT_GAME)) {
            System.out.println(GameConstants.EXITING_GAME);
            return;
        }
        if (input.equals("playerinfo")) {
            player.getPlayerInfo();
            invalidInput = false;
        }
        if (input.startsWith("duel ")) {
            validDuel(originalInput, room);
            invalidInput = false;
        }
        if (!inDuel) { //If in duel state can't use these commands
            if (input.equals(LIST)) {
                player.printItems();
                invalidInput = false;
            }
            //Allowing steal input
            if (input.startsWith(TAKE) || input.startsWith(STEAL)) {
                itemPickup(originalInput, room);
                invalidInput = false;

            }
            if (input.startsWith(DROP)) {
                itemDrop(player.getItems(), originalInput, room);
                invalidInput = false;

            }
            //Allowing walk and run inputs for optional features
            if (input.startsWith(GO) || input.startsWith(WALK) || input.startsWith(RUN)) {
                validDirection(originalInput, room);
                invalidInput = false;
            }
        }
        if (inDuel) {
            if (input.equalsIgnoreCase("attack")) {
                    wonDuel = attack(currentMonster);
                    if (wonDuel) {
                        room.removeMonsterFromRoom(currentMonster.getName());
                        currentMonster = null;
                    }
                displayStatus(currentMonster);
                invalidInput = false;
            }

            if (input.startsWith("attack with ")) {

                wonDuel = attackWithItem(currentMonster, input);
                if (wonDuel) {
                    room.removeMonsterFromRoom(currentMonster.getName());
                    currentMonster = null;
                }
                displayStatus(currentMonster);
                invalidInput = false;

            }
            if (input.equals("disengage")) {
                disengage();
                invalidInput = false;

            }
            if (input.equals("status")) {
                displayStatus(currentMonster);
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
        if (room.getItems() == null || room.getItems().length == 0) {
            System.out.println(GameConstants.NOTHING_IN_ROOM);
        } else {
            System.out.print(GameConstants.ROOM_CONTAINS);
            for (int i = 0; i < room.getItems().length; i++) {
                String itemName = room.getItems()[i].getName();
                if (i == room.getItems().length - 1) {
                    System.out.println(itemName);
                } else {
                    System.out.print(itemName + ", ");
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
        if (monstersExist) {
            System.out.println("There are still monsters here, I can't move");
            return false;
        }
        String inputLowerCase = userInput.toLowerCase();
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

    public static boolean validDuel(String userInput, Room room) {
        if (userInput == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }
        if (room == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ROOM);
        }
        inputIndex = userInput.indexOf(" ") + 1;
        String monsterInput = userInput.substring(inputIndex);

        if (getMonster(monsterInput) != null) {
            currentMonster = getMonster(monsterInput);
            for (int i = 0; i < room.getMonstersInRoom().length; i++) {
                String monsterName = room.getMonstersInRoom()[i];

                if (monsterName.equalsIgnoreCase((currentMonster.getName()))) {
                    System.out.println("You are now in a Duel");
                    inDuel = true;
                    return true;
                }
            }
        }
        System.out.println(CANT + userInput);
        return false;
    }

    public static boolean attack(Monster monster) {
        if (!inDuel) {
            System.out.println("In duel, can't use this command");
            return false;
        }

        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }

        double damageDone = adventureSetup.getPlayer().getAttack() - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;
        System.out.println("You did " + damageDone + " damage");

        if (monsterHealth < 0) {
            inDuel = false;
            System.out.println("You won your duel against " + monster.getName());
            giveEXP(monster);
            return true;
        } else {
            monster.setHealth(monsterHealth);
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

        return false;
    }

    public static boolean attackWithItem(Monster monster, String userInput) {

        int itemIndex = userInput.indexOf(" ") + 1;
        String itemName = userInput.substring(itemIndex);
        //Since there are two spaces, need to repeat twice to get the item name
        itemIndex = itemName.indexOf(" ") + 1;
        itemName = itemName.substring(itemIndex);

        Item item;

        if (!inDuel) {
            return false;
        }
        if (isValidItem(itemName)) {
            item = player.getItemFromMap(itemName);
        } else {
            System.out.println(CANT + itemName);
            return false;
        }

        double damageDone = adventureSetup.getPlayer().getAttack() + item.getDamage() - monster.getDefense();
        double monsterHealth = monster.getHealth() - damageDone;
        System.out.println("You did " + damageDone + " damage");

        if (monsterHealth < 0) {
            inDuel = false;
            System.out.println("You won your duel against " + monster.getName());
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
        int itemArraySize = adventureSetup.getPlayer().getItems().length;
        for (int i = 0; i < itemArraySize; i++) {
            String itemName = adventureSetup.getPlayer().getItems()[i].getName();
            if (itemInput.equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public static void disengage() {
        System.out.println("Retreating from fight");
        inDuel = false;
    }

    public static void displayStatus(Monster monster) {
        if (inDuel) {
            double playerHealth = adventureSetup.getPlayer().getHealth();
            StringBuilder playerHealthOutput = new StringBuilder("Player: ");
            //https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html#ceil-double-
            int numberOfHealthBars = (int) Math.ceil((player.getMaxHealth() / healthPerBar));

            for (int i = 0; i < numberOfHealthBars; i++) {
                if (playerHealth > 0) {
                    playerHealth -= healthPerBar;
                    playerHealthOutput.append("#");
                } else {
                    playerHealthOutput.append("_");
                }
            }
            System.out.println(playerHealthOutput);

            double monsterHealth = monster.getHealth();
            StringBuilder monsterHealthOutput = new StringBuilder("Monster: ");
            numberOfHealthBars = (int) Math.ceil((monster.getMaxHealth() / healthPerBar));

            for (int j = 0; j < numberOfHealthBars; j++) {
                if (monsterHealth > 0) {
                    monsterHealth -= healthPerBar;
                    monsterHealthOutput.append("#");
                } else {
                    monsterHealthOutput.append("_");
                }
            }
            System.out.println(monsterHealthOutput);
        }
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
                ((monster.getAttack() + monster.getDefense()) / 2 + monster.getMaxHealth()) * 20; //formula for exp

        System.out.println("You gained " + totalExpGained + " exp");
        while (totalExpGained > player.experienceNeeded(playerLevel)) {
            totalExpGained = totalExpGained - player.experienceNeeded(playerLevel);

            if (totalExpGained > 0) {
                System.out.println("You leveled up!");
                player.setLevel(playerLevel + 1); //level up
                player.levelUp();
                player.setHealth(player.getMaxHealth());
            }
            playerLevel = adventureSetup.getPlayer().getLevel();
            double leftOverExp = totalExpGained;
            player.setExp(leftOverExp);
        }
        wonDuel = false;
    }
}
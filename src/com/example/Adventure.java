package com.example;

import com.google.gson.Gson;

import java.util.Scanner;

public class Adventure {

    private static Layout layout;
    private static Player player;
    private static Room currentRoom;
    private static Monster monster;
    private static Scanner scanner;

    private static boolean inDuel = false;

    /**
     * Main function. Ends if player gets to ending room
     */
    public static void main() {
        adventureSetup();
        while (!currentRoom.getName().equalsIgnoreCase(layout.getEndingRoom())) {
            userInput(scanner);
        }
        System.out.println(GameConstants.FINAL_DESTINATION);
    }

    /**
     * This is used to setup the adventure before the game starts
     */
    public static void adventureSetup() {
        adventureSetupFromJson(JsonString.SIEBEL);
        scanner = new Scanner(System.in);
        player = layout.getPlayer();
        player.setMaxHealth(player.getHealth());
        layout.setupArrayList();
        layout.setMonsterMaxHealth();
        currentRoom = layout.getRoomFromName(layout.getStartingRoom());

        System.out.println(GameConstants.STARTING_ROOM + currentRoom.getName());
        System.out.println(GameConstants.JOURNEY_BEGINS);
        roomOutput();
    }

    /**
     * Reads the jsonFile from a String and Setups the Gson file
     * @param jsonFile is json String
     */
    private static void adventureSetupFromJson(String jsonFile) {
        Gson gson = new Gson();
        layout = gson.fromJson(jsonFile, Layout.class);
    }

    /**
     * Prints out Description, Items in room, Monsters in room, and Directions if monsters don't exist
     */
    private static void roomOutput() {
        System.out.println(currentRoom.getDescription());
        System.out.println(currentRoom.getItemsAvailable());
        System.out.println(currentRoom.getMonstersAvailable());
        System.out.println(currentRoom.getDirectionsAvailable());
    }

    /**
     * Uses Scanner. Depending on input it will use different methods
     */
    private static void userInput(Scanner scanner) {
        boolean regularCommandUnderstood;
        boolean duelCommandUnderstood = false;
        String originalInput = scanner.nextLine();
        String input = originalInput.trim().replaceAll(" +", " ").toLowerCase();
        String[] inputArray = input.split("\\s+");

        regularCommandUnderstood = regularCommands(inputArray);

        if (inDuel) {
            duelCommandUnderstood = duelCommands(inputArray);
        }

        if (!regularCommandUnderstood && !duelCommandUnderstood) {
            if (input.equals(GameConstants.ATTACK_INPUT) || input.startsWith(GameConstants.ATTACK_ITEM) ||
                    input.equals(GameConstants.DISENGAGE_INPUT) || input.equals(GameConstants.STATUS_INPUT)) {
                System.out.println(GameConstants.INVALID_METHOD_IN_DUEL);
            } else {
                System.out.println(GameConstants.CANT_UNDERSTAND + originalInput);
            }
        }
    }

    /**
     * Non duel commands
     */
    public static boolean regularCommands(String[] input) {
        String firstWord = input[0];

        if (input.length == 1) {
            return oneWordRegularCommands(firstWord);
        } else if (input.length == 2) {
            String secondWord = input[1];
            return twoWordRegularCommands(firstWord, secondWord);
        }
        return false;
    }

    /**
     * Regular commands that are one word
     * @param firstWord is the user command
     * @return true if valid input, false if not
     */
    public static boolean oneWordRegularCommands(String firstWord) {
        switch (firstWord) {
            case GameConstants.QUIT_GAME:
                System.out.println(GameConstants.EXITING_GAME);
                System.exit(0);

            case GameConstants.EXIT_GAME:
                System.out.println(GameConstants.EXITING_GAME);
                System.exit(0);

            case GameConstants.PLAYERINFO:
                System.out.println(player.getPlayerInfo());
                return true;

            case GameConstants.LIST_INPUT:
                System.out.println(player.getItemsList());
                return true;

            case GameConstants.ROOMINFO_INPUT:
                roomOutput();
                return true;

            default:
                return false;
        }
    }

    /**
     * Regular commands that are two words
     * @param firstWord user first command
     * @param secondWord user second command
     * @return true if valid inputs, false otherwise
     */
    public static boolean twoWordRegularCommands(String firstWord, String secondWord) {
        switch (firstWord) {
            case GameConstants.DUEL_INPUT:
                if (currentRoom.validDuel(secondWord)) {
                    inDuel = true;
                    monster = layout.getMonster(secondWord);
                    System.out.println(GameConstants.TIME_TO_DUEL);
                } else {
                    System.out.println(GameConstants.CANT_DUEL + secondWord);
                }
                return true;

            case GameConstants.GO_INPUT:
                if (currentRoom.getDirection(secondWord) != null) {
                    currentRoom = layout.getRoomFromDirection(currentRoom.getDirection(secondWord));
                    roomOutput();
                } else {
                    System.out.println(GameConstants.CANT_GO + secondWord);
                }
                return true;

            case GameConstants.TAKE_INPUT:
                if (currentRoom.takeItem(player, secondWord)) {
                    System.out.println(GameConstants.PICKED_UP + secondWord);
                } else {
                    System.out.println(GameConstants.CANT_TAKE_ITEM + secondWord);
                }
                return true;

            case GameConstants.DROP_INPUT:
                if (currentRoom.dropItem(player, secondWord)) {
                    System.out.println(GameConstants.DROPPED + secondWord);
                } else {
                    System.out.println(GameConstants.CANT_DROP + secondWord);
                }
                return true;

            default:
                return false;
        }
    }

    /**
     * Duel commands
     * @param input user input
     * @return true if valid duel command, false if not
     */
    public static boolean duelCommands(String[] input) {
        String firstWord = input[0];
        // Attack with Item Command, Max length input can be is 3
        if (input.length == 3) {
            String secondWord = input[1];
            String itemName = input[2];
            // Makes sure input is 'attack with'
            if (!firstWord.equals("attack") || !secondWord.equals("with")) {
                return false;
            }
            // Checks if item is valid
            if (player.isValidItem(itemName)) {
                if (player.attackWithItem(monster, itemName)) {
                    currentRoom.removeMonsterFromRoom(monster.getName());
                    inDuel = false;
                    monster = null;
                    roomOutput(); // Output the room info if monster is dead
                }
            } else {
                System.out.println(GameConstants.CANT_ATTACK_WITH + itemName);
            }
            return true;
        }

        switch (firstWord) {
            case GameConstants.ATTACK_INPUT:
                if (player.attack(monster)) {
                    currentRoom.removeMonsterFromRoom(monster.getName());
                    inDuel = false;
                    monster = null;
                    roomOutput(); // Output the room info if monster is dead
                }
                return true;

            case GameConstants.DISENGAGE_INPUT:
                inDuel = false;
                System.out.println(GameConstants.RUNAWAY);
                return true;

            case GameConstants.STATUS_INPUT:
                System.out.println(monster.displayStatus(player));
                return true;

            default:
                return false;
        }
    }
}
package com.example;

import com.google.gson.Gson;

import java.util.Scanner;

public class Adventure {

    private static Layout layout;
    private static Player player;
    private static Room currentRoom;
    private static Monster monster;

    private static boolean inDuel = false;

    public static void main(String[] args) {
        adventureSetup();
    }

    public static void adventureSetup() {
        adventureSetupFromJson(JsonString.SIEBEL);
        Scanner scanner = new Scanner(System.in);
        player = layout.getPlayer();
        layout.setArrayList();
        currentRoom = layout.getRoomFromName(layout.getStartingRoom());
    }

    /**
     * Reads the jsonFile from a String and Setups the Gson file
     * @param jsonFile is json String
     */
    private static void adventureSetupFromJson(String jsonFile) {
        Gson gson = new Gson();
        layout = gson.fromJson(jsonFile, Layout.class);
    }

    public static void userInput(Scanner scanner) {
        String originalInput = scanner.nextLine();
        String input = originalInput.trim().replaceAll(" +", " ").toLowerCase();
        String[] inputArray = input.split("\\s+");

        if (!regularCommands(inputArray) && !duelCommands(inputArray)) {
            System.out.println(GameConstants.CANT_UNDERSTAND + originalInput);
        }
        regularCommands(inputArray);
        if (inDuel) {
            duelCommands(inputArray);
        }
    }

    private static boolean regularCommands(String[] input) {
        String firstWord = input[0];

        if (input.length == 1) {
            return oneWordRegularCommands(firstWord);
        } else if (input.length == 2) {
            String secondWord = input[1];
            return twoWordRegularCommands(firstWord, secondWord);
        }
        return false;
    }

    private static boolean oneWordRegularCommands(String firstWord) {
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

            default:
                return false;
        }
    }

    private static boolean twoWordRegularCommands(String firstWord, String secondWord) {
        switch (firstWord) {
            case GameConstants.DUEL_INPUT:
                if (currentRoom.validDuel(secondWord)) {
                    inDuel = true;
                    monster = layout.getMonster(secondWord);
                    System.out.println(GameConstants.TIME_TO_DUEL);
                } else {
                    System.out.print(GameConstants.CANT_DUEL + secondWord);
                }
                return true;

            case GameConstants.GO_INPUT:
                if (currentRoom.getDirection(secondWord) != null) {
                    currentRoom = layout.getRoomFromDirection(currentRoom.getDirection(secondWord));
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

    private static boolean duelCommands(String[] input) {
        String firstWord = input[0];
        // Attack with Item Command, Max length input can be is 3
        if (input.length == 3) {
            String itemName = input[2];
            if (player.isValidItem(itemName)) {
                if (player.attackWithItem(monster, itemName)) {
                    currentRoom.removeMonsterFromRoom(monster.getName());
                    inDuel = false;
                    monster = null;
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
                } else {
                    System.out.println(GameConstants.CANT_ATTACK + firstWord);
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
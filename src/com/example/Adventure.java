package com.example;

import com.google.gson.Gson;

import java.util.Scanner;

public class Adventure {

    private static Layout layout;
    private static Player player;
    private static Room room;
    private static Monster monster;

    private static boolean inDuel = false;

    public static void main(String[] args) {

    }

    public static void adventureSetup() {
        adventureSetupFromGson(GameConstants.SIEBEL);
        Scanner scanner = new Scanner(System.in);
        player = layout.getPlayer();
    }

    /**
     * Reads the jsonFile from a String and Setups the Gson file
     * @param jsonFile is json String
     */
    private static void adventureSetupFromGson(String jsonFile) {
        Gson gson = new Gson();
        layout = gson.fromJson(jsonFile, Layout.class);
    }

    public static void userInput(Scanner scanner) {
        String input = scanner.nextLine();
        input = input.trim().replaceAll(" +", " ").toLowerCase();

        String[] inputArray = input.split("\\s+");

        regularCommands(inputArray);
        if (inDuel) {
            duelCommands(inputArray);
        }
    }

    private static void regularCommands(String[] input) {
        String firstWord = input[0];

        if (input.length == 1) {
            oneWordRegularCommands(firstWord);
        } else if (input.length == 2) {
            String secondWord = input[1];
            twoWordRegularCommands(firstWord, secondWord);
        }
    }

    private static void oneWordRegularCommands(String firstWord) {
        switch (firstWord) {
            case GameConstants.QUIT_GAME:
                System.out.println(GameConstants.EXITING_GAME);
                System.exit(0);

            case GameConstants.EXIT_GAME:
                System.out.println(GameConstants.EXITING_GAME);
                System.exit(0);

            case GameConstants.PLAYERINFO:
                System.out.println(player.getPlayerInfo());
                break;

            case GameConstants.LIST_INPUT:
                System.out.println(player.getItemsList());

            default:
                System.out.println(GameConstants.CANT_UNDERSTAND + firstWord);
        }
    }

    private static void twoWordRegularCommands(String firstWord, String secondWord) {
        switch (firstWord) {
            case GameConstants.DUEL_INPUT:
                if (room.validDuel(secondWord)) {
                    inDuel = true;
                    System.out.println(GameConstants.TIME_TO_DUEL);
                } else {
                    System.out.print(GameConstants.CANT_DUEL + secondWord);
                }
            case GameConstants.GO_INPUT:
                if (room.getDirection(secondWord) != null) {
                    room = layout.getRoomFromDirection(room.getDirection(secondWord));
                } else {
                    System.out.println(GameConstants.CANT_GO + secondWord);
                }
            case GameConstants.TAKE_INPUT:
                if (room.takeItem(player, secondWord)) {
                    System.out.println(GameConstants.PICKED_UP + secondWord);
                } else {
                    System.out.println(GameConstants.CANT_TAKE_ITEM + secondWord);
                }
            case GameConstants.DROP_INPUT:
                if (room.dropItem(player, secondWord)) {
                    System.out.println(GameConstants.DROPPED + secondWord);
                } else {
                    System.out.println(GameConstants.CANT_DROP + secondWord);
                }
        }
    }

    private static void duelCommands(String[] input) {

    }
}
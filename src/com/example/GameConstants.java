package com.example;

public class GameConstants {
    public static final String JOURNEY_BEGINS = "Your journey begins here";
    public static final String STARTING_ROOM = "Your starting room is ";
    public static final String CANT_UNDERSTAND = "I don't understand ";
    public static final String NOTHING_IN_ROOM = "This room contains nothing";
    public static final String ROOM_CONTAINS = "This room contains ";
    public static final String FINAL_DESTINATION = "You have reached your final destination!";
    public static final String CAN_GO_TO = "From here you can go: ";
    public static final String EXITING_GAME = "Existing Game";
    public static final String CARRYING = "You are carrying ";
    public static final String MONSTERS_EXIST = "There are still monsters here";
    public static final String CANT_MOVE = ", I can't move";
    public static final String NOTHING_OUTPUT = "nothing";
    public static final String INVALID_METHOD_IN_DUEL = "Can't use this method, not in duel";
    public static final String TIME_TO_DUEL = "You are now in a duel";
    public static final String DID = "You did ";
    public static final String RECEIVED = "You received ";
    public static final String GAINED = "You gained ";
    public static final String EXP = " exp";
    public static final String DAMAGE = " damage";
    public static final String WON_DUEL = "You won your duel again ";
    public static final String YOU_DEAD_BRO = "You have died";
    public static final String RUNAWAY = "Retreating from fight";
    public static final String LEVEL_UP = "You leveled up!";

    // User Inputs Below
    private static final String QUIT_GAME = "quit";
    private static final String EXIT_GAME = "exit";
    private static final String TAKE_INPUT = "take ";
    private static final String STEAL_INPUT = "steal ";
    private static final String LIST_INPUT = "list";
    private static final String DROP_INPUT = "drop ";
    private static final String GO_INPUT = "go ";
    private static final String WALK_INPUT = "walk ";
    private static final String RUN_INPUT = "run ";
    private static final String DUEL_INPUT = "duel ";
    private static final String ATTACK_INPUT = "attack";
    private static final String ATTACK_ITEM = "attack with ";
    private static final String DISENGAGE_INPUT = "disengage";
    private static final String STATUS_INPUT = "status";
    private static final String PLAYER = "Player: ";
    private static final String MONSTER = "Monster: ";

    private static final String PLAYERINFO = "playerinfo";
    private static final String CANT = "I can't ";

    public static final String SIEBEL = "{\n" +
            "  \"startingRoom\": \"MatthewsStreet\",\n" +
            "  \"endingRoom\": \"Siebel1314\",\n" +
            "  \"player\":\n" +
            "    {\n" +
            "      \"name\": \"Josh\",\n" +
            "      \"items\": [],\n" +
            "      \"attack\": 5,\n" +
            "      \"defense\": 5,\n" +
            "      \"health\": 20,\n" +
            "      \"level\": 1\n" +
            "    },\n" +
            "  \"monsters\": [\n" +
            "    {\n" +
            "      \"name\": \"Mihir\",\n" +
            "      \"attack\": 10,\n" +
            "      \"defense\": 5,\n" +
            "      \"health\": 100\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Murlock1\",\n" +
            "      \"attack\": 8,\n" +
            "      \"defense\": 1,\n" +
            "      \"health\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Murlock2\",\n" +
            "      \"attack\": 8,\n" +
            "      \"defense\": 1,\n" +
            "      \"health\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Murlock3\",\n" +
            "      \"attack\": 8,\n" +
            "      \"defense\": 1,\n" +
            "      \"health\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Murlock4\",\n" +
            "      \"attack\": 8,\n" +
            "      \"defense\": 1,\n" +
            "      \"health\": 10\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Reaper\",\n" +
            "      \"attack\": 20,\n" +
            "      \"defense\": 5,\n" +
            "      \"health\": 30\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Darkness\",\n" +
            "      \"attack\": 20,\n" +
            "      \"defense\": 15,\n" +
            "      \"health\": 100\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Witch\",\n" +
            "      \"attack\": 25,\n" +
            "      \"defense\": 0,\n" +
            "      \"health\": 20\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Hydra\",\n" +
            "      \"attack\": 25,\n" +
            "      \"defense\": 20,\n" +
            "      \"health\": 100\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Code Reviewer\",\n" +
            "      \"attack\": 30,\n" +
            "      \"defense\": 25,\n" +
            "      \"health\": 100\n" +
            "    }\n" +
            "    ],\n" +
            "  \"rooms\": [\n" +
            "    {\n" +
            "      \"name\": \"MatthewsStreet\",\n" +
            "      \"description\": \"You are on Matthews, outside the Siebel Center\",\n" +
            "      \"monstersInRoom\": [\"Murlock1\"],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "        \"name\": \"coin\",\n" +
            "        \"damage\": 4\n" +
            "      }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"East\",\n" +
            "          \"room\": \"SiebelEntry\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"SiebelEntry\",\n" +
            "      \"description\": \"You are in the west entry of Siebel Center.  You can see the elevator, the ACM office, and hallways to the north and east.\",\n" +
            "      \"monstersInRoom\": [],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"name\": \"sweatshirt\",\n" +
            "          \"damage\": 3\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"key\",\n" +
            "          \"damage\": 5\n" +
            "        }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"West\",\n" +
            "          \"room\": \"MatthewsStreet\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"Northeast\",\n" +
            "          \"room\": \"AcmOffice\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"North\",\n" +
            "          \"room\": \"SiebelNorthHallway\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"East\",\n" +
            "          \"room\": \"SiebelEastHallway\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"AcmOffice\",\n" +
            "      \"description\": \"You are in the ACM office.  There are lots of friendly ACM people.\",\n" +
            "      \"monstersInRoom\": [\"Murlock2\", \"Murlock3\", \"Murlock4\"],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"name\": \"pizza\",\n" +
            "          \"damage\": 2\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"swag\",\n" +
            "          \"damage\": 10\n" +
            "        }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"South\",\n" +
            "          \"room\": \"SiebelEntry\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"SiebelNorthHallway\",\n" +
            "      \"description\": \"You are in the north hallway.  You can see Siebel 1112 and the door toward NCSA.\",\n" +
            "      \"monstersInRoom\": [\"Reaper\"],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"South\",\n" +
            "          \"room\": \"SiebelEntry\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"NorthEast\",\n" +
            "          \"room\": \"Siebel1112\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Siebel1112\",\n" +
            "      \"description\": \"You are in Siebel 1112.  There is space for two code reviews in this room.\",\n" +
            "      \"monstersInRoom\": [\"Witch\"],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"name\": \"USB-C connector\",\n" +
            "          \"damage\": 1\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"grading rubric\",\n" +
            "          \"damage\": 5\n" +
            "        }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"West\",\n" +
            "          \"room\": \"SiebelNorthHallway\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"SiebelEastHallway\",\n" +
            "      \"description\": \"You are in the east hallway.  You can see Einstein Bros' Bagels and a stairway.\",\n" +
            "      \"monstersInRoom\": [\"Darkness\"],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"name\": \"bagel\",\n" +
            "          \"damage\": 3\n" +
            "        },\n" +
            "        {\n" +
            "          \"name\": \"coffee\",\n" +
            "          \"damage\": 10\n" +
            "        }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"West\",\n" +
            "          \"room\": \"SiebelEntry\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"South\",\n" +
            "          \"room\": \"Siebel1314\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"directionName\": \"Down\",\n" +
            "          \"room\": \"SiebelBasement\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"Siebel1314\",\n" +
            "      \"description\": \"You are in Siebel 1314.  There are happy CS 126 students doing a code review.\",\n" +
            "      \"monstersInRoom\": [\"Mihir\", \"Code Reviewer\"],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"North\",\n" +
            "          \"room\": \"SiebelEastHallway\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"name\": \"SiebelBasement\",\n" +
            "      \"description\": \"You are in the basement of Siebel.  You see tables with students working and door to computer labs.\",\n" +
            "      \"monstersInRoom\": [\"Hydra\"],\n" +
            "      \"items\": [\n" +
            "        {\n" +
            "          \"name\": \"pencil\",\n" +
            "          \"damage\": 5\n" +
            "        }\n" +
            "      ],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"Up\",\n" +
            "          \"room\": \"SiebelEastHallway\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}

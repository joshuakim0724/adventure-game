package com.example;

public class AdventureFilesAndURL {
    public static final String JSON_LINK = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";
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

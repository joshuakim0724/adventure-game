package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LayoutTest {
    Layout setup;
    Room[] rooms;
    Monster[] monsters;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        setup = JsonReader.adventureSetupFromFile(JsonString.SIEBEL);
        setup.setupArrayList();
        rooms = setup.getRooms();
        monsters = setup.getMonsters();
    }

    @Test
    public void getStartingRoom() {
        assertEquals("MatthewsStreet", setup.getStartingRoom());
    }

    @Test
    public void getEndingRoom() {
        assertEquals("Siebel1314", setup.getEndingRoom());
    }

    @Test
    public void getMonster() {
        Monster mihir = monsters[0];
        Monster codeReviewer = monsters[monsters.length - 1];

        assertEquals(setup.getMonster("Mihir").getName(), mihir.getName());
        assertEquals(setup.getMonster("Code Reviewer").getName(), codeReviewer.getName());
    }

    @Test
    public void getMonsterFail() {
        assertEquals(null, setup.getMonster("lol"));
        assertEquals(null, setup.getMonster(""));
    }

    @Test
    public void getRoomFromDirection() {
        Room room1 = rooms[0];
        Room room2 = rooms[1];
        Direction direction1 = room1.getDirections()[0];
        Direction direction2 = room2.getDirections()[1];

        assertEquals("SiebelEntry", setup.getRoomFromDirection(direction1).getName());
        assertEquals("AcmOffice", setup.getRoomFromDirection(direction2).getName());
    }

    @Test
    public void nullRoomItem() {
        String str = "";
        try {
            setup.getRoomFromDirection(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Direction Input", str);
    }

    @Test
    public void getRoomFromName() {
        Room room1 = rooms[0];
        Room room2 = rooms[1];

        assertEquals(room1, setup.getRoomFromName("MatthewsStreet"));
        assertEquals(room2, setup.getRoomFromName("SiebelEntry"));
    }
}
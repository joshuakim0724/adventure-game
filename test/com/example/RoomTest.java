package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Layout setup;
    private Room[] rooms;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        setup = gson.fromJson(AdventureFilesAndURL.SIEBEL, Layout.class);
        rooms = setup.getRooms();
    }
    @Test
    public void getNameTestTest() {
        Room room1 = rooms[0];
        Room room3 = rooms[2];

        assertEquals("MatthewsStreet", room1.getName());
        assertEquals("AcmOffice", room3.getName());
    }

    @Test
    public void getDescriptionTest() {
        Room room3 = rooms[2];
        Room room8 = rooms[7];

        assertEquals("You are in the ACM office.  " +
                "There are lots of friendly ACM people.", room3.getDescription());
        assertEquals("You are in the basement of Siebel.  " +
                "You see tables with students working and door to computer labs.", room8.getDescription());
    }

    @Test
    public void getMonstersInRoomTest() {
        Room room1 = rooms[0];
        Room room7 = rooms[6];

        assertEquals("Murlock1", room1.getMonstersInRoom()[0]);
        assertEquals("Mihir", room7.getMonstersInRoom()[0]);
        assertEquals("Code Reviewer", room7.getMonstersInRoom()[1]);
    }

    @Test
    public void printListOfMonstersTest() {
        Room room1 = rooms[0];
        Room room2 = rooms[1];

        assertEquals(false, room2.printListOfMonsters());
        assertEquals(true, room1.printListOfMonsters());
    }

    @Test
    public void itemMapTest() {
        Room room2 = rooms[1];
        Item item1 = room2.getItems()[0];
        Item item2 = room2.getItems()[1];
        Item coin = rooms[0].getItems()[0];

        assertEquals(true, room2.addRoomItemsToMap());
        assertEquals("sweatshirt", item1.getName());
        assertEquals(3, item1.getDamage(), DELTA);
        assertEquals("key", item2.getName());
        assertEquals(5, item2.getDamage(), DELTA);

        room2.addItemToRoom(coin);

        assertEquals(item1, room2.getItemFromMap("sweatshirt"));
        assertEquals(item2, room2.getItemFromMap("key"));
        assertEquals(coin, room2.getItemFromMap("coin"));

        room2.removeItemFromRoom("key");
    }
    public void addNullItem() {
        Room room3 = rooms[2];
        String str = "";
        try {
            room3.addItemToRoom(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Item Input", str);
    }
    public void dropNullItem() {
        Room room3 = rooms[2];
        String str = "";
        try {
            room3.removeItemFromRoom(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Drop Input", str);
    }
}

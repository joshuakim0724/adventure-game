package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

    private Room[] rooms;
    private Player player;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        Layout layout;
        layout = JsonReader.adventureSetupFromFile(JsonString.SIEBEL);
        layout.setupArrayList();
        rooms = layout.getRooms();
        player = layout.getPlayer();
    }
    @Test
    public void getDirectionsAvailable() {
        Room room1 = rooms[0];
        assertEquals("", room1.getDirectionsAvailable()); // Because monsters exist

        room1.removeMonsterFromRoom("Murlock1");
        String directions = "From here you can go: East";

        assertEquals(directions, room1.getDirectionsAvailable());
    }

    @Test
    public void getItemsAvailable() {
        Room room1 = rooms[0];
        String items = "This room contains coin";

        assertEquals(items, room1.getItemsAvailable());
    }

    @Test
    public void monstersExist() {
        Room room1 = rooms[0];

        assertTrue(room1.monstersExist());
        room1.removeMonsterFromRoom("Murlock1");
        assertFalse(room1.monstersExist());
    }

    @Test
    public void getMonstersAvailable() {
        Room room1 = rooms[0];
        Room room5 = rooms[6];
        String monsters = "This room contains monsters Murlock1";

        assertEquals(monsters, room1.getMonstersAvailable());
        monsters = "This room contains monsters Mihir, Code Reviewer";
        assertEquals(monsters, room5.getMonstersAvailable());
    }

    @Test
    public void getDirection() {
        Room room1 = rooms[0];

        room1.removeMonsterFromRoom("Murlock1");
        Direction direction = room1.getDirections()[0];

        assertEquals(direction, room1.getDirection("east"));
    }

    @Test
    public void validDuel() {
        Room room1 = rooms[0];

        assertTrue(room1.validDuel("Murlock1"));
    }

    @Test
    public void invalidDuel() {
        Room room1 = rooms[0];

        assertFalse(room1.validDuel("mom"));
        assertFalse(room1.validDuel("boss"));
        assertFalse(room1.validDuel(""));
    }
    @Test
    public void takeItem() {
        Room room1 = rooms[0];
        room1.removeMonsterFromRoom("Murlock1");

        assertTrue(room1.takeItem(player, "coin"));
        assertFalse(room1.takeItem(player, "coin"));
    }

    @Test
    public void dropItem() {
        Room room1 = rooms[0];
        room1.removeMonsterFromRoom("Murlock1");

        room1.takeItem(player, "coin");
        assertTrue(room1.dropItem(player, "coin"));
        assertFalse(room1.dropItem(player, "coin"));
    }
    
    // Tested within other methods
    @Test
    public void removeMonsterFromRoom() {
    }
}
package com.example;

import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdventureTest {

    private Layout setup;
    private Room[] rooms;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        setup = Adventure.adventureSetupFromFile(AdventureFilesAndURL.SIEBEL);
        rooms = setup.getRooms();
    }

    @Test
    public void main() {
    }

    @Test
    public void testValidDirection() {
        String strWithSpaces = "    go    east";
        String caseTest = "gO eAsT";
        String falseInput = "south";

        assertEquals(true, Adventure.validDirection(strWithSpaces, rooms[0]));
        assertEquals(true, Adventure.validDirection(caseTest, rooms[0]));
        assertEquals(false, Adventure.validDirection(falseInput, rooms[0]));
    }

    @Test
    public void nullInputDirection() {
        String str = "";
        try {
            boolean bool = Adventure.validDirection(null, rooms[0]);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Direction Input", str);
    }

    @Test
    public void nullRoomDirection() {
        String str = "";
        try {
            boolean bool = Adventure.validDirection("go east", null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Room Input", str);
    }

    @Test
    public void testItemPickup() {
        Room room2 = rooms[1];

        assertEquals(false, Adventure.itemPickup("take grading rubric", room2));
        assertEquals(false, Adventure.itemPickup("take sweat", room2));
        assertEquals(false, Adventure.itemPickup("take coin", room2));
    }

    @Test
    public void nullInputItem() {
        String str = "";
        try {
            boolean bool = Adventure.itemPickup(null, rooms[0]);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Item Input", str);
    }

    @Test
    public void nullRoomItem() {
        String str = "";
        try {
            boolean bool = Adventure.itemPickup("take pencil", null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Room Input", str);
    }

    @Test
    public void testValidItemDrop() {
        Room room2 = rooms[1];
        Item[] itemList = setup.getPlayer().getItems();

        String normalInput = "drop thisClass";
        String otherInput = "drop outOfSchool";

        assertEquals(false, Adventure.itemDrop(itemList, normalInput, room2));
        assertEquals(false, Adventure.itemDrop(itemList, otherInput, room2));
    }

    @Test
    public void testValidDuel() {
        Room room1 = rooms[0];
        Room room7 = rooms[6];

        String duelMurlock = "duel Murlock1";
        String duelMihir = "duel Mihir";
        String duelMihirPandya = "duel Mihir Pandya";
        String duelCodeReviewer = "duel Code Reviewer";

        assertEquals(true, Adventure.validDuel(duelMurlock, room1));
        assertEquals(true, Adventure.validDuel(duelMihir, room7));
        assertEquals(false, Adventure.validDuel(duelMihirPandya, room7));
        assertEquals(true, Adventure.validDuel(duelCodeReviewer, room7));
    }

    @Test
    public void testAttack() {
        Monster murlock1 = setup.getMonsters()[1];
        assertEquals(false, Adventure.attack(murlock1));
        assertEquals(false, Adventure.attack(murlock1));
    }

    @Test
    public void attackNullMonster() {
        String str = "";
        try {
            boolean bool = Adventure.attack((null));
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Monster Input", str);
    }

    @Test
    public void testAttackWithItem() {
        Monster murlock1 = setup.getMonsters()[1];

        assertEquals(false, Adventure.attackWithItem(murlock1, "attack with coin"));
        assertEquals(false, Adventure.attackWithItem(murlock1, "grading rubric"));
        assertEquals(false, Adventure.attackWithItem(murlock1, "nothing"));
    }

    @Test
    public void testIsValidItem() {
        Room room1 = rooms[0];
        Item item = room1.getItems()[0];
        Player player = setup.getPlayer();
        player.addItem(item);

        assertEquals(true, Adventure.isValidItem("coin"));
        assertEquals(false, Adventure.isValidItem("nothing"));

    }
}
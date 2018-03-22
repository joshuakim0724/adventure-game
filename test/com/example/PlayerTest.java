package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private Monster[] monsters;
    private Room[] rooms;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        Layout layout;
        layout = JsonReader.adventureSetupFromFile(JsonString.SIEBEL);
        layout.setupArrayList();
        layout.setMonsterMaxHealth();
        monsters = layout.getMonsters();
        rooms = layout.getRooms();
        player = layout.getPlayer();
        player.setMaxHealth(player.getHealth());
    }

    @Test
    public void addItem() {
        Item item = rooms[0].getItems()[0];

        assertEquals(0, player.getItemsArray().size());
        assertTrue(player.addItem(item));
        assertEquals(1, player.getItemsArray().size());
    }

    @Test
    public void removeItem() {
        Item item = rooms[0].getItems()[0];

        player.addItem(item);
        assertEquals(1, player.getItemsArray().size());
        assertTrue(player.removeItem(item));
        assertEquals(0, player.getItemsArray().size());
    }

    @Test
    public void removeItemFail() {
        Item item = rooms[0].getItems()[0];

        assertFalse(player.removeItem(item));
    }

    @Test
    public void getItemsList() {
        assertEquals("You are carrying nothing", player.getItemsList());

        Item item = rooms[0].getItems()[0];
        player.addItem(item);

        String itemList = "You are carrying coin";
        assertEquals(itemList, player.getItemsList());
    }

    @Test
    public void getPlayerInfo() {
        String playerInfo = "Player Level: 1\n" +
                "Player Attack: 5.0\n" +
                "Player Attack: 5.0\n" +
                "Player Attack: 20.0";

        assertEquals(playerInfo, player.getPlayerInfo());

        player.levelUp();
        playerInfo = "Player Level: 2\n" +
                "Player Attack: 7.5\n" +
                "Player Attack: 7.5\n" +
                "Player Attack: 26.0";

        assertEquals(playerInfo, player.getPlayerInfo());
    }

    @Test
    public void experienceNeeded() {
        assertEquals(25, player.experienceNeeded(1), 0.0001);
        assertEquals(50, player.experienceNeeded(2), 0.0001);
        assertEquals(82.5, player.experienceNeeded(3), 0.0001);
    }

    @Test
    public void attack() {
        Monster monster = monsters[4];

        assertFalse(player.attack(monster));
        assertFalse(player.attack(monster));
        // True because monster dies with next attack
        assertTrue(player.attack(monster));
    }

    @Test
    public void attackWithItem() {
        Monster monster = monsters[4];
        Item item = rooms[0].getItems()[0];
        player.addItem(item);

        assertFalse(player.attackWithItem(monster, "coin"));
        // True because monster dies with next attack
        assertTrue(player.attackWithItem(monster, "coin"));
    }
    @Test
    public void nullMonsterInput() {
        String str = "";
        try {
            player.attack(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Monster Input", str);
    }
    @Test
    public void nullMonsterInputAttackWith() {
        String str = "";
        try {
            player.attackWithItem(null, null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Monster Input", str);
    }

    @Test
    public void isValidItem() {
        Item item = rooms[0].getItems()[0];
        player.addItem(item);

        assertTrue(player.isValidItem("coin"));
    }

    @Test
    public void isNotValidItem() {
        assertFalse(player.isValidItem("coin"));
        assertFalse(player.isValidItem("mom"));
        assertFalse(player.isValidItem("test"));
    }

    @Test
    public void getItemIndex() {
        Item item = rooms[0].getItems()[0];
        player.addItem(item);

        assertEquals(0, player.getItemIndex("coin"));
    }
}
package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private static final double DELTA = 1e-15;
    private Player josh;
    private Layout setup;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        setup = gson.fromJson(AdventureFilesAndURL.SIEBEL, Layout.class);
        josh = setup.getPlayer();
        josh.setMaxHealth(josh.getHealth());

    }
    @Test
    public void testPlayerStats() {
        assertEquals("Josh", josh.getName());
        assertEquals(5, josh.getAttack(), DELTA);
        assertEquals(5, josh.getDefense(), DELTA);
        assertEquals(20, josh.getHealth(), DELTA);
        assertEquals(1, josh.getLevel());

        josh.levelUp();
        josh.setHealth(josh.getMaxHealth());

        assertEquals(7.5, josh.getAttack(), DELTA);
        assertEquals(7.5, josh.getDefense(), DELTA);
        assertEquals(26, josh.getHealth(), DELTA);
        assertEquals(2, josh.getLevel());
    }

    @Test
    public void testAddItem() {
        Room[] rooms = setup.getRooms();
        Room room1 = rooms[0];
        Item coin = room1.getItems()[0];

        josh.addItem(coin);
        assertEquals(coin, josh.getItemFromMap("coin"));
    }

    @Test
    public void addNullItem() {
        String str = "";
        try {
            josh.addItem(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Item Input", str);
    }

    @Test
    public void testExperienceNeeded() {
        assertEquals(25, josh.experienceNeeded(1), DELTA);
        assertEquals(50, josh.experienceNeeded(2), DELTA);
        assertEquals(82.5, josh.experienceNeeded(3), DELTA);

    }
}
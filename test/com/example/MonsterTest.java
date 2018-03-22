package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonsterTest {

    private Player player;
    private Monster[] monsters;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        Layout layout = JsonReader.adventureSetupFromFile(JsonString.SIEBEL);
        layout.setupArrayList();
        layout.setMonsterMaxHealth();
        monsters = layout.getMonsters();
        player = layout.getPlayer();
        player.setMaxHealth(player.getHealth());
    }
    @Test
    public void setMaxHealth() {
        Monster monster = monsters[0];
        assertEquals(100.0, monster.getHealth(), 0.0001);

        monster.setMaxHealth(500);
        assertEquals(500, monster.getMaxHealth(), 0.0001);
    }

    @Test
    public void displayStatus() {
        Monster monster = monsters[0];

        String status = "Player: ####\n" +
                "Monster: ####################";

        assertEquals(status, monster.displayStatus(player));
    }

    @Test
    public void nullPlayerInput() {
        String str = "";
        try {
            monsters[0].displayStatus(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Invalid Player Input", str);
    }
}
package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonsterTest {

    private Monster Mihir;
    private Monster Murlock1;
    private Monster Hydra;
    private Monster codeReviewer;
    private static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        Layout setup = gson.fromJson(AdventureFilesAndURL.SIEBEL, Layout.class);
        Monster[] monsters = setup.getMonsters();

        Mihir = monsters[0];
        Murlock1 = monsters[1];
        Hydra = monsters[8];
        codeReviewer = monsters[9];
    }
    @Test
    public void getName() {
        assertEquals("Mihir", Mihir.getName());
        assertEquals("Murlock1", Murlock1.getName());
        assertEquals("Hydra", Hydra.getName());
        assertEquals("Code Reviewer", codeReviewer.getName());
    }

    @Test
    public void getAttack() {
        assertEquals(10, Mihir.getAttack(), DELTA);
        assertEquals(8, Murlock1.getAttack(), DELTA);
        assertEquals(25, Hydra.getAttack(), DELTA);
        assertEquals(30, codeReviewer.getAttack(), DELTA);
    }

    @Test
    public void getDefense() {
        assertEquals(5, Mihir.getDefense(), DELTA);
        assertEquals(1, Murlock1.getDefense(), DELTA);
        assertEquals(20, Hydra.getDefense(), DELTA);
        assertEquals(25, codeReviewer.getDefense(), DELTA);
    }

    @Test
    public void getHealth() {
        assertEquals(100, Mihir.getHealth(), DELTA);
        assertEquals(10, Murlock1.getHealth(), DELTA);
        assertEquals(100, Hydra.getHealth(), DELTA);
        assertEquals(100, codeReviewer.getHealth(), DELTA);
    }
}
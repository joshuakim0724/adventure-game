package com.example;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdventureTest {

    @Before
    public void setUp() throws Exception {
        Adventure adventure = new Adventure();
        Adventure.adventureSetup();
    }

    @Test
    public void regularCommandsFail() {
        String[] input = {"I", "Don't", "know"};
        String[] input1 = {"playerinfo", "Don't"};

        assertFalse(Adventure.regularCommands(input));
        assertFalse(Adventure.regularCommands(input1));
    }

    // Note. For the actual input, userInput method puts the method to lowercase, and trims the input
    @Test
    public void oneWordRegularCommandsPass() {
        assertTrue(Adventure.oneWordRegularCommands("playerinfo"));
        assertTrue(Adventure.oneWordRegularCommands("list"));
        assertTrue(Adventure.oneWordRegularCommands("roominfo"));
    }
    @Test
    public void oneWordRegularCommandsFail() {
        assertFalse(Adventure.oneWordRegularCommands(""));
        assertFalse(Adventure.oneWordRegularCommands("lol"));
        assertFalse(Adventure.oneWordRegularCommands("player"));
        assertFalse(Adventure.oneWordRegularCommands("   work"));
    }
    @Test
    public void twoWordRegularCommandsPass() {
        assertTrue(Adventure.twoWordRegularCommands("duel", "monster"));
        assertTrue(Adventure.twoWordRegularCommands("go", "east"));
        assertTrue(Adventure.twoWordRegularCommands("take", "monster"));
        assertTrue(Adventure.twoWordRegularCommands("drop", "monster"));
    }
    @Test
    public void twoWordRegularCommandsFail() {
        assertFalse(Adventure.twoWordRegularCommands("", "monster"));
        assertFalse(Adventure.twoWordRegularCommands(" ", "east"));
        assertFalse(Adventure.twoWordRegularCommands("lol", "monster"));
        assertFalse(Adventure.twoWordRegularCommands(" failure", "monster"));
    }

    @Test
    public void duelCommandsPass() {
        Adventure.twoWordRegularCommands("duel", "murlock1");

        String[] attack = {"attack"};
        String[] attackWith = {"attack", "with", "coin"};
        String[] disengage = {"disengage"};
        String[] status = {"status"};

        assertTrue(Adventure.duelCommands(attack));
        assertTrue(Adventure.duelCommands(disengage));
        assertTrue(Adventure.duelCommands(status));
        assertTrue(Adventure.duelCommands(attackWith));
    }

    @Test
    public void duelCommandsFail() {
        Adventure.twoWordRegularCommands("duel", "murlock1");

        String[] attack = {"lol"};
        String[] attackWith = {"attack", "no", "coin"};
        String[] disengage = {"fail"};
        String[] status = {" lmao"};

        assertFalse(Adventure.duelCommands(attack));
        assertFalse(Adventure.duelCommands(disengage));
        assertFalse(Adventure.duelCommands(status));
        assertFalse(Adventure.duelCommands(attackWith));
    }
}
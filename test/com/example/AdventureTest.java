package com.example;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdventureTest {

    @Test
    public void main() {
    }

    @Test
    public void testValidDirection() {
        String strWithSpaces = "    go    north";
        String goSouth = "go south";
        String falseInput = "hello";

        assertEquals(true, Adventure.validDirection(strWithSpaces));
        assertEquals(true, Adventure.validDirection((goSouth)));
        assertEquals(false, Adventure.validDirection(falseInput));
    }

    @Test
    public void nullDirection() {
        String str = "";
        try {
            boolean bool = Adventure.validDirection(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Input", str);
    }
}
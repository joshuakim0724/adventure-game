package com.example;

import java.util.ArrayList;

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
        String caseTest = "gO nOrThEaSt";
        String falseInput = "hello";

        assertEquals(true, Adventure.validDirection(strWithSpaces));
        assertEquals(true, Adventure.validDirection((goSouth)));
        assertEquals(true, Adventure.validDirection(caseTest));
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
        assertEquals("Null Direction", str);
    }

    @Test
    public void testValidItemPickup() {
        String normalInput = "take coin";
        String strWithSpaces = "    take    pencil";
        String caseTest = "tAkE coFfEe";
        String falseInput = "nothing";

        assertEquals(true, Adventure.validItemPickup(normalInput));
        assertEquals(true, Adventure.validItemPickup(strWithSpaces));
        assertEquals(true, Adventure.validItemPickup((caseTest)));
        assertEquals(false, Adventure.validItemPickup(falseInput));
    }

    @Test
    public void nullItem() {
        String str = "";
        try {
            boolean bool = Adventure.validItemPickup(null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Item Input", str);
    }

    @Test
    public void testValidItemDrop() {
        ArrayList<String> itemArray = new ArrayList<String>();
        itemArray.add("pencil");
        itemArray.add("USB-C connector");
        itemArray.add("grading rubric");

        String normalInput = "drop pencil";
        String strWithSpaces = "    drop    USB-C connector";
        String caseTest = "dRoP gRaDiNg rUbRiC";
        String falseInput = "nothing";

        assertEquals(true, Adventure.isValidDrop(itemArray, normalInput));
        assertEquals(true, Adventure.isValidDrop(itemArray, strWithSpaces));
        assertEquals(true, Adventure.isValidDrop(itemArray, caseTest));
        assertEquals(false, Adventure.isValidDrop(itemArray, falseInput));
    }

    @Test
    public void nullInputDrop() {
        ArrayList<String> itemArray = new ArrayList<String>();
        String str = "";
        try {
            boolean bool = Adventure.isValidDrop(itemArray,null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Drop Input", str);
    }

    @Test
    public void nullArrayDrop() {
        String str = "";
        try {
            boolean bool = Adventure.isValidDrop(null,str);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Array Input", str);
    }
}
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

    private AdventureSetup setup;
    private Room[] room;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        final HttpResponse<String> stringHttpResponse;

        URL url = new URL(AdventureURL.JSON_LINK);

        stringHttpResponse = Unirest.get(AdventureURL.JSON_LINK).asString();
        String json = stringHttpResponse.getBody();
        setup = gson.fromJson(json, AdventureSetup.class);
        room = setup.getRooms();
    }

    @Test
    public void main() {
    }

    @Test
    public void testValidDirection() {
        String strWithSpaces = "    go    east";
        String caseTest = "gO eAsT";
        String falseInput = "south";

        assertEquals(true, Adventure.validDirection(strWithSpaces, room[0]));
        assertEquals(true, Adventure.validDirection(caseTest, room[0]));
        assertEquals(false, Adventure.validDirection(falseInput, room[0]));
    }

    @Test
    public void nullInputDirection() {
        String str = "";
        try {
            boolean bool = Adventure.validDirection(null, room[0]);
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
    public void testValidItemPickup() {
        String normalInput = "take coin";
        String strWithSpaces = "    take    pencil";
        String caseTest = "tAkE coFfEe";
        String falseInput = "nothing";

        assertEquals(true, Adventure.validItemPickup(normalInput, room[0]));
        assertEquals(true, Adventure.validItemPickup(strWithSpaces, room[7]));
        assertEquals(true, Adventure.validItemPickup(caseTest, room[5]));
        assertEquals(false, Adventure.validItemPickup(falseInput, room[0]));
    }

    @Test
    public void nullInputItem() {
        String str = "";
        try {
            boolean bool = Adventure.validItemPickup(null, room[0]);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Item Input", str);
    }

    @Test
    public void nullRoomItem() {
        String str = "";
        try {
            boolean bool = Adventure.validItemPickup("take pencil", null);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Room Input", str);
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
            boolean bool = Adventure.isValidDrop(null, str);
        } catch (IllegalArgumentException e) {
            str = e.getMessage();
        }
        assertEquals("Null Array Input", str);
    }
}
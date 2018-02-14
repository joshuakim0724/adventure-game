package com.example;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LayoutTest {
    private Layout setup;
    private Room[] rooms;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();

        setup = gson.fromJson(AdventureFilesAndURL.SIEBEL, Layout.class);
        rooms = setup.getRooms();

    }

    @Test
    public void getStartingRoomTest() {
        assertEquals("MatthewsStreet", setup.getStartingRoom());
    }

    @Test
    public void setStartingRoomTest() {
        setup.setStartingRoom("SiebelEntry");

        assertEquals("SiebelEntry", setup.getStartingRoom());

        setup.setStartingRoom("MatthewsStreet"); //Returning back to original so doesn't affect the class
        assertEquals("MatthewsStreet", setup.getStartingRoom());
    }

    @Test
    public void getEndingRoomTest() {
        assertEquals("Siebel1314", setup.getEndingRoom());
    }

    @Test
    public void testDirectionName() {
        Room room4 = rooms[3];
        Direction direction1 = room4.getDirections()[0];
        Direction direction2 = room4.getDirections()[1];

        assertEquals("South", direction1.getDirectionName());
        assertEquals("SiebelEntry", direction1.getRoom());

        assertEquals("NorthEast", direction2.getDirectionName());
        assertEquals("Siebel1112", direction2.getRoom());
    }
    //Tests from previous file

//    @Test
//    public void roomTest() {
//        Room room3 = rooms[2];
//        Room room8 = rooms[7];
//
//        assertEquals("AcmOffice", room3.getName());
//        assertEquals("You are in the ACM office.  " +
//                "There are lots of friendly ACM people.", room3.getDescription());
//        assertEquals("pizza", room3.getItems()[0]);
//        assertEquals("swag", room3.getItems()[1]);
//
//        assertEquals("SiebelBasement", room8.getName());
//        assertEquals("You are in the basement of Siebel.  " +
//                "You see tables with students working and door to computer labs.", room8.getDescription());
//        assertEquals("pencil", room8.getItems()[0]);
//    }
//
//    @Test
//    public void addItemTest() {
//        Room room3 = rooms[2];
//        Room room8 = rooms[7];
//
//        room3.addItem("pencil");
//        assertEquals("pencil", room3.getItems()[2]);
//
//        room8.addItem("swag");
//        assertEquals("swag", room8.getItems()[1]);
//    }
//
//    @Test
//    public void addNullItem() {
//        Room room3 = rooms[2];
//        String str = "";
//        try {
//            room3.addItem(null);
//        } catch (IllegalArgumentException e) {
//            str = e.getMessage();
//        }
//        assertEquals("Null Item Input", str);
//    }
//
//    @Test
//    public void removeItemTest() {
//        Room room3 = rooms[2];
//        Room room8 = rooms[7];
//
//        room3.removeItem("swag");
//        assertEquals(null, room3.getItems()[1]);
//
//        room8.removeItem("pencil");
//        assertEquals(null, room8.getItems()[0]);
//    }
//
//    @Test
//    public void dropNullItem() {
//        Room room3 = rooms[2];
//        String str = "";
//        try {
//            room3.removeItem(null);
//        } catch (IllegalArgumentException e) {
//            str = e.getMessage();
//        }
//        assertEquals("Null Drop Input", str);
//    }
}
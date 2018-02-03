package com.example;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;

public class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private String[] items;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Direction[] getDirections() {
        return directions;
    }

    public String[] getItems() {
        return items;
    }
}

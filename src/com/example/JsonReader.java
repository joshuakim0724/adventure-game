package com.example;

import com.google.gson.Gson;

public class JsonReader {

    /**
     * Initalizer from a file
     * @param jsonFile String of the file that is being read
     * @return The layout/setup of the class
     */
    public static Layout adventureSetupFromFile (String jsonFile) {
        Gson gson = new Gson();
        return gson.fromJson(jsonFile, Layout.class);
    }
}

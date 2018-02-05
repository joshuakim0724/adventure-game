package com.example;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Scanner;

public class Adventure {
    private static URL url;
    private static AdventureSetup adventureSetup;
    private static boolean isFinished = false;
    private static Room[] rooms;


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();
        /**
         * https://github.com/zillesc/WashingtonPost
         * https://echo360.org/lesson/G_0bf45e48-dfa7-488a-88db-4699e6468c8d_b0113c19
         * -4871-4da9-bad8-9a5f6b017a2f_2018-02-01T12:30:00.000_2018-02-01T13:57:00.000/classroom#sortDirection=desc
         * Classroom video link
         * Looked at the lecture in class to figure out how to get a Json from a URL
         *
         * //https://stackoverflow.com/questions/31504123/unhandled-exception-java-net-malformedurlexception
         */
        try {
            final HttpResponse<String> stringHttpResponse;

            url = new URL(AdventureURL.JSON_LINK);

            stringHttpResponse = Unirest.get(AdventureURL.JSON_LINK).asString();
            // Check to see if the request was successful; if so, convert the payload JSON into Java objects

            String json = stringHttpResponse.getBody();
            AdventureSetup adventureSetup = gson.fromJson(json, AdventureSetup.class);

            //while (!isFinished) {
                System.out.println("Your starting room is: " + adventureSetup.getStartingRoom());
                rooms = adventureSetup.getRooms();
                for (Room name : rooms) {
                    if (name.getName().equals(adventureSetup.getStartingRoom())) {
                        System.out.println(name.getDescription());
                        System.out.println("Your journey beings here");
                        if (name.getItems().length == 0) {
                            System.out.println("This room contains nothing");
                        } else {
                            System.out.print("This room contains ");
                            for (int i = 0; i < name.getItems().length; i++) {
                                if (i == name.getItems().length - 1) {
                                    System.out.println(name.getItems()[i]);
                                } else {
                                    System.out.print(name.getItems()[i] + ", ");
                                }
                            }
                        }
                    }
                }

        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }
    }
    //Don't actually use this, but have this to reference Zillecs code that he showed
    static void makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new URL(AdventureURL.JSON_LINK);

        stringHttpResponse = Unirest.get(url).asString();
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus() == 200) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            final AdventureSetup adventureSetup = gson.fromJson(json, AdventureSetup.class);
        }
    }
}
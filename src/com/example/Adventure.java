package com.example;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson;

public class Adventure {
    private static URL url;
    private static AdventureSetup setup;
    private static boolean isFinished = false;
    private static Room[] rooms;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();

        try { //https://stackoverflow.com/questions/31504123/unhandled-exception-java-net-malformedurlexception
            url = new URL(AdventureURL.JSON_LINK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setup = gson.fromJson(url.toString(), AdventureSetup.class);

        while (!isFinished) {
            System.out.println("Your starting room is: " + setup.getStartingRoom());
            rooms = setup.getRooms();
            for (Room name : rooms) {
                if (name.getName().equals(setup.getStartingRoom())) {
                    System.out.println(name.getDescription());
                    System.out.println("Your journey beings here");
                    if (name.getItems().length == 0) {
                        System.out.println("This room contains nothing");
                    } else {
                        System.out.
                    }
                }
            }
        }

        // this is a 'for each' loop; they are useful when you want to do something to
        // every element of a collection and you don't care about the index of the element
        for (String arg : args) {
            System.out.print("\"" + arg + "\" ");
        }

        System.out.println("");
        // you can remove this code; it is just illustrating the use of arguments.
    }
}

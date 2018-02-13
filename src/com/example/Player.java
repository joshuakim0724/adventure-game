package com.example;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private Item[] items;
    private ArrayList<Item> itemsList;
    private double attack;
    private double defense;
    private double health;
    private int level;

    private double exp = 0;
    private double maxHealth = health;

    public String getName() {
        return name;
    }

    public Item[] getItems() {
        return items;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public double getExp() {
        return exp;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        itemsList = new ArrayList<Item>(Arrays.asList(items));

        itemsList.add(item);

        items = itemsList.toArray(new Item[itemsList.size()]);
    }
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        itemsList = new ArrayList<Item>(Arrays.asList(items));

        itemsList.remove(item);

        items = itemsList.toArray(new Item[itemsList.size()]);
    }

    public void printItems() {
        System.out.print(GameConstants.CARRYING);
        if (items == null || items.length == 0) {
            System.out.println(GameConstants.NOTHING_OUTPUT);
        }
        for (int k = 0; k < items.length; k++) {
            String itemName = items[k].getName();
            if (k == items.length - 1) {
                System.out.println(itemName);
            } else {
                System.out.print(itemName + ", ");
            }
        }
    }
//    public void getPlayerInfo() {
//        System.out.println("Player Level: " + getLevel());
//        System.out.println("Player Attack: " + getAttack());
//        System.out.println("Player Defense: " + getDefense());
//        System.out.println("Player Health: " + getHealth());
//    }
}

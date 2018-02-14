package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Player {
    private String name;
    private Item[] items;
    private ArrayList<Item> itemsList;
    private double attack;
    private double defense;
    private double health;
    private int level;

    private Map<String, Item> itemMap = new HashMap();
    private double exp = 0;
    private double maxHealth;

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

    public void setHealth(double health) {
        this.health = health;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    private void setAttack(double attack) {
        this.attack = attack;
    }

    private void setDefense(double defense) {
        this.defense = defense;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Item getItemFromMap(String itemName) {
        return itemMap.get(itemName);
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        itemMap.put(item.getName(), item);

        itemsList = new ArrayList<Item>(Arrays.asList(items));

        itemsList.add(item);

        items = itemsList.toArray(new Item[itemsList.size()]);
    }

    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        itemMap.remove(item.getName());

        itemsList = new ArrayList<Item>(Arrays.asList(items));

        itemsList.remove(item);

        items = itemsList.toArray(new Item[itemsList.size()]);
    }

    /**
     * Prints out list of items player is carrying
     */
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

    /**
     * Prints out player info
     */
    public void getPlayerInfo() {
        System.out.println("Player Level: " + getLevel());
        System.out.println("Player Attack: " + getAttack());
        System.out.println("Player Defense: " + getDefense());
        System.out.println("Player Health: " + getHealth());
    }

    /**
     * Will return amount of EXP needed to level up
     * @param playerLevel what level the player is
     * @return How much exp is needed at the player level
     */
    public double experienceNeeded(int playerLevel) {
        double expNeeded;

        if (playerLevel == 1) {
            expNeeded = 25;
            return expNeeded;
        }
        if (playerLevel == 2) {
            expNeeded = 50;
            return expNeeded;
        }
        return expNeeded = (experienceNeeded(playerLevel - 1) +
                experienceNeeded(playerLevel - 2)) * 1.1;
    }

    /**
     * This will level up the player by the amounts given in the file
     */
    public void levelUp() {
        //Round to two decimals
        double attackRounded = Math.round(getAttack() * 1.5 * 100.0) / 100.0;
        double defenseRounded = Math.round(getDefense() * 1.5 * 100.0) / 100.0;
        double healthRounded = Math.round(getMaxHealth() * 1.3 * 100.0) / 100.0;

        setAttack(attackRounded);
        setDefense(defenseRounded);
        setMaxHealth(healthRounded);

        setHealth(getMaxHealth()); //Hp restored to full on level up
        setLevel(getLevel() +1 );
    }
}

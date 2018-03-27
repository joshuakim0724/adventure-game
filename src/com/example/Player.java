package com.example;

import java.util.*;


public class Player {
    private String name;
    private Item[] items;
    private double attack;
    private double defense;
    private double health;
    private int level;

    private ArrayList<Item> itemsList = new ArrayList<>();
    private double exp = 0;
    private double maxHealth;

    private static final String DONT_HAVE = "I don't have ";
    private static final String PLEVEL = "Player Level: ";
    private static final String PATTACK = "Player Attack: ";
    private static final String PDEFENSE = "Player Attack: ";
    private static final String PHEALTH = "Player Attack: ";

    private static final int LEVEL1_EXP = 25;
    private static final int LEVEL2_EXP = 50;
    private static final double EXP_MODIFIER = 1.1;


    // Getters/Setters
    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    public double getHealth() {
        return health;
    }

    public double getLevel() { return level; }

    public ArrayList<Item> getItemsArray() {
        return itemsList;
    }

    public double getExp() {
        return exp;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double health) {
        this.maxHealth = health;
    }

    /**
     * This method will setup the ArrayList using the given array
     */
    public void setUpArrayList() {
        if (items != null) {
            Collections.addAll(itemsList, items);
        }
    }

    /**
     * This method will add an item to the Player ArrayList
     * @param item that is going to be added
     * @return if can add the item
     */
    public boolean addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        return (itemsList.add(item));
    }

    /**
     * This method will remove an item from the Player ArrayList
     * @param item that is goign to be removed
     * @return If the item is valid to remove true, false if not
     */
    public boolean removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        if (!itemsList.contains(item)) {
            return false;
        }
        itemsList.remove(item);
        return true;
    }

    /**
     * @return a String of all the items the player is carrying
     */
    public String getItemsList() {
        StringBuilder itemOutput = new StringBuilder(GameConstants.CARRYING);
        if (itemsList.size() == 0) {
            itemOutput.append(GameConstants.NOTHING_OUTPUT);
            return itemOutput.toString();
        }

        for (int i = 0; i < itemsList.size(); i++) {
            if (i != itemsList.size() - 1) {
                itemOutput.append(itemsList.get(i).getName());
                itemOutput.append(", ");
            } else {
                itemOutput.append(itemsList.get(i).getName());
            }
        }
        return itemOutput.toString();
    }

    /**
     * @return a String of the player information of level, attack, defense, health
     */
    public String getPlayerInfo() {
        String playerInfo = PLEVEL + level + "\n" +
                PATTACK + attack + "\n" +
                PDEFENSE + defense + "\n" +
                PHEALTH + health;

        return playerInfo;
    }

    /**
     * Will return amount of EXP needed to level up
     * @param playerLevel what level the player is
     * @return How much exp is needed at the player level
     */
    public double experienceNeeded(int playerLevel) {
        if (playerLevel == 1) {
            return LEVEL1_EXP;
        }
        if (playerLevel == 2) {
            return LEVEL2_EXP;
        }
        return (experienceNeeded(playerLevel - 1) +
                experienceNeeded(playerLevel - 2)) * EXP_MODIFIER;
    }

    /**
     * This will level up the player by the amounts given in the file
     */
    public void levelUp() {
        //Round to two decimals
        double attackRounded = Math.round(getAttack() * 1.5 * 100.0) / 100.0;
        double defenseRounded = Math.round(getDefense() * 1.5 * 100.0) / 100.0;
        double healthRounded = Math.round(getMaxHealth() * 1.3 * 100.0) / 100.0;

        attack = attackRounded;
        defense = defenseRounded;
        health = healthRounded; //Hp restored to full on level up
        maxHealth = healthRounded;
        level += 1;
    }

    /**
     * This method is used to attack a monster
     * @param monster that is attacked by the player
     * @return true if the monster is dead, false if monster is still alive
     */
    public boolean attack(Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }

        double damageDealt;

        // Player Attack
        if (criticalHit()) {
            damageDealt = roundNumber(attack * 2 - monster.getDefense());
        } else {
            damageDealt = roundNumber(attack - monster.getDefense());
        }

        double monsterHealth = monster.getHealth() - damageDealt;
        // Not letting player do negative damage
        if (damageDealt < 0) {
            damageDealt = 0;
        }
        System.out.println(GameConstants.DID + damageDealt + GameConstants.DAMAGE);

        if (wonDuel(monster, monsterHealth)) {
            return true;
        }
        // Monster attack
        double monsterDamageDone = roundNumber(monster.getAttack() - defense);
        double playerHealth = health - monsterDamageDone;
        // Not letting monsters do negative damage
        if (monsterDamageDone < 0) {
            monsterDamageDone = 0;
        }
        System.out.println(GameConstants.RECEIVED + monsterDamageDone + GameConstants.DAMAGE);

        if (playerHealth > 0) {
            health = playerHealth;
        } else {
            System.out.println(GameConstants.YOU_DEAD_BRO);
            System.exit(0);
        }

        return false;
    }

    /**
     * This method is used to attack a monster with an item
     * @param monster that is going be attacked by player
     * @param itemName is the item that is going to be used by the player
     * @return false if
     */
    public boolean attackWithItem(Monster monster, String itemName) {
        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }
        Item item;
        if (getItemIndex(itemName) != -1) {
            item = itemsList.get(getItemIndex(itemName));
        } else {
            System.out.println(DONT_HAVE + itemName);
            return false;
        }
        double damageDealt;

        // Player Attack
        if (criticalHit()) {
            damageDealt = roundNumber(attack * 2 + item.getDamage() - monster.getDefense());
        } else {
            damageDealt = roundNumber(attack - monster.getDefense());
        }

        double monsterHealth = monster.getHealth() - damageDealt;
        // Not letting player do negative damage
        if (damageDealt < 0) {
            damageDealt = 0;
        }
        System.out.println(GameConstants.DID + damageDealt + GameConstants.DAMAGE);

        if (wonDuel(monster, monsterHealth)) {
            return true;
        }
        // Monster attack
        double monsterDamageDone = roundNumber(monster.getAttack() - defense);
        double playerHealth = health - monsterDamageDone;
        // Not letting monsters do negative damage
        if (monsterDamageDone < 0) {
            monsterDamageDone = 0;
        }
        System.out.println(GameConstants.RECEIVED + monsterDamageDone + GameConstants.DAMAGE);

        if (playerHealth > 0) {
            health = playerHealth;
        } else {
            System.out.println(GameConstants.YOU_DEAD_BRO);
            System.exit(0);
        }

        return false;
    }

    /**
     * Checks to see if item is valid. Helper method
     * @param itemInput Item String that you are testing
     * @return true if is valid, false if not
     */
    public boolean isValidItem(String itemInput) {
        for (Item anItemsList : itemsList) {
            String itemName = anItemsList.getName();
            if (itemInput.equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Will get the index of the Item from the itemName
     * @param itemInput is the name of the item
     * @return the index from the ArrayList, -1 if can't be found
     */
    public int getItemIndex(String itemInput) {
        for (int i = 0; i < itemsList.size(); i++) {
            String itemName = itemsList.get(i).getName();
            if (itemInput.equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Helper method for attack methods
     * @param monster getting attacked
     * @param monsterHealth health of the monster
     * @return true if the monster is dead, false if not
     */
    private boolean wonDuel(Monster monster, double monsterHealth) {
        if (monsterHealth < 0) {
            System.out.println(GameConstants.WON_DUEL + monster.getName());
            giveEXP(monster);
            return true;
        } else {
            monster.setHealth(monsterHealth);
        }
        return false;
    }

    /**
     * Simple rounding method
     */
    private static double roundNumber(double number) {
        return Math.round(number * 100) / 100;
    }

    /**
     * Gives exp earned to the player
     * @param monster which exp gained is calculated from
     */
    private void giveEXP(Monster monster) {
        double totalExpGained = exp +
                ((monster.getAttack() + monster.getDefense()) / 2 + monster.getMaxHealth()) * 20; //formula for exp
        System.out.println(GameConstants.GAINED + totalExpGained + GameConstants.EXP);

        while (totalExpGained > experienceNeeded(level)) {
            totalExpGained = totalExpGained - experienceNeeded(level);

            if (totalExpGained > 0) {
                System.out.println(GameConstants.LEVEL_UP);
                levelUp();
            }
        }
        exp = totalExpGained; // Left over exp;
    }

    /**
     * This will return true if it is a critical hit || Extra Feature
     * @return true if 25% chance to crit, false if not
     */
    private boolean criticalHit() {
        int number = (int)(Math.random() * 4);

        return number < 1;
    }
}
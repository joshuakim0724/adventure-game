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


    public void setUpArrayList() {
        Collections.addAll(itemsList, items);
    }

    public boolean addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_ITEM);
        }
        itemsList.add(item);

        return true;
    }

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

    public String getItemsList() {
        StringBuilder itemOutput = new StringBuilder(GameConstants.CARRYING);
        if (items == null || items.length == 0) {
            itemOutput.append(GameConstants.NOTHING_OUTPUT);
            return itemOutput.toString();
        }

        for (int i = 0; i < itemsList.size(); i++) {
            if (i != itemsList.size() - 1) {
                itemOutput.append(itemsList.get(i));
                itemOutput.append(", ");
            } else {
                itemOutput.append(itemsList.get(i));
            }
        }
        return itemOutput.toString();
    }

    public String getPlayerInfo() {
        String playerInfo = "Player Level: " + level +
                "Player Attack: " + attack +
                "Player Defense: " + defense +
                "Player Health: " + health;

        return playerInfo;
    }

    /**
     * Will return amount of EXP needed to level up
     * @param playerLevel what level the player is
     * @return How much exp is needed at the player level
     */
    public double experienceNeeded(int playerLevel) {
        if (playerLevel == 1) {
            return 25;
        }
        if (playerLevel == 2) {
            return 50;
        }
        return (experienceNeeded(playerLevel - 1) +
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

        attack = attackRounded;
        defense = defenseRounded;
        health = healthRounded; //Hp restored to full on level up
        maxHealth = healthRounded;
        level += 1;
    }

    public boolean attack(Monster monster) {
        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }
        // Player Attack
        double damageDealt = roundNumber(attack - monster.getDefense());
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

    public boolean attackWithItem(Monster monster, String itemName) {
        if (monster == null) {
            throw new IllegalArgumentException(ErrorConstants.NULL_MONSTER);
        }
        Item item;
        if (getItemIndex(itemName) != -1) {
            item = itemsList.get(getItemIndex(itemName));
        } else {
            System.out.println("I don't have " + itemName);
            return false;
        }

        // Player Attack
        double damageDealt = roundNumber(attack + item.getDamage() - monster.getDefense());
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

    public int getItemIndex(String itemInput) {
        for (int i = 0; i < itemsList.size(); i++) {
            String itemName = itemsList.get(i).getName();
            if (itemInput.equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return -1;
    }

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
}
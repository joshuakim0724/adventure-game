package com.example;

public class Monster {

    private String name;
    private double attack;
    private double defense;
    private double health;

    private double maxHealth;

    private static final int healthPerBar = 5;
    private static final String PLAYER = "Player: ";
    private static final String MONSTER = "Monster: ";

    public String getName() {
        return name;
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

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * Display Player health bar and monster health bar
     * @param player which monsters health bar you are displaying
     */
    public String displayStatus(Player player) {
        // Player health output
        double playerHealth = player.getHealth();
        StringBuilder playerHealthOutput = new StringBuilder(PLAYER);
        int numberOfHealthBars = (int) Math.ceil((player.getMaxHealth() / healthPerBar));

        for (int i = 0; i < numberOfHealthBars; i++) {
            if (playerHealth > 0) {
                playerHealth -= healthPerBar;
                playerHealthOutput.append("#");
            } else {
                playerHealthOutput.append("_");
            }

        }
        playerHealthOutput.append("\n");

        // Monster health output
        double monsterHealth = health;
        StringBuilder monsterHealthOutput = new StringBuilder(MONSTER);
        numberOfHealthBars = (int) Math.ceil((maxHealth / healthPerBar));
        for (int j = 0; j < numberOfHealthBars; j++) {
            if (monsterHealth > 0) {
                monsterHealth -= healthPerBar;
                monsterHealthOutput.append("#");
            } else {
                monsterHealthOutput.append("_");
            }
        }
        playerHealthOutput.append(monsterHealthOutput);

        return playerHealthOutput.toString();
    }
}

package com.example;

public class Monster {

    private String name;
    private double attack;
    private double defense;
    private double health;

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

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}

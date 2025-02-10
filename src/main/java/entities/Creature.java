package entities;


import java.awt.*;

public class Creature extends Entity {
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private int level;
    private int experience;
    private int experienceToNextLevel;
    private int gold;

    public Creature(String name, char glyph, Color color, int x, int y) {
        super(name, glyph, color, x, y);
        this.maxHealth = 100;
        this.health = maxHealth;
        this.attack = 10;
        this.defense = 5;
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = 100;
        this.gold = 0;
    }

    public void moveBy(int mx, int my) {
        x += mx;
        y += my;
    }

    // Health methods
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int amount) {
        int damage = Math.max(0, amount - defense);
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    // Attack methods
    public int getAttack() {
        return attack;
    }

    public void increaseAttack(int amount) {
        attack += amount;
    }

    // Defense methods
    public int getDefense() {
        return defense;
    }

    public void increaseDefense(int amount) {
        defense += amount;
    }

    // Level and Experience methods
    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }

    public void gainExperience(int amount) {
        experience += amount;
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        experience -= experienceToNextLevel;
        level++;
        experienceToNextLevel += 50;
        maxHealth += 20;
        attack += 5;
        defense += 2;
        health = maxHealth; // Restore health on level up
    }

    // Gold methods
    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
        }
    }
}

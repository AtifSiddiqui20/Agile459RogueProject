package entities;


import world.Dungeon;
import entities.Item;
import entities.Gold;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Creature extends Entity {
    private int health;
    private int maxHealth;
    private int attack;
    private int defense;
    private int level;
    private int experience;
    private int strength;
    private int experienceToNextLevel;
    private int gold;
    private int attackBonus;
    private int damageBonus;
    private static final Random random = new Random();
    private final List<Item> inventory = new ArrayList<Item>();


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
        calculateModifiers();
    }

    private void calculateModifiers() {
        // Attack Bonus
        if (strength < 8) attackBonus = -7;
        else if (strength < 17) attackBonus = -4;
        else if (strength < 19) attackBonus = -3;
        else if (strength < 21) attackBonus = -2;
        else if (strength < 31) attackBonus = -1;
        else attackBonus = 0;

        // Damage Bonus
        if (strength < 8) damageBonus = -7;
        else if (strength < 16) damageBonus = -6;
        else if (strength < 17) damageBonus = -5;
        else if (strength < 18) damageBonus = -4;
        else if (strength < 20) damageBonus = -3;
        else if (strength < 22) damageBonus = -2;
        else if (strength < 31) damageBonus = -1;
        else damageBonus = 0;
    }

    public void useItem(Item item) {
        if (item == null) return;

        switch (item.getType()) {
            case "potion":
                health += item.getEffectValue();
                if (health > maxHealth) health = maxHealth;
                System.out.println("You used a " + item.getName() + " and restored " + item.getEffectValue() + " HP!");
                break;
            case "shield":
                defense += item.getEffectValue();
                System.out.println("You equipped a " + item.getName() + " and gained " + item.getEffectValue() + " armor!");
                break;
            default:
                System.out.println("This item has no use yet!");
        }

        inventory.remove(item);
    }

    public void setStrength(int strength) {
        if (strength < 3) {
            this.strength = 3;  //  Minimum strength is 3
        } else if (strength > 31) {
            this.strength = 31; //  Maximum strength is 31
        } else {
            this.strength = strength;
        }
        calculateModifiers(); //  Recalculate attack and damage bonuses
    }

    public int getStrength() {
        return strength;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public int getDamageBonus() {
        return damageBonus;
    }
    public void moveBy(int mx, int my, Dungeon dungeon) {
        x += mx;
        y += my;

        pickUpGold(dungeon);
        pickUpItem(dungeon);
    }

    // Health methods
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean attackRoll(int defenderArmor) {
        int roll = random.nextInt(20) + 1; // 1d20
        int required = 20 - this.level - defenderArmor + this.attackBonus; // Attack formula
        return roll > required;
    }


    public int dealDamage() {
        int baseDamage = random.nextInt(4) + 1; // 1d4 for player, monster damage will override
        return Math.max(baseDamage + damageBonus, 1); // Minimum 1 damage
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

    public boolean isAlive() {
        return health > 0;
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


    public void pickUpGold(Dungeon dungeon) {
        Iterator<Gold> iterator = dungeon.getGoldPile().iterator();
        while (iterator.hasNext()) {
            Gold gold = iterator.next();
            if (gold.getX() == this.x && gold.getY() == this.y) {
                addGold(gold.getAmount());
                iterator.remove(); // Remove gold from dungeon
                dungeon.getMap()[x][y] = '.';
                dungeon.addMessage("You picked up " + gold.getAmount() + " gold!");
                System.out.println(getName() + " picked up " + gold.getAmount() + " gold!");
            }
        }
    }



    public void pickUpItem(Dungeon dungeon) {
        Iterator<Item> iterator = dungeon.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getX() == this.x && item.getY() == this.y) {
                inventory.add((item));
                iterator.remove(); // Remove item from dungeon
                dungeon.getMap()[x][y] = '.'; // Remove item from dungeon map
                dungeon.addMessage("Picked up a " + item.getName() + "!");

                System.out.println(getName() + " picked up a " + item.getName() + "!");
                System.out.println("Inventory now contains:");
                for (Item i : inventory) {
                    System.out.println("- " + i.getName());
                }
            }
        }
    }



    public List<Item> getInventory() {
        return inventory;
    }

    public int getArmor() {
        return 5; // Placeholder: We'll update armor logic later
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

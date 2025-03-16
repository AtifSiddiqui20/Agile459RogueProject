package entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Monster extends Creature {
    private char letter;
    private boolean isDiscovered = false;
    private double carryChance;
    private Set<String> flags;
    private int exp;
    private int level;
    private int armor;
    private int hitPoints;
    private int damage;
    private int strength;
    private String levelsFound;
    private String specialAbility;
    private boolean isAlive;

    private static final Random random = new Random();
    public static final int BASE_STRENGTH = 10;

    //  List of all monsters
    public static final List<Monster> MONSTER_LIST = new ArrayList<>();

    //  Constructor
    public Monster(String name, char letter, Color color, int x, int y, double carryChance, Set<String> flags,
                   int exp, int level, int armor, int hitPoints, int damage, String levelsFound, String specialAbility) {
        super(name, letter, color, x, y);
        this.letter = letter;
        this.carryChance = carryChance;
        this.flags = flags;
        this.exp = exp;
        this.level = level;
        this.armor = armor;
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.strength = BASE_STRENGTH;
        this.levelsFound = levelsFound;
        this.specialAbility = specialAbility;
        this.isAlive = true;
        setStrength(10);
    }
    public int dealDamage() {
        int baseDamage = Math.max(damage, 1);
        return Math.max(random.nextInt(baseDamage) + 1 + getDamageBonus(), 1);
    }

    public boolean isMean() {
        return flags.contains("M"); //  Monster attacks first if Mean
    }
    //  Roll hit points using dice (e.g., "5d8")
    public static int rollHitPoints(String dice) {
        String[] parts = dice.split("d");
        int count = Integer.parseInt(parts[0]);
        int sides = Integer.parseInt(parts[1]);

        int total = 0;
        for (int i = 0; i < count; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }

    //  Monster attacks the player
    public int attack() {
        int baseDamage = Math.max(damage, 1);
        return random.nextInt(baseDamage) + 1;
    }
    public void discover() {
        this.isDiscovered = true;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }
    //  Monster takes damage and checks if it dies
    public void takeDamage(int damage) {
        int reducedDamage = Math.max(damage - armor, 0);
        hitPoints -= reducedDamage;  //  Damage reduces HP
        if (hitPoints <= 0) {       //  Monster dies if HP <= 0
            die();
        }
    }

    //  Monster dies and is removed from the map
    public void die() {
        isAlive = false;
        hitPoints = 0;
    }

    //  Check if monster is still alive
    public boolean isAlive() {
        return isAlive;
    }

    //  Monster Flags
    public boolean hasFlag(String flag) {
        return flags.contains(flag);
    }

    //  Getters
    public char getLetter() { return letter; }
    public double getCarryChance() { return carryChance; }
    public Set<String> getFlags() { return flags; }
    public int getExp() { return exp; }
    public int getLevel() { return level; }
    public int getArmor() { return armor; }
    public int getHitPoints() { return hitPoints; }
    public int getDamage() { return damage; }
    public int getStrength() { return strength; }
    public String getLevelsFound() { return levelsFound; }
    public String getSpecialAbility() { return specialAbility; }

    //  Initialize the list of 26 monsters using a static block
    static {
       addMonster('A', "Aquator", 0, Set.of("M"), 20, 5, 9, "5d8", 0, "-", "Reduces player armor by 1 with each solid hit");
       addMonster('B', "Bat", 0, Set.of("F"), 1, 1, 8, "1d8", 2, "1-6", "");
       addMonster('C', "Centaur", 15, Set.of(), 25, 4, 7, "4d8", 6, "7", "");
       addMonster('D', "Dragon", 100, Set.of("M"), 6800, 10, 1, "10d8", 10, "-", "");
       addMonster('E', "Emu", 0, Set.of("M"), 2, 1, 10, "1d8", 2, "1-4", "");
       addMonster('F', "Venus Flytrap", 0, Set.of("M"), 80, 8, 3, "8d8", 8, "-", "");
       addMonster('G', "Griffon", 20, Set.of("M", "F", "R"), 2000, 13, -2, "13d8", 4, "-", "");
       addMonster('H', "Hobgoblin", 0, Set.of("M"), 3, 1, 10, "1d8", 8, "1-7", "");
       addMonster('I', "Ice Monster", 0, Set.of("M"), 15, 1, 10, "1d8", 2, "6", "Freeze the player for 1d4 turns on a solid hit");
       addMonster('J', "Jabberwock", 70, Set.of(), 4000, 15, -4, "15d8", 12, "-", "");
       addMonster('K', "Kestral", 0, Set.of("M", "F"), 1, 1, 10, "1d8", 4, "1-4", "");
       addMonster('L', "Leprechaun", 100, Set.of("G"), 10, 3, 8, "3d8", 2, "6", "Steals gold on a solid hit");
       addMonster('M', "Medusa", 40, Set.of("M"), 200, 8, 9, "8d8", 4, "-", "Confuse with their gaze for 1d4 turns on a solid hit");
       addMonster('N', "Nymph", 100, Set.of(), 37, 3, 2, "3d8", 0, "-", "Steals a random unequipped magic item on a solid hit");
       addMonster('O', "Orc", 15, Set.of("G"), 5, 1, 5, "1d8", 8, "3-6", "");
       addMonster('P', "Phantom", 0, Set.of("I"), 120, 8, 8, "8d8", 4, "-", "");
       addMonster('Q', "Quagga", 30, Set.of("M"), 32, 3, 9, "3d8", 2, "4-7", "");
       addMonster('R', "Rattlesnake", 0, Set.of("M"), 9, 2, 8, "2d8", 6, "4-7", "Poison");
       addMonster('S', "Snake", 0, Set.of("M"), 1, 2, 3, "2d8", 3, "1-6", "");
       addMonster('T', "Troll", 50, Set.of("R", "M"), 120, 6, 7, "6d8", 8, "-", "");
       addMonster('U', "Ur-vile", 0, Set.of("M"), 190, 7, 13, "7d8", 3, "-", "");
      addMonster('V', "Vampire", 20, Set.of("R", "M"), 350, 8, 10, "8d8", 10, "-", "Reduces maximum health points. If the player hits 0, they die");
       addMonster('W', "Wraith", 0, Set.of(), 55, 5, 7, "5d8", 6, "-", "Reduces experience points. If the player hits 0, they die");
        addMonster('X', "Xeroc", 30, Set.of(), 100, 7, 4, "7d8", 4, "-", "Mimic other items and attack when picked up. A wand of cancellation will reveal them");
        addMonster('Y', "Yeti", 30, Set.of(), 50, 4, 5, "4d8", 6, "-", "");
        addMonster('Z', "Zombie", 0, Set.of("M"), 6, 2, 3, "2d8", 4, "5", "");
    }

    //  Helper method to add a monster to the list
    private static void addMonster(char letter, String name, double carryChance, Set<String> flags,
                                   int exp, int level, int armor, String hptDice, int damage,
                                   String levelsFound, String specialAbility) {
        MONSTER_LIST.add(new Monster(name, letter, Color.RED, 0, 0, carryChance, flags, exp, level, armor,
                rollHitPoints(hptDice), damage, levelsFound, specialAbility));
    }
}

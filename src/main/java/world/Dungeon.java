package world;

import entities.Gold;
import entities.Item;
import entities.Monster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Dungeon {
    private int width;
    private final int height;
    private final char[][] map;
    private final boolean[][] traversed;
    private final Random random = new Random();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Gold> goldPile = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final List<String> messageLog = new ArrayList<>();

    //  Monster list
    private final List<Monster> monsters = new ArrayList<>();

    //  Constructor
    public Dungeon(int width, int height) {
        this.width = 70;
        this.height = height;
        this.map = new char[width][height];
        this.traversed = new boolean[width][height];
        generateDungeon();
    }

    //  Dungeon generation
    public void generateDungeon() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = '#';
            }
        }


        int roomCount = 6 + random.nextInt(4);

        for (int i = 0; i < roomCount; i++) {
            int roomWidth = 5 + random.nextInt(6);
            int roomHeight = 4 + random.nextInt(5);
            int x = random.nextInt(width - roomWidth - 2) + 1;
            int y = random.nextInt(height - roomHeight - 2) + 1;

            Room room = new Room(x, y, roomWidth, roomHeight);
            rooms.add(room);
            createRoom(room);
        }

        for (int i = 0; i < rooms.size() - 1; i++) {
            connectRooms(rooms.get(i), rooms.get(i + 1));
        }

        addGoldAndItems();
       // printDungeon();
    }

    private void createRoom(Room room) {
        for (int x = room.x; x < room.x + room.width; x++) {
            for (int y = room.y; y < room.y + room.height; y++) {
                map[x][y] = '.';
            }
        }
        addDoorsToRoom(room);

    }

    private void addDoorsToRoom(Room room) {
        int doorCount = 1 + random.nextInt(2);

        for (int i = 0; i < doorCount; i++) {
            int doorX, doorY;

            if (random.nextBoolean()) {
                doorX = (random.nextBoolean()) ? room.x : room.x + room.width - 1;
                doorY = room.y + random.nextInt(room.height);
            } else {
                doorX = room.x + random.nextInt(room.width);
                doorY = (random.nextBoolean()) ? room.y : room.y + room.height - 1;
            }

            map[doorX][doorY] = '+';
            room.doors.add(new int[]{doorX, doorY});
        }
    }

    private void connectRooms(Room room1, Room room2) {
        int[] door1 = room1.doors.get(random.nextInt(room1.doors.size()));
        int[] door2 = room2.doors.get(random.nextInt(room2.doors.size()));

        int x1 = door1[0], y1 = door1[1];
        int x2 = door2[0], y2 = door2[1];

        if (random.nextBoolean()) {
            createHorizontalTunnel(x1, x2, y1);
            createVerticalTunnel(y1, y2, x2);
        } else {
            createVerticalTunnel(y1, y2, x1);
            createHorizontalTunnel(x1, x2, y2);
        }
    }

    private void createHorizontalTunnel(int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            map[x][y] = '.';
        }
    }

    private void createVerticalTunnel(int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            map[x][y] = '.';
        }
    }

    //  Monster functionality

    // ➤ Add monster to the dungeon
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    // ➤ Remove dead monsters from the list
    public void removeDeadMonsters() {
        Iterator<Monster> iterator = monsters.iterator();
        while (iterator.hasNext()) {
            Monster monster = iterator.next();
            if (!monster.isAlive()) {
                iterator.remove();
            }
        }
    }

    // ➤ Get all monsters
    public List<Monster> getMonsters() {
        return monsters;
    }

    // ➤ Check if a tile is occupied by a monster
    private boolean isOccupied(int x, int y) {
        return monsters.stream().anyMatch(m -> m.getX() == x && m.getY() == y);
    }

    // ➤ Spawn random monsters
    public void spawnRandomMonsters(int count) {
        for (int i = 0; i < count && i < Monster.MONSTER_LIST.size(); i++) {
            int x, y;
            do {
                x = random.nextInt(width);
                y = random.nextInt(height);
            } while (map[x][y] != '.' || isOccupied(x, y));

            Monster baseMonster = Monster.MONSTER_LIST.get(i);

            Monster monster = new Monster(
                    baseMonster.getName(),
                    baseMonster.getLetter(),
                    baseMonster.getColor(),
                    x, y,
                    baseMonster.getCarryChance(),
                    baseMonster.getFlags(),
                    baseMonster.getExp(),
                    baseMonster.getLevel(),
                    baseMonster.getArmor(),
                    baseMonster.getHitPoints(),
                    baseMonster.getDamage(),
                    baseMonster.getLevelsFound(),
                    baseMonster.getSpecialAbility()
            );

            addMonster(monster);
            printDungeon();
        }
    }

    // Gold and item functionality

    public void addGoldAndItems() {
        for (Room room: rooms) {
            if (random.nextBoolean()) {
                int x = room.x + random.nextInt(room.width);
                int y = room.y + random.nextInt(room.height);
                Gold gold = new Gold(x, y, random.nextInt(100) + 1);
                goldPile.add(gold);
                map[x][y] = '$';
            }
            if (random.nextBoolean()) {
                int x = room.x + random.nextInt(room.width);
                int y = room.y + random.nextInt(room.height);
                String[] possibleItems = {"Potion", "Sword", "Shield"};
                String itemName = possibleItems[random.nextInt(possibleItems.length)];
                switch (itemName) {
                    case "Potion":
                        map[x][y] = '!';
                        items.add(new Item("Potion", "potion", 10, x, y));
                        break;
                    case "Sword":
                        map[x][y] = ')';
                        items.add(new Item("Sword", "sword", 30, x, y));
                        break;
                    case "Shield":
                        map[x][y] = '[';
                        items.add(new Item("Shield", "shield", 15, x, y));
                        break;
                }
                map[x][y] = '*';
            }
        }
    }


    //  Traversed tiles
    public boolean isTraversed(int x, int y) {
        return traversed[x][y];
    }

    public void markTraversed(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            traversed[x][y] = true;
            revealAdjacentTiles(x, y);

        }
    }

    private void revealAdjacentTiles(int x, int y) {
        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {1, -1}, {-1, 1}, {1, 1}
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int newX = x + dx;
            int newY = y + dy;
            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                traversed[newX][newY] = true;
            }
        }
    }

    //  Getters
    public char[][] getMap() {
        return map;
    }

    public boolean[][] getTraversedMap() {
        return traversed;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Gold> getGoldPile() {
        return goldPile;
    }

    public List<Item> getItems() {
        return items;
    }

    //  Print the dungeon layout
    public void printDungeon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
    }

    //  Find a random spawn location for the player
    public int[] findSpawnLocation() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y] == '.') {
                    return new int[]{x, y};
                }
            }
        }
        throw new IllegalArgumentException("No valid spawn found in dungeon.");
    }

    public void addMessage(String s) {
        messageLog.add(s);
        if (messageLog.size() > 5) {
            messageLog.remove(0);
        }


    }

    public List<String> getMessageLog() {
        return messageLog;
    }
}

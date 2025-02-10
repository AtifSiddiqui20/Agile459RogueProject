package world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dungeon {
    private final int width;
    private final int height;
    private final char[][] map;
    private final boolean [][] traversed;
    private final Random random = new Random();
    private final List<Room> rooms = new ArrayList<>();

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.map = new char[width][height];
        this.traversed = new boolean[width][height];
        generateDungeon();
    }

    private void generateDungeon() {
        // Fill map with walls (`#`)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = '#';
            }
        }

        // Generate random rooms
        int roomCount = 6 + random.nextInt(4); // 6-9 rooms

        for (int i = 0; i < roomCount; i++) {
            int roomWidth = 5 + random.nextInt(6); // 5-10 width
            int roomHeight = 4 + random.nextInt(5); // 4-8 height
            int x = random.nextInt(width - roomWidth - 2) + 1;
            int y = random.nextInt(height - roomHeight - 2) + 1;

            Room room = new Room(x, y, roomWidth, roomHeight);
            rooms.add(room);
            createRoom(room);
        }

        // Connect rooms with passages
        for (int i = 0; i < rooms.size() - 1; i++) {
            connectRooms(rooms.get(i), rooms.get(i + 1));
        }
    }

    private void createRoom(Room room) {
        for (int x = room.x; x < room.x + room.width; x++) {
            for (int y = room.y; y < room.y + room.height; y++) {
                map[x][y] = '.'; // Floor
            }
        }

        // Add doors on room walls
        addDoorsToRoom(room);
    }

    private void addDoorsToRoom(Room room) {
        int doorCount = 1 + random.nextInt(2); // 1-2 doors per room

        for (int i = 0; i < doorCount; i++) {
            int doorX, doorY;

            // Randomly place the door on one of the room's walls
            if (random.nextBoolean()) {
                // Left or Right wall
                doorX = (random.nextBoolean()) ? room.x : room.x + room.width - 1;
                doorY = room.y + random.nextInt(room.height);
            } else {
                // Top or Bottom wall
                doorX = room.x + random.nextInt(room.width);
                doorY = (random.nextBoolean()) ? room.y : room.y + room.height - 1;
            }

            map[doorX][doorY] = '+'; // Door
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

    public boolean isTraversed(int x, int y) {
        return traversed[x][y];
    }

    public void markTraversed(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height){
            traversed[x][y] = true;
            revealAdjacentTiles(x, y);
        }

    }

    private void revealAdjacentTiles(int x, int y) {
        int [][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1},
                {-1, -1}, {1, -1}, {-1, 1}, {1, 1}
    };

        for (int [] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int newX = x + dx;
            int newY = y + dy;
            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                traversed[newX][newY] = true;
            }
        }
    }

    public char[][] getMap() {
        return map;
    }

    public boolean[][] getTraversedMap() {
        return traversed;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void printDungeon() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
    }
    public int[] findSpawnLocation() {
        if (rooms.isEmpty()) {
            return new int[]{1, 1}; // Fallback in case no rooms were generated
        }

        Room startRoom = rooms.get(0); // Get the first room
        int spawnX = startRoom.x + 1; // Inside the room, not on the walls
        int spawnY = startRoom.y + 1;

        return new int[]{spawnX, spawnY};
    }

}

package world;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public int x, y, width, height;
    public List<int[]> doors = new ArrayList<>();

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(int px, int py) {
        return px >= x && px < x + width && py >= y && py < y + height;
    }
}

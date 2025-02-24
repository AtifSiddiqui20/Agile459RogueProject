package entities;

import java.awt.*;

public abstract class Enemy extends Creature {
    public Enemy(int x, int y, int health, char glyph, Color color) {
        super("Enemy", glyph, color, x, y);
        this.health = health;
    }

    public void moveTowards(int targetX, int targetY) {
        if (targetX > x) x++;
        else if (targetX < x) x--;

        if (targetY > y) y++;
        else if (targetY < y) y--;
    }

    public abstract void update(int playerX, int playerY);
}



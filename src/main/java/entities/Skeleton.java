package entities;

import java.awt.*;

public class Skeleton extends Enemy {
    public Skeleton(int x, int y) {
        super(x, y, 10, 's', Color.WHITE);
    }

    @Override
    public void update(int playerX, int playerY) {
        int dx = (Math.random() > 0.5) ? 1 : -1;
        int dy = (Math.random() > 0.5) ? 1 : -1;
        moveBy(dx, dy);
    }
}

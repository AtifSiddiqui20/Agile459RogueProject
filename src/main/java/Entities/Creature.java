package Entities;


import java.awt.*;

public class Creature extends Entity {
    public Creature(String name, char glyph, Color color, int x, int y) {
        super(name, glyph, color, x, y);
    }

    public void moveBy(int mx, int my) {
        x += mx;
        y += my;
    }
}

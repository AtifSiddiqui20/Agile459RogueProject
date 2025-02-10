package entities;

import java.awt.*;

public class Entity {
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getGlyph() {
        return glyph;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    protected int x;
    protected int y;
    protected char glyph;
    protected String type;
    protected String name;
    protected Color color;


    public Entity(String name, char glyph, Color color, int x, int y) {
        this.name = name;
        this.glyph = glyph;
        this.color = color;
        this.x = x;
        this.y = y;
    }


}

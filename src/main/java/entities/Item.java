package entities;

public class Item {
    private String name;
    private int x,y;
    private String type; // e.g., "potion", "armor", "weapon"
    private int effectValue;

    public Item(String name, String type, int effectValue, int x, int y){
        this.name = name;
        this.type = type;
        this.effectValue = effectValue;
        this.x = x;
        this.y = y;
    }

    public String getName(){
        return name;
    }
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }


    public String getType() { return type; }
    public int getEffectValue() { return effectValue; }
}

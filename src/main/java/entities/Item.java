package entities;

public class Item {
    private String name;
    private int x,y;

    public Item(String name, int x, int y){
        this.name = name;
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
}

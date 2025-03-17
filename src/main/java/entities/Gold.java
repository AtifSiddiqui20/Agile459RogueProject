package entities;

public class Gold {
    private int x,y;
    private int amount;

    public Gold(int x, int y, int amount){
        this.x = x;
        this.y = y;
        this.amount = amount;
    }


    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }
    public int getAmount(){
        return amount;
    }
}

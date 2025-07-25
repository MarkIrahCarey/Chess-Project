package core;

public class Piece {
    private String color;
    private Position pos;
    private String name;

    public Piece(){
        color = "None";
        pos = new Position();
        name = "_";
    }
    
    public Piece(String color, Position pos, String name){
        this.color = color;
        this.pos = pos;
        this.name = name;
    }

    public void updatePos(Position pos){

    }

    public int getRow(){
        return pos.getRow();
    }

    public int getCol(){
        return pos.getCol();
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }
}

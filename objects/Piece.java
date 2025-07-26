package objects;

public class Piece {
    private String color;
    private String name;

    public Piece(){
        color = "None";
        name = "_";
    }
    
    public Piece(String color, String name){
        this.color = color;
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name;
    }
}

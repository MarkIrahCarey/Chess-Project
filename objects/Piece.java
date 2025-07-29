package objects;

public class Piece {
    private String color;
    private String name;
    private int row;
    private int col;
    private boolean setFirstMove;
    private boolean turn;

    public Piece(){
        color = "None";
        name = "_";
        setFirstMove = false;
        turn = false;
    }
    
    public Piece(String color, String name){
        this.color = color;
        this.name = name;
        setFirstMove = false;
        turn = false;
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

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public boolean getTurn(){
        return turn;
    }

    public void setTurn(boolean t){
        turn = t;
    }

    public boolean hasMoved(){
        return setFirstMove;
    }

    public void setMoved(){
        setFirstMove = true;
    }
}

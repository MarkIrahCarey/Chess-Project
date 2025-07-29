package core;

public class Game{

    private BoardManager boardManager;
    private boolean GameTurn;

    public Game(){
        // GameTurn is true or false, True is White, False is Black
        boardManager = new BoardManager();
    }
}

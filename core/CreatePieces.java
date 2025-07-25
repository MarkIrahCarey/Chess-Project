package core;

public class CreatePieces {
    Piece pieces[][];

    public CreatePieces(){
        // 
        pieces = new Piece[4][8];

        // Define piece order for back ranks
        String[] names = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};

        // Black pieces 
        for (int col = 0; col < 8; col++) {
            pieces[0][col] = new Piece("Black", new Position(0, col), names[col]);
            pieces[1][col] = new Piece("Black", new Position(1, col), "Pawn");        // Pawns
        }

        // White pieces
        for (int col = 0; col < 8; col++) {
            pieces[2][col] = new Piece("White", new Position(6, col), "Pawn");        // Pawns
            pieces[3][col] = new Piece("White", new Position(7, col), names[col]); 
        }

    }

    public Piece[][] getPieces(){
        return pieces;
    }
}

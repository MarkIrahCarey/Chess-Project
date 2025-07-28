package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import objects.Piece;

public class CreatePieces{
    Piece pieces[][];

    public CreatePieces(){
        // 
        pieces = new Piece[4][8];

        // Define piece order for back ranks
        String[] names = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};

        // Black pieces 
        for (int col = 0; col < 8; col++) {
            pieces[0][col] = new Piece("Black", names[col]);
            pieces[1][col] = new Piece("Black", "Pawn");      
        }

        // White pieces
        for (int col = 0; col < 8; col++) {
            pieces[2][col] = new Piece("White", "Pawn");        
            pieces[3][col] = new Piece("White", names[col]); 
        }

    }

    public Piece[][] getPieces(){
        return pieces;
    }

    public void addPieceFuctionality(Piece piece, JButton jButton, JButton[][] board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPieceFuctionality'");
    }
}

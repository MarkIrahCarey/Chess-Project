package core;

import java.awt.*;
import javax.swing.*;

import objects.Piece;

import java.util.HashMap;

public class BoardManager {
    private static JButton board[][];
    private static JFrame gameWindow;
    private static Piece pieces[][];

    public BoardManager(){
        // create Board of Buttons
        board = new JButton[8][8];

        // create game window, features, and its operations
        gameWindow = new JFrame();
        gameWindow.setSize(1280, 960);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setTitle("Chess Game by Mark Ira Galang");
        gameWindow.setLayout(null);

        // add the board into the frame
        JPanel panelBoard = displayBoard();
        panelBoard.setBounds(50, 50, 900, 760);
        gameWindow.add(panelBoard);

        // add the panel that contains the pieces that are taken

        // display window
        gameWindow.setVisible(true);
    }

    // this is just a helper method to display the board on the console, would not be used in the future
    public JPanel displayBoard(){
        JPanel panel = new JPanel(new GridLayout(8, 8));
        panel.setSize(900, 760);

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                // initialize button
                board[i][j] = new JButton("");
            }
        }
        
        // fill board with pieces
        fillBoard();

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                // add it to the board
                panel.add(board[i][j]);
            }
        }
        
        return panel;
    }

    // fillBoard is like a reset button that just adds the pieces
    private void fillBoard(){
        // get the pieces
        CreatePieces pieceManager = new CreatePieces();

        pieces = pieceManager.getPieces();
        
        // TODO: Create a hashmap to link names with pictures

       
        for (int i = 0; i < pieces.length; i++){
            for (int j = 0; j < pieces[i].length; j++){
                String piece_name = pieces[i][j].getColor() + " " + pieces[i][j].getName();

                // row checks what piece will we add into the board
                // if i < 2, black, else white
                int row = i < 2 ? i : (i + 4);

                board[row][j].setName(piece_name);
                board[row][j].setText(piece_name);
                
                pieceManager.addPieceFuctionality(pieces[i][j].getName(), board[row][j], board);
            }
        }
    }
}

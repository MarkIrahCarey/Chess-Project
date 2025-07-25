package core;

import java.awt.*;
import javax.swing.*;
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
                board[i][j] = new JButton("Text");
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
        CreatePieces pieces_init = new CreatePieces();

        pieces = pieces_init.getPieces();
        
        // TODO: Create a hashmap to link names with pictures

        for (int i = 0; i < pieces.length; i++){
            for (int j = 0; j < pieces[i].length; j++){
                // we will change this later for a photo of the pieces
                board[pieces[i][j].getRow()][pieces[i][j].getCol()].setText(pieces[i][j].getName());
                board[pieces[i][j].getRow()][pieces[i][j].getCol()].setName(
                    pieces[i][j].getName() + " " + pieces[i][j].getName());
            }
        }
    }
}

package core;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
// math is for rotations, will be discussed later
import java.math.*;

import objects.Piece;

public class BoardManager {
    private static JLabel[][] board;
    private static JFrame gameWindow;
    private static Piece[][] pieces;
    private ChessGUI gui;

    public BoardManager() {
        // create game window, features, and its operations
        gameWindow = new JFrame();
        gameWindow.setSize(1280, 960);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setTitle("Chess Game by Mark Ira Galang");
        gameWindow.setLayout(null);

        // initialize GUI and get the board
        gui = new ChessGUI();
        board = gui.getBoard();

        // add the board into the frame
        JPanel panelBoard = createDisplayBoard();
        panelBoard.setBounds(50, 50, 900, 760);
        gameWindow.add(panelBoard);

        // add the panel that contains the pieces that are taken

        // fill board with pieces
        fillBoard();

        // display window
        gameWindow.setVisible(true);
    }

    // this is just a helper method to display the board on the console, would not be used in the future
    // We now rely on ChessGUI to generate the board
    // Kept for structure consistency, but not used directly
    public JPanel createDisplayBoard() {
        return gui.createChessBoardPanel();
    }

    // fillBoard is like a reset button that just adds the pieces
    private void fillBoard() {
        // get the pieces
        CreatePieces pieceManager = new CreatePieces();
        pieces = pieceManager.getPieces();

        // TODO: Create a hashmap to link names with pictures

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                String piece_name = pieces[i][j].getColor() + " " + pieces[i][j].getName();

                // row checks what piece will we add into the board
                // if i < 2, black, else white
                int row = i < 2 ? i : (i + 4);

                gui.setSquareName(row, j, piece_name);
                gui.setSquareText(row, j, piece_name);

                pieces[i][j].setRow(row);
                pieces[i][j].setCol(j);

                addPieceFunctionality(pieces[i][j], row, j);
            }
        }
    }

    public void addPieceFunctionality(Piece piece, int p_row, int p_col) {
        // p_row and p_column are called piece row and piece column for short

        String name = piece.getName();
        JLabel label = gui.getLabel(p_row, p_col);

        // add a switch statement to apply logic based on the piece
        switch (name) {
            case "Pawn":
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int row = piece.getRow();
                        int col = piece.getCol();
                        int dir = piece.getColor().equals("White") ? -1 : 1;
                        
                        gui.clearHighlights();

                        // Forward one square
                        if (isValid(row + dir, col) && gui.getSquareText(row + dir, col).equals("")) {
                            gui.highlightSquare(row + dir, col, Color.GREEN);
                        }

                        // Forward two squares on first move
                        if (!piece.hasMoved()
                                && isValid(row + dir, col) && gui.getSquareText(row + dir, col).equals("")
                                && isValid(row + 2 * dir, col) && gui.getSquareText(row + 2 * dir, col).equals("")) {
                            gui.highlightSquare(row + 2 * dir, col, Color.GREEN);
                        }
                        
                        // update position (implemented later, for now we add this)
                        System.out.println("Pawn clicked at: " + row + "," + col);
                    }
                });
                break;

            // TODO: Implement other piece logic
            case "Rook":
                // Placeholder for Rook logic
                break;
            case "Knight":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        int row = piece.getRow();
                        int col = piece.getCol();

                        gui.clearHighlights();
                        // for knight logic, its a 6 Ls around itself
                        // easiest I can think of is that we go two spaces in North, East, South, and West, then look at the adjacent squares
                        // we will just use sin and cosine to roate 
                        for (int degree = 0; degree < 360; degree += 90){
                            double radian = Math.toRadians(degree);
                            int tempRow = (int) Math.cos(radian);
                            int tempCol = (int) Math.sin(radian);

                            // then we check the adjacent squares based on if sin is 0 or 1
                            // if sin is 0, then we affect the col, otherwise we affect the row
                            if ((int)Math.sin(degree) == 1){
                                if (isValid(row, col + 1) && gui.getSquareText(row, col + 1).equals(""))
                                    gui.highlightSquare(row, col + 1, Color.GREEN);
                                
                                if (isValid(row, col - 1) && gui.getSquareText(row, col - 1).equals(""))
                                    gui.highlightSquare(row, col - 1, Color.GREEN);
                            } 
                            else {
                                if (isValid(row + 1, col) && gui.getSquareText(row + 1, col).equals(""))
                                    gui.highlightSquare(row, col + 1, Color.GREEN);
                                
                                if (isValid(row - 1, col) && gui.getSquareText(row - 1, col).equals(""))
                                    gui.highlightSquare(row, col - 1, Color.GREEN);
                            }

                            
                        }
                        System.out.println("Knight clicked at: " + row + "," + col);
                    }
                });
                break;
            case "Bishop":
                // Placeholder for Bishop logic
                break;
            case "Queen":
                // Placeholder for Queen logic
                break;
            case "King":
                // Placeholder for King logic
                break;

            default:
                break;
        }
    }

    // Helper method
    private boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}

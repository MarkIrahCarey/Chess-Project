package core;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
// math is for rotations, will be discussed later
import java.math.*;

import objects.Piece;

public class BoardManager {
    private static JLabel[][] board;
    private static JFrame gameWindow;
    private static Piece[][] pieces;
    private ChessGUI gui;

    // this is for the movement logic
    private Piece selectedPiece = null;

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
        // fillBoard();
        fillTestBoard();

        // add movement logic to that square
        addBoardClickListeners();
        
        // display window
        gameWindow.setVisible(true);
    }

    private void addBoardClickListeners() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JLabel square = gui.getLabel(row, col);
                final int r = row;
                final int c = col;

                square.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        // If this square is highlighted and there's a selected piece, move to it
                        if (selectedPiece != null && gui.isHighlighted(r, c)) {
                            movePiece(selectedPiece, r, c);
                            gui.clearHighlights();
                            selectedPiece = null;
                        }
                    }
                });
            }
        }
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

    public void fillTestBoard(){
        CreatePieces cp = new CreatePieces();
        Piece[] testPieces = cp.getTestPieces();

        int row = 3;  
        int col = 0;

        for (Piece p : testPieces) {
            // Wrap to next row if needed
            if (col >= 8) {
                row++;
                col = 0;
            }

            String name = p.getColor() + " " + p.getName();
            p.setRow(row);
            p.setCol(col);

            gui.setSquareName(row, col, name);
            gui.setSquareText(row, col, name);

            addPieceFunctionality(p, row, col);

            col++;
        }
    }

    public void addPieceFunctionality(Piece piece, int p_row, int p_col) {
        // p_row and p_column are called piece row and piece column for short

        String name = piece.getName();
        JLabel label = gui.getLabel(p_row, p_col);

        // since my logic sucks, apparenlty when we move pieces, where I set the mouse listener
        // still holding the piece logic even if they move
        // so we will remove all the logic, then re-add
        for (MouseListener ml : label.getMouseListeners()) {
            label.removeMouseListener(ml);
        }

        // add a switch statement to apply logic based on the piece
        switch (name) {
            case "Pawn":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedPiece = piece;

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

            case "Knight":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedPiece = piece;

                        int row = piece.getRow();
                        int col = piece.getCol();

                        gui.clearHighlights();
                        // for knight logic, its a 6 Ls around itself
                        // easiest I can think of is that we go two spaces in North, East, South, and West, then look at the adjacent squares
                        // we will just use sin and cosine to roate the L shape
                        int[][] L_shapes = {{2, 1}, {1, 2}};
                        
                        for (int[] L : L_shapes){
                            int init_row = L[0];
                            int init_col = L[1];

                            for (int degree = 0; degree < 360; degree += 90){
                                double radians = Math.toRadians(degree);

                                // get the rotation for row and column
                                int rotatedRowOffset = (int) Math.round(init_row * Math.cos(radians) - init_col * Math.sin(radians));
                                int rotatedColOffset = (int) Math.round(init_row * Math.sin(radians) + init_col * Math.cos(radians));
                                
                                // then apply that to the location of the knight
                                int newRow = row + rotatedRowOffset;
                                int newCol = col + rotatedColOffset;
                                
                                // then check for position is it is valid
                                if (isValid(newRow, newCol) && gui.getSquareText(newRow, newCol).equals("")) {
                                    gui.highlightSquare(newRow, newCol, Color.GREEN);
                                }
                            }
                        }
                        System.out.println("Knight clicked at: " + row + "," + col);
                    }
                });
                break;
            case "Bishop":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedPiece = piece;
                        pieceLogicQueenRookBishop(piece);
                    }
                });
                break;
            case "Rook":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedPiece = piece;
                        pieceLogicQueenRookBishop(piece);
                    }
                });
                break;
            case "Queen":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        selectedPiece = piece;
                        pieceLogicQueenRookBishop(piece);
                    }
                });

                break;
            case "King":
                label.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e){
                        selectedPiece = piece;

                        int row = piece.getRow();
                        int col = piece.getCol();
                        gui.clearHighlights();

                        // King is just a 1 square around
                        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

                        // then we loop through
                        for (int[] dir : directions){
                            int x = dir[0] + row;
                            int y = dir[1] + col;

                            if (isValid(x, y) &&
                                gui.getSquareText(x, y).equals("")) {
                                gui.highlightSquare(x, y, Color.GREEN);
                            }
                        }
                        System.out.println("King clicked at: " + row + "," + col);
                    }
                }); 
                break;

            default:
                break;
        }
        System.out.println("Added Functionality to " + name);
    }

    private void addMovementLogic(int row, int col){
        if (selectedPiece != null) {
            int destRow = row;
            int destCol = col;

            // Check if square is highlighted
            if (gui.isHighlighted(destRow, destCol)) {
                movePiece(selectedPiece, destRow, destCol);
                gui.clearHighlights();
                selectedPiece = null;
            }
        }
    }
    private void movePiece(Piece piece, int newRow, int newCol){
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();

        // update the GUI on the old square
        gui.setSquareText(oldRow, oldCol, "");
        gui.setSquareName(oldRow, oldCol, "");

        // then swap to the new square
        gui.setSquareText(newRow, newCol, piece.getColor() + " " + piece.getName());
        gui.setSquareName(newRow, newCol, piece.getColor() + " " + piece.getName());

        // Update piece position
        piece.setRow(newRow);
        piece.setCol(newCol);
        piece.setMoved(); // for the pawn logic

        // then add the new logic
        addPieceFunctionality(piece, newRow, newCol);
    }

    // then the game logic will be in the game class, we will return the board, pieces, etc
    public JLabel[][] getBoard(){
        return board;
    }

    public Piece[][] getPieces(){
        return pieces;
    }

    public JFrame getWindow(){
        return gameWindow;
    }

    public ChessGUI getGUI(){
        return gui;
    }

    // Helper method
    private boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // since the Queen is both the rook and bishop, I combined all of the logic together
    private void pieceLogicQueenRookBishop(Piece piece){
        int row = piece.getRow();
        int col = piece.getCol();
        gui.clearHighlights();

        String name = piece.getName();

        // then, queen is both bishop and rook, so we take dependent on name
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},     // Rook directions
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}    // Bishop directions
        };

        int start = 0;
        int end = directions.length;

        switch (name){
            case "Rook": end = 4;  // Only the first 4 directions
            break;

            case "Bishop": start = 4; // Only the last 4 directions
            break;

            case "Queen": // Use all 8 directions
            break;

            default:
            return;
        }

        for (int i = start; i < end; i++){
            // grab the directions
            int x = directions[i][0];
            int y = directions[i][1];

            // then get the distance for each square
            int distance = 1;

            while (isValid(row + x * distance, col + y * distance) &&
                gui.getSquareText(row + x * distance, col + y * distance).equals("")) {
                gui.highlightSquare(row + x * distance, col + y * distance, Color.GREEN);
                distance++;
            }
        }
        System.out.println(name + " clicked at: " + row + "," + col);
    }
}

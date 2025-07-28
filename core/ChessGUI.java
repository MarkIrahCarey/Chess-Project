package core;

import javax.swing.*;
import java.awt.*;

public class ChessGUI {
    private JLabel[][] board;

    public ChessGUI() {
        board = new JLabel[8][8];
    }

    public JPanel createChessBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 8));
        panel.setSize(900, 760);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                JLabel square = new JLabel("", SwingConstants.CENTER);
                square.setOpaque(true);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                square.setFont(new Font("Arial", Font.PLAIN, 16));

                board[i][j] = square;
                panel.add(square);
            }
        }

        return panel;
    }

    public JLabel[][] getBoard() {
        return board;
    }

    public void clearHighlights() {
        for (JLabel[] row : board) {
            for (JLabel square : row) {
                square.setBackground(null);
            }
        }
    }

    public void highlightSquare(int row, int col, Color color) {
        if (isValid(row, col)) {
            board[row][col].setBackground(color);
        }
    }

    public void setSquareText(int row, int col, String text) {
        if (isValid(row, col)) {
            board[row][col].setText(text);
        }
    }

    public void setSquareName(int row, int col, String name) {
        if (isValid(row, col)) {
            board[row][col].setName(name);
        }
    }

    public String getSquareText(int row, int col) {
        if (isValid(row, col)) {
            return board[row][col].getText();
        }
        return "";
    }

    public JLabel getLabel(int row, int col) {
        if (isValid(row, col)) {
            return board[row][col];
        }
        return null;
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}


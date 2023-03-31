package com.backstreetbrogrammer.ch03_tdd;

public class TicTacToe {

    private static final String EMPTY = "-1";

    private final String[][] board = new String[][]{
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public void play(final int x, final int y) {
        checkAxis(x, "X");
        checkAxis(y, "Y");
        setBox(x, y);
    }

    private void checkAxis(final int axis, final String axisName) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(String.format("%s is outside board", axisName));
        }
    }

    private void setBox(int x, int y) {
        if (EMPTY.equals(board[x - 1][y - 1])) {
            board[x - 1][y - 1] = "X";
        } else {
            throw new RuntimeException("Box is already occupied");
        }
    }

}

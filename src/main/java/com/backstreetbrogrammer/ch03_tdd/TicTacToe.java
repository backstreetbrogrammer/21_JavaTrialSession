package com.backstreetbrogrammer.ch03_tdd;

import java.util.Arrays;

public class TicTacToe {

    private static final String EMPTY = "-1";

    enum Player {
        X("X"),
        O("O"),
        UNKNOWN("Unknown");

        private final String player;

        Player(final String player) {
            this.player = player;
        }

        public static Player fromPlayer(final String player) {
            return Arrays.stream(values())
                         .filter(pl -> pl.player.equals(player))
                         .findFirst()
                         .orElse(UNKNOWN);
        }

        @Override
        public String toString() {
            return player;
        }
    }

    private Player lastPlayer = Player.UNKNOWN;

    private final String[][] board = new String[][]{
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public String play(final int x, final int y) {
        checkAxis(x, "X");
        checkAxis(y, "Y");
        lastPlayer = Player.fromPlayer(nextPlayer());
        setBox(x, y, lastPlayer);
        final String result;
        if (hasWin()) {
            result = String.format("%s is the winner", lastPlayer);
        } else if (isDraw()) {
            result = "The result is draw";
        } else {
            result = "No winner";
        }
        return result;
    }

    private boolean isDraw() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (EMPTY.equals(board[x][y])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasWin() {
        return horizontalWinCheck() || verticalWinCheck() || diagonalWinCheck();
    }

    private boolean horizontalWinCheck() {
        for (int index = 0; index < 3; index++) {
            if (board[0][index].equals(lastPlayer.player) &&
                    board[1][index].equals(lastPlayer.player) &&
                    board[2][index].equals(lastPlayer.player)) {
                return true;
            }
        }
        return false;
    }

    private boolean verticalWinCheck() {
        for (int index = 0; index < 3; index++) {
            if (board[index][0].equals(lastPlayer.player) &&
                    board[index][1].equals(lastPlayer.player) &&
                    board[index][2].equals(lastPlayer.player))
                return true;
        }
        return false;
    }

    private boolean diagonalWinCheck() {
        return (board[0][2].equals(lastPlayer.player) &&
                board[1][1].equals(lastPlayer.player) &&
                board[2][0].equals(lastPlayer.player)) // topLeft to bottomRight
                || (board[2][2].equals(lastPlayer.player) &&
                board[1][1].equals(lastPlayer.player) &&
                board[0][0].equals(lastPlayer.player)); // topRight to bottomLeft
    }

    private void checkAxis(final int axis, final String axisName) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException(String.format("%s is outside board", axisName));
        }
    }

    private void setBox(final int x, final int y, final Player lastPlayer) {
        if (EMPTY.equals(board[x - 1][y - 1])) {
            board[x - 1][y - 1] = lastPlayer.player;
        } else {
            throw new RuntimeException("Box is already occupied");
        }
    }

    public String nextPlayer() {
        if (lastPlayer == Player.X) {
            return Player.O.player;
        }
        return Player.X.player;
    }

}

package com.backstreetbrogrammer.ch03_tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicTacToeTest {

    private TicTacToe ticTacToe;

    @BeforeEach
    void setUp() {
        ticTacToe = new TicTacToe();
    }

    @Test
    @DisplayName("When a piece is placed outside the X axis, then RuntimeException is thrown")
    void whenXOutsideBoardThenThrowRuntimeException() {
        final Throwable exception = assertThrows(RuntimeException.class, () -> ticTacToe.play(6, 2));
        assertEquals(exception.getMessage(), "X is outside board");
    }

    @Test
    @DisplayName("When a piece is placed outside the Y axis, then RuntimeException is thrown")
    void whenYOutsideBoardThenThrowRuntimeException() {
        final Throwable exception = assertThrows(RuntimeException.class, () -> ticTacToe.play(2, 5));
        assertEquals(exception.getMessage(), "Y is outside board");
    }

    @Test
    @DisplayName("When a piece is placed on an occupied space, then RuntimeException is thrown")
    void whenBoxOccupiedThenThrowRuntimeException() {
        ticTacToe.play(1, 2);
        final Throwable exception = assertThrows(RuntimeException.class, () -> ticTacToe.play(1, 2));
        assertEquals(exception.getMessage(), "Box is already occupied");
    }

    @Test
    @DisplayName("The first turn should be played by player X")
    void firstTurnShouldBePlayerX() {
        assertEquals("X", ticTacToe.nextPlayer());
    }

    @Test
    @DisplayName("If the last turn was played by O, then the next turn should be played by X")
    void ifLastPlayerOThenNextPlayerIsX() {
        ticTacToe.play(1, 2); // this will be X
        ticTacToe.play(2, 1); // this will be O
        assertEquals("X", ticTacToe.nextPlayer());
    }

    @Test
    @DisplayName("If no winning condition is fulfilled, then there is no winner")
    public void ifNoWinningConditionThenNoWinner() {
        final String actual = ticTacToe.play(1, 2);
        assertEquals("No winner", actual);
    }

    @Test
    @DisplayName("The player wins when the whole horizontal line is occupied by her pieces")
    public void whenWholeHorizontalLineIsOccupiedByXThenXIsWinner() {
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 1); // X
        ticTacToe.play(2, 2); // O
        final String actual = ticTacToe.play(3, 1); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    @DisplayName("The player wins when the whole vertical line is occupied by her pieces")
    public void whenWholeVerticalLineIsOccupiedByXThenXIsWinner() {
        ticTacToe.play(1, 1); // X
        ticTacToe.play(2, 1); // O
        ticTacToe.play(1, 2); // X
        ticTacToe.play(2, 2); // O
        final String actual = ticTacToe.play(1, 3); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    @DisplayName("The player wins when the whole diagonal line from the top-left to bottom-right is occupied by her pieces")
    public void whenWholeDiagonalLineFromTopLeftToBottomRightIsOccupiedByXThenXIsWinner() {
        ticTacToe.play(1, 1); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        final String actual = ticTacToe.play(3, 3); // X
        assertEquals("X is the winner", actual);
    }

    @Test
    @DisplayName("The player wins when the whole diagonal line from the top-right to bottom-left is occupied by her pieces")
    public void whenWholeDiagonalLineFromTopRightToBottomLeftIsOccupiedByXThenXIsWinner() {
        ticTacToe.play(3, 3); // X
        ticTacToe.play(1, 2); // O
        ticTacToe.play(2, 2); // X
        ticTacToe.play(1, 3); // O
        final String actual = ticTacToe.play(1, 1); // X
        assertEquals("X is the winner", actual);
    }

}

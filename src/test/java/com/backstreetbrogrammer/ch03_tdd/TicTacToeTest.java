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

}

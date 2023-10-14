package edu.hw1;

import static edu.hw1.utils.ExceptionMessageTask8.BOARD_SIZE_MUST_BE_8x8;
import static edu.hw1.utils.ExceptionMessageTask8.ZERO_OR_ONE_IN_BOARD;

public final class Task8 {
    private static final int BOARD_SIZE = 8;
    private static final int[] DX = {2, 2, 1, 1, -2, -2, -1, -1};
    private static final int[] DY = {1, -1, 2, -2, 1, -1, 2, -2};

    private Task8() {
    }

    private static void numberZeroOrOne(int[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 0 && board[i][j] != 1) {
                    throw new RuntimeException(ZERO_OR_ONE_IN_BOARD.getMessage());
                }
            }
        }
    }

    public static boolean knightBoardCapture(int[][] board) {
        if (board.length != BOARD_SIZE) {
            throw new RuntimeException(BOARD_SIZE_MUST_BE_8x8.getMessage());
        }
        for (int[] line : board) {
            if (line.length != BOARD_SIZE) {
                throw new RuntimeException(BOARD_SIZE_MUST_BE_8x8.getMessage());
            }
        }

        numberZeroOrOne(board);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 1) {
                    for (int k = 0; k < DX.length; k++) {
                        int x = i + DX[k];
                        int y = j + DY[k];
                        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && board[x][y] == 1) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}

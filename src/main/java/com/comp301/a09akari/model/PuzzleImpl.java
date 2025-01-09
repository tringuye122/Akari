package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;
  private final CellType[][] grid;

  public PuzzleImpl(int[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException();
    }

    this.board = board;
    int rows = board.length;
    int cols = board[0].length;
    grid = new CellType[board.length][board[0].length];

    for (int index = 0; index < rows; index++) {
      if (board[index].length != cols) {
        throw new IllegalArgumentException();
      }
      for (int col = 0; col < cols; col++) {
        grid[index][col] = assignCellType(board[index][col]);
      }
    }
  }

  private CellType assignCellType(int value) {
    switch (value) {
      case 0:
        return CellType.CLUE;
      case 1:
        return CellType.CLUE;
      case 2:
        return CellType.CLUE;
      case 3:
        return CellType.CLUE;
      case 4:
        return CellType.CLUE;
      case 5:
        return CellType.WALL;
      case 6:
        return CellType.CORRIDOR;
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  private void validateCoordinates(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public CellType getCellType(int r, int c) {
    validateCoordinates(r, c);
    return grid[r][c];
  }

  @Override
  public int getClue(int r, int c) {
    validateCoordinates(r, c);
    CellType cellType = grid[r][c];
    if (cellType != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}

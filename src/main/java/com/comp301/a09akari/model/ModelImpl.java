package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary library;
  private int active;
  private List<List<Integer>> lampLocations;
  private boolean solved;
  private List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }

    this.library = library;
    this.active = 0;
    this.lampLocations = new ArrayList<List<Integer>>();
    this.solved = false;
    this.observers = new ArrayList<ModelObserver>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    ArrayList<Integer> location = new ArrayList<>();
    location.add(r);
    location.add(c);
    for (List<Integer> list : this.lampLocations) {
      if (list.equals(location)) {
        return;
      }
    }
    this.lampLocations.add(location);
    for (ModelObserver o : this.observers) {
      o.update(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    ArrayList<Integer> location = new ArrayList<>();
    location.add(r);
    location.add(c);
    for (List<Integer> list : this.lampLocations) {
      if (list.equals(location)) {
        this.lampLocations.remove(location);
        for (ModelObserver o : this.observers) {
          o.update(this);
        }
        return;
      }
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    if (this.isLamp(r, c)) {
      return true;
    }

    for (int row = r - 1; row >= 0; row--) {
      if (this.library.getPuzzle(active).getCellType(row, c) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(row, c)) {
        return true;
      }
    }

    for (int row = r + 1; row < this.getActivePuzzle().getHeight(); row++) {
      if (this.getActivePuzzle().getCellType(row, c) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(row, c)) {
        return true;
      }
    }

    for (int col = c - 1; col >= 0; col--) {
      if (this.getActivePuzzle().getCellType(r, col) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(r, col)) {
        return true;
      }
    }

    for (int col = c + 1; col < this.getActivePuzzle().getWidth(); col++) {
      if (this.getActivePuzzle().getCellType(r, col) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(r, col)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    ArrayList<Integer> location = new ArrayList<>();
    location.add(r);
    location.add(c);
    for (List<Integer> list : this.lampLocations) {
      if (list.equals(location)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (!this.isLamp(r, c)) {
      throw new IllegalArgumentException();
    }

    for (int row = r - 1; row >= 0; row--) {
      if (this.getActivePuzzle().getCellType(row, c) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(row, c)) {
        return true;
      }
    }

    for (int row = r + 1; row < this.getActivePuzzle().getHeight(); row++) {
      if (this.getActivePuzzle().getCellType(row, c) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(row, c)) {
        return true;
      }
    }

    for (int col = c - 1; col >= 0; col--) {
      if (this.getActivePuzzle().getCellType(r, col) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(r, col)) {
        return true;
      }
    }

    for (int col = c + 1; col < this.getActivePuzzle().getWidth(); col++) {
      if (this.getActivePuzzle().getCellType(r, col) != CellType.CORRIDOR) {
        break;
      }
      if (this.isLamp(r, col)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.library.getPuzzle(this.active);
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.active;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= this.getPuzzleLibrarySize() || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    this.active = index;
    for (ModelObserver o : this.observers) {
      o.update(this);
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return this.library.size();
  }

  @Override
  public void resetPuzzle() {
    this.lampLocations.clear();
    for (ModelObserver o : this.observers) {
      o.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < this.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < this.getActivePuzzle().getWidth(); c++) {
        if (this.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
          if (!this.isLit(r, c)) {
            return false;
          }
          if (this.isLamp(r, c)) {
            if (this.isLampIllegal(r, c)) {
              return false;
            }
          }
        }
        if (this.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          if (!this.isClueSatisfied(r, c)) {
            return false;
          }
        }
      }
    }
    this.solved = true;
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= this.getActivePuzzle().getHeight()
        || c >= this.getActivePuzzle().getWidth()
        || r < 0
        || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (this.getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    int numLamps = 0;
    if (r > 0) {
      if (this.getActivePuzzle().getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (this.isLamp(r - 1, c)) {
          numLamps++;
        }
      }
    }
    if (r + 1 < this.getActivePuzzle().getHeight()) {
      if (this.getActivePuzzle().getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (this.isLamp(r + 1, c)) {
          numLamps++;
        }
      }
    }
    if (c > 0) {
      if (this.getActivePuzzle().getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (this.isLamp(r, c - 1)) {
          numLamps++;
        }
      }
    }
    if (c + 1 < this.getActivePuzzle().getWidth()) {
      if (this.getActivePuzzle().getCellType(r, c + 1) == CellType.CORRIDOR)
        if (this.isLamp(r, c + 1)) {
          numLamps++;
        }
    }
    return (numLamps == this.getActivePuzzle().getClue(r, c));
  }

  @Override
  public void addObserver(ModelObserver observer) {
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    this.observers.remove(observer);
  }
}

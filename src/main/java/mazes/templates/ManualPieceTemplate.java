package mazes.templates;

import mazes.Maze;
import mazes.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManualPieceTemplate implements  MazeTemplate
{


  private int x;
  private int y;
  private char piece;

  public ManualPieceTemplate(int x, int y, char piece)
  {
    this.x = x;
    this.y = y;

    this.piece = piece;
  }

  @Override
  public List<List<Character>> apply(List<List<Character>> maze)
  {
    maze.get(y).set(x, piece);
    return maze;
  }
}

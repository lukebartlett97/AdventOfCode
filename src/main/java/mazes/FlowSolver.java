package mazes;

import java.util.ArrayList;
import java.util.List;

public class FlowSolver extends RunSolver
{
  private static final boolean VERBOSE = false;
  String RESOURCE_PATH = "/mazes/maze1/";

  public FlowSolver()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  public String solve(Maze maze) throws InterruptedException
  {
    solveFull(maze);
    printMaze(maze);
    maze.repaint();
    return Integer.toString(maze.getEndPoint().value - 1);
  }

  public void solveFull(Maze maze) throws InterruptedException
  {
    solveFull(maze, new ArrayList<>());
  }

  public void solveFull(Maze maze, List<Point> existingPath) throws InterruptedException
  {
    List<Point> newExistingPath = new ArrayList<>();
    for(Point point : existingPath)
    {
      newExistingPath.add(maze.get(point.x, point.y));
    }
    solvePoint(maze, maze.getStartPoint(), 1, newExistingPath, false);
    List<Point> path = getPath(maze, maze.getEndPoint());
    maze.setPath(path, true);
  }

  public void solveBasic(Maze maze) throws InterruptedException
  {
    solvePoint(maze, maze.getStartPoint(), 1, new ArrayList<>(), true);
  }

  @SuppressWarnings("Duplicates")
  private List<Point> getPath(Maze maze, Point current)
  {
    if(current == maze.getStartPoint())
    {
      List<Point> path = new ArrayList<>();
      path.add(current);
      return path;
    }
    Point bestPoint = null;
    Point point = maze.get(current.x, current.y - 1);
    if(point != null && point.value > 0 && point.value < current.value)
    {
      bestPoint = point;
    }
    point = maze.get(current.x + 1, current.y );
    if(point != null && point.value > 0 && point.value < current.value && (bestPoint == null || point.value < bestPoint.value))
    {
      bestPoint = point;
    }
    point = maze.get(current.x, current.y + 1);
    if(point != null && point.value > 0 && point.value < current.value && (bestPoint == null || point.value < bestPoint.value))
    {
      bestPoint = point;
    }
    point = maze.get(current.x - 1, current.y);
    if(point != null && point.value > 0 && point.value < current.value && (bestPoint == null || point.value < bestPoint.value))
    {
      bestPoint = point;
    }
    if(bestPoint != null)
    {
      //System.out.println("Going to " + bestPoint + " with value " + bestPoint.value);
      List<Point> path = getPath(maze, bestPoint);
      path.add(current);
      return path;
    }
    return new ArrayList<>();
  }

  void solvePoint(Maze maze, Point currentPoint, int value, List<Point> path, boolean basic) throws InterruptedException
  {
    if(currentPoint == null || currentPoint.space == Maze.WALL)
    {
      return;
    }
    if(currentPoint.value == 0 || (currentPoint.value > value && !basic))
    {
      int count = 0;
      count += path.contains(maze.get(currentPoint.x, currentPoint.y - 1)) ? 1 : 0;
      count += path.contains(maze.get(currentPoint.x - 1, currentPoint.y)) ? 1 : 0;
      count += path.contains(maze.get(currentPoint.x, currentPoint.y + 1)) ? 1 : 0;
      count += path.contains(maze.get(currentPoint.x + 1, currentPoint.y)) ? 1 : 0;
      if(count > 1)
      {
        return;
      }
      currentPoint.value = value;
      maze.paintSquare(null, currentPoint);
      printMaze(maze);
      List<Point> newPath = new ArrayList<>(path);
      newPath.add(currentPoint);
      if(currentPoint.space != Maze.END)
      {
        solvePoint(maze, maze.get(currentPoint.x, currentPoint.y - 1), value + 1, newPath, basic);
        solvePoint(maze, maze.get(currentPoint.x + 1, currentPoint.y), value + 1, newPath, basic);
        solvePoint(maze, maze.get(currentPoint.x, currentPoint.y + 1), value + 1, newPath, basic);
        solvePoint(maze, maze.get(currentPoint.x - 1, currentPoint.y), value + 1, newPath, basic);
      }
    }
  }

  private void printMaze(Maze maze) throws InterruptedException
  {
    if(VERBOSE)
    {
      for(List<Point> line : maze.getMap())
      {
        StringBuilder sb = new StringBuilder();
        for(Point point : line)
        {
          boolean addSpace = true;
          if(point.space == Maze.WALL)
          {
            sb.append("## ");
          }
          else
          {
            sb.append(point.value);
            sb.append(" ");
            if (point.value < 10)
            {
              sb.append(" ");
            }
          }
        }
        printInfo(sb.toString());
      }
    }
    if(maze.isDrawn())
    {
      Thread.sleep(10);
    }
  }
}

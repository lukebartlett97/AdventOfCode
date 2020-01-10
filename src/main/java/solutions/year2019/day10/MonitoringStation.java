package solutions.year2019.day10;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class MonitoringStation extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day10/";

  public MonitoringStation()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    return "";
  }

  class Grid
  {
    List<List<Point>> points = new ArrayList<>();

    private Grid()
    {

    }

    public Grid(List<String> data)
    {
      for(String line : data)
      {
        List<Point> pointLine = new ArrayList<>();
        for(char character : line.toCharArray())
        {
          pointLine.add(new Point(character));
        }
      }
    }

    public Grid clone()
    {
      Grid copy = new Grid();
      for(List<Point> line : points)
      {
        List<Point> pointLine = new ArrayList<>();
        for(Point point : line)
        {
          pointLine.add(point.clone());
        }
        copy.points.add(pointLine);
      }
      return copy;
    }

    public void markBlocked(int originX, int originY, int blockerX, int blockerY)
    {
      int xDiff = blockerX - originX;
      int yDiff = blockerY - originY;
      int divider = 1;
      for(int i = Math.max(xDiff, yDiff); i>0; i--)
      {
        if(xDiff % i == 0 && yDiff % i == 0)
        {
          divider = i;
          break;
        }
      }
      xDiff /= divider;
      yDiff /= divider;
      int count = 1;
      while(inRange(blockerX + (xDiff * count), blockerY + (yDiff * count)))
      {
        points.get(blockerY + (yDiff * count)).get(blockerX + (xDiff * count)).type = PointType.BLOCKED;
      }
    }

    private boolean inRange(int x, int y)
    {
      return y < points.size() && x < points.get(0).size();
    }
  }

  class Point
  {
    PointType type = PointType.BLANK;

    private Point(PointType typeIn)
    {
      type = typeIn;
    }

    Point(char character)
    {
      switch(character)
      {
        case '.':
          type = PointType.BLANK;
          break;
        case '#':
          type = PointType.ASTEROID;
          break;
        case 'x':
          type = PointType.BLOCKED;
          break;
        case 'o':
          type = PointType.STATION;
          break;
      }
    }

    public Point clone()
    {
      return new Point(type);
    }
  }

  enum PointType
  {
    BLANK,
    ASTEROID,
    STATION,
    BLOCKED
  }
}

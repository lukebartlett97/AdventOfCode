package solutions.year2019.day3;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrossedWires extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day3/";

  public CrossedWires()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data) throws IOException
  {
    List<Line> wire1 = createWire(data.get(0).split(","));
    printInfo(wire1.toString());
    List<Line> wire2 = createWire(data.get(1).split(","));
    printInfo(wire2.toString());
    List<Intersection> allIntersections = new ArrayList<>();
    for(Line line : wire1)
    {
      for(Line line2 : wire2)
      {
        if(line.intersects(line2))
        allIntersections.add(line.getIntersection(line2));
      }
    }
    printInfo(allIntersections.toString());
    return allIntersections.stream().map(Intersection::getValue).filter(x -> x != 0).min(Integer::compareTo).orElse(0).toString();
  }

  private List<Line> createWire(String[] lines)
  {
    Point currentPoint = new Point(0,0);
    List<Line> out = new ArrayList<>();
    int currentDistance = 0;
    for(String line : lines)
    {
      char direction = line.charAt(0);
      int distance = Integer.parseInt(line.substring(1));
      Point endPoint;
      switch(direction)
      {
        case 'U':
          endPoint = new Point(currentPoint.x, currentPoint.y + distance);
          break;
        case 'D':
          endPoint = new Point(currentPoint.x, currentPoint.y - distance);
          break;
        case 'L':
          endPoint = new Point(currentPoint.x - distance, currentPoint.y);
          break;
        case 'R':
          endPoint = new Point(currentPoint.x + distance, currentPoint.y);
          break;
        default:
          endPoint = new Point(currentPoint.x, currentPoint.y);
      }
      out.add(new Line(currentPoint.copy(),endPoint.copy(), currentDistance));
      currentDistance += distance;
      currentPoint = endPoint;
    }
    return out;
  }

  class Line
  {
    final Point startPoint;
    final Point endPoint;
    final int startDistance;
    Line(Point start, Point end, int startDistance)
    {
      startPoint = start;
      endPoint = end;
      this.startDistance = startDistance;
    }

    Intersection getIntersection(Line otherLine)
    {
      if(startPoint.x == endPoint.x)
      {
        return new Intersection(new Point(startPoint.x, otherLine.startPoint.y), startDistance + Math.abs(otherLine.startPoint.y - startPoint.y), otherLine.startDistance + Math.abs(otherLine.startPoint.x - startPoint.x));
      } else {
        return new Intersection(new Point(otherLine.startPoint.x, startPoint.y), startDistance + Math.abs(otherLine.startPoint.x - startPoint.x), otherLine.startDistance + Math.abs(otherLine.startPoint.y - startPoint.y));
      }
    }

    @Override
    public String toString()
    {
      return startPoint + "->" + endPoint;
    }

    boolean intersects(Line line2)
    {
      return (Integer.compare(startPoint.x, line2.startPoint.x) != Integer.compare(endPoint.x, line2.endPoint.x))
              && (Integer.compare(startPoint.y, line2.startPoint.y) != Integer.compare(endPoint.y, line2.endPoint.y));
    }
  }

  class Point
  {
    final int x;
    final int y;
    Point(int x, int y)
    {
      this.x = x;
      this.y = y;
    }

    Point copy()
    {
      return new Point(x, y);
    }

    int getValue()
    {
      return Math.abs(x) + Math.abs(y);
    }

    @Override
    public String toString()
    {
      return "(" + x + "," + y + ")";
    }
  }

  class Intersection
  {
    final Point point;
    final int line1Distance;
    final int line2Distance;
    Intersection(Point point, int line1Distance, int line2Distance)
    {
      this.point = point;
      this.line1Distance = line1Distance;
      this.line2Distance = line2Distance;
    }

    int getValue()
    {
      return line1Distance + line2Distance;
    }

    @Override
    public String toString()
    {
      return line1Distance + "+" + line2Distance + " at " + point.toString();
    }
  }
}

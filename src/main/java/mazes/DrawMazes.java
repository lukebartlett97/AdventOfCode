package mazes;

import solutions.SolutionMain;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrawMazes extends SolutionMain
{
  String RESOURCE_PATH = "/mazes/maze1/";

  public DrawMazes()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @SuppressWarnings("Duplicates")
  @Override
  protected String solve(List<String> data)
  {
    List<List<Character>> converted = new ArrayList<>();
    for(String line : data)
    {
      converted.add(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
    }
    Maze maze = new Maze(converted);
    maze.repaint();
    return "";
  }
}

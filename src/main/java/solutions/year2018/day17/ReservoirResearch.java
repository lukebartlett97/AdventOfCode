package solutions.year2018.day17;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Day 17: Reservoir Research
// https://adventofcode.com/2018/day/17
public class ReservoirResearch extends SolutionMain
{
  String RESOURCE_PATH = "/year2018/Day17/";

  public ReservoirResearch()
  {
    setResourcePath(RESOURCE_PATH);
  }

  protected String solve(List<String> data) throws IOException
  {
    return "I dunno yet";
  }

  private List<Clay> getClayBlocks(List<String> data)
  {
    List<Clay> blocks = new ArrayList<>();
    for(String line : data)
    {

    }
    return blocks;
  }

  private List<String> breakUpLine(String line)
  {
    List<String> out = new ArrayList<>();
    String[] split = line.split(",");
    String[] first = split[0].split("=");
    //TODO: Stuff
    return out;
  }


  class Clay
  {
    final int xPos;
    final int yPos;
    Clay(int xPos, int yPos)
    {

      this.xPos = xPos;
      this.yPos = yPos;
    }
  }
}

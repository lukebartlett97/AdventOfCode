package solutions.year2018.day25;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Day 17: Reservoir Research
// https://adventofcode.com/2018/day/17
public class Constellations extends SolutionMain
{
  String RESOURCE_PATH = "/year2018/Day25/";

  public Constellations()
  {
    setResourcePath(RESOURCE_PATH);
  }

  protected String solve(List<String> data)
  {
    List<Star> stars = data.stream().map(Star::new).collect(Collectors.toList());
    List<Constellation> constellations = new ArrayList<>();
    for(Star star : stars)
    {
      List<Constellation> matchingConstellations = new ArrayList<>();
      for(Constellation constellation : constellations)
      {
        if(constellation.withinRange(star))
        {
          matchingConstellations.add(constellation);
        }
      }
      if(matchingConstellations.isEmpty())
      {
        Constellation newConstellation = new Constellation();
        newConstellation.addStar(star);
        constellations.add(newConstellation);
      } else {
        Constellation base = matchingConstellations.remove(0);
        base.addStar(star);
        for(Constellation extra : matchingConstellations)
        {
          base.addConstellation(extra);
          constellations.remove(extra);
        }
      }
    }
    for(Constellation constellation : constellations)
    {
      printInfo(constellation.toString());
    }
    return Integer.toString(constellations.size());
  }

  private class Constellation
  {
    private List<Star> stars = new ArrayList<>();

    void addStar(Star star)
    {
      stars.add(star);
    }

    List<Star> getStars()
    {
      return stars;
    }

    void addConstellation(Constellation other)
    {
      stars.addAll(other.getStars());
    }

    boolean withinRange(Star star)
    {
      for(Star myStar : stars)
      {
        if(myStar.getDistance(star) <= 3)
        {
          return true;
        }
      }
      return false;
    }

    @Override
    public String toString()
    {
      return stars.toString();
    }
  }

  private class Star
  {
    List<Integer> position = new ArrayList<>();
    Star(String pos)
    {
      String[] split = pos.split(",");
      for(String coord : split)
      {
        position.add(Integer.parseInt(coord));
      }
    }

    int getDistance(Star other)
    {
      int sum = 0;
      for (int i = 0; i < 4; i++)
      {
        sum += Math.abs(position.get(i) - other.position.get(i));
      }
      return sum;
    }

    @Override
    public String toString()
    {
      return position.toString();
    }
  }
}

package solutions.year2019.day6;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class UniversalOrbit extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day6/";

  public UniversalOrbit()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    Map<String, List<String>> dictionary = new HashMap<>();
    for(String line : data)
    {
      line = line.replace(')', '>');
      String[] parts = line.split(">");
      if(dictionary.containsKey(parts[0]))
      {
        List<String> value = dictionary.get(parts[0]);
        value.add(parts[1]);
        dictionary.put(parts[0], value);
      } else {
        List<String> first = new ArrayList<>();
        first.add(parts[1]);
        dictionary.put(parts[0], first);
      }
    }
    Star comStar = new Star("COM", 0, dictionary);
    comStar.printOrbiters();
    comStar.distanceFrom("SAN", "YOU");
    return Integer.toString(comStar.distanceFrom("SAN", "YOU"));
  }

  class Star
  {
    List<Star> orbiters = new ArrayList<>();
    int orbitVal;
    String name;

    public Star(String name, int val, Map<String, List<String>> dictionary)
    {
      orbitVal = val;
      this.name = name;
      if(dictionary.containsKey(name))
      {
        for(String starName : dictionary.get(name))
        {
          orbiters.add(new Star(starName, val+1, dictionary));
        }
      }
    }

    public int getValue()
    {
      int ret = orbitVal;
      for(Star orbiter : orbiters)
      {
        ret += orbiter.getValue();
      }
      return ret;
    }

    public void printOrbiters()
    {
      printInfo(name + " has " + orbiters.size() + " orbiters and a value of " + orbitVal);
      printInfo(name + " is " + stepsTo("YOU") + " away from YOU and " + stepsTo("SAN") + " away from SAN");
      for(Star orbiter : orbiters)
      {
        orbiter.printOrbiters();
      }
    }

    public int distanceFrom(String target, String origin)
    {
      for(Star orbiter : orbiters)
      {
        int val = orbiter.distanceFrom(target, origin);
        if(val >= 0)
        {
          return val;
        }
      }
      int toTarget = stepsTo(target);
      int toOrigin = stepsTo(origin);
      if(toTarget >= 0 && toOrigin >= 0)
      {
        return toTarget + toOrigin;
      }
      return -1;
    }

    public int stepsTo(String target)
    {
      for(Star orbiter : orbiters)
      {
        if(orbiter.name.equals(target))
        {
          return 0;
        } else {
          int orbitersSteps = orbiter.stepsTo(target);
          if(orbitersSteps >= 0)
          {
            return orbitersSteps + 1;
          }
        }
      }
      return -1;
    }


  }
}

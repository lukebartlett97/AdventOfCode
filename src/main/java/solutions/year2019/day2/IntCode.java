package solutions.year2019.day2;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntCode extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day2/";

  public IntCode()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    String[] nums = data.get(0).split(",");
    List<Integer> ints = convertToIntegerList(Arrays.asList(nums));
    return bruteForce(ints);
  }

  private String bruteForce(List<Integer> ints)
  {
    String expected = "19690720";
    for(int i = 0; i < 100; i++)
    {
      for(int j = 0; j < 100; j++)
      {
        List<Integer> innerInts = new ArrayList<>(ints);
        innerInts.set(1,i);
        innerInts.set(2,j);
        String result = "";
        try
        {
          result = getResult(innerInts);
        }
        catch (IndexOutOfBoundsException ignored)
        {

        }
        if(result.equals(expected))
        {
          int ret = (i * 100) + j;
          return Integer.toString(ret);
        }
      }
    }
    return "Not found.";
  }

  private String getResult(List<Integer> ints) throws IndexOutOfBoundsException
  {
    int currentIndex = 0;
    while(ints.get(currentIndex) != 99)
    {
      int val;
      switch(ints.get(currentIndex))
      {
        case 1:
          val = ints.get(ints.get(currentIndex + 1)) + ints.get(ints.get(currentIndex + 2));
          break;
        case 2:
          val = ints.get(ints.get(currentIndex + 1)) * ints.get(ints.get(currentIndex + 2));
          break;
        default:
          val = 0;
      }
      printInfo(buildString(ints, currentIndex));
      ints.set(ints.get(currentIndex + 3), val);
      currentIndex += 4;
    }
    return ints.get(0).toString();
  }

  private String buildString(List<Integer> ints, int currentIndex)
  {
    StringBuilder line = new StringBuilder();
    line.append("Current Index: ");
    line.append(currentIndex);
    line.append(", OpCode: ");
    line.append(ints.get(currentIndex));
    line.append(", Values: ");
    line.append(ints.get(currentIndex+1));
    line.append("(");
    line.append(ints.get(ints.get(currentIndex+1)));
    line.append(")");
    line.append("-");
    line.append(ints.get(currentIndex+2));
    line.append("(");
    line.append(ints.get(ints.get(currentIndex+2)));
    line.append(")");
    line.append(", Location: ");
    line.append(ints.get(currentIndex+3));
    return line.toString();

  }
}

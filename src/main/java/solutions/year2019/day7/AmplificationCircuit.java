package solutions.year2019.day7;

import solutions.SolutionMain;
import solutions.year2019.day5.OpCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class AmplificationCircuit extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day7/";

  public AmplificationCircuit()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    String[] nums = data.get(0).split(",");
    List<Long> ints = convertToLongList(Arrays.asList(nums));
    long maxValue = 0;
    for(int a = 5; a < 10; a++)
    {
      for(int b = 5; b < 10; b++)
      {
        if(b != a)
        {
          for(int c = 5; c < 10; c++)
          {
            if(c != a && c != b)
            {
              for(int d = 5; d < 10; d++)
              {
                if(d != a && d != b &&d != c)
                {
                  for(int e = 5; e < 10; e++)
                  {
                    if(e != a && e != b && e != c && e != d)
                    {
                      List<Integer> settings = new ArrayList<>();
                      settings.add(a);
                      settings.add(b);
                      settings.add(c);
                      settings.add(d);
                      settings.add(e);
                      long value = runAmplifier(ints,settings);
                      maxValue = Math.max(maxValue, value);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return Long.toString(maxValue);
  }

  private long runAmplifier(List<Long> ints, List<Integer> settings)
  {
    List<IntCoder> intCoders = new ArrayList<>();
    for(int i = 0; i<settings.size(); i++)
    {
      List<Long> intCopy = (new ArrayList<>());
      intCopy.addAll(ints);
      IntCoder coder = new IntCoder(intCopy);
      coder.process();
      coder.process(settings.get(i));
      intCoders.add(coder);
    }
    long prevVal = 0;
    while(!intCoders.get(intCoders.size()-1).isFinished)
    {
      for(IntCoder coder : intCoders)
      {
        prevVal = coder.process(prevVal);
      }
    }
    printInfo(settings.toString() + "  Value: " + prevVal);
    return prevVal;
  }
}

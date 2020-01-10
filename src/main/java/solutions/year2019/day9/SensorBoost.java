package solutions.year2019.day9;

import org.apache.commons.lang3.StringUtils;
import solutions.SolutionMain;
import solutions.year2019.day7.IntCoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SensorBoost extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day9/";

  public SensorBoost()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    String[] nums = data.get(0).split(",");
    List<Long> longs = convertToLongList(Arrays.asList(nums));
    IntCoder coder = new IntCoder(longs);
    coder.process();
    long output = coder.process(1);
    return Long.toString(output);
  }
}

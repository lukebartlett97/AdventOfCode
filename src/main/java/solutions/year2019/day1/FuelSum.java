package solutions.year2019.day1;

import solutions.SolutionMain;

import java.util.List;

public class FuelSum extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day1/";

  public FuelSum()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    int fuel = data.stream().map(Integer::parseInt).map(this::getFuelForModule).reduce((x, y) -> x + y).orElse(0);
    return Integer.toString(fuel);
  }

  private Integer getFuelForModule(Integer mass)
  {
    int fuel = (mass / 3) - 2;
    int total = 0;
    while (fuel>0)
    {
      total += fuel;
      fuel = (fuel / 3) - 2;
    }
    return total;

  }
}

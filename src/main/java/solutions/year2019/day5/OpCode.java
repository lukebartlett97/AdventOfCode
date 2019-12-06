package solutions.year2019.day5;

public class OpCode
{
  int value;

  public OpCode(int value)
  {
    this.value = value;
  }

  public int getOperation()
  {
    return value % 100;
  }

  public int getParam(int num)
  {
    int valueCopy = value;
    for (int i = 1; i <= num + 1; i++)
    {
      valueCopy -= valueCopy % (Math.pow(10, i));
    }
    return (valueCopy % (int) (Math.pow(10, num + 2))) / (int) Math.pow(10, num + 1);
  }
}

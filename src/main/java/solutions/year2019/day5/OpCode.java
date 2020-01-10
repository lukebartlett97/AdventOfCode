package solutions.year2019.day5;

public class OpCode
{
  long value;

  public OpCode(long value)
  {
    this.value = value;
  }

  public long getOperation()
  {
    return value % 100;
  }

  public long getParam(long num)
  {
    long valueCopy = value;
    for (int i = 1; i <= num + 1; i++)
    {
      valueCopy -= valueCopy % (Math.pow(10, i));
    }
    return (valueCopy % (int) (Math.pow(10, num + 2))) / (int) Math.pow(10, num + 1);
  }

  public int getSize()
  {
    switch((int) getOperation())
    {
      case 1:
        return 4;
      case 2:
        return 4;
      case 3:
        return 2;
      case 4:
        return 2;
      case 5:
        return 3;
      case 6:
        return 3;
      case 7:
        return 4;
      case 8:
        return 4;
      case 9:
        return 2;
    }
    return 1;
  }
}

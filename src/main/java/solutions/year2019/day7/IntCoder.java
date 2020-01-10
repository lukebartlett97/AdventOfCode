package solutions.year2019.day7;

import solutions.year2019.day5.OpCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class IntCoder
{

  private boolean verbose = false;
  private long currentIndex = 0;
  private long nextIndex = 0;
  private Map<Long, Long> memory = new HashMap<>();
  public boolean isFinished = false;
  private long relativeBase = 0;

  public IntCoder(List<Long> longs)
  {
    for(int i = 0; i<longs.size(); i++)
    {
      memory.put((long) i, longs.get(i));
    }
  }

  public IntCoder(List<Integer> ints, boolean ignored)
  {
    for(int i = 0; i<ints.size(); i++)
    {
      memory.put((long) i, (long) ints.get(i));
    }
  }

  public long process()
  {
    return process(0, false);
  }

  public long process(long input)
  {
    return process(input, true);
  }

  private long process(long input, boolean setInput) throws IndexOutOfBoundsException
  {
    printInfo("Process called with " + input + " and " + setInput + " when currentIndex=" + currentIndex);
    if(isFinished)
    {
      printInfo("Oops shouldnt be here im finished");
      return 0;
    }
    if(setInput)
    {
      memory.put(memory.get(currentIndex + 1), input);
      currentIndex = nextIndex;
    }
    List<Long> outputs = new ArrayList<>();
    while (memory.get(currentIndex) != 99)
    {
      OpCode opCode = new OpCode(memory.get(currentIndex));
      nextIndex = currentIndex + opCode.getSize();
      switch ((int) opCode.getOperation())
      {
        case 1:
          long addVal = getValue(currentIndex, opCode, 1) + getValue(currentIndex, opCode, 2);
          memory.put(memory.get(currentIndex + 3), addVal);
          break;
        case 2:
          long multiplyVal = getValue(currentIndex, opCode, 1) * getValue(currentIndex, opCode, 2);
          memory.put(memory.get(currentIndex + 3), multiplyVal);
          break;
        case 3:
          return outputs.size() > 0 ? outputs.get(outputs.size() - 1) : -1;
        case 4:
          outputs.add(memory.get(memory.get(currentIndex + 1)));
          break;
        case 5:
          if (getValue(currentIndex, opCode, 1) != 0)
          {
            nextIndex = getValue(currentIndex, opCode, 2);
          }
          break;
        case 6:
          if (getValue(currentIndex, opCode, 1) == 0)
          {
            nextIndex = getValue(currentIndex, opCode, 2);
          }
          break;
        case 7:
          long lessThanVal = (getValue(currentIndex, opCode, 1) < getValue(currentIndex, opCode, 2)) ? 1 : 0;
          memory.put(memory.get(currentIndex + 3), lessThanVal);
          break;
        case 8:
          long equalsVal = (getValue(currentIndex, opCode, 1) == getValue(currentIndex, opCode, 2)) ? 1 : 0;
          memory.put(memory.get(currentIndex + 3), equalsVal);
          break;
        case 9:
          relativeBase += getValue(currentIndex, opCode, 1);
          break;
        default:
          printInfo("Oops shouldnt be here");
          printInfo(Long.toString(currentIndex));
          return 0;
      }
      currentIndex = nextIndex;
    }
    isFinished = true;
    printInfo(outputs.toString());
    return outputs.size() > 0 ? outputs.get(outputs.size() - 1) : -1;
  }

  private void printLine(List<Integer> ints, int currentIndex, int size, OpCode opCode)
  {
    printInfo("Handing instruction: " + opCode.getOperation() + " at " + currentIndex);
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++)
    {
      sb.append(ints.get(currentIndex + i));
      if (i > 0 && opCode.getParam(i) == 0)
      {
        sb.append("(");
        sb.append(ints.get(ints.get(currentIndex + 1)));
        sb.append(")");
      }
      sb.append(",");
    }
    printInfo(sb.toString());
  }

  private long getValue(long currentIndex, OpCode opCode, long param)
  {
    switch((int) opCode.getParam(param))
    {
      case 0:
        return memory.get(memory.get(currentIndex + param));
      case 1:
        return memory.get(currentIndex + param);
      case 2:
        return memory.get(relativeBase + memory.get(currentIndex + param));
    }
    return memory.get(memory.get(currentIndex + param));
  }

  protected void printInfo(String line)
  {
    if(verbose)
    {
      System.out.println(line);
    }
  }

  public void setVerbose(boolean verbose)
  {
    this.verbose = verbose;
  }
}

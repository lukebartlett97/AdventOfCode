package solutions.year2019.day5;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("Duplicates")
public class IntCodeCont extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day5/";

  public IntCodeCont()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    String[] nums = data.get(0).split(",");
    List<Integer> ints = convertToIntegerList(Arrays.asList(nums));
    return getResult(ints);
  }

  private String getResult(List<Integer> ints) throws IndexOutOfBoundsException
  {
    int currentIndex = 0;
    List<Integer> outputs = new ArrayList<>();
    while(ints.get(currentIndex) != 99)
    {
      OpCode opCode = new OpCode(ints.get(currentIndex));
      switch(opCode.getOperation())
      {
        case 1:
          printLine(ints, currentIndex, 4, opCode);
          int addVal = getValue(ints, currentIndex, opCode, 1) + getValue(ints, currentIndex, opCode, 2);
          ints.set(ints.get(currentIndex + 3), addVal);
          currentIndex += 4;
          break;
        case 2:
          printLine(ints, currentIndex, 4, opCode);
          int multiplyVal = getValue(ints, currentIndex, opCode, 1) * getValue(ints, currentIndex, opCode, 2);
          ints.set(ints.get(currentIndex + 3), multiplyVal);
          currentIndex += 4;
          break;
        case 3:
          printLine(ints, currentIndex, 2, opCode);
          ints.set(ints.get(currentIndex + 1), 5);
          currentIndex += 2;
          break;
        case 4:
          printLine(ints, currentIndex, 2, opCode);
          outputs.add(ints.get(ints.get(currentIndex + 1)));
          currentIndex += 2;
          break;
        case 5:
          printLine(ints, currentIndex, 3, opCode);
          currentIndex = (getValue(ints, currentIndex, opCode, 1) != 0) ? ints.get(currentIndex + 2) : currentIndex + 3;
          break;
        case 6:
          printLine(ints, currentIndex, 3, opCode);
          currentIndex = (getValue(ints, currentIndex, opCode, 1) == 0) ? ints.get(currentIndex + 2) : currentIndex + 3;
          break;
        case 7:
          printLine(ints, currentIndex, 4, opCode);
          int lessThanVal = (getValue(ints, currentIndex, opCode, 1) < getValue(ints, currentIndex, opCode, 2)) ? 1 : 0;
          ints.set(ints.get(currentIndex + 3), lessThanVal);
          currentIndex += 4;
          break;
        case 8:
          printLine(ints, currentIndex, 4, opCode);
          int equalsVal = (getValue(ints, currentIndex, opCode, 1) == getValue(ints, currentIndex, opCode, 2)) ? 1 : 0;
          ints.set(ints.get(currentIndex + 3), equalsVal);
          currentIndex += 4;
          break;
        default:
          printInfo("Oops shouldnt be here");
          printInfo(Integer.toString(currentIndex));
          printInfo(ints.toString());
          return "";
      }
      printInfo(ints.toString());
    }
    printInfo(outputs.toString());
    return outputs.get(outputs.size() - 1).toString();
  }

  private void printLine(List<Integer> ints, int currentIndex, int size, OpCode opCode)
  {
    printInfo("Handing instruction: " + opCode.getOperation() + " at " + currentIndex);
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < size; i++)
    {
      sb.append(ints.get(currentIndex + i));
      if(i>0 && opCode.getParam(i) == 0)
      {
        sb.append("(");
        sb.append(ints.get(ints.get(currentIndex+1)));
        sb.append(")");
      }
      sb.append(",");
    }
    printInfo(sb.toString());
  }

  private int getValue(List<Integer> ints, int currentIndex, OpCode opCode, int param)
  {
    if(opCode.getParam(param) == 0){
      return ints.get(ints.get(currentIndex + param));
    } else {
      return ints.get(currentIndex + param);
    }
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

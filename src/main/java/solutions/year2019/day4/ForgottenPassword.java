package solutions.year2019.day4;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class ForgottenPassword extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day4/";

  public ForgottenPassword()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data) throws IOException
  {
    return Long.toString(Stream.iterate(Integer.parseInt(data.get(0)), n -> n+1).limit(Integer.parseInt(data.get(1))-Integer.parseInt(data.get(0))).map(x -> x.toString().toCharArray()).filter(this::meetsCriteria).map(this::printValue).count());

  }

  private char[] printValue(char[] chars)
  {
    printInfo(String.valueOf(chars));
    return chars;
  }

  private boolean meetsCriteria(char[] digits)
  {
    String test = String.valueOf(digits);
    Character previousDigit = null;
    boolean hasDouble = false;
    boolean reallyHasDouble = false;
    boolean tooMuchDouble = false;
    for(char digit : digits)
    {
      if(previousDigit != null)
      {
        if(digit < previousDigit)
        {
          return false;
        }
        if(previousDigit == digit)
        {
          if(hasDouble)
          {
            tooMuchDouble = true;
          } else {
            hasDouble = true;
          }
        } else {
          if(hasDouble && !tooMuchDouble)
          {
            reallyHasDouble = true;
          }
          tooMuchDouble = false;
          hasDouble = false;
        }
      }
      previousDigit = digit;
    }
    return (hasDouble && !tooMuchDouble) || reallyHasDouble;
  }
}

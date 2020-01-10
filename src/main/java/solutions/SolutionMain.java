package solutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SolutionMain
{
  private String resourcePath;

  private boolean verbose = false;

  public void printSolution(boolean verbose) throws IOException, InterruptedException
  {
    this.verbose = verbose;
    getProblem().forEach(System.out::println);
    List<String> exampleData = getExample();
    if(exampleData != null)
    {
      System.out.println("Example Data:");
      System.out.println(exampleData);
      System.out.println("Solution:");
      System.out.println(solve(exampleData));
    }
    List<String> data = getData();
    System.out.println("Real Data:");
    System.out.println(data);
    System.out.println("Solution:");
    System.out.println(solve(data));
  }

  protected abstract String solve(List<String> data) throws IOException, InterruptedException;

  protected List<String> getData() throws IOException
  {
    URL resource = this.getClass().getResource(resourcePath + "data.txt");
    return readFile(resource);
  }

  protected List<String> getExample() throws IOException
  {
    URL resource = this.getClass().getResource(resourcePath + "example.txt");
    return resource == null ? null : readFile(resource);
  }

  protected List<String> getProblem() throws IOException
  {
    URL resource = this.getClass().getResource(resourcePath + "problem.txt");
    return readFile(resource);
  }

  private List<String> readFile (URL file) throws IOException
  {
    BufferedReader in = new BufferedReader(
            new InputStreamReader(file.openStream()));

    String inputLine;
    List<String> lines = new ArrayList<String>();
    while ((inputLine = in.readLine()) != null)
      lines.add(inputLine);
    in.close();
    return lines;
  }

  public void setResourcePath(String resourcePath)
  {
    this.resourcePath = resourcePath;
  }

  protected List<Integer> convertToIntegerList(List<String> strings)
  {
    return strings.stream().map(Integer::parseInt).collect(Collectors.toList());
  }

  protected List<Long> convertToLongList(List<String> strings)
  {
    return strings.stream().map(Long::parseLong).collect(Collectors.toList());
  }

  protected void printInfo(String line)
  {
    if(verbose)
    {
      System.out.println(line);
    }
  }
}

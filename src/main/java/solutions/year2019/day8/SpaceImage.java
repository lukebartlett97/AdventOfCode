package solutions.year2019.day8;

import org.apache.commons.lang3.StringUtils;
import solutions.SolutionMain;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SpaceImage extends SolutionMain
{
  String RESOURCE_PATH = "/year2019/Day8/";

  public SpaceImage()
  {
    setResourcePath(RESOURCE_PATH);
  }

  @Override
  protected String solve(List<String> data)
  {
    Image image = new Image(6,25,data.get(0));
    image.createFinalLayer(6, 25).print();
    return Integer.toString(image.getChecksum());
  }

  class Image
  {
    List<Layer> layers = new ArrayList<>();

    Image(int height, int width, String data)
    {
      int currentIndex = 0;
      while(currentIndex<data.length())
      {
        layers.add(new Layer(width, data.substring(currentIndex, currentIndex + (height*width))));
        currentIndex += (width * height);
      }
    }

    int getChecksum()
    {
      int min = Integer.MAX_VALUE;
      Layer minLayer = null;
      for(Layer layer : layers)
      {
        int layerVal = layer.countValue(0);
        if(layerVal < min)
        {
          min = layerVal;
          minLayer = layer;
        }
      }
      if(minLayer == null)
      {
        return 0;
      }
      return minLayer.countValue(1) * minLayer.countValue(2);
    }

    String getVal(int height, int width)
    {
      for(Layer layer : layers)
      {
        String val = layer.getVal(height, width);
        if(!val.equals("2"))
        {
          return val;
        }
      }
      return "2";
    }

    Layer createFinalLayer(int height, int width)
    {
      StringBuilder sb = new StringBuilder();
      for(int i = 0; i < height; i++)
      {
        for(int j = 0; j < width; j++)
        {
          sb.append(getVal(i, j));
        }
      }
      return new Layer(width, sb.toString());
    }
  }

  class Layer
  {
    List<String> lines = new ArrayList<>();

    public Layer(int width, String data)
    {
      int currentIndex = 0;
      while(currentIndex<data.length())
      {
        lines.add(data.substring(currentIndex, currentIndex + (width)));
        currentIndex += width;
      }
    }

    int countValue(int val)
    {
      int total = 0;
      for(String line : lines)
      {
        total += StringUtils.countMatches(line, Integer.toString(val));
      }
      printInfo("Found " + total + " " + val + "'s");
      return total;
    }

    String getVal(int height, int width)
    {
      return lines.get(height).substring(width, width+1);
    }

    public void print()
    {
      for(String line : lines)
      {
        printInfo(line.replace('0', ' '));
      }
    }
  }
}

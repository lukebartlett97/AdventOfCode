package solutions.year2018.day1;

import solutions.SolutionMain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChronalCalibration extends SolutionMain
{
    String RESOURCE_PATH = "/year2018/Day1/";

    public ChronalCalibration()
    {
        setResourcePath(RESOURCE_PATH);
    }

    protected String solve(List<String> data)
    {
        return Integer.toString(findRepeatedFrequency(data));
    }

    //Part 1
    private int calculateFrequency(List<String> data) {
        return data.stream().map(this::mapLineToInt).reduce(Integer::sum).orElse(0);
    }

    //Part 2
    private int findRepeatedFrequency(List<String> data) {
        List<Integer> values = data.stream().map(this::mapLineToInt).collect(Collectors.toList());
        boolean found = false;
        Set<Integer> frequenciesFound = new HashSet<>();
        int currentFrequency = 0;
        while(!found) {
            for(int value : values) {
                currentFrequency += value;
                if(!frequenciesFound.add(currentFrequency)) {
                    found = true;
                    break;
                }
            }
        }
        return currentFrequency;
    }

    private int mapLineToInt(String line) {
        int val = Integer.parseInt(line.substring(1));
        if(line.charAt(0) == '-') {
            val *= -1;
        }
        return val;
    }
}

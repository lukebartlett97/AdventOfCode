package solutions.year2020.day15;

import javafx.collections.transformation.SortedList;
import solutions.SolutionMain;

import java.util.*;

public class MemoryGame extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day15/";

    public MemoryGame()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<Long> starting = convertToLongList(Arrays.asList(data.get(0).split(",")));
        Map<Long, Long> memoryMap = setupMap(starting);
        long answer = generateNumbers(memoryMap, 30000000L, 0);
        return Long.toString(answer);
    }

    private Map<Long,  Long> setupMap(List<Long> starting) {
        Map<Long, Long> out = new HashMap<>();
        for(long i = 0; i < starting.size(); i++) {
            out.put(starting.get((int) i), i);
        }
        return out;
    }

    private long generateNumbers(Map<Long, Long> memoryMap, long limit, long previous) {
        long count = memoryMap.size();
        while(count < limit-1) {
            Long index = memoryMap.get(previous);
            memoryMap.put(previous, count);
            if(index == null)  {
                previous = 0;
            } else {
                previous = count - index;
            }
            count++;
        }
        return previous;
    }
}

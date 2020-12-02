package solutions.year2020.day1;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseReport  extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day1/";

    public ExpenseReport()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        return Integer.toString(findTriple(convertToIntegerList(data)));
    }

    private int findPair(List<Integer> data, int target) {
        List<Integer> ints = new ArrayList<>();
        for(Integer value : data) {
            if(ints.contains(value)) {
                printInfo("Number 1: " + value);
                printInfo("Number 2: " + (target - value));
                return value * (target - value);
            }
            ints.add(target - value);
        }
        return -1;
    }

    private int findTriple(List<Integer> data) {
        ArrayList<Integer> dataArray = (ArrayList<Integer>) data;
        int index = 1;
        for(Integer value : dataArray) {
            int product = findPair(data.subList(index, dataArray.size()), 2020 - value);
            if(product > -1) {
                printInfo("Number 3: " + value);
                return value * product;
            }
            index++;
        }
        return -1;
    }

    private int findTripleBetter(List<Integer> data) {
        for(int i = 0; i < 2020; i++) {
            for(int j = 0; j < 2020; j++) {
                for(int k = 0; k < 2020; k++) {
                    if (i+j+k==2020 && data.contains(i)&& data.contains(j) && data.contains(k)) {
                        return i * j * k;
                    }
                }
            }
        }
        return -1;
    }
}

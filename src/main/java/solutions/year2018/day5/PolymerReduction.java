package solutions.year2018.day5;

import solutions.SolutionMain;

import java.util.List;
import java.util.stream.Collectors;

public class PolymerReduction extends SolutionMain {
    String RESOURCE_PATH = "/year2018/Day5/";

    public PolymerReduction()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        return Integer.toString(findShortest(data.get(0)).length());
    }

    private String findShortest(String polymer) {
        String shortest = null;
        for(int ascii = 65; ascii < 91; ascii++) {
            char lower = (char) ascii;
            char upper = (char) (ascii + 32);
            List<Character> chars = polymer.chars().mapToObj(x -> (char)x).collect(Collectors.toList());
            chars.removeIf(x -> x == lower || x == upper);
            String reduced = reduce(chars);
            if(shortest == null || reduced.length() < shortest.length()) {
                shortest = reduced;
            }
        }
        return shortest;
    }

    private String reduce(List<Character> chars) {
        int index = 0;

        while(index < chars.size() - 1) {
            if(chars.get(index) - chars.get(index+1) == 32 || chars.get(index) - chars.get(index+1) == -32) {
                chars.remove(index+1);
                chars.remove(index);
                if(index > 0) {
                    index--;
                }
            } else {
                index++;
            }
        }
        StringBuilder out = new StringBuilder();
        for(char letter : chars) {
            out.append(letter);
        }
        return out.toString();
    }
}

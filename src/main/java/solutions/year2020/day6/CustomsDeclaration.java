package solutions.year2020.day6;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomsDeclaration extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day6/";

    public CustomsDeclaration()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        return Integer.toString(allMatch(data));
    }

    private int allMatch(List<String> data) {
        Set<Character> chars = new HashSet<>();
        int acc = 0;
        boolean first = true;
        for(String line : data) {
            if(line.equals("")) {
                acc += chars.size();
                chars.clear();
                first = true;
            } else {
                Set<Character> lineList = new HashSet<>();
                for(char letter : line.toCharArray()) {
                    lineList.add(letter);
                }
                if(first) {
                    chars = lineList;
                    first = false;
                } else {
                    chars.retainAll(lineList);
                }
            }
        }
        acc += chars.size();
        return acc;
    }

    private int anyMatch(List<String> data) {
        Set<Character> chars = new HashSet<>();
        int acc = 0;
        for(String line : data) {
            if(line.equals("")) {
                acc += chars.size();
                chars.clear();
            } else {
                for(char letter : line.toCharArray()) {
                    chars.add(letter);
                }
            }
        }
        acc += chars.size();
        return acc;
    }


}

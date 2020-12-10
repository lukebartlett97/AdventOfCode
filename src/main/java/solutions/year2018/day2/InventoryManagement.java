package solutions.year2018.day2;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagement extends SolutionMain
{
    String RESOURCE_PATH = "/year2018/Day2/";

    public InventoryManagement()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException
    {

        return buildFromList(findSimilar(data));
    }

    //Part 2
    private List<Character> findSimilar(List<String> data) {
        for(int i = 0; i < data.size(); i++) {
            char[] base = data.get(i).toCharArray();
            for(int j = i + 1; j < data.size(); j++) {
                List<Character> matching = new ArrayList<>();
                char[] chars = data.get(j).toCharArray();
                for(int k = 0; k < chars.length; k++) {
                    if(chars[k] == base[k]) {
                        matching.add(chars[k]);
                    }
                }
                if(matching.size() == chars.length - 1) {
                    return matching;
                }
            }
        }
        return null;
    }

    private String buildFromList(List<Character> chars) {
        if(chars == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(Character letter : chars) {
            sb.append(letter);
        }
        return sb.toString();
    }

    //Part 1
    private int calculateChecksum(List<String> data) {
        int twos = 0;
        int threes = 0;
        for(String line : data) {
            Map<Character, Integer> mappedLine = mapToCharAmountMap(line);
            printInfo(line);
            printInfo(mappedLine.toString());
            if(mappedLine.values().contains(2)) {
                twos++;
            }
            if(mappedLine.values().contains(3)) {
                threes++;
            }
        }
        return twos * threes;
    }

    private Map<Character, Integer> mapToCharAmountMap(String line) {
        Map<Character, Integer> out = new HashMap<>();
        for(char letter : line.toCharArray()) {
            out.put(letter, out.get(letter) == null ? 1 : out.get(letter) + 1);
        }
        return out;
    }
}

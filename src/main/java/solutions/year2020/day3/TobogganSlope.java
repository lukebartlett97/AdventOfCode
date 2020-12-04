package solutions.year2020.day3;

import solutions.SolutionMain;

import java.util.List;

public class TobogganSlope extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day3/";

    public TobogganSlope()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        return Long.toString(countTrees(data, 1, 1) *
                        countTrees(data, 3, 1) *
                        countTrees(data, 5, 1) *
                        countTrees(data, 7, 1) *
                        countTrees(data, 1, 2)
                );
    }

    private long countTrees(List<String> map, int right, int down) {
        int mapWidth = map.get(0).length();
        int mapHeight = map.size();
        long count = 0;
        int currentX = right;
        int currentY = down;
        while(currentY < mapHeight) {
            if(map.get(currentY).charAt(currentX) == '#') {
                count++;
            }
            currentX = (currentX + right) % mapWidth;
            currentY += down;
        }
        return count;
    }
}

package solutions.year2020.day5;

import solutions.SolutionMain;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class BinaryBoarding extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day5/";

    public BinaryBoarding()
    {
        setResourcePath(RESOURCE_PATH);
    }


    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        List<Long> sortedSeats = data.stream()
                .map(BoardingPass::new)
                .map(BoardingPass::calculateSeatID)
                .sorted().collect(Collectors.toList());
        long maxVal = sortedSeats.get(sortedSeats.size()-1); //THIS IS THE ANSWER TO PART ONE
        printInfo("Part 1 answer: " + maxVal);
        long minVal = sortedSeats.get(0) - 1;
        long expected = ((maxVal * (maxVal+1))/2) - ((minVal * (minVal+1))/2);
        long actual = sortedSeats.stream().reduce(0L, (x, y) -> x + y);
        return Long.toString(expected - actual);
    }

    private class BoardingPass {
        int rowValue;
        int columnValue;
        BoardingPass(String line) {
            String rowString = line.substring(0, 7);
            String columnString = line.substring(7, 10);
            rowValue = calculateValue(rowString, 'B');
            columnValue = calculateValue(columnString, 'R');
        }

        int calculateValue(String data, char positive) {
            int out = 0;
            for(char letter : data.toCharArray()) {
                out = out << 1;
                if(letter == positive) {
                    out++;
                }
            }
            return out;
        }

        long calculateSeatID() {
            return (rowValue * 8) + columnValue;
        }
    }
}

package solutions.year2020.day8;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameInstructions extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day8/";

    public GameInstructions()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        return Integer.toString(findBroken(data));
    }

    private Result executeInstructions(List<String> data) {
        int acc = 0;
        int index = 0;
        List<Integer> indexes = new ArrayList<>();
        while(!indexes.contains(index) && index < data.size()) {
            indexes.add(index);
            String[] instruction = data.get(index).split(" ");
            if(instruction[0].equals("acc")) {
                acc += Integer.parseInt(instruction[1]);
            }
            if(instruction[0].equals("jmp")) {
                index += Integer.parseInt(instruction[1]) - 1;
            }
            index++;
        }
        return new Result(index==data.size(), acc);
    }

    private class Result {
        boolean finished;
        int acc;

        Result(boolean finished, int acc) {

            this.finished = finished;
            this.acc = acc;
        }
    }

    private int findBroken(List<String> data) {
        for(int index = 0; index < data.size(); index++) {
            List<String> copied = new ArrayList<>(data);
            String[] instruction = data.get(index).split(" ");
            if(instruction[0].equals("jmp")) {
                copied.set(index, "nop " + instruction[1]);
                printInfo(copied.toString());
                Result result = executeInstructions(copied);
                if(result.finished) {
                    return result.acc;
                }
            } else if(instruction[0].equals("nop")) {
                copied.set(index, "jmp " + instruction[1]);
                printInfo(copied.toString());
                Result result = executeInstructions(copied);
                if(result.finished) {
                    return result.acc;
                }
            }
        }
        return 0;
    }
}

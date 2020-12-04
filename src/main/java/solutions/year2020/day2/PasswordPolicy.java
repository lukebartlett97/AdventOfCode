package solutions.year2020.day2;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import solutions.SolutionMain;

import java.io.IOException;
import java.util.List;

public class PasswordPolicy extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day2/";

    public PasswordPolicy()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
//        long amount = longWinded(data);
        long amount = data.stream().map(Password::new).filter(Password::isValidPart2).count();
        return Long.toString(amount);
    }

    private class Password {
        String code;
        char searchChar;
        int lowerAmount;
        int higherAmount;
        Password(String line) {
            String[] all = line.split(":");
            String[] firstHalf = all[0].split(" ");
            String[] range = firstHalf[0].split("-");
            code = all[1];
            searchChar = firstHalf[1].charAt(0);
            lowerAmount = Integer.parseInt(range[0]);
            higherAmount = Integer.parseInt(range[1]);
        }

        boolean isValidPart1() {
            int count = StringUtils.countMatches(code, searchChar);
            return count >= lowerAmount && count <= higherAmount;
        }

        boolean isValidPart2() {
            return code.length() > higherAmount && ((code.toCharArray()[lowerAmount] == searchChar) != (code.toCharArray()[higherAmount] == searchChar));
        }
    }

    private Long longWinded(List<String> data) {
        char[] fullData = data.stream().reduce("", String::concat).toCharArray();
        printInfo(ArrayUtils.toString(fullData));
        String collected = "";
        int min = -1;
        int max = -1;
        char target = '-';
        long count = 0;
        for(char current : fullData) {
            if(current == '1' || current == '2' || current == '3' || current == '4' || current == '5' || current == '6' || current == '7' || current == '8' || current == '9') {
                if(max != -1 && min != -1) {
                    if(StringUtils.countMatches(collected, target) >= min && StringUtils.countMatches(collected, target) <= max) {
                        count++;
                    }
                    max = -1;
                    collected = "" + current;

                } else {
                    collected += current;
                }

            } else if (current == ':') {
                target = collected.charAt(0);
                collected = "";
            } else if (current == '-') {
                min = Integer.parseInt(collected);
                collected = "";
            } else if (current == ' ') {
                if(max == -1) {
                    max = Integer.parseInt(collected);
                    collected = "";
                }
            } else {
                collected += current;
            }
        }
        if(StringUtils.countMatches(collected, target) >= min && StringUtils.countMatches(collected, target) <= max) {
            count++;
        }
        return count;
    }
}

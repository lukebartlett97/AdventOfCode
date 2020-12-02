package solutions.year2020.day2;

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
        return Long.toString(data.stream().map(Password::new).filter(Password::isValidPart2).count());
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
            return (code.toCharArray()[lowerAmount] == searchChar) != (code.toCharArray()[higherAmount] == searchChar);
        }
    }
}

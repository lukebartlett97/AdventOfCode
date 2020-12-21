package solutions.year2020.day18;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathsHomework extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day18/";

    public MathsHomework() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        return Long.toString(calculateSum(data));
    }

    private Long calculateSum (List<String> data) {
        List<Long> answers = new ArrayList<>();
        for(String line : data) {
            answers.add(Long.parseLong(simplifyLine(line)));
        }
        printInfo(answers.size() + " Answers: " + answers);
        return answers.stream().reduce(Long::sum).orElse(0L);
    }

    public boolean testInput(String line, String expected) {
        System.out.println("Input: " + line);
        System.out.println("Expected: " + expected);
        String actual = simplifyLine(line);
        System.out.println("Actual: " + actual);
        return expected.equals(actual);
    }

    public String simplifyLine(String line) {
        String noSpaces = line.replaceAll(" ", "");
        printInfo("Line: " + noSpaces);
        String noBrackets = simplifyBrackets(noSpaces);
        printInfo("Line after brackets simplified: " + noBrackets);
        String newLine = simplifySection(noBrackets);
        printInfo(newLine);
        return newLine;
    }

    private String simplifyBrackets(String line) {
        String newLine = line;
        Pattern pattern = Pattern.compile("\\([^()]*\\)");
        Matcher matcher = pattern.matcher(line);
        while(matcher.find()) {
            String found = matcher.group();
            printInfo("New Section: " + found);
            String simplified = simplifySection(found.substring(1,found.length()-1));
            newLine = newLine.replace(found, simplified);
            printInfo("Line after Simplify: " + newLine);
        }
        return newLine.equals(line) ? newLine : simplifyBrackets(newLine);
    }

    private String simplifySection(String section) {
        String simplifiedAddition = simplifyAddition(section);
        return simplifyMultiplication(simplifiedAddition);
    }

    private String simplifyAddition(String section) {
        String newLine = section;
        Pattern pattern = Pattern.compile("\\d+\\+\\d+");
        Matcher matcher = pattern.matcher(section);
        if(matcher.find()) {
            String found = matcher.group();
            int index = matcher.start();
            printInfo("New Addition: " + found);
            String simplified = addNumbers(found);
            newLine = betterReplace(newLine, found, simplified, index);
            printInfo(newLine);
        }
        printInfo("Checking newline = " + newLine + ", section = " + section);
        return newLine.equals(section) ? newLine : simplifyAddition(newLine);
    }

    private String addNumbers(String equation) {
        String[] split = equation.split("\\+");
        return Long.toString(Long.parseLong(split[0]) + Long.parseLong(split[1]));
    }

    private String simplifyMultiplication(String section) {
        String newLine = section;
        Pattern pattern = Pattern.compile("\\d+\\*\\d+");
        Matcher matcher = pattern.matcher(section);
        if(matcher.find()) {
            String found = matcher.group();
            int index = matcher.start();
            printInfo("New Multiplication: " + found);
            String simplified = multiplyNumbers(found);
            newLine = betterReplace(newLine, found, simplified, index);
            printInfo(newLine);
        }
        printInfo("Checking newline = " + newLine + ", section = " + section);
        return newLine.equals(section) ? newLine : simplifyMultiplication(newLine);
    }

    private String betterReplace(String old, String replaced, String replacer, int index) {
        printInfo("Replacing: " + replaced + " with " + replacer + " inside: " + old + " at: " + index);
        StringBuilder sb = new StringBuilder();
        sb.append(old, 0, index);
        sb.append(replacer);
        sb.append(old.substring(index + replaced.length()));
        return sb.toString();
    }

    private String multiplyNumbers(String equation) {
        String[] split = equation.split("\\*");
        return Long.toString(Long.parseLong(split[0]) * Long.parseLong(split[1]));
    }

    private String parseLinePart1(String line) {
        char[] formatted = line.replaceAll(" ", "").toCharArray();
        List<Long> memory = new ArrayList<>();
        List<Boolean> isAdd = new ArrayList<>();
        isAdd.add(true);
        memory.add(0L);
        for(char letter : formatted) {
            if(Character.isDigit(letter)) {
                int number = Character.getNumericValue(letter);
                if(isAdd.get(isAdd.size()-1)) {
                    memory.set(memory.size()-1, memory.get(memory.size()-1) + number);
                } else {
                    memory.set(memory.size()-1, memory.get(memory.size()-1) * number);
                }
            } else {
                switch(letter) {
                    case('+') : {
                        isAdd.set(isAdd.size()-1, true);
                        break;
                    }
                    case('*') : {
                        isAdd.set(isAdd.size()-1, false);
                        break;
                    }
                    case('(') : {
                        isAdd.add(true);
                        memory.add(0L);
                        break;
                    }
                    case(')') : {
                        isAdd.remove(isAdd.size() - 1);
                        Long value = memory.remove(memory.size() - 1);
                        if(isAdd.get(isAdd.size()-1)) {
                            memory.set(memory.size()-1, memory.get(memory.size()-1) + value);
                        } else {
                            memory.set(memory.size()-1, memory.get(memory.size()-1) * value);
                        }
                        break;
                    }
                }
            }
            printInfo("Memory: " + memory.toString());
            printInfo("isAdd: " + isAdd.toString());
        }
        return Long.toString(memory.get(0));
    }
}

package solutions.year2020.day14;

import solutions.SolutionMain;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryBitmask extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day14/";

    public MemoryBitmask() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<Instruction> instructions = data.stream().map(Instruction::new).collect(Collectors.toList());
        return Long.toString(executeInstructions(instructions).stream().reduce(0L, (x, y) -> x + y));
    }

    private Collection<Long> executeInstructions(List<Instruction> instructions) {
        String mask = "";
        Map<Long, Long> memory = new HashMap<>();
        for(Instruction instruction : instructions) {
            if(instruction.location == -1) {
                mask = instruction.value;
            } else {
                List<Long> locations = applyMask(mask, instruction.location);
                locations.forEach(x -> memory.put(x, Long.parseLong(instruction.value)));
            }
        }
        //printInfo("Memory:");
        //printInfo(memory.toString());
        return memory.values();
    }

    private List<Long> applyMask(String mask, long location) {
        List<Long> out = new ArrayList<>();
        out.add(0L);
        StringBuilder before = new StringBuilder();
        StringBuilder after = new StringBuilder();
        for(int i = 0; i < mask.length(); i++) {
            long bitWorth = (long) Math.pow(2, mask.length() - i - 1);
            if(location >= bitWorth) {
                before.append('1');
                location -= bitWorth;
                if(mask.charAt(i) != 'X') {
                    out = out.stream().map(x -> x + bitWorth).collect(Collectors.toList());
                    after.append('1');
                } else {
                    out = duplicateAndAdd(out, bitWorth);
                    after.append('X');
                }
            } else {
                before.append('0');
                if(mask.charAt(i) == '1') {
                    out = out.stream().map(x -> x + bitWorth).collect(Collectors.toList());
                    after.append('1');
                } else if(mask.charAt(i) == 'X') {
                    out = duplicateAndAdd(out, bitWorth);
                    after.append('x');
                } else {
                    after.append('0');
                }
            }
        }
        printInfo(mask);
        printInfo(before.toString());
        printInfo(after.toString());
        return out;
    }

    private List<Long> duplicateAndAdd(List<Long> baseList, long add) {
        List<Long> out = new ArrayList<>();
        for(Long base : baseList) {
            out.add(base);
            out.add(base + add);
        }
        return out;
    }

    private class Instruction {
        int location = -1;
        String value;
        Instruction(String line) {
            String[] split = line.split("=");
            String[] start = split[0].split("[\\[\\]]");
            if(start.length == 3) {
                location = Integer.parseInt(start[1]);
            }

            value = split[1].substring(1);
        }
    }
}

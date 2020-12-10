package solutions.year2018.day4;

import solutions.SolutionMain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class SleepingGuards extends SolutionMain {
    String RESOURCE_PATH = "/year2018/Day4/";

    public SleepingGuards()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<Record> records = data.stream().map(Record::new).sorted(Comparator.comparing(x -> x.date)).collect(Collectors.toList());
        int answer = methodTwo(records);
        return Integer.toString(answer);
    }

    private int methodOne(List<Record> records) {
        Map<Integer, List<Integer>> sleepingMins = getSleepingMins(records);
        int sleepiest = 0;
        int amountOfSleep = 0;
        for(int guard : sleepingMins.keySet()) {
            int sleepyness = sleepingMins.get(guard).size();
            if(sleepyness > amountOfSleep) {
                sleepiest = guard;
                amountOfSleep = sleepyness;
            }
        }
        Map<Integer, Integer> timesSlept = new HashMap<>();
        for(int min : sleepingMins.get(sleepiest)) {
            if(timesSlept.containsKey(min)) {
                timesSlept.put(min, timesSlept.get(min) + 1);
            } else {
                timesSlept.put(min, 1);
            }
        }
        int highestMin = 0;
        int highestAmount = 0;
        for(int min : timesSlept.keySet()) {
            int times = timesSlept.get(min);
            if(times > highestAmount) {
                highestMin = min;
                highestAmount = times;
            }
        }
        return highestMin * sleepiest;
    }

    private int methodTwo(List<Record> records) {
        Map<Integer, List<Integer>> sleepingMins = getSleepingMins(records);
        int sleepiest = 0;
        int minuteSlept = 0;
        int amountOfSleep = 0;
        for(int guard : sleepingMins.keySet()) {
            Map<Integer, Integer> timesSlept = new HashMap<>();
            for(int min : sleepingMins.get(guard)) {
                if(timesSlept.containsKey(min)) {
                    timesSlept.put(min, timesSlept.get(min) + 1);
                } else {
                    timesSlept.put(min, 1);
                }
            }
            int highestMin = 0;
            int highestAmount = 0;
            for(int min : timesSlept.keySet()) {
                int times = timesSlept.get(min);
                if(times > highestAmount) {
                    highestMin = min;
                    highestAmount = times;
                }
            }
            if(amountOfSleep < highestAmount) {
                minuteSlept = highestMin;
                amountOfSleep = highestAmount;
                sleepiest = guard;
            }

        }
        return minuteSlept * sleepiest;
    }

    private Map<Integer, List<Integer>> getSleepingMins(List<Record> records) {
        Map<Integer, List<Integer>> sleepingMins = new HashMap<>();
        int guardNumber = 0;
        int sleepingMin = 0;
        for(Record record : records) {
            if(record.action.equals("Guard")) {
                guardNumber = record.guardNumber;
            } else if(record.action.equals("falls")) {
                sleepingMin = Integer.parseInt(record.date.substring(10, 12));
            } else if(record.action.equals("wakes")) {
                int wakingMin = Integer.parseInt(record.date.substring(10, 12));
                for(int min = sleepingMin; min < wakingMin; min++) {
                    if(sleepingMins.containsKey(guardNumber)) {
                        sleepingMins.get(guardNumber).add(min);
                    } else {
                        sleepingMins.put(guardNumber, new ArrayList<>(min));
                    }
                }
            }
        }
        return sleepingMins;
    }

    private class Record {
        String date;
        String action;
        int guardNumber;
        Record(String line) {
            String dateString = line.substring(1, 17);
            date = dateString.replaceAll("\\D", "");
            String[] words = line.substring(19).split(" ");
            action = words[0];
            if(action.equals("Guard")) {
                guardNumber = Integer.parseInt(words[1].substring(1));
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Date:");
            sb.append(date);
            sb.append(" - Action:");
            sb.append(action);
            sb.append(" - Guard:");
            sb.append(guardNumber);
            return sb.toString();
        }
    }
}

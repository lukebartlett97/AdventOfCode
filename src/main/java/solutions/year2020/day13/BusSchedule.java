package solutions.year2020.day13;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BusSchedule extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day13/";

    public BusSchedule()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        int time = Integer.parseInt(data.get(0));
        List<Bus> buses = getBuses(data.get(1));
        long answer = crt(buses);
        return Long.toString(answer);
    }

    private int getWaitingTimeTimesID(int currentTime, List<Bus> buses) {
        buses.forEach(x -> x.setWaitTime(currentTime));
        return buses.stream().min(Comparator.comparingInt(x -> x.waitTime)).map(x -> x.waitTime * x.ID).orElse(0);
    }

    private long getEarliestAllBusesMeet(List<Bus> buses) {
        Bus bigBus = buses.stream().max(Comparator.comparingInt(x -> x.ID)).orElse(null);
        long start = buses.size() > 5 ? 349098744021572L : 1000000L;
        long time = start - (start % bigBus.ID) - bigBus.index;
        boolean found = false;
        int count = 0;
        while(!found) {
            count++;
            time += bigBus.ID;
            found = true;
            if(count % 10000000 == 0) {
                printInfo("Trying: " + time);
            }
            for(Bus bus : buses) {
                if((time + bus.index) % bus.ID != 0) {
                    found = false;
                    break;
                }
            }
        }
        return time;
    }

    private long crt(List<Bus> buses) {
        printInfo("size: " + buses.size());
        buses.forEach(bus -> bus.setMultipliedOthers(buses));
        buses.forEach(bus -> printInfo(bus.toString()));
        buses.forEach(Bus::increaseMultiplied);
        printInfo("After expansion:");
        buses.forEach(bus -> printInfo(bus.toString()));
        long acc = 0;
        for(Bus bus : buses) {
            acc += bus.multipliedOthers;
        }
        printInfo("Biggest answer: " + acc);
        long idMulti = buses.stream().map(x -> (long) x.ID).reduce(1L, (x,y) -> x*y);
        return acc % idMulti;
    }

    private List<Bus> getBuses(String line) {
        String[] split = line.split(",");
        List<Bus> buses = new ArrayList<>();
        for(int index = 0; index < split.length; index++) {
            String value = split[index];
            if(!value.equals("x")) {
                buses.add(new Bus(Integer.parseInt(value), index));
            }
        }
        return buses;
    }

    private class Bus {
        int ID;
        int waitTime = -1;
        int index;
        long multipliedOthers = -1;

        Bus(int ID, int index) {
            this.ID = ID;
            this.index = index;
        }

        void setWaitTime(int currentTime) {
            waitTime = ID - (currentTime % ID);
            if(waitTime == ID) {
                waitTime = 0;
            }
        }

        void setMultipliedOthers(List<Bus> buses) {
            multipliedOthers = 1;
            for(Bus iterated : buses) {
                if(iterated != this) {
                    multipliedOthers *= iterated.ID;
                }
            }
        }

        void increaseMultiplied() {
            long tempMulti = multipliedOthers;
            int check = (ID - (index % ID));
            if(check == ID) {
                check = 0;
            }
            printInfo("ID = " + ID + ", check=" + check);
            while(tempMulti % ID != check) {
                tempMulti += multipliedOthers;
            }
            multipliedOthers = tempMulti;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ID:" + ID);
            sb.append(". waitTime:" + waitTime);
            sb.append(". index:" + index);
            sb.append(". multipliedOthers:" + multipliedOthers);
            return sb.toString();
        }
    }

}

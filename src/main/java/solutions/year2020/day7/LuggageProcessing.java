package solutions.year2020.day7;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LuggageProcessing extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day7/";

    public LuggageProcessing()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        List<Bag> bags = processBags(data);

        //Part one
        int part1Answer = findBagsContaining("shiny gold", bags, new ArrayList<>()).size();
        printInfo("Part one recursively: " + part1Answer);
        int part1AnswerNonRec = findBagsContainingNonRec("shiny gold", bags).size();
        printInfo("Part one non-recursively: " + part1AnswerNonRec);

        //Part two
        Bag shinyBag = findBagWithName("shiny gold", bags);
        long amount = shinyBag == null ? 0 : shinyBag.howManyBagsAmI(bags) - 1;
        return Long.toString(amount);
    }

    private List<Bag> processBags(List<String> data) {
        List<Bag> bags = new ArrayList<>();
        for(String line : data) {
            String[] words = line.split(" ");
            String parentName = words[0] + " " + words[1];
            Bag parent =  new Bag(parentName);
            int index = 4;
            while(index < words.length) {
                if(!words[index].equals("no")) {
                    int amount = Integer.parseInt(words[index]);
                    String name = words[index+1] + " " + words[index+2];
                    parent.addContents(name, amount);
                }
                index += 4;
            }
            bags.add(parent);
        }
        return bags;
    }

    private Bag findBagWithName(String bagName, List<Bag> bags) {
        for(Bag bag : bags) {
            if(bag.name.equals(bagName)) {
                return bag;
            }
        }
        return null;
    }

    private List<Bag> findBagsContainingNonRec(String bagName, List<Bag> bags) {
        List<Bag> allBagsFound = new ArrayList<>();
        List<String> bagsToSearchFor = new ArrayList<>();
        bagsToSearchFor.add(bagName);
        while(!bagsToSearchFor.isEmpty()) {
            String searchBag = bagsToSearchFor.remove(0);
            List<Bag> bagsFound = findAllBagsContaining(searchBag, bags);
            for(Bag bag : bagsFound) {
                if(!allBagsFound.contains(bag)) {
                    allBagsFound.add(bag);
                    bagsToSearchFor.add(bag.name);
                }
            }
        }
        return allBagsFound;
    }

    private List<Bag> findBagsContaining(String bagName, List<Bag> bags, List<Bag> bagsFound) {
        List<Bag> bagsFoundInt = findAllBagsContaining(bagName, bags);
        for(Bag bag : bagsFoundInt) {
            findBagsContaining(bag.name, bags, bagsFound);
            if(!bagsFound.contains(bag)) {
                bagsFound.add(bag);
            }
        }
        return bagsFound;
    }

    private List<Bag> findAllBagsContaining(String bagName, List<Bag> bags) {
        List<Bag> out = new ArrayList<>();
        for(Bag bag : bags) {
            if(bag.contents.containsKey(bagName)) {
                out.add(bag);
            }
        }
        return out;
    }

    private class Bag {
        final String name;
        Map<String, Integer> contents = new HashMap<>();
        Bag(String name) {
            this.name = name;
        }

        void addContents(String name, int amount) {
            contents.put(name, amount);
        }

        long howManyBagsAmI(List<Bag> bags) {
            long acc = 1;
            for(String key : contents.keySet()) {
                Bag bag = findBagWithName(key, bags);
                acc += bag == null ? 0 : (bag.howManyBagsAmI(bags) * contents.get(key));
            }
            printInfo(this.toString());
            printInfo("I contain: " + acc);
            return acc;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(" contains: ");
            for(String key : contents.keySet()) {
                int amount = contents.get(key);
                sb.append(amount);
                sb.append(" ");
                sb.append(key);
                sb.append(", ");
            }
            return sb.toString();
        }
    }
}

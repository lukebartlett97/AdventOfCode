package solutions.year2020.day19;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageRules extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day19/";

    public MessageRules() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<String> ruleLines = new ArrayList<>();
        List<String> matchingLines = new ArrayList<>();
        boolean isRule = true;
        for(String line : data) {
            if(line.equals("")) {
                isRule = false;
            } else if(isRule) {
                ruleLines.add(line);
            } else {
                matchingLines.add(line);
            }
        }
        Map<Integer, Rule> rules = parseRules(ruleLines);
        printInfo("0");
        printInfo(rules.get(0).buildPattern());
        int acc = 0;
        for(String match : matchingLines) {
            if(rules.get(0).matches(match)) {
                acc++;
                printInfo("Found match: " + match);
            }
        }
        return Integer.toString(acc);
    }

    private Map<Integer, Rule> parseRules(List<String> data) {
        Map<Integer, Rule> rules = new HashMap<>();
        for(String line : data) {
            String[] split = line.split(":");
            rules.put(Integer.parseInt(split[0]), new Rule(split[1]));
        }
        rules.values().forEach(x -> x.produceChildren(rules));
//        for(int key : rules.keySet()) {
//            printInfo("Rule " + key + " has " + rules.get(key).part1.size() + " part 1 children and " + rules.get(key).part2.size() + " part 2 children." );
//        }
        return rules;
    }

    class Rule {
        String line;
        List<Rule> part1 = new ArrayList<>();
        List<Rule> part2 = new ArrayList<>();
        String base = "";
        String pattern = "";

        Rule(String line) {
            this.line = line;
        }

        private String buildPattern() {
            if(!pattern.equals("")) {
                return pattern;
            }
            if(part1.size() == 0) {
                this.pattern = base;
                return base;
            }
            if(part2.size() == 0) {
                return buildPatternPart(true, 0);
            }
            StringBuilder pattern = new StringBuilder();
            pattern.append("(");
            pattern.append(buildPatternPart(true, 0));
            pattern.append("|");
            pattern.append(buildPatternPart(false, 0));
            pattern.append(")");
            this.pattern = pattern.toString();
            return pattern.toString();
        }

        private String buildPatternPart(boolean isPart1, int depth) {
            List<Rule> rules = isPart1 ? part1 : part2;
            StringBuilder pattern = new StringBuilder();
            pattern.append("(");
            if(rules.contains(this)) {
                List<Rule> preRules = new ArrayList<>();
                List<Rule> postRules = new ArrayList<>();
                boolean isSelf = false;
                for(Rule rule : rules) {
                    if(rule == this) {
                        isSelf = true;
                    } else {
                        if(isSelf) {
                            postRules.add(rule);
                        } else {
                            preRules.add(rule);
                        }
                    }
                }
                for(int i = 1; i < 20; i++) {
                    for(Rule rule : preRules) {
                        for(int j = 0; j < i; j++) {
                            pattern.append(rule.buildPattern());
                        }
                    }
                    for(Rule rule : postRules) {
                        for(int j = 0; j < i; j++) {
                            pattern.append(rule.buildPattern());
                        }
                    }
                    if(i < 19) {
                        pattern.append("|");
                    }
                }
            } else {
                for(Rule rule : rules) {
                    pattern.append(rule.buildPattern());
                }
            }
            pattern.append(")");
            this.pattern = pattern.toString();
            return pattern.toString();
        }

        boolean matches(String match) {
            String completePattern = "^" + pattern + "$";
            return Pattern.compile(completePattern).matcher(match).matches();
        }

        void produceChildren(Map<Integer, Rule> rules) {
            if(line.contains("\"")) {
                base = line.split("\"")[1];
            } else if(line.contains("|")) {
                String[] split = line.split("\\|");
                String[] part1Values = split[0].split(" ");
                for(String value : part1Values) {
                    if(!value.equals("")) {
                        part1.add(rules.get(Integer.parseInt(value)));
                    }
                }
                String[] part2Values = split[1].split(" ");
                for(String value : part2Values) {
                    if(!value.equals("")) {
                        part2.add(rules.get(Integer.parseInt(value)));
                    }
                }
            } else {
                String[] part1Values = line.split(" ");
                for(String value : part1Values) {
                    if(!value.equals("")) {
                        part1.add(rules.get(Integer.parseInt(value)));
                    }
                }
            }
        }
    }
}

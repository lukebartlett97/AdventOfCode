package solutions.year2020.day4;

import org.apache.commons.lang3.StringUtils;
import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class PassportProcessing extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day4/";

    public PassportProcessing()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        Processor processor = new Processor();
        processor.addField("byr", true, new IntRangeChecker(1920, 2002));
        processor.addField("iyr", true, new IntRangeChecker(2010, 2020));
        processor.addField("eyr", true, new IntRangeChecker(2020, 2030));
        processor.addField("hgt", true, new HeightChecker());
        processor.addField("hcl", true, new RegexChecker("^#([0-9]|[a-f]){6}$"));
        processor.addField("ecl", true, new RegexChecker("^(amb|blu|brn|gry|grn|hzl|oth)$"));
        processor.addField("pid", true, new RegexChecker("^[0-9]{9}$"));
        processor.addField("cid", false, null);

        long amount = mapToHashMapList(data).stream().filter(processor::isValid).count();
        return Long.toString(amount);
    }

    private List<HashMap<String, String>> mapToHashMapList(List<String> data) {
        List<HashMap<String, String>> out = new ArrayList<>();
        HashMap<String, String> current = new HashMap<>();
        for(String line: data) {
            if(line != null && !line.equals("")) {
                String[] fields = line.split(" ");
                for(String field: fields) {
                    String[] split = field.split(":");
                    current.put(split[0], split[1]);
                }
            } else {
                out.add(current);
                current = new HashMap<>();
            }
        }
        return out;
    }

    private class Processor {
        List<PassportField> fields = new ArrayList<>();
        Processor() {

        }

        void addField(String code, boolean required, Checker checker) {
            fields.add(new PassportField(code, required, checker));
        }

        boolean isValid(HashMap<String, String> passport) {
            printInfo("Checking if valid: " + passport.toString());
            for(PassportField field : fields) {
                String value = passport.get(field.code);
                if(value == null && field.required) {
                    printInfo("Failing because field missing: " + field.code);
                    return false;
                }
                if(field.checker != null && !field.checker.check(value)) {
                    printInfo("Failing because checker failed: " + field.code);
                    return false;
                }
            }
            return true;
        }

    }

    private class PassportField {
        String code;
        boolean required;
        Checker checker;
        PassportField(String code, boolean required, Checker checker) {
            this.code = code;
            this.required = required;
            this.checker = checker;
        }
    }

    // --- Below are all the checkers ---

    private abstract class Checker {
        abstract boolean check(String value);
    }

    private class IntRangeChecker extends Checker{
        int min;
        int max;

        IntRangeChecker(int min, int max) {
            this.min = min;
            this.max = max;
        }
        @Override
        boolean check(String value) {
            printInfo("Int Range Checker. Min: " + min + " - Max: " + max + " - Actual: " + value);
            if(value.charAt(0) == '0') {
                return false;
            }
            int intValue = 0;
            try {
                intValue = Integer.parseInt(value);
            } catch(Exception e) {
                return false;
            }
            return intValue >= min && intValue <= max;
        }
    }

    private class HeightChecker extends Checker {

        @Override
        boolean check(String value) {
            printInfo("Height Checker. Actual: " + value);
            if (StringUtils.endsWith(value, "cm")) {
                return (new IntRangeChecker(150, 193).check(value.replace("cm", "")));
            } else if (StringUtils.endsWith(value, "in")) {
                return (new IntRangeChecker(59, 76).check(value.replace("in", "")));
            }
            return false;
        }
    }

    private class RegexChecker extends Checker {

        private final Pattern pattern;

        RegexChecker(String regex) {
            this.pattern = Pattern.compile(regex);
        }
        @Override
        boolean check(String value) {
            printInfo("Regex Checker. Pattern: " + pattern.pattern() + " - Actual: " + value);
            return pattern.matcher(value).matches();
        }
    }
}

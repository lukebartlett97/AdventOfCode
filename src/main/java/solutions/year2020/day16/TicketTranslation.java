package solutions.year2020.day16;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TicketTranslation extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day16/";

    public TicketTranslation() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<TicketField> fields = getFields(data);
        Ticket myTicket = getTickets(data, "your ticket:").get(0);
        List<Ticket> otherTickets = getTickets(data, "nearby tickets:");
        printInfo(otherTickets.size() + " tickets.");
        List<Ticket> validTickets = filterInvalidTickets(fields, otherTickets);
        printInfo(validTickets.size() + " valid tickets.");
        fields.forEach(x -> x.setValidIndexes(validTickets));
        calculateIndexes(fields);
        return Long.toString(calculateTicketValue(myTicket, fields));
    }

    private void calculateIndexes(List<TicketField> fields) {
        fields.sort(Comparator.comparingInt(x -> x.validIndexes.size()));
        for(TicketField field : fields) {
            if(field.validIndexes.size() > 1) {
                printInfo("PANIC");
            } else {
                field.index = field.validIndexes.get(0);
                removeIndex(fields, field.index);
            }
        }
    }

    private void removeIndex(List<TicketField> fields, Integer value) {
        fields.forEach(x -> x.validIndexes.remove(value));
    }

    private long calculateTicketValue(Ticket ticket, List<TicketField> fields) {
        long acc = 1;
        for(TicketField ticketField : fields) {
            printInfo("Field: " + ticketField.name + ", index: " + ticketField.index + ", value: " + ticket.values.get(ticketField.index));
            if(ticketField.name.startsWith("departure")) {
                acc *= ticket.values.get(ticketField.index);
            }
        }
        return acc;
    }

    private List<Ticket> filterInvalidTickets(List<TicketField> fields, List<Ticket> tickets) {
        return tickets.stream().filter(x -> x.isValid(fields)).collect(Collectors.toList());
    }

    private List<TicketField> getFields(List<String> data) {
        List<TicketField> out = new ArrayList<>();
        for(String line : data) {
            if(line.equals(""))  {
                break;
            }
            out.add(new TicketField(line));
        }
        return out;
    }

    private List<Ticket> getTickets(List<String> data, String startingLine) {
        boolean started = false;
        List<Ticket> out = new ArrayList<>();
        for(String line : data) {
            if(started) {
                if(line.equals(""))  {
                    break;
                }
                out.add(new Ticket(line));
            } else {
                if(line.equals(startingLine)) {
                    started = true;
                }
            }
        }
        return out;
    }

    private class Ticket {
        List<Integer> values;

        Ticket(String line) {
            values = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }

        boolean isValid(List<TicketField> fields) {
            for(Integer value : values) {
                if(!isValidField(fields, value)) {
                    return false;
                }
            }
            return true;
        }

        private boolean isValidField(List<TicketField> fields, int value) {
            for(TicketField field : fields) {
                if(field.isValid(value)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class TicketField {
        String name;
        int firstLower;
        int firstUpper;
        int secondLower;
        int secondUpper;
        int index = -1;
        List<Integer> validIndexes = new ArrayList<>();

        TicketField(String line) {
            String[] half = line.split(":");
            name = half[0];
            String[] words = half[1].split(" ");
            String[] firstRange = words[1].split("-");
            firstLower = Integer.parseInt(firstRange[0]);
            firstUpper = Integer.parseInt(firstRange[1]);
            String[] secondRange = words[3].split("-");
            secondLower = Integer.parseInt(secondRange[0]);
            secondUpper = Integer.parseInt(secondRange[1]);
        }

        boolean isValid(int value) {
            boolean valid = (value >= firstLower && value <= firstUpper) || (value >= secondLower && value <= secondUpper);
            //printInfo(name + " is " + (valid ? "" : "not") + " valid with value: " + value);
            return valid;
        }

        void setValidIndexes(List<Ticket> tickets) {
            int fields = tickets.get(0).values.size();
            for(int i = 0; i < fields; i++) {
                if(isValidAtIndex(tickets, i)) {
                    validIndexes.add(i);
                }
            }
        }

        private boolean isValidAtIndex(List<Ticket> tickets, int checkIndex) {
            for(Ticket ticket : tickets) {
                if(!isValid(ticket.values.get(checkIndex))) {
                    return false;
                }
            }
            return true;
        }
    }
}

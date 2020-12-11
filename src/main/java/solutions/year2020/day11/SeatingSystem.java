package solutions.year2020.day11;

import solutions.SolutionMain;
import sun.reflect.annotation.ExceptionProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SeatingSystem extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day11/";

    public SeatingSystem()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        Room room = new Room(data);
        return Integer.toString(fillRoom(room).countFilledSeats());
    }

    private Room fillRoom(Room room) {
        int seatsChanged = 1;
        while(seatsChanged > 0) {
            seatsChanged = 0;
            seatsChanged += room.flipSeats();
        }
        return room;
    }

    private class Room {
        List<List<Space>> spaces = new ArrayList<>();

        Room(List<String> data) {
            for(String row : data) {
                List<Space> spaceRow = new ArrayList<>();
                for(char letter : row.toCharArray()) {
                    spaceRow.add(new Space(letter == 'L'));
                }
                spaces.add(spaceRow);
            }
        }

        int countFilledSeats() {
            int out = 0;
            for(List<Space> row : spaces) {
                for(Space space : row) {
                    out += space.isOccupied ? 1 : 0;
                }
            }
            return out;
        }

        int flipSeats() {
            List<Space> seatsToFlip = new ArrayList<>();
            for(int y = 0; y < spaces.size(); y++) {
                List<Space> thisRow = spaces.get(y);
                for(int x = 0; x < thisRow.size(); x++) {
                    if(thisRow.get(x).isSeat && !thisRow.get(x).isOccupied && countInView(x, y) == 0) {
                        seatsToFlip.add(thisRow.get(x));
                    }
                    if(thisRow.get(x).isOccupied && countInView(x, y) > 4) {
                        seatsToFlip.add(thisRow.get(x));
                    }
                }
            }
            for(Space space : seatsToFlip) {
                space.isOccupied = !space.isOccupied;
            }
            printInfo(this.toString());
            return seatsToFlip.size();
        }

        int countAdjacent(int x, int y) {
            int out = 0;
            out += seatIsFilled(x-1, y-1) ? 1 : 0;
            out += seatIsFilled(x, y-1) ? 1 : 0;
            out += seatIsFilled(x+1, y-1) ? 1 : 0;
            out += seatIsFilled(x-1, y) ? 1 : 0;
            out += seatIsFilled(x+1, y) ? 1 : 0;
            out += seatIsFilled(x-1, y+1) ? 1 : 0;
            out += seatIsFilled(x, y+1) ? 1 : 0;
            out += seatIsFilled(x+1, y+1) ? 1 : 0;
            return out;
        }

        int countInView(int x, int y) {
            int out = 0;
            out += seatInView(x, y, -1, -1) ? 1 : 0;
            out += seatInView(x, y, 0, -1) ? 1 : 0;
            out += seatInView(x, y, 1, -1) ? 1 : 0;
            out += seatInView(x, y, -1, 0) ? 1 : 0;
            out += seatInView(x, y, 1, 0) ? 1 : 0;
            out += seatInView(x, y, -1, 1) ? 1 : 0;
            out += seatInView(x, y, 0, 1) ? 1 : 0;
            out += seatInView(x, y, 1, 1) ? 1 : 0;
            return out;
        }

        boolean seatIsFilled(int x, int y) {
            try {
                Space space = spaces.get(y).get(x);
                return space.isSeat && space.isOccupied;
            } catch(Exception e) {
                return false;
            }
        }

        boolean seatIsEmpty(int x, int y) {
            try {
                Space space = spaces.get(y).get(x);
                return space.isSeat && !space.isOccupied;
            } catch(Exception e) {
                return false;
            }
        }

        boolean seatInView(int x, int y, int xDirection, int yDirection) {
            while(y >= 0 && y < spaces.size() && x >= 0 && x < spaces.get(y).size()) {
                x += xDirection;
                y += yDirection;
                if(seatIsFilled(x, y)) {
                    return true;
                }
                if(seatIsEmpty(x, y)) {
                    return false;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(List<Space> row : spaces) {
                for(Space space : row) {
                    sb.append(space.toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    private class Space {
        boolean isSeat;
        boolean isOccupied = false;

        Space(boolean isSeat) {
            this.isSeat = isSeat;
        }

        @Override
        public String toString() {
            return isSeat ? (isOccupied ? "#" : "L") : ".";
        }
    }
}

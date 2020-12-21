package solutions.year2020.day12;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.List;

public class ShipDirections extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day12/";

    public ShipDirections()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        return Integer.toString(calculateDistance(data));
    }

    private int calculateDistance(List<String> data) {
        Ferry ferry = new Ferry();
        for(String line : data) {
            int value = Integer.parseInt(line.substring(1));
            switch(line.charAt(0)) {
                case('N'): {
                    ferry.offsetY -= value;
                    break;
                }
                case('S'): {
                    ferry.offsetY += value;
                    break;
                }
                case('E'): {
                    ferry.offsetX += value;
                    break;
                }
                case('W'): {
                    ferry.offsetX -= value;
                    break;
                }
                case('L'): {
                    for(int i = 0; i < value / 90; i++) {
                        ferry.turnLeft();
                    }
                    break;
                }
                case('R'): {
                    for(int i = 0; i < value / 90; i++) {
                        ferry.turnRight();
                    }
                    break;
                }
                case('F'): {
                    ferry.moveForward(value);
                    break;
                }
            }
            printInfo(ferry.toString());
        }
        return ferry.x + ferry.y;
    }

    private class Ferry {
        int x = 0;
        int y = 0;
        int offsetX = 10;
        int offsetY = -1;

        void moveForward(int amount) {
            x += offsetX * amount;
            y += offsetY * amount;
        }

        void turnLeft() {
            int temp = offsetX;
            offsetX = offsetY;
            offsetY = 0 - temp;
        }

        void turnRight() {
            int temp = offsetY;
            offsetY = offsetX;
            offsetX = 0 - temp;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Ferry at: (");
            sb.append(x);
            sb.append(",");
            sb.append(y);
            sb.append(") Waypoint at: (");
            sb.append(offsetX);
            sb.append(",");
            sb.append(offsetY);
            sb.append(")");
            return sb.toString();
        }
    }

}

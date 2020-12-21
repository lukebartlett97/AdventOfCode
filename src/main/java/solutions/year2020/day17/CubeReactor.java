package solutions.year2020.day17;

import solutions.SolutionMain;

import java.util.*;

public class CubeReactor extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day17/";

    public CubeReactor() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        PocketDimension pocketDimension = new PocketDimension(data);
        for(int i = 0; i < 6; i++) {
            printInfo("Active Cubes after " + i + " cycles: " + pocketDimension.activeCubes.size());
            pocketDimension.reactionCycle();
        }
        return Integer.toString(pocketDimension.activeCubes.size());
    }

    private class PocketDimension {
        Set<Cube> activeCubes = new HashSet<>();

        PocketDimension(List<String> data) {
            for(int lineNumber = 0; lineNumber < data.size(); lineNumber++) {
                String line = data.get(lineNumber);
                for(int charNumber = 0; charNumber < line.length(); charNumber++) {
                    if(line.charAt(charNumber) == '#') {
                        activeCubes.add(new Cube(0, charNumber, lineNumber, 0));
                    }
                }
            }
            printInfo("MinW = " + getMinW());
            printInfo("MaxW = " + getMaxW());
            printInfo("MinX = " + getMinX());
            printInfo("MaxX = " + getMaxX());
            printInfo("MinY = " + getMinY());
            printInfo("MaxY = " + getMaxY());
            printInfo("MinZ = " + getMinZ());
            printInfo("MaxZ = " + getMaxZ());
        }

        private int getMinW() {
            return activeCubes.stream().min(Comparator.comparingInt(x -> x.w)).map(x -> x.w).orElse(0);
        }

        private int getMaxW() {
            return activeCubes.stream().max(Comparator.comparingInt(x -> x.w)).map(x -> x.w).orElse(0);
        }

        private int getMinX() {
            return activeCubes.stream().min(Comparator.comparingInt(x -> x.x)).map(x -> x.x).orElse(0);
        }

        private int getMaxX() {
            return activeCubes.stream().max(Comparator.comparingInt(x -> x.x)).map(x -> x.x).orElse(0);
        }

        private int getMinY() {
            return activeCubes.stream().min(Comparator.comparingInt(x -> x.y)).map(x -> x.y).orElse(0);
        }

        private int getMaxY() {
            return activeCubes.stream().max(Comparator.comparingInt(x -> x.y)).map(x -> x.y).orElse(0);
        }

        private int getMinZ() {
            return activeCubes.stream().min(Comparator.comparingInt(x -> x.z)).map(x -> x.z).orElse(0);
        }

        private int getMaxZ() {
            return activeCubes.stream().max(Comparator.comparingInt(x -> x.z)).map(x -> x.z).orElse(0);
        }

        private Cube getActiveCoord(int w, int x, int y, int z) {
            for(Cube cube : activeCubes) {
                if(cube.w == w && cube.x == x && cube.y == y && cube.z == z) {
                    return cube;
                }
            }
            return null;
        }

        void reactionCycle() {
            for(Cube cube : getCubesToFlip()) {
                Cube active = getActiveCoord(cube.w, cube.x, cube.y, cube.z);
                if(active == null) {
                    activeCubes.add(cube);
                } else {
                    activeCubes.remove(active);
                }
            }
        }

        private List<Cube> getCubesToFlip() {
            List<Cube> flips = new ArrayList<>();
            for(int w = getMinW() - 1; w < getMaxW() + 2; w++) {
                for (int x = getMinX() - 1; x < getMaxX() + 2; x++) {
                    for (int y = getMinY() - 1; y < getMaxY() + 2; y++) {
                        for (int z = getMinZ() - 1; z < getMaxZ() + 2; z++) {
                            int count = getNumAdjacentActive(w, x, y, z);
                            boolean isActive = getActiveCoord(w, x, y, z) != null;
                            if ((isActive && (count < 2 || count > 3)) ||
                                    (!isActive && count == 3)) {
                                flips.add(new Cube(w, x, y, z));
                            }
                        }
                    }
                }
            }
            return flips;
        }

        private int getNumAdjacentActive(int w, int x, int y, int z) {
            int count = 0;
            for(int checkW = w-1; checkW < w+2; checkW++) {
                for (int checkX = x - 1; checkX < x + 2; checkX++) {
                    for (int checkY = y - 1; checkY < y + 2; checkY++) {
                        for (int checkZ = z - 1; checkZ < z + 2; checkZ++) {
                            if (!(checkW == w && checkX == x && checkY == y && checkZ == z) && getActiveCoord(checkW, checkX, checkY, checkZ) != null) {
                                count++;
                            }
                        }
                    }
                }
            }
            return count;
        }
    }

    private class Cube {
        int w;
        int x;
        int y;
        int z;
        Cube(int w, int x, int y, int z) {
            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

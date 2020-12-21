package solutions.year2020.day20;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JigsawImage extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day20/";

    public JigsawImage() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<Tile> tiles = buildTiles(data);
        for(Tile tile : tiles) {
            tile.populateMatchers(tiles);
        }
        List<Tile> corners = tiles.stream().filter(tile -> tile.getTotalMatchers() == 2).collect(Collectors.toList());
        List<Tile> edges = tiles.stream().filter(tile -> tile.getTotalMatchers() == 3).collect(Collectors.toList());
        printInfo(corners.size() + " Corners: " + corners.toString());
        printInfo(edges.size() + " Edges: " + edges.toString());
        Tile topLeft = setTopLeftTile(corners);
        Sheet sheet = new Sheet(topLeft, tiles);
        printInfo(sheet.toString());
        sheet.prettyPrint();
        Tile image = sheet.buildFullImage();
        image.prettyPrint();
        image.removeSeaMonsters();
        image.prettyPrint();
        return Integer.toString(image.countHashes());
    }

    public void testingTile() {
        printInfo("Test tile:");
        Tile testTile = getTestTile();
        testTile.printMatchersInfo();
        testTile.prettyPrint();
        testTile.rotate();
        testTile.prettyPrint();
        testTile.flipHorizontal();
        testTile.prettyPrint();
        testTile.flipVertical();
        testTile.prettyPrint();
        printSeaMonster();
    }

    private void printSeaMonster() {
        printInfo("Sea Monster:");
        List<Coord> seaMonster = getSeaMonster();
        for(int i = 0; i < 3; i++) {
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < 20; j++) {
                final int xCoord = j;
                final int yCoord = i;
                boolean isSeaMonster = seaMonster.stream().anyMatch(x -> x.x == xCoord && x.y == yCoord);
                sb.append((isSeaMonster ? "#" : " "));
            }
            printInfo(sb.toString());
        }
    }

    private List<Coord> getSeaMonster() {
        List<Coord> out = new ArrayList<>();
        out.add(new Coord(18,0));
        out.add(new Coord(0,1));
        out.add(new Coord(5,1));
        out.add(new Coord(6,1));
        out.add(new Coord(11,1));
        out.add(new Coord(12,1));
        out.add(new Coord(17,1));
        out.add(new Coord(18,1));
        out.add(new Coord(19,1));
        out.add(new Coord(1,2));
        out.add(new Coord(4,2));
        out.add(new Coord(7,2));
        out.add(new Coord(10,2));
        out.add(new Coord(13,2));
        out.add(new Coord(16,2));
        return out;
    }

    private Tile setTopLeftTile(List<Tile> tiles) {
        Tile tile = tiles.get(0);
        while(tile.topMatchers.size() + tile.leftMatchers.size() > 0) {
            tile.rotate();
        }
        return tile;
    }

    private List<Tile> buildTiles(List<String> data) {
        List<Tile> tiles = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        int ID = -1;
        for(String line : data) {
            if(ID>-1) {
                if(line.equals("")) {
                    tiles.add(new Tile(ID, rows));
                    rows.clear();
                    ID = -1;
                } else {
                    rows.add(line);
                }
            } else {
                ID = Integer.parseInt(line.substring(5,9));
            }
        }
        tiles.add(new Tile(ID, rows));
        return tiles;
    }

    private Tile getTestTile() {
        List<String> rows = new ArrayList<>();
        rows.add("abc");
        rows.add("def");
        rows.add("ghi");
        Tile test = new Tile(0, rows);
        test.topMatchers.add(new Tile(1, rows));
        test.rightMatchers.add(new Tile(2, rows));
        test.bottomMatchers.add(new Tile(3, rows));
        test.leftMatchers.add(new Tile(4, rows));
        return test;
    }

    private class Sheet {
        List<List<Tile>> tiles = new ArrayList<>();

        List<Tile> unsortedTiles;

        Sheet(Tile topLeft, List<Tile> allTiles) {
            List<Tile> firstRow = new ArrayList<>();
            firstRow.add(topLeft);
            tiles.add(firstRow);
            unsortedTiles = allTiles;
            sortTiles();
        }

        private void sortTiles() {
            completeNextRow();
            while(tiles.get(tiles.size()-1).get(0).bottomMatchers.size() > 0) {
                startNewRow();
                completeNextRow();
            }
            printMissingTiles();
        }

        private void printMissingTiles() {
            List<Tile> missing = unsortedTiles;
            for(Tile tile : flattenTiles()) {
                missing.remove(tile);
            }
            if(missing.size() > 0) {
                printInfo("Missing tiles: " + missing);
                missing.forEach(Tile::prettyPrint);
                missing.forEach(Tile::printMatchersInfo);
            }
        }

        private List<Tile> flattenTiles() {
            List<Tile> flat = new ArrayList<>();
            for(List<Tile> row : tiles) {
                flat.addAll(row);
            }
            return flat;
        }

        private void completeNextRow() {
            int bottomRowIndex = tiles.size()-1;
            List<Tile> bottomRow = tiles.get(bottomRowIndex);
            while(bottomRow.get(bottomRow.size()-1).rightMatchers.size() > 0) {
                Tile lastTile = bottomRow.get(bottomRow.size()-1);
                Tile nextTile = lastTile.rightMatchers.get(0);
                while(!nextTile.leftMatchers.contains(lastTile)) {
                    nextTile.rotate();
                }
                if(!nextTile.getLeftEdge(false).equals(lastTile.getRightEdge(false))) {
                    nextTile.flipVertical();
                }
                bottomRow.add(nextTile);
                while(!nextTile.leftMatchers.contains(lastTile)) {
                    nextTile.rotate();
                }
            }
        }

        private void startNewRow() {
            Tile lastTile = tiles.get(tiles.size()-1).get(0);
            Tile nextTile = lastTile.bottomMatchers.get(0);
            while(!nextTile.topMatchers.contains(lastTile)) {
                nextTile.rotate();
            }
            if(!nextTile.getTopEdge(false).equals(lastTile.getBottomEdge(false))) {
                nextTile.flipHorizontal();
            }
            List<Tile> newRow = new ArrayList<>();
            newRow.add(nextTile);
            tiles.add(newRow);
        }

        private Tile buildFullImage() {
            List<String> rows = new ArrayList<>();
            for(List<Tile> tileRow : tiles) {
                for(int i = 1; i < tileRow.get(0).grid.get(0).size() - 1; i++) {
                    StringBuilder sb = new StringBuilder();
                    for(Tile tile : tileRow) {
                        sb.append(tile.convertToString(tile.grid.get(i).subList(1,tile.grid.get(i).size() - 1)));
                    }
                    rows.add(sb.toString());
                }
            }
            return new Tile(0, rows);
        }

        @Override
        public String toString() {
            return tiles.toString();
        }

        private void prettyPrint() {
            for(List<Tile> row : tiles) {
                for(int i = 0; i < row.get(0).grid.size(); i++) {
                    StringBuilder sb = new StringBuilder();
                    for(Tile tile : row) {
                        List<Character> charRow = tile.grid.get(i);
                        for(Character character : charRow) {
                            sb.append(character);
                        }
                        sb.append(" ");
                    }
                    printInfo(sb.toString());
                }
                printInfo("");
            }
        }
    }

    private class Tile {
        int ID;
        List<List<Character>> grid = new ArrayList<>();
        List<Tile> leftMatchers = new ArrayList<>();
        List<Tile> topMatchers = new ArrayList<>();
        List<Tile> rightMatchers = new ArrayList<>();
        List<Tile> bottomMatchers = new ArrayList<>();

        Tile(int ID, List<String> rows) {
            this.ID = ID;
            for(String row : rows) {
                List<Character> rowList = new ArrayList<>();
                for(Character character : row.toCharArray()) {
                    rowList.add(character);
                }
                grid.add(rowList);
            }
        }

        void rotate() {
            List<Tile> temp = bottomMatchers;
            bottomMatchers = rightMatchers;
            rightMatchers = topMatchers;
            topMatchers = leftMatchers;
            leftMatchers = temp;
            rotateGrid();
        }

        private void printMatchersInfo() {
            printInfo("For Tile " + ID + ":");
            printInfo("Left Matchers: " + leftMatchers.toString());
            printInfo("Top Matchers: " + topMatchers.toString());
            printInfo("Right Matchers: " + rightMatchers.toString());
            printInfo("Bottom Matchers: " + bottomMatchers.toString());
        }

        private void rotateGrid() {
            List<List<Character>> newGrid = new ArrayList<>();
            for(int i = 0; i < grid.size(); i++) {
                List<Character> newRow = new ArrayList<>();
                for(int j = 0; j < grid.size(); j++) {
                    int inverse = grid.size() - j - 1;
                    newRow.add(grid.get(inverse).get(i));
                }
                newGrid.add(newRow);
            }
            grid = newGrid;
        }

        void flipHorizontal() {
            List<Tile> tempMatcher = leftMatchers;
            leftMatchers = rightMatchers;
            rightMatchers = tempMatcher;
            for(List<Character> row : grid) {
                Collections.reverse(row);
            }
        }

        void flipVertical() {
            List<Tile> tempMatcher = bottomMatchers;
            bottomMatchers = topMatchers;
            topMatchers = tempMatcher;
            for(int across = 0; across < grid.size(); across++) {
                List<Character> temp = new ArrayList<>();
                for(int down = 0; down < grid.size(); down++) {
                    temp.add(grid.get(down).get(across));
                }
                for(int down = 0; down < grid.size(); down++) {
                    grid.get(down).set(across, temp.get(grid.size() - down - 1));
                }
            }
        }

        int getTotalMatchers() {
            return leftMatchers.size() + topMatchers.size() + rightMatchers.size() + bottomMatchers.size();
        }

        void populateMatchers(List<Tile> tiles) {
            printInfo("For Tile " + ID + ":");
            populateMatcher(leftMatchers, tiles, getLeftEdge(false));
            populateMatcher(topMatchers, tiles, getTopEdge(false));
            populateMatcher(rightMatchers, tiles, getRightEdge(false));
            populateMatcher(bottomMatchers, tiles, getBottomEdge(false));
            printMatchersInfo();
        }

        private void populateMatcher(List<Tile> matches, List<Tile> tiles, String matcher) {
            for(Tile tile : tiles) {
                if(tile != this && tile.hasMatchingEdge(matcher)) {
                    matches.add(tile);
                }
            }
        }

        boolean hasMatchingEdge(String matcher) {
            return matcher.equals(getTopEdge(false)) ||
                    matcher.equals(getBottomEdge(false)) ||
                    matcher.equals(getLeftEdge(false)) ||
                    matcher.equals(getRightEdge(false)) ||
                    matcher.equals(getTopEdge(true)) ||
                    matcher.equals(getBottomEdge(true)) ||
                    matcher.equals(getLeftEdge(true)) ||
                    matcher.equals(getRightEdge(true));

        }

        String convertToString(List<Character>characters) {
            StringBuilder sb = new StringBuilder();
            for(Character character : characters) {
                sb.append(character);
            }
            return sb.toString();
        }

        private String convertToStringReversed(List<Character>characters) {
            StringBuilder sb = new StringBuilder();
            for(int i = characters.size()-1; i >= 0; i--) {
                sb.append(characters.get(i));
            }
            return sb.toString();
        }

        private String getTopEdge(boolean reversed) {
            List<Character> edge = grid.get(0);
            return reversed ? convertToStringReversed(edge) : convertToString(edge);
        }

        private String getBottomEdge(boolean reversed) {
            List<Character> edge = grid.get(grid.size()-1);
            return reversed ? convertToStringReversed(edge) : convertToString(edge);
        }

        private String getLeftEdge(boolean reversed) {
            List<Character> edge = new ArrayList<>();
            for(List<Character> row : grid) {
                edge.add(row.get(0));
            }
            return reversed ? convertToStringReversed(edge) : convertToString(edge);
        }

        private String getRightEdge(boolean reversed) {
            List<Character> edge = new ArrayList<>();
            for(List<Character> row : grid) {
                edge.add(row.get(row.size() - 1));
            }
            return reversed ? convertToStringReversed(edge) : convertToString(edge);
        }

        void removeSeaMonsters() {
            int hashesBefore = countHashes();
            List<Coord> monster = getSeaMonster();
            List<Coord> seaMonsters = findSeaMonsters(monster);
            prettyPrint(seaMonsters);
            printInfo(seaMonsters.size() + " Sea Monsters found at: " + seaMonsters.toString());
            for(Coord root : seaMonsters) {
                for(Coord offset : monster) {
                    grid.get(root.y + offset.y).set(root.x + offset.x, '.');
                }
            }
            int hashesAfter = countHashes();
            printInfo(hashesBefore + " hashes before, " + hashesAfter + " hashes after.");
        }

        private List<Coord> findSeaMonsters(List<Coord> monster) {
            List<Coord> found = new ArrayList<>();
            for(int yCoord = 0; yCoord < grid.size()-2; yCoord++) {
                for(int xCoord = 0; xCoord < grid.size()-19; xCoord++) {
                    if(thereIsASeaMonsterHere(new Coord(xCoord, yCoord), monster)) {
                        found.add(new Coord(xCoord, yCoord));
                    }
                }
            }
            return found;
        }

        private boolean thereIsASeaMonsterHere(Coord searchPlace, List<Coord> monster) {
            for(Coord monsterPiece : monster) {
                if(grid.get(searchPlace.y + monsterPiece.y).get(searchPlace.x + monsterPiece.x) != '#') {
                    return false;
                }
            }
            return true;
        }

        int countHashes() {
            int acc = 0;
            for(List<Character> row : grid) {
                for(Character letter : row) {
                    if(letter == '#') {
                        acc++;
                    }
                }
            }
            return acc;
        }

        @Override
        public String toString() {
            return "Tile " + ID;
        }

        private void prettyPrint() {
            prettyPrint(new ArrayList<>());
        }

        private boolean isSeaMonsterPiece(Coord coord, List<Coord> seaMonsters) {
            List<Coord> offsets = getSeaMonster();
            for(Coord root : seaMonsters) {
                for(Coord offset : offsets) {
                    if(root.y + offset.y == coord.y && root.x + offset.x == coord.x) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void prettyPrint(List<Coord> seaMonsters) {
            printInfo(this.toString());
            for(int y = 0; y < grid.size(); y++) {
                StringBuilder sb = new StringBuilder();
                List<Character> row = grid.get(y);
                for(int x = 0; x < row.size(); x++) {
                    Character letter = row.get(x);
                    if(isSeaMonsterPiece(new Coord(x, y), seaMonsters)) {
                        letter = 'O';
                    }
                    sb.append(letter);
                }
                printInfo(sb.toString());
            }
        }
    }

    private class Coord {
        int x;
        int y;
        Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + "," + y;
        }
    }
}

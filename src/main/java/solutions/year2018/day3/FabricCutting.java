package solutions.year2018.day3;

import solutions.SolutionMain;

import java.util.ArrayList;
import java.util.List;

public class FabricCutting extends SolutionMain {
    String RESOURCE_PATH = "/year2018/Day3/";

    public FabricCutting()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        Fabric sheet = new Fabric();
        data.stream().map(Claim::new).forEach(sheet::applyClaim);
        Claim claim = data.stream().map(Claim::new).filter(x -> !sheet.isClaimContested(x)).findFirst().orElse(null);
        printInfo(sheet.toString());
        return Integer.toString(claim == null ? -1 : claim.id);
    }

    private class Square {
        List<Claim> claims = new ArrayList<>();

        void addClaim(Claim claim) {
            claims.add(claim);
        }

        boolean isContested() {
            return claims.size() > 1;
        }

        @Override
        public String toString() {
            if(isContested()) {
                return "  X  ";
            }
            if(claims.isEmpty()) {
                return "  .  ";
            }
            String id = Integer.toString(claims.get(0).id);
            if(id.length() == 1) {
                return "  " + id + "  ";
            }
            if(id.length() == 2) {
                return "  " + id + " ";
            }
            if(id.length() == 3) {
                return " " + id + " ";
            }
            return " " + id + "";
        }
    }

    private class Fabric {
        List<List<Square>> rows = new ArrayList<>();

        Fabric() {
            for(int i = 0; i<1000; i++) {
                List<Square> newRow = new ArrayList<>();
                for(int j = 0; j<1000; j++) {
                    newRow.add(new Square());
                }
                rows.add(newRow);
            }
        }

        Square getSquare(int x, int y) {
            return rows.get(y).get(x);
        }

        void applyClaim(Claim claim) {
            for(int x = claim.left; x < claim.left + claim.width; x++) {
                for(int y = claim.down; y < claim.down + claim.height; y++) {
                    getSquare(x, y).addClaim(claim);
                }
            }
        }

        int amountContested() {
            int acc = 0;
            for(List<Square> row : rows) {
                for (Square square : row) {
                    acc += square.isContested() ? 1 : 0;
                }
            }
            return acc;
        }

        boolean isClaimContested(Claim claim) {
            for(int x = claim.left; x < claim.left + claim.width; x++) {
                for(int y = claim.down; y < claim.down + claim.height; y++) {
                    if(getSquare(x, y).isContested()) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(List<Square> row : rows) {
                for(Square square : row) {
                    sb.append(square.toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }


    private class Claim {
        final int id;
        final int left;
        final int down;
        final int width;
        final int height;

        Claim(String line) {
            String replaced = line.replaceAll("\\D+", ",");
            String[] split = replaced.split(",");
            id = Integer.parseInt(split[1]);
            left = Integer.parseInt(split[2]);
            down = Integer.parseInt(split[3]);
            width = Integer.parseInt(split[4]);
            height = Integer.parseInt(split[5]);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("id:");
            sb.append(id);
            sb.append(",left:");
            sb.append(left);
            sb.append(",down:");
            sb.append(down);
            sb.append(",width:");
            sb.append(width);
            sb.append(",height:");
            sb.append(height);
            return sb.toString();
        }
    }
}

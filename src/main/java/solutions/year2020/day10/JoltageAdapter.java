package solutions.year2020.day10;

import solutions.SolutionMain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoltageAdapter extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day10/";

    public JoltageAdapter()
    {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) throws IOException, InterruptedException {
        List<Integer> intList = convertToIntegerList(data);
        intList.add(0);
        List<Integer> sorted = intList.stream().sorted().collect(Collectors.toList());
        sorted.add(3 + sorted.get(sorted.size()-1));
        return Long.toString(mathsySolve(sorted));
    }

    private long mathsySolve(List<Integer> data) {
        int ones = 0;
        long paths = 1;
        int joint = 0;
        int previous = -1;
        for(int value : data) {
            if(previous != -1) {
                if(value - previous == 1) {
                    joint += ones == 0 ? 1 : ones;
                    ones++;
                } else {
                    if(joint > 0) {
                        paths *= joint;
                    }
                    joint = 0;
                    ones = 0;
                }

            }
            previous = value;
        }
        return paths;
    }

    private long nodeSolve(List<Integer> data) {

        List<Node> nodes = data.stream().map(Node::new).collect(Collectors.toList());
        for(Node node : nodes) {
            nodes.stream().filter(x -> x.value-node.value > 0 && x.value-node.value < 4).forEach(node::addNode);
        }
        return nodes.get(0).getNumRoutes();
    }

    private class Node {
        private int value;

        Node(int value) {
            this.value = value;
        }

        List<Node> children = new ArrayList<>();

        void addNode(Node child) {
            children.add(child);
        }

        long getNumRoutes() {
            if(children.isEmpty()) {
                return 1;
            }
            long out = 0;
            for(Node child : children) {
                out += child.getNumRoutes();
            }
            return out;
        }
    }

    private long calculateChecksum(List<Integer> data) {
        int ones = 0;
        int threes = 0;
        for(int index = 1; index < data.size(); index++) {
            int diff = data.get(index) - data.get(index-1);
            if(diff == 1) {
                ones++;
            }
            if(diff == 3) {
                threes++;
            }
        }
        printInfo("Ones: " + ones);
        printInfo("Threes: " + threes);
        return ones * threes;
    }

}

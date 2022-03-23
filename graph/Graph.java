package graph;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    HashMap<Integer, Node> nodes;   // 点集，当用户输入int时，value匹配到对应的节点
    HashSet<Edge> edges;            // 边集

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}

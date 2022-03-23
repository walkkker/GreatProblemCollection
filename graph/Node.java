package graph;

import java.util.ArrayList;

public class Node {
    int val;    //  当前结点的值，或者区分ID
    int in;     //  当前节点的入度
    int out;    //  当前结点的出度
    ArrayList<Node> nexts;    // 该点指向的邻居
    ArrayList<Edge> edges;    // 该点出发的边

    public Node(int v) {
        val = v;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}

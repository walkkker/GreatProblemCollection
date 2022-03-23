package graph;

public class Edge {

    int weight;   // 边的 权重
    Node from;    // 从哪一个节点出发的
    Node to;      // 到哪一个节点的

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}

import java.util.Stack;

public class ShortestPathsTopological {
    private int[] parent;
    private int s;
    private double[] dist;

    public ShortestPathsTopological(WeightedDigraph G, int s) {
        this.s = s;
        parent = new int[G.V()];
        dist = new double[G.V()];
        for (int v = 0; v < G.V(); v++) {
            parent[v] = -1;
            dist[v] = Double.POSITIVE_INFINITY;
        }
        dist[s] = 0.0;
        parent[s] = s;

        TopologicalWD topo = new TopologicalWD(G);
        topo.dfs(s);
        if (topo.hasCycle()) {
            throw new IllegalArgumentException("graph contains a cycle");
        }
        Stack<Integer> order = topo.order();
        while (!order.isEmpty()) {
            int v = order.pop();
            for (DirectedEdge e : G.incident(v)) {
                relax(e);
            }
        }
    }

    public void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (dist[v] + e.weight() < dist[w]) {
            dist[w] = dist[v] + e.weight();
            parent[w] = v;
        }
    }

    public boolean hasPathTo(int v) {
        return parent[v] >= 0;
    }

    public Stack<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int w = v; w != s; w = parent[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }
}


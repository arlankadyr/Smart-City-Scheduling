package graph.scc;

import graph.*;
import java.util.*;

public class KosarajuSCC {
    private final Graph g;
    private final List<List<Integer>> sccs;
    private final int[] componentId;
    private final Metrics metrics = new Metrics();

    public KosarajuSCC(Graph g) {
        this.g = g;
        this.sccs = new ArrayList<>();
        this.componentId = new int[g.n];
        compute();
    }

    private void compute() {
        long start = System.nanoTime();
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[g.n];

        // Phase 1: DFS on original
        for (int i = 0; i < g.n; i++) {
            if (!visited[i]) dfs1(i, visited, order);
        }

        // Phase 2: DFS on transpose
        Arrays.fill(visited, false);
        int id = 0;
        for (int i = order.size() - 1; i >= 0; i--) {
            int v = order.get(i);
            if (!visited[v]) {
                List<Integer> component = new ArrayList<>();
                dfs2(v, visited, component);
                sccs.add(component);
                for (int u : component) componentId[u] = id;
                id++;
            }
        }
        metrics.time = System.nanoTime() - start;
        metrics.components = sccs.size();
    }

    private void dfs1(int u, boolean[] visited, List<Integer> order) {
        visited[u] = true;
        metrics.dfsVisits++;
        for (Edge e : g.adj.get(u)) {
            metrics.dfsEdges++;
            if (!visited[e.to]) dfs1(e.to, visited, order);
        }
        order.add(u);
    }

    private void dfs2(int u, boolean[] visited, List<Integer> component) {
        visited[u] = true;
        component.add(u);
        for (Edge e : g.adjT.get(u)) {
            if (!visited[e.to]) dfs2(e.to, visited, component);
        }
    }

    public List<List<Integer>> getSCCs() { return sccs; }
    public int[] getComponentId() { return componentId; }

    public Graph buildCondensation() {
        int m = sccs.size();
        Graph dag = new Graph(""); // dummy
        dag.n = m;
        dag.adj = new ArrayList<>();
        dag.adjT = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            dag.adj.add(new ArrayList<>());
            dag.adjT.add(new ArrayList<>());
        }

        Set<String> used = new HashSet<>();
        for (int u = 0; u < g.n; u++) {
            for (Edge e : g.adj.get(u)) {
                int cu = componentId[u], cv = componentId[e.to];
                if (cu != cv && used.add(cu + "-" + cv)) {
                    dag.adj.get(cu).add(new Edge(cv, e.weight));
                }
            }
        }
        dag.buildTranspose();
        return dag;
    }

    public Metrics getMetrics() { return metrics; }
}
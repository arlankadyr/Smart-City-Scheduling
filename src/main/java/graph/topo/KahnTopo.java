package graph.topo;

import graph.*;
import java.util.*;

public class KahnTopo {
    private final List<Integer> order;
    private final Metrics metrics = new Metrics();

    public KahnTopo(Graph dag) {
        long start = System.nanoTime();
        int[] indegree = new int[dag.n];
        for (int u = 0; u < dag.n; u++) {
            for (Edge e : dag.adj.get(u)) indegree[e.to]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < dag.n; i++) {
            if (indegree[i] == 0) q.add(i);
        }

        order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.pops++;
            order.add(u);
            for (Edge e : dag.adj.get(u)) {
                metrics.pushes++;
                if (--indegree[e.to] == 0) q.add(e.to);
            }
        }

        if (order.size() != dag.n) {
            throw new RuntimeException("Graph has a cycle! Not a DAG.");
        }
        metrics.time = System.nanoTime() - start;
    }

    public List<Integer> getOrder() { return order; }

    public List<Integer> getTaskOrder(int[] compId, List<List<Integer>> sccs) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < sccs.size(); i++) {
            List<Integer> list = new ArrayList<>(sccs.get(i));
            list.sort(Integer::compareTo);
            map.put(i, list);
        }
        for (int c : order) result.addAll(map.get(c));
        return result;
    }

    public Metrics getMetrics() { return metrics; }
}
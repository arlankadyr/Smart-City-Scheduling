package graph.dagsp;

import graph.*;
import java.util.*;

public class DAGShortestPath {
    private final double[] distShort, distLong;
    private final int[] prevShort, prevLong;
    private final Metrics metrics = new Metrics();

    public DAGShortestPath(Graph dag, int source, List<Integer> topo) {
        long start = System.nanoTime();
        distShort = new double[dag.n]; Arrays.fill(distShort, Double.POSITIVE_INFINITY);
        distLong = new double[dag.n]; Arrays.fill(distLong, Double.NEGATIVE_INFINITY);
        prevShort = new int[dag.n]; Arrays.fill(prevShort, -1);
        prevLong = new int[dag.n]; Arrays.fill(prevLong, -1);

        distShort[source] = 0;
        distLong[source] = 0;

        for (int u : topo) {
            if (distShort[u] == Double.POSITIVE_INFINITY) continue;
            for (Edge e : dag.adj.get(u)) {
                double w = e.weight;

                // Shortest
                if (distShort[u] + w < distShort[e.to]) {
                    distShort[e.to] = distShort[u] + w;
                    prevShort[e.to] = u;
                    metrics.relaxations++;
                }

                // Longest
                if (distLong[u] + w > distLong[e.to]) {
                    distLong[e.to] = distLong[u] + w;
                    prevLong[e.to] = u;
                    metrics.relaxations++;
                }
            }
        }
        metrics.time = System.nanoTime() - start;
    }

    public List<Integer> reconstruct(int[] prev, int target) {
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = prev[v]) path.add(v);
        Collections.reverse(path);
        return path.isEmpty() ? Collections.emptyList() : path;
    }

    public double[] getShortest() { return distShort; }
    public double[] getLongest() { return distLong; }
    public Metrics getMetrics() { return metrics; }
}
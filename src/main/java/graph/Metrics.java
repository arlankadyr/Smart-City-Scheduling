package graph;

public class Metrics {
    public long time = 0;
    public int dfsVisits = 0;
    public int dfsEdges = 0;
    public int pops = 0;
    public int pushes = 0;
    public int relaxations = 0;
    public int components = 0;

    @Override
    public String toString() {
        return String.format(
            "Time: %d ns | DFS: %d visits, %d edges | Kahn: %d pops, %d pushes | SP: %d relax | SCC: %d",
            time, dfsVisits, dfsEdges, pops, pushes, relaxations, components
        );
    }
}
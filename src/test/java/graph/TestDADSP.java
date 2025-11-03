package graph;

import graph.dagsp.DAGShortestPath;
import graph.scc.KosarajuSCC;
import graph.topo.KahnTopo;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestDAGSP {

    @Test
    public void testShortestPathInDAG() {
        Graph g = new Graph("data/small1.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        Graph dag = scc.buildCondensation();
        KahnTopo topo = new KahnTopo(dag);
        DAGShortestPath sp = new DAGShortestPath(dag, 0, topo.getOrder());

        double[] dist = sp.getShortest();
        assertEquals("Distance to node 5", 26.0, dist[5], 0.001);
    }

    @Test
    public void testLongestPathInDAG() {
        Graph g = new Graph("data/small1.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        Graph dag = scc.buildCondensation();
        KahnTopo topo = new KahnTopo(dag);
        DAGShortestPath sp = new DAGShortestPath(dag, 0, topo.getOrder());

        double[] dist = sp.getLongest();
        assertEquals("Longest path to node 5", 26.0, dist[5], 0.001);
    }
}
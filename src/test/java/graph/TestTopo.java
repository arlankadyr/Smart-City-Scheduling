package graph;

import graph.scc.KosarajuSCC;
import graph.topo.KahnTopo;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTopo {

    @Test
    public void testTopoOnDAG() {
        Graph g = new Graph("data/small1.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        Graph dag = scc.buildCondensation();
        KahnTopo topo = new KahnTopo(dag);
        assertEquals("Topological order must cover all nodes", dag.n, topo.getOrder().size());
    }

    @Test(expected = RuntimeException.class)
    public void testTopoDetectsCycle() {
        Graph g = new Graph("data/small2.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        Graph dag = scc.buildCondensation();
        new KahnTopo(dag); // Should throw if cycle remains (but condensation removes cycles)
    }
}
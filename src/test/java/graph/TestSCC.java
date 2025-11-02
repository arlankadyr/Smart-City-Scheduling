package graph;

import graph.scc.KosarajuSCC;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSCC {

    @Test
    public void testTasksJsonSCCCount() {
        Graph g = new Graph("data/tasks.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        assertEquals("tasks.json should have 6 SCCs", 6, scc.getSCCs().size());
    }

    @Test
    public void testSmall1IsDAG() {
        Graph g = new Graph("data/small1.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        assertEquals("small1.json is DAG → 1 SCC per node", g.n, scc.getSCCs().size());
    }

    @Test
    public void testSmall2HasOneCycle() {
        Graph g = new Graph("data/small2.json");
        KosarajuSCC scc = new KosarajuSCC(g);
        assertTrue("small2.json has 1 cycle → at least 1 SCC with >1 node",
                scc.getSCCs().stream().anyMatch(comp -> comp.size() > 1));
    }
}
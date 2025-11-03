package graph;

import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -cp bin:gson-2.10.1.jar graph.Main data/tasks.json");
            return;
        }

        Graph g = new Graph(args[0]);
        KosarajuSCC scc = new KosarajuSCC(g);
        Graph dag = scc.buildCondensation();
        KahnTopo topo = new KahnTopo(dag);
        DAGShortestPath sp = new DAGShortestPath(dag, 0, topo.getOrder());

        System.out.println("SCCs: " + scc.getSCCs());
        System.out.println("Component order: " + topo.getOrder());
        System.out.println("Task order: " + topo.getTaskOrder(scc.getComponentId(), scc.getSCCs()));
        System.out.println("Shortest: " + Arrays.toString(sp.getShortest()));
        System.out.println("Longest: " + Arrays.toString(sp.getLongest()));
        System.out.println("Metrics: " + scc.getMetrics());
    }
}
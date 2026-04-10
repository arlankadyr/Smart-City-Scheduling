# Assignment 4: Smart City Scheduling

**Goal**: Consolidate SCC & Topological Ordering + Shortest Paths in DAGs

---

## Weight Model
**Edge weights** (chosen for all datasets)

---

## Packages
- `graph.scc` → Kosaraju + condensation DAG
- `graph.topo` → Kahn topological sort
- `graph.dagsp` → DAG shortest & longest paths

---

## Datasets (`data/`)
| File | n | e | Description |
|------|----|----|-----------|
| tasks.json | 8 | 7 | From assignment |
| small1.json | 6 | 5 | Pure DAG |
| small2.json | 7 | 6 | 1 cycle |
| small3.json | 9 | 8 | 2 SCCs |
| medium1.json | 12 | 11 | 2 SCCs + chain |
| medium2.json | 15 | 17 | Mixed density |
| medium3.json | 18 | 17 | DAG |
| large1.json | 25 | 24 | Cyclic, sparse |
| large2.json | 30 | 32 | DAG, dense |
| large3.json | 40 | 39 | 4 SCCs |

---

## Metrics (per algorithm)
| Algorithm | Metric |
|---------|--------|
| Kosaraju | DFS visits/edges, time, SCC count |
| Kahn | Pops/pushes, time |
| DAG-SP | Relaxations, time |

---

## JUnit Tests (`src/test/java`)
- `TestSCC.java`: SCC count, cycle detection
- `TestTopo.java`: valid topo order
- `TestDAGSP.java`: shortest/longest paths

---

## Build & Run (IntelliJ / Terminal)
```bash
# Terminal
javac -cp gson-2.10.1.jar -d bin $(find src/main/java -name "*.java")
java -cp bin:gson-2.10.1.jar graph.Main data/tasks.json

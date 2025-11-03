package graph;

import com.google.gson.*;
import java.io.FileReader;
import java.util.*;

public class Graph {
    public int n;
    public List<List<Edge>> adj, adjT;
    public int source;
    public String weightModel;

    public Graph(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            this.n = json.get("n").getAsInt();
            this.source = json.get("source").getAsInt();
            this.weightModel = json.get("weight_model").getAsString();

            adj = new ArrayList<>();
            adjT = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
                adjT.add(new ArrayList<>());
            }

            JsonArray edges = json.getAsJsonArray("edges");
            for (JsonElement e : edges) {
                JsonObject edge = e.getAsJsonObject();
                int u = edge.get("u").getAsInt();
                int v = edge.get("v").getAsInt();
                int w = edge.get("w").getAsInt();
                adj.get(u).add(new Edge(v, w));
            }
            buildTranspose();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON: " + filename, e);
        }
    }

    private void buildTranspose() {
        for (int u = 0; u < n; u++) {
            for (Edge e : adj.get(u)) {
                adjT.get(e.to).add(new Edge(u, e.weight));
            }
        }
    }
}
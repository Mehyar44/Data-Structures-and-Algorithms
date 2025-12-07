// Stores intersections and roads, supports Dijkstra

import java.util.*;

public class Graph {
    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<Node, List<Edge>> adj = new HashMap<>();

    public void addNode(String id, double lat, double lon) {
        Node n = new Node(id, lat, lon);
        nodes.put(id, n);
        adj.put(n, new ArrayList<>());
    }

    public void addEdge(String roadId, String idA, String idB) {
        Node a = nodes.get(idA);
        Node b = nodes.get(idB);
        if (a == null || b == null) return; // safety
        double d = haversine(a.getLat(), a.getLon(), b.getLat(), b.getLon());
        Edge e = new Edge(roadId, a, b, d);
        adj.get(a).add(e);
        adj.get(b).add(e);
    }

    public Node getNode(String id) { return nodes.get(id); }
    public List<Edge> getEdges(Node n) { return adj.get(n); }
    public Collection<Node> getNodes() { return nodes.values(); }

    public Node dijkstra(Node start, Node end) {
        if (start == null || end == null) return null;
        for (Node n : nodes.values()) {
            n.dist = Double.POSITIVE_INFINITY;
            n.prev = null;
            n.visited = false;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(n -> n.dist));
        start.dist = 0;
        pq.add(start);

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.visited) continue;
            cur.visited = true;
            if (cur == end) break;

            for (Edge e : adj.get(cur)) {
                Node next = (e.getA() == cur) ? e.getB() : e.getA();
                double nd = cur.dist + e.getDist();
                if (nd < next.dist) {
                    next.dist = nd;
                    next.prev = cur;
                    pq.add(next);
                }
            }
        }
        return end.dist == Double.POSITIVE_INFINITY ? null : end;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 3958.8; // miles
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2) +
                   Math.cos(lat1)*Math.cos(lat2)*Math.sin(dLon/2)*Math.sin(dLon/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }
}
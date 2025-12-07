// Represents an intersection in the graph

public class Node {
    private final String id;      // intersection ID
    private final double lat;     // latitude
    private final double lon;     // longitude

    // Dijkstra fields
    public double dist;           // current distance from start
    public Node prev;             // previous node in shortest path
    public boolean visited;       // whether node has been visited

    public Node(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.dist = Double.POSITIVE_INFINITY;
        this.prev = null;
        this.visited = false;
    }

    public String getId() { return id; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
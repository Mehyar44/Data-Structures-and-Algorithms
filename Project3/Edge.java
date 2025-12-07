// Represents a road connecting two intersections

public class Edge {
    private final String id;  // unique road ID
    private final Node a;     // one endpoint of the road
    private final Node b;     // other endpoint of the road
    private final double dist; // distance in miles

    // Constructor to initialize all fields
    public Edge(String id, Node a, Node b, double dist) {
        this.id = id;
        this.a = a;
        this.b = b;
        this.dist = dist;
    }

    // Get the road ID
    public String getId() { return id; }

    // Get one endpoint
    public Node getA() { return a; }

    // Get the other endpoint
    public Node getB() { return b; }

    // Get the distance of this edge
    public double getDist() { return dist; }
}
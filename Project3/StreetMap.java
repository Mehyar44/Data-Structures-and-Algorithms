// For reading input, showing map, and computing directions

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class StreetMap extends JPanel {
    private final Graph graph = new Graph(); // the map graph
    private Node startNode = null;
    private Node endNode = null;
    private List<Node> path = null;          // stores shortest path
    private double minLat, maxLat, minLon, maxLon; // for scaling map

    // Constructor loads data, computes path if requested, and optionally shows map
    public StreetMap(String filename, boolean show, String start, String end) throws IOException {
        loadData(filename);

        // Compute shortest path if start and end given
        if (start != null && end != null) {
            startNode = graph.getNode(start);
            endNode = graph.getNode(end);
            Node dest = graph.dijkstra(startNode, endNode);
            path = new ArrayList<>();
            if (dest != null) {
                for (Node n = dest; n != null; n = n.prev) path.add(0, n); // build path
                System.out.println("Path:");
                for (Node n : path) System.out.print(n.getId() + " ");
                System.out.printf("\nDistance: %.3f miles\n", dest.dist);
            } else {
                System.out.println("No path exists between " + start + " and " + end);
            }
        }

        // Show map if requested
        if (show) {
            JFrame frame = new JFrame("Street Map");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(this);
            frame.setSize(800, 800);
            frame.setVisible(true);
        }
    }

    // Load intersections and roads from file
    private void loadData(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        minLat = Double.MAX_VALUE; maxLat = -Double.MAX_VALUE;
        minLon = Double.MAX_VALUE; maxLon = -Double.MAX_VALUE;

        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\t");
            if (parts[0].equals("i")) {
                String id = parts[1];
                double lat = Double.parseDouble(parts[2]);
                double lon = Double.parseDouble(parts[3]);
                graph.addNode(id, lat, lon);
                // Update bounds for scaling
                minLat = Math.min(minLat, lat);
                maxLat = Math.max(maxLat, lat);
                minLon = Math.min(minLon, lon);
                maxLon = Math.max(maxLon, lon);
            } else if (parts[0].equals("r")) {
                graph.addEdge(parts[1], parts[2], parts[3]);
            }
        }
        br.close();
    }

    // Draw the map and highlight path
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw all edges
        g2.setColor(Color.BLACK);
        Set<Edge> drawn = new HashSet<>();
        for (Node n : graph.getNodes()) {
            for (Edge e : graph.getEdges(n)) {
                if (!drawn.contains(e)) {
                    Point p1 = mapToPanel(e.getA());
                    Point p2 = mapToPanel(e.getB());
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    drawn.add(e);
                }
            }
        }

        // Draw shortest path in red
        if (path != null && path.size() > 1) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(2));
            for (int i = 0; i < path.size() - 1; i++) {
                Point p1 = mapToPanel(path.get(i));
                Point p2 = mapToPanel(path.get(i+1));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    // Convert latitude/longitude to panel coordinates
    private Point mapToPanel(Node n) {
        int w = getWidth();
        int h = getHeight();
        int x = (int)((n.getLon() - minLon)/(maxLon - minLon)*w);
        int y = (int)((maxLat - n.getLat())/(maxLat - minLat)*h);
        return new Point(x, y);
    }

    // Main entry point: parse command line arguments
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        boolean show = false;
        String start = null, end = null;
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--show")) show = true;
            else if (args[i].equals("--directions") && i+2 < args.length) {
                start = args[i+1];
                end = args[i+2];
                i += 2;
            }
        }
        new StreetMap(filename, show, start, end);
    }
}
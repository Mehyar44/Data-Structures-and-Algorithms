import java.io.*;
import java.util.*;

public class HuffmanImpl implements Huffman {

    // There are 256 possible byte values (0–255)
    private static final int ALPHABET = 256;

    // A node in the Huffman tree
    private static class Node implements Comparable<Node> {
        final int symbol;     // actual byte value (0–255) for leaves; -1 for internal nodes
        final long freq;      // frequency count
        final Node left, right;

        Node(int symbol, long freq, Node left, Node right) {
            this.symbol = symbol;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() { return left == null && right == null; }

        // Priority queue orders by frequency
        public int compareTo(Node o) {
            if (this.freq < o.freq) return -1;
            if (this.freq > o.freq) return 1;
            return 0;
        }
    }

    // Reads raw bytes and counts how often each byte appears
    private long[] buildFrequencies(String inputFile) throws IOException {
        long[] freq = new long[ALPHABET];
        File file = new File(inputFile);

        if (!file.exists()) return freq;

        FileInputStream fis = new FileInputStream(file);
        int b;
        while ((b = fis.read()) != -1) {
            freq[b & 0xFF]++;
        }
        fis.close();

        return freq;
    }

    // Writes the frequency file in "xxxxxxxx:number" format for each used byte
    private void writeFreqFile(String freqFile, long[] freq) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(freqFile));

        for (int i = 0; i < ALPHABET; i++) {
            if (freq[i] > 0) {
                // Convert byte to 8-bit binary
                String bin = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
                bw.write(bin + ":" + freq[i]);
                bw.newLine();
            }
        }

        bw.close();
    }

    // Reads a frequency file back into an array
    private long[] readFreqFile(String freqFile) throws IOException {
        long[] freq = new long[ALPHABET];

        BufferedReader br = new BufferedReader(new FileReader(freqFile));

        String line;
        while ((line = br.readLine()) != null) {
            int colon = line.indexOf(':');
            String bin = line.substring(0, colon);
            long count = Long.parseLong(line.substring(colon + 1));

            int symbol = Integer.parseInt(bin, 2);
            freq[symbol] = count;
        }

        br.close();
        return freq;
    }

    // Builds the Huffman tree using a priority queue
    private Node buildHuffmanTree(long[] freq) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Add one leaf node per used byte
        for (int i = 0; i < ALPHABET; i++) {
            if (freq[i] > 0) {
                pq.add(new Node(i, freq[i], null, null));
            }
        }

        // No symbols in file
        if (pq.isEmpty()) return null;

        // Combine lowest-frequency nodes until one tree remains
        while (pq.size() > 1) {
            Node a = pq.poll();
            Node b = pq.poll();
            pq.add(new Node(-1, a.freq + b.freq, a, b));
        }

        return pq.poll();
    }

    // Generates a Huffman code for each byte
    private void buildCodes(Node root, String[] codes) {
        if (root.isLeaf()) {
            // Edge case: only one symbol
            codes[root.symbol] = "0";
            return;
        }
        buildCodesRec(root, "", codes);
    }

    // Recursive DFS to assign codes
    private void buildCodesRec(Node node, String prefix, String[] codes) {
        if (node.isLeaf()) {
            codes[node.symbol] = prefix;
            return;
        }
        buildCodesRec(node.left, prefix + "0", codes);
        buildCodesRec(node.right, prefix + "1", codes);
    }

    // Encodes inputFile → outputFile using Huffman coding
    public void encode(String inputFile, String outputFile, String freqFile) {
        try {
            // Count frequencies
            long[] freq = buildFrequencies(inputFile);

            // Save frequency file
            writeFreqFile(freqFile, freq);

            // Build Huffman tree
            Node root = buildHuffmanTree(freq);

            // Count total bytes
            long total = 0;
            for (long f : freq) total += f;

            // If file is empty, write nothing
            if (total == 0) {
                new FileOutputStream(outputFile).close();
                return;
            }

            // Generate codes
            String[] codes = new String[ALPHABET];
            buildCodes(root, codes);

            // Write encoded bits
            BinaryOut out = new BinaryOut(outputFile);
            FileInputStream fis = new FileInputStream(inputFile);

            int b;
            while ((b = fis.read()) != -1) {
                String code = codes[b & 0xFF];
                for (char bit : code.toCharArray()) {
                    out.write(bit == '1'); // write boolean (bit)
                }
            }

            fis.close();
            out.flush();
        } catch (Exception e) {}
    }

    // Decodes inputFile back into outputFile using the stored freqFile
    public void decode(String inputFile, String outputFile, String freqFile) {
        try {
            // Rebuild frequencies
            long[] freq = readFreqFile(freqFile);

            // Rebuild tree
            Node root = buildHuffmanTree(freq);

            long total = 0;
            for (long f : freq) total += f;

            // Empty file
            if (total == 0 || root == null) {
                new FileOutputStream(outputFile).close();
                return;
            }

            // Special case: only one byte in file
            if (root.isLeaf()) {
                FileOutputStream fos = new FileOutputStream(outputFile);
                byte value = (byte) root.symbol;
                for (long i = 0; i < total; i++) fos.write(value);
                fos.close();
                return;
            }

            // Decode bit-by-bit
            BinaryIn in = new BinaryIn(inputFile);
            FileOutputStream fos = new FileOutputStream(outputFile);

            long written = 0;
            Node current = root;

            while (written < total && !in.isEmpty()) {
                boolean bit = in.readBoolean();
                current = bit ? current.right : current.left;

                if (current.isLeaf()) {
                    fos.write((byte) current.symbol);
                    written++;
                    current = root;
                }
            }

            fos.close();
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        Huffman h = new HuffmanImpl();
        h.encode("ur.jpg", "ur.enc", "freq.txt");
        h.decode("ur.enc", "ur_dec.jpg", "freq.txt");
    }
}
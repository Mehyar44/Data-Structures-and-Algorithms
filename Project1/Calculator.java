import java.io.File;
import java.io.PrintWriter;

public class Calculator {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Calculator <input_file> <output_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try (java.util.Scanner in = new java.util.Scanner(new File(inputFile));
             PrintWriter out = new PrintWriter(new File(outputFile))) {

            while (in.hasNextLine()) {
                String infix = in.nextLine().trim();
                if (infix.isEmpty()) continue;

                try {
                    String postfix = InfixToPostfix.convert(infix);
                    double result = PostfixEvaluator.evaluate(postfix);
                    out.printf("%.2f%n", result);
                } catch (Exception e) {
                    out.printf("0.00%n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
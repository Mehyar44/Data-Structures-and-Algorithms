public class Tokenizer {

    public static String[] tokenize(String expr) {
        LinkedList<String> tokens = new LinkedList<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                number.append(c); // build multi-digit numbers
            } else {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }

                if (c == ' ') continue; // skip spaces

                if ("+-*/^%<>=&|()!".indexOf(c) >= 0) {
                    // handle unary minus
                    if (c == '-' && (i == 0 || expr.charAt(i - 1) == '(' || "+-*/^%<>=&|!".indexOf(expr.charAt(i - 1)) >= 0)) {
                        tokens.add("~"); // unary minus
                    } else {
                        tokens.add(String.valueOf(c));
                    }
                }
            }
        }

        if (number.length() > 0) tokens.add(number.toString());
        
        // Convert LinkedList to array
        String[] result = new String[tokens.size()];
        for (int i = 0; i < tokens.size(); i++) {
            result[i] = tokens.get(i);
        }
        return result;
    }
}
public class InfixToPostfix {

    public static String convert(String infix) {
        String[] tokens = Tokenizer.tokenize(infix);
        Stack<String> stack = new Stack<>();
        StringBuilder output = new StringBuilder();

        for (String token : tokens) {
            if (isOperand(token)) {
                output.append(token).append(" ");
            } else if (token.equals("~") || isFunction(token)) {
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                stack.pop(); // remove "("
                if (!stack.isEmpty() && isFunction(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
            } else { // operator
                while (!stack.isEmpty() && !stack.peek().equals("(") &&
                        ((precedence(stack.peek()) > precedence(token)) ||
                         (precedence(stack.peek()) == precedence(token) && !isRightAssociative(token)))) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) output.append(stack.pop()).append(" ");
        return output.toString().trim();
    }

    private static boolean isOperand(String token) {
        try { 
            Double.parseDouble(token); 
            return true; 
        } catch (NumberFormatException e) { 
            return false; 
        }
    }

    private static boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan");
    }

    private static int precedence(String op) {
        switch (op) {
            case "~": return 5; // unary minus highest
            case "sin": case "cos": case "tan": case "!": return 4;
            case "^": return 3;
            case "*": case "/": case "%": return 2;
            case "+": case "-": return 1;
            case "<": case ">": case "=": return 0;
            case "&": return -1;
            case "|": return -2;
            default: return -10;
        }
    }

    private static boolean isRightAssociative(String op) {
        return op.equals("^") || op.equals("!") || op.equals("~") || isFunction(op);
    }
}
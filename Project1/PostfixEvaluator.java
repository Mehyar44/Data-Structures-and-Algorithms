public class PostfixEvaluator {

    public static double evaluate(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (token.equals("~")) { // unary minus
                if (stack.isEmpty()) throw new IllegalArgumentException("Unary minus with no operand");
                stack.push(-stack.pop());
            } else if (token.equals("!")) { // logical NOT
                if (stack.isEmpty()) throw new IllegalArgumentException("Logical NOT with no operand");
                stack.push(stack.pop() == 0 ? 1.0 : 0.0);
            } else if (token.equals("sin")) {
                if (stack.isEmpty()) throw new IllegalArgumentException("sin with no operand");
                stack.push(Math.sin(Math.toRadians(stack.pop())));
            } else if (token.equals("cos")) {
                if (stack.isEmpty()) throw new IllegalArgumentException("cos with no operand");
                stack.push(Math.cos(Math.toRadians(stack.pop())));
            } else if (token.equals("tan")) {
                if (stack.isEmpty()) throw new IllegalArgumentException("tan with no operand");
                stack.push(Math.tan(Math.toRadians(stack.pop())));
            } else { // binary operators
                if (stack.size() < 2) throw new IllegalArgumentException("Not enough operands for operator " + token);
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/": stack.push(a / b); break;
                    case "^": stack.push(Math.pow(a, b)); break;
                    case "%": stack.push(a % b); break;
                    case "<": stack.push(a < b ? 1.0 : 0.0); break;
                    case ">": stack.push(a > b ? 1.0 : 0.0); break;
                    case "=": stack.push(a == b ? 1.0 : 0.0); break;
                    case "&": stack.push((a != 0 && b != 0) ? 1.0 : 0.0); break;
                    case "|": stack.push((a != 0 || b != 0) ? 1.0 : 0.0); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }

        if (stack.size() != 1) throw new IllegalArgumentException("Invalid expression: leftover operands");
        return stack.pop();
    }

    private static boolean isNumber(String token) {
        try { 
            Double.parseDouble(token); 
            return true; 
        } catch (NumberFormatException e) { 
            return false; 
        }
    }
}
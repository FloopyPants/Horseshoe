package horseshoe;

import java.util.HashMap;
import java.util.Map;

public class Horseshoe {
    private final Map<String, Object> variables = new HashMap<>();

    public void Initialize() {
        // initialize some variables..

        variables.put("test", "\"HELLO WORLD\"");
    }

    public Object Evaluate(String expr) {
        expr = expr.trim();

        if (expr.matches("-?(\\d*\\.\\d+|\\d+)")) {
            if (expr.contains(".")) {
                return Double.parseDouble(expr);
            }
            else {
                return Integer.parseInt(expr);
            }
        }

        if (expr.startsWith("\"") && expr.endsWith("\"")) {
            return expr.substring(1, expr.length() - 1);
        }

        if (expr.startsWith("{") && expr.endsWith("}")) {
            String varname = expr.substring(1, expr.length() - 1);

            if (variables.containsKey(varname)) {
                Object value = variables.get(varname);
                if (value instanceof String s && s.startsWith("\"") && s.endsWith("\"")) {
                    return s.substring(1, s.length() - 1);
                } else if (value instanceof String i && i.matches("-?(\\d*\\.\\d+|\\d+)")) {
                    if (expr.contains(".")) {
                        return Double.parseDouble(expr);
                    }
                    else {
                        return Integer.parseInt(expr);
                    }
                }
                return value;
            }
        }
        
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            Object left = Evaluate(parts[0]);
            Object right = Evaluate(parts[1]);
            if (left instanceof String && right instanceof String) {
                return left.toString() + right.toString();
            } else {
                return ((Number) left).doubleValue() + ((Number) right).doubleValue();
            }
        }
        if (expr.contains("-")) {
            String[] parts = expr.split("-");
            Object left = Evaluate(parts[0]);
            Object right = Evaluate(parts[1]);
            return ((Number) left).doubleValue() - ((Number) right).doubleValue();
        }

        if (expr.contains("nil")) {
            return "nil";
        }

        throw new Error("Unknown expression - " + expr);
    }
}

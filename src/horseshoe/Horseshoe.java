package horseshoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Horseshoe {
    public interface HSFunction {
        Object call(Object... args);
    }

    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, HSFunction> functions = new HashMap<>();

    public void Initialize() {
        // initialize some variables..

        PushVariable("test", "HELLO WORLD");

        PushHSFunction("print", args -> {
            for (Object o : args) {
                System.out.println(o);
            }
            return null;
        });
    }

    public void PushVariable(String name, Object value) {
        if (value != null) {
            variables.put(name, value);
        }
    }

    public void PushHSFunction(String name, HSFunction hs) {
        if (hs != null) {
            functions.put(name, hs);
        }
    }

    public Object Evaluate(String expr) {
        expr = expr.trim();

        if (expr.matches("-?(\\d*\\.\\d+|\\d+)")) {
            if (expr.contains(".")) {
                return Double.parseDouble(expr);
            } else {
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
                    } else {
                        return Integer.parseInt(expr);
                    }
                }
                return value;
            }
        }

        if (expr.matches("[A-Za-z_][A-Za-z0-9_]*\\(.*\\)")) {
            String fname = expr.substring(0, expr.indexOf("("));
            String inside = expr.substring(expr.indexOf("(") + 1, expr.lastIndexOf(")")).trim();

            if (!functions.containsKey(fname)) {
                throw new RuntimeException("Unknown function - " + fname);
            }

            List<Object> args = new ArrayList<>();

            if (!inside.isEmpty()) {
                String[] parts = splitArgs(inside);
                for (String p : parts) {
                    args.add(Evaluate(p));
                }
            }

            return functions.get(fname).call(args.toArray());
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

        if (expr.equals("nil"))
            return "nil";

        throw new RuntimeException("Unknown expression - " + expr);
    }
    
    private String[] splitArgs(String s) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        boolean inString = false; 
        StringBuilder current = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (c == '"')
                inString = !inString;

            if (c == ',' && !inString && depth == 0) {
                parts.add(current.toString().trim());
                current.setLength(0);
            } else {
                if (c == '(')
                    depth++;
                if (c == ')')
                    depth--;
                current.append(c);
            }
        }

        parts.add(current.toString().trim());
        return parts.toArray(new String[0]);
    }

    public void Execute(String[] str) {
        for (String line : str) {
            line = line.trim();

            if (line.endsWith(";")) line = line.substring(0, line.length() - 1).trim();

            if (line.startsWith("let ")) {
                String rest = line.substring(4).trim();
                String[] parts = rest.split("=", 2);
                String varName = parts[0].trim();
                String expr = parts[1].trim();
                Object value = Evaluate(expr);
                variables.put(varName, value);
                continue;
            }

            if (!line.isEmpty() || !line.isBlank()) {
                Evaluate(line);
            }
        }
    }
}

package horseshoe;

import java.util.HashMap;
import java.util.Map;

public class Horseshoe {
    private final Map<String, Object> variables = new HashMap<>();

    public void Initialize() {
        // initialize some variables..

        variables.put("test", "\"HELLO MY BEAUTIFUL CHILDREN\"");
        variables.put("test2", "23");
    }

    public Object Evaluate(String expr) {
        
        if (expr.matches("-?\\d+")) {
            return Integer.parseInt(expr);
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
                }
                else if (value instanceof String i && i.matches("-?\\d+")) {
                    return Integer.parseInt(i);
                }
                return value;
            }
        }

        throw new Error("Unknown expression - " + expr);
    }
}

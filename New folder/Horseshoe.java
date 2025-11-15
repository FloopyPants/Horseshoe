import java.util.HashMap;
import java.util.Map;

public class Horseshoe {
    private final Map<String, Object> variables = new HashMap<>();

    public Object Evaluate(String expr) {
        
        if (expr.startsWith("{") && expr.endsWith("}")) {
            
        }

        throw new Error("Unknown expression - " + expr);
    }
}

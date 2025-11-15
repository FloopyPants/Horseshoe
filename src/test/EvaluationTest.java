package test;

import horseshoe.Horseshoe;

public class EvaluationTest {
    public static void main(String[] args) {
        Horseshoe hs = new Horseshoe();

        hs.Initialize();

        // this prints 69...
        
        Object result = hs.Evaluate("2 + 67");

        System.out.println(result);
    }
}

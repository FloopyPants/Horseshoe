package test;

import horseshoe.Horseshoe;

public class EvaluationTest {
    public static void main(String[] args) {
        Horseshoe hs = new Horseshoe();

        hs.Initialize();

        Object result = hs.Evaluate("{test}");
        Object result2 = hs.Evaluate("{test2}");

        System.out.println(result + " " + result2);
    }
}

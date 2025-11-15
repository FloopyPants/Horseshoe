package test;

import horseshoe.Horseshoe;

public class EvaluationTest {
    public static void main(String[] args) {
        Horseshoe hs = new Horseshoe();

        hs.Initialize();

        Object result = hs.Evaluate("{test} + \"...DON'T YOU PEOPLE REALIZE YOU ARE IN A SIMULATION?!?\"");

        System.out.println(result);
    }
}

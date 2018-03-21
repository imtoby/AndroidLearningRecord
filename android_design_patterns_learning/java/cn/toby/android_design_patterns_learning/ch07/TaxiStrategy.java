package cn.toby.android_design_patterns_learning.ch07;

/**
 * Created by toby on 18-3-21.
 */

public class TaxiStrategy implements CalculateStrategy {
    @Override
    public int calculatePrice(final int km) {
        return km * 2;
    }
}

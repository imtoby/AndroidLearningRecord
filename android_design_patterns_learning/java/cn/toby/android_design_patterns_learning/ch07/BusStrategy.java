package cn.toby.android_design_patterns_learning.ch07;

/**
 * Created by toby on 18-3-21.
 */

public class BusStrategy implements CalculateStrategy {
    @Override
    public int calculatePrice(final int km) {
        if (km <= 10) {
            return 1;
        }
        int extraTotal = km - 10;
        int extraFactor = extraTotal / 5;
        int fraction = extraTotal % 5;
        int price = 1 + extraFactor;
        return fraction > 0 ? ++price : price;
    }
}

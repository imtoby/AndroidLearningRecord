package cn.toby.android_design_patterns_learning.ch07;

/**
 * Created by toby on 18-3-21.
 */

public class SubwayStrategy implements CalculateStrategy {
    @Override
    public int calculatePrice(final int km) {
        if (km <= 6) {
            return 3;
        } else if (km > 6 && km <= 12) {
            return 4;
        } else if (km > 12 && km <= 22) {
            return 5;
        } else if (km > 22 && km <= 32) {
            return 6;
        }

        // 其他距离简化为 7 元
        return 7;
    }
}

package cn.toby.android_design_patterns_learning.ch07;

/**
 * Created by toby on 18-3-21.
 * 北京公交车地铁出行计价器
 */

public class PriceCalculator {
    private final static int TYPE_BUS = 1;
    private final static int TYPE_SUBWAY = 2;

    /**
     * 公交车，十公里之内一元，超过十公里之后每加一元可以乘坐五公里
     *
     * @param km
     * @return price
     */
    private int busPrice(final int km) {
        if (km <= 10) {
            return 1;
        }
        int extraTotal = km - 10;
        int extraFactor = extraTotal / 5;
        int fraction = extraTotal % 5;
        int price = 1 + extraFactor;
        return fraction > 0 ? ++price : price;
    }

    private int subwayPrice(final int km) {
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

    int calculatePrice(int km, int type) {
        if (TYPE_BUS == type) {
            return busPrice(km);
        } else if (TYPE_SUBWAY == type) {
            return subwayPrice(km);
        }
        return 0;
    }
}

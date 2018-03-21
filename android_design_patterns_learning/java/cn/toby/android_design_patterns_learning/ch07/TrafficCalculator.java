package cn.toby.android_design_patterns_learning.ch07;

/**
 * Created by toby on 18-3-21.
 */

public class TrafficCalculator {

    CalculateStrategy strategy;

    public void setStrategy(CalculateStrategy strategy) {
        this.strategy = strategy;
    }

    public int calculatePrice(final int km) {
        return strategy.calculatePrice(km);
    }

    public static void main(String[] args) {
        TrafficCalculator calculator = new TrafficCalculator();
        calculator.setStrategy(new BusStrategy());
        System.out.println("16 km bus price: " + calculator.calculatePrice(16));
    }

}

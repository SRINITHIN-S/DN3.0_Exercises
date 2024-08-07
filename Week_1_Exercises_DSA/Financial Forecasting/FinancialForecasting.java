public class FinancialForecasting {

    // Recursive method to calculate future value
    public static double calculateFutureValue(double initialValue, double growthRate, int years) {
        // Base Case: If years is 0, return the initial value
        if (years == 0) {
            return initialValue;
        }
        // Recursive Case: Calculate future value by applying growth rate for one year and reducing the years
        return calculateFutureValue(initialValue * (1 + growthRate), growthRate, years - 1);
    }

    // Iterative method to calculate future value
    public static double calculateFutureValueIterative(double initialValue, double growthRate, int years) {
        double futureValue = initialValue;
        for (int i = 0; i < years; i++) {
            futureValue *= (1 + growthRate);
        }
        return futureValue;
    }

    public static void main(String[] args) {
        // Example parameters
        double initialValue = 1000; // Initial investment
        double growthRate = 0.05;  // Growth rate (5%)
        int years = 10;            // Number of years to forecast

        // Calculate the future value using recursive method
        double futureValueRecursive = calculateFutureValue(initialValue, growthRate, years);
        System.out.println("Future Value after " + years + " years (Recursive): $" + String.format("%.2f", futureValueRecursive));

        // Calculate the future value using iterative method
        double futureValueIterative = calculateFutureValueIterative(initialValue, growthRate, years);
        System.out.println("Future Value after " + years + " years (Iterative): $" + String.format("%.2f", futureValueIterative));
    }
}

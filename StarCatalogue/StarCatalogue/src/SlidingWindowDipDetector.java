
public class SlidingWindowDipDetector {

    public static class Result {
        public final int startIndex;
        public final int endIndex;
        public final double average;

        public Result(int start, int end, double average) {
            this.startIndex = start;
            this.endIndex = end;
            this.average = average;
        }

        @Override
        public String toString() {
            return "Start: " + startIndex +
                   ", End: " + endIndex +
                   ", Average: " + average;
        }
    }

    public static Result findLowestAverageWindow(
            double[] readings, int windowSize) {

        if (readings == null ||
            windowSize <= 0 ||
            windowSize > readings.length) {
            throw new IllegalArgumentException(
                "Invalid window size or readings"
            );
        }

        double sum = 0;

        for (int i = 0; i < windowSize; i++) {
            sum += readings[i];
        }

        double minSum = sum;
        int minStart = 0;

        for (int i = windowSize; i < readings.length; i++) {
            sum += readings[i];
            sum -= readings[i - windowSize];

            if (sum < minSum) {
                minSum = sum;
                minStart = i - windowSize + 1;
            }
        }

        return new Result(
            minStart,
            minStart + windowSize - 1,
            minSum / windowSize
        );
    }
}

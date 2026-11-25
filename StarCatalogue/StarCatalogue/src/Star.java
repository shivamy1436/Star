
public class Star {
    private String name;
    private double magnitude;
    private double x;
    private double y;

    public Star(String name, double magnitude, double x, double y) {
        this.name = name;
        this.magnitude = magnitude;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Star Name: " + name +
               " | Magnitude: " + magnitude +
               " | X: " + x +
               " | Y: " + y;
    }
}

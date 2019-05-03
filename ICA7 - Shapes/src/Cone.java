public class Cone implements ThreeDShape  {
    private double radius;
    private double height;

    public Cone (double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    public double volume() {
        return (1.0/3.0)*Math.PI*radius*radius*height;
    }

    public void describe() {
        System.out.print("Cone[radius=");
        System.out.print(radius);
        System.out.print(",height=");
        System.out.print(height);
        System.out.println("]");
    }
}

public class Sphere implements ThreeDShape  {
    private double radius;

    public Sphere (double radius) {
        this.radius = radius;
    }

    public double volume() {
        return (4.0/3.0)*Math.PI*radius*radius*radius;
    }

    public void describe() {
        System.out.print("Sphere[radius=");
        System.out.print(radius);
        System.out.println("]");
    }

}

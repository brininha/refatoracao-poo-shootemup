public abstract class Entity {
    protected int state;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double radius;

    public abstract void move(long delta);

    public abstract void draw();

}

public abstract class Entity {
    // constantes de estado
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    // atributos encapsulados
    protected int state;
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double radius;

    // métodos abstratos
    public abstract void move(long delta);
    public abstract void draw(long currentTime);

    // getters
    public int getState() {
        return this.state;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getRadius() {
        return this.radius;
    }

    // setters
    public void setState(int state) {
        this.state = state;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
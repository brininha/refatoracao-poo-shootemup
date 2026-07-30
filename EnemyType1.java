import java.awt.Color;

public class EnemyType1 extends Enemy {

    private long nextShot;

    // cnstrutor para satisfazer a herança
    public EnemyType1(double x, double y, double v, double angle, double rv) {
        super(x, y, v, angle, rv, 9.0); 
        this.nextShot = System.currentTimeMillis() + 500;
    }

    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public long getNextShot() {
        return nextShot;
    }

    @Override
    public Projectile shoot(long currentTime, double playerY, double previousY) {

        if (currentTime > this.nextShot && this.y < playerY) {

            this.nextShot = currentTime + 200 + (long) (Math.random() * 500);

            double projVx = Math.cos(this.getAngle()) * 0.45;
            double projVy = Math.sin(this.getAngle()) * 0.45 * (-1.0);

            return new Projectile(this.x, this.y, projVx, projVy);
        }

        return null;
    }

    @Override
    public void drawShape() {
        GameLib.setColor(Color.CYAN);
        GameLib.drawCircle(this.x, this.y, this.radius);
    }
}
import java.awt.Color;

public class EnemyType1 extends Enemy {

    private long nextShot;

    public void setNextShot(long nextShot) {
        this.nextShot = nextShot;
    }

    public long getNextShot() {
        return nextShot;
    }

    @Override
    public Projectile shoot(long currentTime, double playerY, double previousY) {

        if (currentTime > nextShot && y < playerY) {

            nextShot = currentTime + 200 + (long) (Math.random() * 500);

            Projectile p = new Projectile();
            p.x = x;
            p.y = y;
            p.vx = Math.cos(getAngle()) * 0.45;
            p.vy = Math.sin(getAngle()) * 0.45 * (-1.0);
            p.state = ACTIVE;
            return p;
        }

        return null;
    }

    @Override
    public void drawShape() {
        GameLib.setColor(Color.CYAN);
        GameLib.drawCircle(x, y, radius);
    }
}
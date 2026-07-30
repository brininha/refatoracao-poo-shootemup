import java.awt.Color;

public class EnemyType2 extends Enemy {

    // construtor para satisfazer herança
    public EnemyType2(double x, double y, double v, double angle, double rv) {
        super(x, y, v, angle, rv, 12.0); 
    }

    @Override
    public Projectile shoot(long currentTime, double playerY, double previousY) {

        double threshold = GameLib.HEIGHT * 0.30;

        if (previousY < threshold && this.y >= threshold) {
            if (this.x < GameLib.WIDTH / 2) setRv(0.003);
            else setRv(-0.003);
        }

        boolean shootNow = false;

        if (getRv() > 0 && Math.abs(getAngle() - 3 * Math.PI) < 0.05) {
            setRv(0.0);
            setAngle(3 * Math.PI);
            shootNow = true;
        }

        if (getRv() < 0 && Math.abs(getAngle()) < 0.05) {
            setRv(0.0);
            setAngle(0.0);
            shootNow = true;
        }

        if (!shootNow) return null;

        double a = Math.PI / 2 + Math.random() * Math.PI / 6 - Math.PI / 12;

        double projVx = Math.cos(a) * 0.30;
        double projVy = Math.sin(a) * 0.30;

        return new Projectile(this.x, this.y, projVx, projVy);
    }

    @Override
    public void drawShape() {
        GameLib.setColor(Color.MAGENTA);
        GameLib.drawDiamond(this.x, this.y, this.radius);
    }
}
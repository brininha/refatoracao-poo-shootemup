import java.awt.Color;

public class EnemyType2 extends Enemy {

    @Override
    public Projectile shoot(long currentTime, double playerY, double previousY) {

        double threshold = GameLib.HEIGHT * 0.30;

        // ao cruzar a altura de referência, decide pra que lado vai curvar
        if (previousY < threshold && y >= threshold) {
            if (x < GameLib.WIDTH / 2) setRv(0.003);
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

        Projectile p = new Projectile();
        p.x = x;
        p.y = y;
        p.vx = Math.cos(a) * 0.30;
        p.vy = Math.sin(a) * 0.30;
        p.state = ACTIVE;
        return p;
    }

    @Override
    public void drawShape() {
        GameLib.setColor(Color.MAGENTA);
        GameLib.drawDiamond(x, y, radius);
    }
}
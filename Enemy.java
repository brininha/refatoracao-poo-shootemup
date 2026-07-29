public abstract class Enemy extends Entity {

    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    private double angle;
    private double rv;
    private double explosionStart;
    private double explosionEnd;
    private double v;

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRv() {
        return rv;
    }

    public void setRv(double rv) {
        this.rv = rv;
    }

    public double getExplosionStart() {
        return explosionStart;
    }

    public void setExplosionStart(double explosionStart) {
        this.explosionStart = explosionStart;
    }

    public double getExplosionEnd() {
        return explosionEnd;
    }

    public void setExplosionEnd(double explosionEnd) {
        this.explosionEnd = explosionEnd;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    @Override
    public void move(long delta) {

        long currentTime = System.currentTimeMillis();

        if (state == EXPLODING) {
            if (currentTime > getExplosionEnd()) {
                state = INACTIVE;
            }
            return;
        }

        if (state == ACTIVE) {

            x += v * Math.cos(angle) * delta;
            y += v * Math.sin(angle) * delta * (-1.0);
            angle += rv * delta;

            if (y > GameLib.HEIGHT + radius || x < -radius || x > GameLib.WIDTH + radius) {
                state = INACTIVE;
            }
        }
    }

    public void explode(long currentTime) {
        state = EXPLODING;
        explosionStart = currentTime;
        explosionEnd = currentTime + 500;
    }

    @Override
    public void draw() {

        if (state == EXPLODING) {
            double alpha = (System.currentTimeMillis() - explosionStart) / (explosionEnd - explosionStart);
            GameLib.drawExplosion(x, y, alpha);
            return;
        }

        if (state == ACTIVE) {
            drawShape();
        }
    }

    public abstract void drawShape();

    public abstract Projectile shoot(long currentTime, double playerY, double previousY);

}
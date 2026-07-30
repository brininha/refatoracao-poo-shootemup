public abstract class Enemy extends Entity {

    private double angle;
    private double rv;
    private long explosionStart;
    private long explosionEnd;
    private double v;

    // construtor do inimigo
    public Enemy(double x, double y, double v, double angle, double rv, double radius) {
        this.x = x;
        this.y = y;
        this.v = v;
        this.angle = angle;
        this.rv = rv;
        this.radius = radius;
        this.state = ACTIVE;
    }

    // getters e setters
    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }
    public double getRv() { return rv; }
    public void setRv(double rv) { this.rv = rv; }
    public long getExplosionStart() { return explosionStart; }
    public void setExplosionStart(long explosionStart) { this.explosionStart = explosionStart; }
    public long getExplosionEnd() { return explosionEnd; }
    public void setExplosionEnd(long explosionEnd) { this.explosionEnd = explosionEnd; }
    public double getV() { return v; }
    public void setV(double v) { this.v = v; }

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
    public void draw(long currentTime) {
        if (state == EXPLODING) {
            double alpha = (double) (currentTime - explosionStart) / (explosionEnd - explosionStart);
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
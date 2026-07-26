public class Player extends Entity {
    private long nextShot;
    private long explosionStart;
    private long explosionEnd;

    public Projectile shoot(long currentTime) {
        if (currentTime > this.nextShot) {
            this.nextShot = currentTime + 100;
            // return new Projectile(this.x, this.y - 2 * this.radius, 0.0, -1.0);
        }
        return null;
    }
}

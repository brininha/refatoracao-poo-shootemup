public class Projectile extends Entity 
{
    public void move(long delta) {
        x += vx * delta;
        y += vy * delta;
    }
}

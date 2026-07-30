import java.awt.Color;

public class Player extends Entity {
    
    private long nextShot;
    private long explosionStart;
    private long explosionEnd;

    // construtor
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0.25;
        this.vy = 0.25;
        this.radius = 12.0;
        this.state = ACTIVE;
        this.nextShot = System.currentTimeMillis();
    }

    // controle do teclado e das barreiras da tela
    @Override
    public void move(long delta) {
        if (GameLib.iskeyPressed(GameLib.KEY_UP)) {
            this.y -= delta * this.vy;
        }
        if (GameLib.iskeyPressed(GameLib.KEY_DOWN)) {
            this.y += delta * this.vy;
        }
        if (GameLib.iskeyPressed(GameLib.KEY_LEFT)) {
            this.x -= delta * this.vx;
        }
        if (GameLib.iskeyPressed(GameLib.KEY_RIGHT)) {
            this.x += delta * this.vx;
        }

        if (this.x < 0.0) this.x = 0.0;
        if (this.x >= GameLib.WIDTH) this.x = GameLib.WIDTH - 1;
        if (this.y < 25.0) this.y = 25.0;
        if (this.y >= GameLib.HEIGHT) this.y = GameLib.HEIGHT - 1;
    }

    // método de atirar
    public Projectile shoot(long currentTime) {
        if (currentTime > this.nextShot) {
            this.nextShot = currentTime + 100;
            return new Projectile(this.x, this.y - 2 * this.radius, 0.0, -1.0);
        }
        return null;
    }

    // desenha a nave do player ou a explosão dela
    @Override
    public void draw(long currentTime) {
        if (this.state == EXPLODING) {
            double alpha = (double) (currentTime - this.explosionStart) / (this.explosionEnd - this.explosionStart);
            GameLib.drawExplosion(this.x, this.y, alpha);
        } else {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.x, this.y, this.radius);
        }
    }

    // acionado pela main quando colide
    public void explode(long currentTime) {
        this.setState(EXPLODING);
        this.explosionStart = currentTime;
        this.explosionEnd = currentTime + 2000;
    }

    // usado pela main para saber se a explosão já acabou
    public long getExplosionEnd() {
        return this.explosionEnd;
    }
}
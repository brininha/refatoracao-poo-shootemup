import java.awt.Color;

public class Player extends Entity {
    private long nextShot;
    private long explosionStart;
    private long explosionEnd;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.radius = 12.0;
        this.state = ACTIVE;
        this.nextShot = System.currentTimeMillis();
        this.vx = 0.25;
        this.vy = 0.25;
    }

    public Projectile shoot(long currentTime) {
        if (currentTime > this.nextShot) {
            this.nextShot = currentTime + 100;
            return new Projectile(this.x, this.y - 2 * this.radius, 0.0, -1.0);
        }
        return null;
    }

    public void draw(long currentTime) {
        if (this.state == EXPLODING) {
            double alpha = (currentTime - this.explosionStart) / (this.explosionEnd - this.explosionStart);
            GameLib.drawExplosion(this.x, this.y, alpha);
        } else {
            GameLib.setColor(Color.BLUE);
            GameLib.drawPlayer(this.x, this.y, this.radius);
        }
    }

    @Override
    public void move(long delta) {
        // verificando entrada do usuário (teclado)
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

        // verificando se coordenadas do player ainda estão dentro 
	    // da tela de jogo após processar entrada do usuário.
        if (this.x < 0.0) {
            this.x = 0.0;
        }
        if (this.x >= GameLib.WIDTH) {
            this.x = GameLib.WIDTH - 1;
        }
        if (this.y < 25.0) {
            this.y = 25.0;
        }
        if (this.y >= GameLib.HEIGHT) {
            this.y = GameLib.HEIGHT - 1;
        }
    }
}

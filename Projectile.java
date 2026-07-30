import java.awt.Color;

public class Projectile extends Entity {

    // construtor
    public Projectile(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = 2.0;
        this.state = ACTIVE; 
    }

    @Override
    public void move(long delta) {
        // atualiza a posição baseada na velocidade e no tempo
        this.x += this.vx * delta;
        this.y += this.vy * delta;

        if (this.y < 0.0 || this.y > GameLib.HEIGHT) {
            this.state = INACTIVE;
        }
    }

    @Override
    public void draw(long currentTime) {
        // só desenha se a bala ainda estiver ativa na tela
        if (this.state == ACTIVE) {
            GameLib.setColor(Color.RED);
            
            GameLib.drawLine(this.x, this.y - 5, this.x, this.y + 5);
            GameLib.drawLine(this.x - 1, this.y - 3, this.x - 1, this.y + 3);
            GameLib.drawLine(this.x + 1, this.y - 3, this.x + 1, this.y + 3);
        }
    }
}
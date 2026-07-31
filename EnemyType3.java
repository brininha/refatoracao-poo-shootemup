import java.awt.Color;

public class EnemyType3 extends Enemy {

    private double rotation;
    private Color color;

    public EnemyType3(double x, double y, double v, double angle, double rv) {
        super(x, y, v, angle, rv, 20.0);
        // tamanho e cor aleatórios
        double r = 10.0 + Math.random() * 25.0; // raio entre 10 e 35
        this.radius = r;
        this.rotation = 0.0;

        Color[] palette = new Color[] {
            new Color(198, 108, 58),   // marrom
            new Color(255, 165, 0),    // laranja
            new Color(100, 149, 237),  // azul
            new Color(144, 238, 144),  // verde
            new Color(128, 128, 128)   // cinza
        };
        this.color = palette[(int) (Math.random() * palette.length)];
    }

    @Override
    public Projectile shoot(long currentTime, double playerY, double previousY) {
        return null;
    }

    @Override
    public void drawShape() {
        // preencher o círculo desenhando círculos concêntricos (GameLib não tem função para preencher círculo)
        GameLib.setColor(color);
        int R = (int) Math.round(this.radius);
        for (int r = R; r > 0; r--) {
            GameLib.drawCircle(this.x, this.y, r);
        }

        // detalhe de anel/iluminação
        GameLib.setColor(new Color(255, 255, 200, 200));
        double inner = Math.max(2.0, this.radius * 0.35);
        GameLib.drawCircle(this.x - this.radius * 0.15, this.y - this.radius * 0.15, inner);
    }

    @Override
    // trata explosão, movimento e reposicionamento em loop
    public void move(long delta) {
        long currentTime = System.currentTimeMillis();

        if (state == EXPLODING) {
            if (currentTime > getExplosionEnd()) {
                state = INACTIVE;
            }
            return;
        }

        if (state == ACTIVE) {
            x += getV() * Math.cos(getAngle()) * delta;
            y += getV() * Math.sin(getAngle()) * delta * (-1.0);
            rotation += 0.002 * delta;

            if (y > GameLib.HEIGHT + radius) {
                y = -radius;
            }

            if (x < -radius) {
                x = GameLib.WIDTH + radius;
            } else if (x > GameLib.WIDTH + radius) {
                x = -radius;
            }
        }
    }
}

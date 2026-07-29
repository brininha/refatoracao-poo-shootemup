import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Background {

    /* listas que guardam as posições X e Y das estrelas do fundo */
    private final List<Double> xCoordinates = new ArrayList<>();
    private final List<Double> yCoordinates = new ArrayList<>();
    private final double speed;
    private double acumulo;

    /* inicializa as estrelas com posições aleatórias */
    public Background(int countEstrelas, double speed) {
        this.speed = speed;
        for (int i = 0; i < countEstrelas; i++) {
            xCoordinates.add(Math.random() * GameLib.WIDTH);
            yCoordinates.add(Math.random() * GameLib.HEIGHT);
        }
    }

    /* atualiza o deslocamento global do fundo com base no tempo */
    public void update(long delta) {
        acumulo += speed * delta;
        if (acumulo >= GameLib.HEIGHT) {
            acumulo %= GameLib.HEIGHT;
        }
    }

    /* desenha as estrelas do fundo na tela */
    public void draw() {
        GameLib.setColor(Color.GRAY);
        for (int i = 0; i < xCoordinates.size(); i++) {
            double x = xCoordinates.get(i);
            double y = (yCoordinates.get(i) + acumulo) % GameLib.HEIGHT;
            GameLib.fillRect(x, y, 3, 3);
        }
    }
}
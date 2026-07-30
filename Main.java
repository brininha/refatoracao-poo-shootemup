import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/***********************************************************************/
/*                                                                     */
/* Para jogar:                                                         */
/*                                                                     */
/*    - cima, baixo, esquerda, direita: movimentação do player.        */
/*    - control: disparo de projéteis.                                 */
/*    - ESC: para sair do jogo.                                        */
/*                                                                     */
/***********************************************************************/

public class Main {
    
    /* Espera, sem fazer nada, até que o instante de tempo atual seja */
    /* maior ou igual ao instante especificado no parâmetro "time.    */
    public static void busyWait(long time){
        while(System.currentTimeMillis() < time) Thread.yield();
    }
    
    /* Método principal */
    public static void main(String [] args){

        /* Indica que o jogo está em execução */
        boolean running = true;

        /* variáveis usadas no controle de tempo efetuado no main loop */
        long delta;
        long currentTime = System.currentTimeMillis();

        // instanciando o jogador
        Player player = new Player(GameLib.WIDTH / 2.0, GameLib.HEIGHT * 0.90);

        // listas dinâmicas
        List<Projectile> playerProjectiles = new ArrayList<>();
        List<Enemy> enemies = new ArrayList<>();
        List<Projectile> enemyProjectiles = new ArrayList<>();

        long nextEnemy1 = currentTime + 2000;                   /* instante em que um novo inimigo 1 deve aparecer */
        long nextEnemy2 = currentTime + 7000;                   /* instante em que um novo inimigo 2 deve aparecer */
        
        double enemy2_spawnX = GameLib.WIDTH * 0.20;            /* coordenada x do próximo inimigo tipo 2 a aparecer */
        int enemy2_count = 0;                                   /* contagem de inimigos tipo 2 (usada na "formação de voo") */
        
        /* inicializações */
        Background background1 = new Background(20, 0.070);
        Background background2 = new Background(50, 0.045);
                        
        /* iniciado interface gráfica */
        GameLib.initGraphics_SAFE_MODE(); 
        
        /*************************************************************************************************/
        /*                                                                                               */
        /* Main loop do jogo                                                                             */
        /* -----------------                                                                             */
        /*                                                                                               */
        /* O main loop do jogo executa as seguintes operações:                                           */
        /*                                                                                               */
        /* 1) Verifica se há colisões e atualiza estados dos elementos conforme a necessidade.           */
        /*                                                                                               */
        /* 2) Atualiza estados dos elementos baseados no tempo que correu entre a última atualização     */
        /*    e o timestamp atual: posição e orientação, execução de disparos de projéteis, etc.         */
        /*                                                                                               */
        /* 3) Processa entrada do usuário (teclado) e atualiza estados do player conforme a necessidade. */
        /*                                                                                               */
        /* 4) Desenha a cena, a partir dos estados dos elementos.                                        */
        /*                                                                                               */
        /* 5) Espera um período de tempo (de modo que delta seja aproximadamente sempre constante).      */
        /*                                                                                               */
        /*************************************************************************************************/
        
        while(running){
        
            /* Usada para atualizar o estado dos elementos do jogo    */
            /* (player, projéteis e inimigos) "delta" indica quantos  */
            /* ms se passaram desde a última atualização.             */
            delta = System.currentTimeMillis() - currentTime;
            
            /* Já a variável "currentTime" nos dá o timestamp atual.  */
            currentTime = System.currentTimeMillis();
            
            /***************************/
            /* Verificação de colisões */
            /***************************/
                        
            if(player.getState() == Entity.ACTIVE){
                
                /* colisões player - projeteis (inimigo) */
                for(Projectile p : enemyProjectiles){
                    double dx = p.getX() - player.getX();
                    double dy = p.getY() - player.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    
                    if(dist < (player.getRadius() + p.getRadius()) * 0.8){
                        player.explode(currentTime);
                    }
                }
            
                /* colisões player - inimigos */
                for(Enemy en : enemies){
                    if(en.getState() == Entity.ACTIVE){
                        double dx = en.getX() - player.getX();
                        double dy = en.getY() - player.getY();
                        double dist = Math.sqrt(dx * dx + dy * dy);
                        
                        if(dist < (player.getRadius() + en.getRadius()) * 0.8){
                            player.explode(currentTime);
                        }
                    }
                }
            }
            
            /* colisões projeteis (player) - inimigos */
            for(Projectile p : playerProjectiles){
                if(p.getState() == Entity.ACTIVE){
                    for(Enemy en : enemies){
                        if(en.getState() == Entity.ACTIVE){
                            double dx = en.getX() - p.getX();
                            double dy = en.getY() - p.getY();
                            double dist = Math.sqrt(dx * dx + dy * dy);
                            
                            if(dist < en.getRadius()){
                                en.explode(currentTime);
                                p.setState(Entity.INACTIVE);
                            }
                        }
                    }
                }
            }
                
            /***************************/
            /* Atualizações de estados */
            /***************************/
            
            /* projeteis (player) */
            for(Projectile p : playerProjectiles) p.move(delta);
            playerProjectiles.removeIf(p -> p.getState() == Entity.INACTIVE);
            
            /* projeteis (inimigos) */
            for(Projectile p : enemyProjectiles) p.move(delta);
            enemyProjectiles.removeIf(p -> p.getState() == Entity.INACTIVE);
            
            // movimento e teclado do player
            if(player.getState() == Entity.ACTIVE) {
                player.move(delta); 
                
                if(GameLib.iskeyPressed(GameLib.KEY_CONTROL)) {
                    Projectile p = player.shoot(currentTime);
                    if (p != null) playerProjectiles.add(p);
                }
            } else if (player.getState() == Entity.EXPLODING) {
                /* Verificando se a explosão do player já acabou.         */
                /* Ao final da explosão, o player volta a ser controlável */
                if(currentTime > player.getExplosionEnd()) { 
                    player.setState(Entity.ACTIVE);
                }
            }
            
            /* inimigos (tipo 1 e tipo 2, via polimorfismo) */
            for(Enemy en : enemies){
                double previousY = en.getY();
                en.move(delta);
                
                if(en.getState() == Entity.ACTIVE){
                    Projectile p = en.shoot(currentTime, player.getY(), previousY);
                    if(p != null) enemyProjectiles.add(p);
                }
            }
            enemies.removeIf(en -> en.getState() == Entity.INACTIVE);
            
            /* verificando se novos inimigos (tipo 1) devem ser "lançados" */
            if(currentTime > nextEnemy1){
                double x = Math.random() * (GameLib.WIDTH - 20.0) + 10.0;
                double v = 0.20 + Math.random() * 0.15;
                enemies.add(new EnemyType1(x, -10.0, v, (3 * Math.PI) / 2, 0.0));
                nextEnemy1 = currentTime + 500;
            }
            
            /* verificando se novos inimigos (tipo 2) devem ser "lançados" */
            if(currentTime > nextEnemy2){
                enemies.add(new EnemyType2(enemy2_spawnX, -10.0, 0.42, (3 * Math.PI) / 2, 0.0));
                enemy2_count++;
                if(enemy2_count < 10){
                    nextEnemy2 = currentTime + 120;
                } else {
                    enemy2_count = 0;
                    enemy2_spawnX = Math.random() > 0.5 ? GameLib.WIDTH * 0.2 : GameLib.WIDTH * 0.8;
                    nextEnemy2 = (long) (currentTime + 3000 + Math.random() * 3000);
                }
            }
            
            if(GameLib.iskeyPressed(GameLib.KEY_ESCAPE)) running = false;

            /*******************/
            /* Desenho da cena */
            /*******************/
            
            /* desenhando plano fundo próximo e distante */
            background1.update(delta);
            background1.draw();
            background2.update(delta);
            background2.draw();
                        
            /* desenhando player */
            player.draw(currentTime);
            
            /* deenhando projeteis (player) */
            for(Projectile p : playerProjectiles) p.draw(currentTime);
            
            /* desenhando projeteis (inimigos) */
            for(Projectile p : enemyProjectiles) p.draw(currentTime);
            
            /* desenhando inimigos (tipo 1 e tipo 2, via polimorfismo) */
            for(Enemy en : enemies) en.draw(currentTime);
            
            /* chamada a display() da classe GameLib atualiza o desenho exibido pela interface do jogo. */
            GameLib.display();
            
            /* faz uma pausa de modo que cada execução do laço do main loop demore aproximadamente 3 ms. */
            busyWait(currentTime + 3);
        }
        
        System.exit(0);
    }
}
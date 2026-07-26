# Projeto de Computação Orientada a Objetos

Projeto da disciplina de Computação Orientada a Objetos da graduação. Refatoração de um jogo procedural estilo shoot 'em up para o paradigma de orientação a objetos.

## Integrantes
Manuela Campos de Amorim, Mariana Medeiros Santos, Mikelly Dantas Reis e Sabrina Cristan.

## Visão geral do projeto

O relatório deste projeto apresenta a refatoração arquitetural de um jogo clássico no estilo vertical shoot 'em up. Na sua versão original, a aplicação funcionava de forma procedural, rodando indefinidamente em um loop básico de execução. Nosso desafio e objetivo principal foi elevar a qualidade do código, transformando uma estrutura de variáveis soltas em uma organização robusta de objetos bem encapsulados.  

Para tornar o projeto mais personalizado, ele foi recriado em um universo de culinária, onde pratos e ingredientes se tornam os obstáculos. Todo o redesenho de classes e heranças foi focado na classe de execução Main, respeitando a diretriz do projeto de tratar a biblioteca gráfica interna, GameLib, como um módulo ao qual não tínhamos acesso para modificação.

## Como executar o projeto

### Pré-requisitos:

- Java Development Kit (JDK) instalado na máquina.

### Passo a passo:

1. Abra o terminal e navegue até o diretório raiz do projeto.

2. Compile todos os arquivos Java utilizando o comando:

```bash
javac *.java
```

3. Inicie o jogo executando a classe principal:

```bash
java Main
```

### Controles:

- Setas direcionais: movimentação do jogador.

- Tecla CTRL: disparo de projéteis.

- Tecla ESC: encerrar o jogo.

## Problemas identificados no código anterior

### Uso de arrays paralelos

Para gerenciar as entidades que aparecem em multiplicidade, o sistema original utiliza arrays de tamanho fixo. Para representar os atributos de um único inimigo do tipo 1, por exemplo, o programa declara nove arrays distintos. Essa abordagem prejudica a coesão dos dados, pois as informações pertencentes a uma mesma entidade ficam fragmentadas em várias estruturas diferentes.  

Exemplo de variáveis fragmentadas no código original:

```java
int[] enemy1_states = new int[10]; // estados
double[] enemy1_X = new double[10]; // coordenadas x
double[] enemy1_Y = new double[10]; // coordenadas y
double[] enemy1_V = new double[10]; // velocidades
```

### Falta de encapsulamento

Devido à ausência de classes estruturadas e de modificadores de acesso, os dados não estão protegidos. Consequentemente, qualquer parte da aplicação pode acessar e alterar os atributos das entidades diretamente, comprometendo a integridade do sistema.  

### Redundância de código

A lógica do jogo apresenta alta duplicação, como o cálculo de detecção de colisões, que é reescrito para diferentes entidades. Essa redundância dificulta a escalabilidade, pois, caso um novo elemento seja adicionado (como o terceiro inimigo), as desenvolvedoras seriam forçadas a copiar e colar a mesma fórmula matemática. Além disso, se a regra de uma funcionalidade sofrer alterações, o ajuste precisará ser replicado em diversos pontos do arquivo, aumentando o risco de bugs.

### Concentração de toda a lógica no método Main

Quase todas as operações do jogo estão centralizadas no método Main. Essa concentração excessiva dificulta a leitura, a testabilidade e a manutenção do software, uma vez que uma alteração simples pode gerar falhas no sistema inteiro. Essa arquitetura também impede o reaproveitamento de rotinas já implementadas, violando os princípios de um bom design orientado a objetos.

## Melhorias implementadas

### Diretrizes para a reestruturação do código

Para solucionar os problemas da versão procedural, a nova arquitetura foi baseada nas seguintes premissas:  

### Modelagem de entidades

Transformar os elementos do jogo em classes independentes.

### Proteção de dados

Aplicar o encapsulamento para garantir que nenhuma classe externa altere as coordenadas ou estados das entidades diretamente.  

### Reaproveitamento de código

Como as entidades do jogo compartilham atributos semelhantes (estado, coordenadas X e Y, velocidade, raio), utilizamos herança para extrair essas redundâncias para uma classe genérica, especializando os comportamentos nas subclasses.  

### Flexibilidade

Aplicação de polimorfismo e interfaces para padronizar ações comuns, como desenhar na tela e atualizar posições, permitindo que o loop principal trate todos os objetos de forma unificada.

## Mapeamento de classes

Para implementar o paradigma da Computação Orientada a Objetos, o primeiro passo foi o mapeamento dos componentes do código original. A tabela abaixo ilustra a organização inicial das responsabilidades:

| Classes identificadas          | Atributos                                                                                                                                                                                                  | Métodos                              |
|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| Jogador                        | Estado, coordenada X, coordenada Y, velocidade no eixo X, velocidade no eixo Y, raio do jogador, instante do início da explosão, instante do final da explosão, instante em que pode haver um próximo tiro | Atirar, mover-se, explodir, desenhar |
| Inimigo (superclasse abstrata) | Estado, coordenada X, coordenada Y, velocidade, ângulo, velocidade de rotação, instante do início da explosão, instante do final da explosão, instante em que pode haver um próximo tiro, raio do inimigo  | Mover-se, desenhar, explodir         |
| Inimigo tipo 1 (subclasse)     | Instante do próximo tiro                                                                                                                                                                                   | Atirar                               |
| Inimigo tipo 2 (subclasse)     | Instante do próximo tiro                                                                                                                                                                                   | Atirar                               |
| Projétil (superclasse)         | Estado, coordenada X, coordenada Y, velocidade no eixo X, velocidade no eixo Y, raio                                                                                                                       | Desenhar, mover                      |
| Fundo                          | Lista de coordenadas X, lista de coordenadas Y, velocidade, contagem                                                                                                                                       | Desenhar, atualizar                  |
| Colisão                        | Sem atributos, classe utilitária                                                                                                                                                                           | Detectar colisão                     |

Depois desse mapeamento, foi construída a nova estrutura orientada a objetos. O diagrama de classes a seguir ilustra a arquitetura adotada:

![diagrama de classes](diagrama_classes.png)

### Ações que ainda ficam na classe Main

- O loop principal.
- Captura de entradas do teclado.  
- Orquestração das classes.  

### Análise das decisões tomadas

Ao invés de declararmos arrays paralelos repetitivos para gerenciar posições e velocidades, a classe Entity centraliza os atributos x, y, speed e state. As classes Player, Projectile e Enemy herdam essas características. Da mesma forma, criamos uma segunda camada de abstração com a superclasse Enemy, que fornece atributos de rotação para todos os inimigos. Isso reduziu a repetição de código.  

A visibilidade dos dados foi controlada. Atributos que precisam ser acessados pelas subclasses, como coordenadas, foram definidos como protected, enquanto variáveis exclusivas, como os cronômetros de tiro, foram marcadas como private. Dessa forma, nenhuma classe externa, incluindo a Main, pode alterar o estado de uma entidade sem passar por seus métodos oficiais.  

O diagrama destaca o uso de polimorfismo através do método abstrato shoot() na classe mãe Enemy. Embora todos os inimigos saibam atirar, cada um o faz de maneira distinta: o EnemyType1 utiliza um gatilho baseado em tempo, enquanto o EnemyType2 baseia-se em sua posição geométrica. O loop principal apenas chama enemy.shoot(), e a linguagem Java garante a execução do comportamento correto em tempo de execução.  

Uma prova da eficiência dessa nova arquitetura foi a implementação do novo obstáculo exigido para o projeto: o EnemyType3. No código procedural original, adicionar esse inimigo exigiria a criação de vários arrays novos e a duplicação manual de fórmulas matemáticas. Com a estrutura orientada a objetos, bastou criar uma nova classe EnemyType3 estendendo a superclasse Enemy, ganhando instantaneamente todas as suas características e ações, restando às desenvolvedoras programar a sua regra específica de tiro e comportamento.  

## Funções de cada arquivo
Com a separação das lógicas, o sistema foi modularizado nos seguintes arquivos:

- **Main.java**: Orquestra o jogo. Inicializa as coleções de objetos, gerencia o laço de repetição contínuo e capta os comandos de teclado, repassando as ações para os objetos correspondentes.

- **GameLib.java**: Biblioteca gráfica de terceiros. Trata do desenho geométrico na tela e da interface, sem alterações em sua estrutura.

- **Entity.java**: Superclasse abstrata que fornece coordenadas e controles básicos de estado e física para todos os elementos visuais móveis do jogo.

- **Player.java**: Especialização da Entidade que contém a lógica de controle do usuário e regras para disparar projéteis aliados.

- **Enemy.java**: Superclasse abstrata que adiciona lógicas de movimentação angular e velocidades de rotação comuns aos adversários.

- **EnemyType1.java**, **EnemyType2.java** e **EnemyType3.java**: Especializações de adversários, cada um contendo seu próprio critério (temporizador, posição na tela ou mecânica nova) para realizar disparos.

- **Projectile.java**: Classe responsável pela movimentação unidirecional e renderização dos tiros (tanto do jogador quanto dos inimigos).

- **Background.java**: Classe de suporte visual que cuida do cálculo do efeito de deslocamento do cenário (estrelas ao fundo) de maneira autônoma.

- **Collision.java**: Classe puramente utilitária que concentra a fórmula matemática estática para validar sobreposições entre quaisquer duas entidades geométricas.

## Repositório
O histórico de commits e o código-fonte completo desta refatoração podem ser consultados no link abaixo:

Link do repositório no GitHub: [Insira a URL do seu repositório aqui]
# Chess

### Implementação do jogo Xadrez, utilizando a linguagem Java, com os princípios de OO, e Swing, para interface gráfica.

<div>
<a href="https://ibb.co/8snYj32"><img src="https://i.ibb.co/4tw1jyM/Captura-de-tela-2024-10-23-100719.png" alt="Captura-de-tela-2024-10-23-100719" border="0" height="400" width="400"/></a>
</div>

## Como jogar?

Para rodar o jogo, basta executar arquivo "Chess.jar" que a partida de xadrez começa. Conforme as regras do xadrez, o branco começa, então o jogador pode selecionar uma peça e fazer a primeira jogada, então a vez é das pretas, onde outro jogador irá realizar o segundo lance, e por aí vai, até o cheque-mate ou o empate.

Obs: O arquivo .jar foi compilado na versão 21, caso difira da sua versão, as classes deverão ser recompiladas.

## Limitações

1. O jogo não possui uma inteligência artificial para combater o jogador, implicando em não haver um modo single-player.
2. De todas as jogadas especiais (promoção, roque e en-passant), esse jogo não implementa o movimento "en-passant".
3. O jogo não marca pontuação.
4. O jogo não salva o progresso de uma partida, para dar continuidade depois.

### Como recompilar as classes

Para compilar o projeto para para a versão do seu computador, faça:

``` 
cd src
javac $(find . -name "*.java")
```

Pronto, agora basta executar a classe principal:

```
java br.com.erick.chess.vision.GameWindow
```

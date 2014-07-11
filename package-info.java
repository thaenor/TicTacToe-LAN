/**
 * Documentação técnica da user storie 087: Jogo do galo.
 * <br/>
 * <br/>
 *
 * <b>Requisitos</b><br/>
 * Fazer o jogo do galo entre 2 instâncias do cleansheets.
 * <br/>
 * <br/>
 *
 * <b>S086a: Análise</b><br/>
 * O jogo do galo (em Inglês Tic Tac Toe), é um jogo simples, jogado entre 2
 * pessoas <br/>
 * Um "tabuleiro" é composto por uma matrix de 3x3. Em cada turno um jogador
 * atribui um "x" enquanto o oponente atribui um "o" a uma celula
 * especifica.<br/>
 * O jogo termina quando um jogador conseguir fazer uma combinacao em linha
 * (horizontal, vertical ou diagonal). <br/>
 * O jogo empata quando forem jogados 9 turnos sem um vencedor. <br/>
 * Para que uma instancia do jogo seja criada sem interromper o trabalho do
 * utilizador no cleansheets devem ser usadas threads para multi
 * processamento<br/>
 * Deve ser establecida uma ligação tcp (através de sockets de berkeley). A
 * ligação manter-se-a até o empate ou vitoria de um dos jogadores.
 * <br/>
 * <br/>
 *
 * <b>S086d: Design</b><br/>
 * Os dois jogadores serão divididos como cliente e servidor. O cliente  envia a
 * informacao do indice da "casa" onde o utlizador carregou. <br/>
 * O cliente, após receber esta informacao e actualizar a janela em conformidade,
 * fará a sua jogada.
 * Uma vez preenchido o tabuleiro (turnos &lt; 10) a
 * conexao e fechada e ambas as maquinas/instancias decalram o empate.
 * Uma matrix mantém uma sequencia com as jogadas vencedores (por exemplo de um 
 * jogador) que joga "1,2,3" ou seja uma fila na parte superior do tabuleiro.
 * Em caso de empate ou vitória por parte de um dos jogadores a comunicação é encerrada
 * e o vencedor declarado.
 * <br/>
 * É utilizado componentes JFrame, sendo a matriz composta graficamente por
 * botoes cuja acçao envia dados para a outra maquina/instancia.
 * A camada de comunicação é implementada com base na já existente (us085, us086).
 * Uma classe controladora vai mediar a comunicação entre esta camada e a ui.
 * O seguinte gráfico demonstra um exemplo da comunicação entre dois jogadores.
 * A classe controlador impoe as regras do jogo, definindo quando este termina.
 * <img src="../../../csheets/userstories/us087/doc-files/us087_design.png">
 *
 * Antes de se inciar o jogo é necessário determinar qual o jogador que realiza
 * a primeira jogada. Para tal, servidor e cliente geram um numbero aleatório o
 * qual é comunicado. Cada um compara o seu número e podem assim iniciar o jogo
 * como descrito na imagem anterior
 * NOTA: esta ideia foi abandonada dado a problemas de funcionalidade.
 * O servidor é sempre o primeiro a iniciar a jogada.
 * 
 * Os gráficos seguintes pretendem ilustrar o funcionamento das classes implementadas.
 * <br />
 * <img
 * src="../../../csheets/userstories/us087/doc-files/us087_designServerCom.png">
 * <img
 * src="../../../csheets/userstories/us087/doc-files/us087_designClientCom.png">
 * <br/>
 * <br/>
 *
 * <b>S086c: Codificar</b><br/>
 * ver:<br/>
 * <a href="../../../csheets/ext/jogo/Clientes">csheets.ext.jogo.Clientes.java</a><br/>
 * <a href="../../../csheets/ext/jogo/Servidors">csheets.ext.jogo.Servidors.java</a><br/>
 * <a href="../../../csheets/ext/jogo/GameController">csheets.ext.jogo.GameController.java</a><br/>
 * <a href="../../../csheets/ext/jogo/package-info">csheets.ext.jogo.package-info.java</a><br/>
 * <a href="../../../csheets/ext/jogo/ui/GaloMenus">csheets.ext.jogo.ui.GaloMenus.java</a><br/>
 * <a href="../../../csheets/ext/jogo/ui/UIGaloJogoCliente">csheets.ext.jogo.ui.UIGaloJogoCliente.java</a><br/>
 * <a href="../../../csheets/ext/jogo/ui/UIGaloJogoServer">csheets.ext.jogo.ui.UIGaloJogoServer.java</a><br/>
 * <a href="../../../csheets/ext/jogo/ui/package-info">csheets.ext.jogo.ui.package-info.java</a><br/>
 * <br/>
 * <br/>
 *
 * <b>S086u: Testes unitários</b><br/>
 * ver:<br/>
 * <br/>
 * <br/>
 *
 * <b>S086f: Testes funcionais</b><br/>
 * Para testar o programa, siga os seguintes passsos:
 * 1- Abrir o menu "jogo" na barra "extensions".
 * 2- selecionar "Jogo do Galo"
 * 3- Preencher o nome e selecionar "Pesquisar"
 * 4- selecionar uma pasta do directório de imagens (partindo do directorio do 
 * projecto) "src\\csheets\\ext\\jogo\\Imagem".
 * 5- Repetir os passos até 4 para o cliente.
 * 
 * Servidor:
 * 1s- selecionar o botão "ligar".
 * 2s- esperar ligação de um cliente
 * 3s- a janela automaticamente sera actulizada e uma janela com 9 botões aparece
 * 4s- selecionar "casa" onde pretende efectuar a jogada.
 * 5s- esperar que o cliente efectue a jogada. O tabuleiro actualiza automaticamente
 * NOTA: a janela pode não aparecer correctamente, caso aconteça, espere que o cliente
 * efectue a jogada.
 * 
 * Cliente:
 * 1c- selecionar o botao "ligar-se".
 * 2c- a janela automaticamente sera actulizada e uma janela com 9 botões aparece
 * NOTA: a janela pode não aparecer correctamente, caso aconteça, espere que o servidor
 * efectue a jogada.
 * 3c- selecionar "casa" onde pretende efectuar a jogada.
 * <br/>
 * <br/>
 * o jogo prossegue até um jogador vencer ou empate.
 * 
 * <br/>
 * <br/>
 *
 * @author FranciscoSantos(1111315)
 */

/*
@startuml
title Exemplo de jogo do galo entre duas instancias
autonumber
actor Cliente
actor Servidor
Cliente -> Controller
Controller -> Servidor: play(int index_tile ) turnos=1
...
Servidor -[#0000FF]-> Controller: play(int index_tile ) turnos=2
Controller -> Cliente
...
Cliente -> Controller: play(int index_tile ) turnos=3
Controller -> Servidor
...
Cliente <-[#0000FF]- Controller: play(int index_tile ) turnos=4
alt cliente vence
end
Controller -> Servidor: play(int index_tile ) turnos=5
alt servidor vence
end
Controller -> Cliente
...
Cliente <-[#0000FF]- Controller: play(int index_tile ) turnos=6
Controller -> Servidor
...
Cliente -> Controller: play(int index_tile ) turnos=7
Controller -> Servidor
...
Cliente <-[#0000FF]- Controller: play(int index_tile ) turnos=8
Controller -> Servidor
...
Cliente -> Controller: play(int index_tile ) turnos=9

alt ambas instancias declaram o empate
end
Controller -> Servidor :close
Cliente -> Servidor :close
@enduml
 */


/*
@startuml doc-files/us087_designServerCom
title ligacao inicial entre duas instancias (ponto de vista do servidor)
JogosAction -> GaloMenus: seleção de jogo
GaloMenus -> GameController: cria servidor
GameController -> Servidors: botão "ligar"
Servidors -> Socket_Server: aguarda ligação
...
Socket_Client --> Socket_Server: envia nome, imagem, jogo e numero aleatório

Socket_Server [#0000FF]-> Servidors:
alt decide quem começa o jogo.
end
@enduml
 */


/*
@startuml doc-files/us087_designClientCom.png
title ligacao inicial entre duas instancias (ponto de vista do cliente)
JogosAction -> GaloMenus: seleção de jogo
GaloMenus -> GameController: cria cliente
GameController -> Cliente: botão "ligar-se"
Cliente -> Socket_Cliente: envia nome, imagem, jogo e numero aleatório
...
Socket_Server [#0000FF]-> Socket_Cliente: envia nome, imagem, jogo e numero aleatório

Socket_Cliente -> Cliente: compara numero aleatorio do servidor com o próprio
alt decide quem começa o jogo.
end
@enduml
 */
package csheets.userstories.us087;

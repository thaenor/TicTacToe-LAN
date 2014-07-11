
package csheets.ext.jogo;

import csheets.ext.jogos.ui.UIGaloJogoServer;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto, Francisco Santos(1111315)
 * esta classe servidor, o construtor da classe recebe 3 strings uma com o nome do jogador que iniciar o jogo, a outra e o caminho da imagem que este escolher, e no fim é o
 * jogo que ele escolheu.
 * nesta classe alem de enviar os dados que o jogador colocar ao iniciar a aplicacao tambem recebe os dados do utilizador que se liga a este servidor
 */
public class servidors {
    //dados do cliente que se ligar a este servidor
    private String NOME, IMAGEM, JOGO, TEMA, PALAVRACHAVE;
    private static int PORT_JENFORCADO = 12345,
                       PORT_JGALO = 12346;
    //socket servidor
    private ServerSocket servidor;
    private Socket CLIENTE;
    
    Scanner sjodaga;
    Scanner tturno;
    PrintStream saida;
    Scanner s;
    int turn = 0;
    
    
    public servidors(String Nome,String ima,String jogo, String tema, String palavraChave) throws IOException{
        
        //iniciar o socket com a porta 12345
        servidor = new ServerSocket(PORT_JENFORCADO);
     System.out.println("Porta "+PORT_JENFORCADO+" aberta!");
     
     
     //establece ligacao
     Socket cliente = servidor.accept();
     System.out.println("Nova conexão com o cliente " +   
       cliente.getInetAddress().getHostAddress()
     );
     
     
     //envia dados para o cliente
     Scanner teclado = new Scanner(Nome);
     Scanner pic = new Scanner(ima);
     Scanner j=new Scanner(jogo);
     Scanner temaScanner = new Scanner(tema);
     Scanner palavraChaveScanner = new Scanner(palavraChave);
     
     PrintStream saida = new PrintStream(cliente.getOutputStream());
     saida.println(teclado.nextLine());
     saida.println(pic.nextLine());
     saida.println(j.nextLine());
     saida.println(temaScanner.nextLine());
     saida.println(palavraChaveScanner.nextLine());

     
     
     //recebe dados do cliente
     Scanner s = new Scanner(cliente.getInputStream());
        NOME=s.nextLine();
        IMAGEM=s.nextLine();
        JOGO=s.nextLine();
     
     
        
        //fecho de todas as portas utilizadas para a comunicação
     s.close();
     servidor.close();
     cliente.close();
     
     saida.close();
     teclado.close();
     j.close();
     temaScanner.close();
     palavraChaveScanner.close();
    }
    
    
    public servidors(String Nome,String ima,String jogo) throws IOException{
      
  
     Random rng = new Random();
     int myRandomint = rng.nextInt();
     String myRandom = String.valueOf(myRandomint);
     String otherRandom;

        
     //iniciar o socket com a porta 12345
     servidor = new ServerSocket(PORT_JGALO);
     System.out.println("Porta "+PORT_JGALO+" aberta!");
     
     
     //establece ligacao
     CLIENTE = servidor.accept();
     System.out.println("Nova conexão com o cliente " +   
       CLIENTE.getInetAddress().getHostAddress()
     );
     
     
     //envia dados para o cliente
     Scanner teclado = new Scanner(Nome);
     Scanner pic = new Scanner(ima);
     Scanner j=new Scanner(jogo);
     Scanner randomScanner = new Scanner(myRandom);
     
     PrintStream saida = new PrintStream(CLIENTE.getOutputStream());
     saida.println(teclado.nextLine());
     saida.println(pic.nextLine());
     saida.println(j.nextLine());
     saida.println(randomScanner.nextLine());
     
     
     //recebe dados do cliente
     Scanner s = new Scanner(CLIENTE.getInputStream());
        NOME=s.nextLine();
        IMAGEM=s.nextLine();
        JOGO=s.nextLine();
        
        otherRandom = s.nextLine();
        
        //como o socket comunica por strings e necessario converter para inteiro 
        int converted_otherRandom = Integer.parseInt(otherRandom);
        //isto de momento nao faz nada de util

     //fecho de todas as portas utilizadas para a comunicação
//     s.close();
//     servidor.close();
//     CLIENTE.close();
     
//     saida.close();
     teclado.close();
     j.close();
    }
    
    
    public void fazerjogada(int move) throws IOException
    {
        
        String jogada = Integer.toString(move);
        String sturn = Integer.toString(turn);
        //envia dados para o cliente
        sjodaga = new Scanner(jogada);
        tturno = new Scanner(sturn);

        saida = new PrintStream(CLIENTE.getOutputStream());
        saida.println(sjodaga.nextLine());
        saida.println(tturno.nextLine());
        turn++;  
        System.out.println("servidor diz: O servidor jogou "+jogada+"turno: "+turn);
    }
    
    public int receberjogada () throws IOException
    {
        int jogada;
        
        //recebe dados do cliente
        s = new Scanner(CLIENTE.getInputStream());
        String sjogada = s.nextLine();
        String sturn = s.nextLine();
        jogada = Integer.parseInt(sjogada);
        int turn2 = Integer.parseInt(sturn);
        if(turn2 != turn){System.out.println("erro, turnos não batem certo");}
        System.out.println("servidor diz: O servidor jogou "+jogada+"turno: "+turn);
        return jogada;
    }
    
    public int getTurn(){
        return turn;
    }
    
    
    public String getNome(){return NOME;}
    public String getImagem(){return IMAGEM;}
    public String getJogo(){return JOGO;}
    
    //serve para ver se o servidor ja se encontra activo
    public boolean ServidorActivo()
    {
        if(!servidor.isClosed())
            return true;
        return false;
    }
    
    public void CloseConnection() throws IOException
    {
     //s.close();
     servidor.close();
     CLIENTE.close();
     //saida.close();
    }
}


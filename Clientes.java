package csheets.ext.jogo;

import csheets.ext.jogos.ui.UIGaloJogoServer;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alberto, António Silva(1111305), Francisco Santos(1111315)
 *
 * classe cliente recebe 3 parametros(strings) a 1 com o nome do utilizador que
 * se vai ligar ao servidor, o caminho da imagem e o jogo que escolheu. esta
 * classe alem de eviar os dados, tambem recebe os mesmos do servidor.
 */
public class Clientes {

    //dados do servidor
    private String NOME, IMAGEM, JOGO, TEMA, PALAVRACHAVE;
    private static int PORT_JENFORCADO = 12345,
                       PORT_JGALO = 12346;
    private static Scanner teclado, pic, j, s;
    private static PrintStream saida;
    private static Socket cliente;
    private Socket CLIENTE_GALO;
    
    private Scanner sjodaga;
    private Scanner tturno;
    private int turn = 0;


    public Clientes(String nome, String ima, String jogo) throws IOException {
        //cria o socket cliente com a mesma porta do servidor e um ip
        Socket cliente = new Socket("127.0.0.1", PORT_JENFORCADO);
        System.out.println("O cliente se conectou ao servidor!");

        //envia dados para o servidor
        Scanner teclado = new Scanner(nome);
        Scanner pic = new Scanner(ima);
        j = new Scanner(jogo);
        

        PrintStream saida = new PrintStream(cliente.getOutputStream());

        saida.println(teclado.nextLine());
        saida.println(pic.nextLine());
        saida.println(j.nextLine());

        //recebe dados do servidor
        Scanner s = new Scanner(cliente.getInputStream());

        NOME = s.nextLine();
        IMAGEM = s.nextLine();
        JOGO = s.nextLine();
        TEMA = s.nextLine();
        PALAVRACHAVE = s.nextLine();

        //fecha todas as comunicações abertas pelo cliente
//        saida.close();
//        teclado.close();
//        cliente.close();
//        pic.close();
//        s.close();
//        j.close();
    }
    
    public Clientes(String nome, String ima, String jogo, String myRandom) throws IOException {
        
        Random rng = new Random();
        int myRandomint = rng.nextInt();
        myRandom = String.valueOf(myRandomint);
        String otherRandom;

        //cria o socket cliente com a mesma porta do servidor e um ip
        CLIENTE_GALO = new Socket("127.0.0.1", PORT_JGALO);
        System.out.println("O cliente se conectou ao servidor!");

        //envia dados para o servidor
        Scanner teclado = new Scanner(nome);
        Scanner pic = new Scanner(ima);
        j = new Scanner(jogo);
        Scanner randomScanner = new Scanner(myRandom);
        

        PrintStream saida = new PrintStream(CLIENTE_GALO.getOutputStream());

        saida.println(teclado.nextLine());
        saida.println(pic.nextLine());
        saida.println(j.nextLine());
        saida.println(randomScanner.nextLine());

        //recebe dados do servidor
        Scanner s = new Scanner(CLIENTE_GALO.getInputStream());

        NOME = s.nextLine();
        IMAGEM = s.nextLine();
        JOGO = s.nextLine();
        otherRandom = s.nextLine();

  
        //como o socket comunica por strings e necessario converter para inteiro
        int converted_otherRandom = Integer.parseInt(otherRandom);
        
        
        //fecha todas as comunicações abertas pelo cliente
//        saida.close();
//        teclado.close();
//      CLIENTE_GALO.close();
//        pic.close();
//        s.close();
//        j.close();
    }
    
    
    public void fazerjogada(int move) throws IOException
    {

        String jogada = Integer.toString(move);
        String sturn = Integer.toString(turn);
        //envia dados para o servidor
        sjodaga = new Scanner(jogada);
        tturno = new Scanner(sturn);

        saida = new PrintStream(CLIENTE_GALO.getOutputStream());
        saida.println(sjodaga.nextLine());
        saida.println(tturno.nextLine());
        turn++;
        System.out.println("cliente diz: O servidor jogou "+jogada+"turno: "+turn);
    }
    
    public int receberjogada () throws IOException
    {
        int jogada;

        //recebe dados do servidor
        s = new Scanner(CLIENTE_GALO.getInputStream());
        String sjogada = s.nextLine();
        String sturn = s.nextLine();
        jogada = Integer.parseInt(sjogada);
        int turn2 = Integer.parseInt(sturn);
        if(turn2 != turn){System.out.println("erro, turnos não batem certo");}
        System.out.println("cliente diz: O servidor jogou "+jogada+"turno: "+turn);
        return jogada;
    }
    
    
    

    public static void EnviaDados(String dados) {
        Scanner enviaDados = new Scanner(dados);
        enviaDados.close();
    }

    /*fecha todas as comunicações abertas pelo cliente */
    public static void close() throws IOException {
        cliente.close();
    }
    
    public void CloseConnection()
    {
        try {
//            saida.close();
//            teclado.close();
//            pic.close();
//            s.close();
//            j.close();
            CLIENTE_GALO.close();
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getTurn(){
        return turn;
    }
    
    //metodos que retorna os dados do servidor
    public String getNome() {
        return NOME;
    }

    public String getImagem() {
        return IMAGEM;
    }

    public String getJogo() {
        return JOGO;
    }

    public String getTema() {
        return TEMA;
    }

    public String getPalavraChave() {
        return PALAVRACHAVE;
    }
}

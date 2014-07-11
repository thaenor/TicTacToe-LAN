package csheets.ext.jogo;

import csheets.ext.jogos.ui.UIGaloJogoCliente;
import csheets.ext.jogos.ui.UIGaloJogoServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * A controller class to regulate the tic tac toe (Jogo do galo) game.
 * 
 * this is a controller class that contains a server, a cliente and their respective
 * ui's. It regulates the comunication and determines the game rules.
 * 
 * @author francisco
 */
public class GameController {

    private servidors serv;
    private Clientes cli;
    private UIGaloJogoServer uiServer = new UIGaloJogoServer(this);
    private UIGaloJogoCliente uiClient = new UIGaloJogoCliente(this);
    private int[] jogadas_servidor = new int[9];
    private int[] jogadas_cliente = new int[9];
    private int turns = 1;
  final int winCombo[][] = new int[][]{
    {1, 2, 3}, {1, 4, 7}, {1, 5, 9},
    {4, 5, 6}, {2, 5, 8}, {3, 5, 7},
    {7, 8, 9}, {3, 6, 9}
    /*Horizontal Wins*/ /*Vertical Wins*/ /*Diagonal Wins*/
  };
    
    /**
     * Controller creates and returns a server.
     * 
     * This method creates, and returns, a server. This allows the controller to
     * maintain the object for later use in comunication with the ui.
     * this a common programming pattern, controller.
     * 
     * @param nome
     * @param ima
     * @param jogo
     * @return
     * @throws IOException 
     */
    public servidors CreateServer(String nome, String ima, String jogo) throws IOException {
        serv = new servidors(nome, ima, jogo);
        return serv;
    }

    
    /**
     * Controller creates and returns a client.
     * 
     * This method creates, and returns, a client. This allows the controller to
     * maintain the object for later use in comunication with the ui.
     * this a common programming pattern, controller.
     * 
     * @param nome
     * @param ima
     * @param jogo
     * @param myRandom
     * @return
     * @throws IOException 
     */
    public Clientes CreateClients(String nome, String ima, String jogo, String myRandom) throws IOException {
        cli = new Clientes(nome, ima, jogo, myRandom);
        return cli;
    }

    
    /**
     * This methods starts the game by showing the server game board.
     * 
     * This method calls ui to design itself and show on screen.
     * Although the boards are essentially the same, a issue with method calls 
     * for communicate lead it so that the ui's would be kept separately.
     */
    public void IniciaJogoServidor() {
        uiServer.CreateTable();
    }

    
    /**
     * This methods starts the game by showing the client game board.
     * 
     * This method calls ui to design itself and show on screen.
     * Although the boards are essentially the same, a issue with method calls 
     * for communicate lead it so that the ui's would be kept separately.
     * 
     * Since the server always plays first, the client needs to start by 
     * "listening" to the socket and wait a move from the server.
     */
    public void IniciaJogoCliente() {
        uiClient.CreateTable();
        try {
            int n = cli.receberjogada();
            uiClient.setCasa(n);
            uiClient.showGame();
        } catch (IOException ex) { Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);  }
    }

    
    
    /**
     * Makes the move choosen by the server user.
     * 
     * This method is called upon by the ui, by the click of a button within
     * the tic tac toe board. The method calls the server class to send a number,
     * the index of the tile selected by the server.
     * It also keeps track of the turns that have been played. Once 9 turns have
     * passed, the board is expected to be full, thus a tie is declared.
     * 
     * @param jogada 
     */
    public void Server_move(int jogada) {
        try {
            turns++;
            System.out.println("turns "+turns);
            serv.fazerjogada(jogada);
            //int n = serv.getTurn();
            if(turns > 9)
            {    empata(); }
            jogadas_servidor[turns] = jogada;
            chckWinner();
            
            int jogaga_oponente;
            turns++;
            jogaga_oponente = serv.receberjogada();
            uiServer.setCasa(jogaga_oponente);
            uiServer.showGame();
        } catch (IOException ex) { Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex); }
    }

    
    
    /**
     * Makes the move chosen by the client user.
     * 
     * This method is called upon by the ui, by the click of a button within
     * the tic tac toe board. The method calls the client class to send a number,
     * the index of the tile selected by the server.
     * It also keeps track of the turns that have been played. Once 9 turns have
     * passed, the board is expected to be full, thus a tie is declared.
     * 
     * @param jogada 
     */
    public void Client_move(int jogada) {
        try {
            turns++;
            System.out.println("turns "+turns);
            cli.fazerjogada(jogada);
            //int n = cli.getTurn();
            if(turns > 9)
            {    empata(); }
            jogadas_cliente[turns] = jogada;
            chckWinner();
            
            int jogaga_server;
            turns++;
            jogaga_server = cli.receberjogada();
            uiClient.setCasa(jogaga_server);
            uiClient.showGame();
        } catch (IOException ex) { Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex); }
    }

    
    /**
     * Method that declares a tie.
     * 
     * Once the board has been filled and this method is called, it closes all
     * sockets and scanners being used by both client and server.
     * Shows a message declaring a tie.
     */
    public void empata()
    {
        uiClient.clearboard();
        uiServer.clearboard();
        JOptionPane.showMessageDialog(null, "it's a tie! - é um empate!");
    }
    
    
    /**
     * Method that confirms and declares the winner.
     * 
     * The way this method works. There is a matrix containing all the winning
     * possibilities, e.g. filling the tiles 1,2 and 3 means filling the top row
     * in the board. What this method does is compare those winning combinations
     * with the tiles chosen by the player.
     * Once declared a winner through this method, the connection is closed and 
     * a message congratulates the winner (or accuses him of cheating).
     * 
     */
    public void chckWinner()
    {
        for (int i = 0; i < jogadas_cliente.length - 1; i++) {
            if (jogadas_cliente[i] > jogadas_cliente[i + 1]) {
                int aux = jogadas_cliente[i];
                jogadas_cliente[i] = jogadas_cliente[i + 1];
                jogadas_cliente[i + 1] = aux;
            }
        }
        
        for (int i = 0; i < jogadas_servidor.length - 1; i++) {
            if (jogadas_servidor[i] > jogadas_servidor[i + 1]) {
                int aux = jogadas_servidor[i];
                jogadas_servidor[i] = jogadas_servidor[i + 1];
                jogadas_servidor[i + 1] = aux;
            }
        }
        
        
        for (int i = 0; i < 7; i++) {
            if(jogadas_cliente[i] == winCombo[i][0]
                    && jogadas_cliente[i] == winCombo[i][1]
                    && jogadas_cliente[i] == winCombo[i][2])
            {
                uiClient.clearboard();
                uiServer.clearboard();
                JOptionPane.showMessageDialog(null, "Client wins! GO YOU!");
            }
        }
        
        //repeat for server
        for (int i = 0; i < 7; i++) {
            if(jogadas_servidor[i] == winCombo[i][0]
                    && jogadas_servidor[i] == winCombo[i][1]
                    && jogadas_servidor[i] == winCombo[i][2])
            {
                uiClient.clearboard();
                uiServer.clearboard();
            JOptionPane.showMessageDialog(null, "Server wins! CONGRATULATIONS!");
            }
        }
        
    }
}

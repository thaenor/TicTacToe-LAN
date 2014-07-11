
package csheets.ext.jogos.ui;

import csheets.ext.jogo.GameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* Classe UI para o jogo do Galo.
* Esta classe instancia constructores e metodos para a interface gráfica do jogo
*
* @author francisco
*/
public class UIGaloJogoCliente implements ActionListener {

  JFrame window = new JFrame("Jogo do Galo - Cliente");
  JPanel pnlSouth = new JPanel(),
  pnlPlayingField = new JPanel();
  JButton btnEmpty[] = new JButton[10];
  final int X = 412, Y = 268, color = 190;
  boolean btnEmptyClicked = false;
  private GameController controlador;
  
  
  /**
  * Metodo que define a interface grafica do jogo do galo.
  * Este metodo cria e define os paineis utilizados para o ambiente grafico
  * São utilizados botoes para definir o "tabuleiro" de jogo. Um click equivale a uma jogada.
  */
  public UIGaloJogoCliente(GameController controlador) {
      this.controlador=controlador;
  }
  
  /**
   * Method to create the ui table game.
   * 
   * A simple gamming window show in the screen by this method.
   */
  public void CreateTable()
  {
    //definir layouts e propriedades da janela
    window.setSize(X, Y);
    window.setLocation(100, 430);
    window.setResizable(false);
    window.setLayout(new BorderLayout());
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Defenir layouts e propriedades dos paineis
    pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
    pnlSouth.setBackground(new Color(color, color, color));

    //Adicionar Action Listener aos botoes.

    //Cria o tabuleiro de jogo
    pnlPlayingField.setLayout(new GridLayout(3, 3, 2, 2));
    pnlPlayingField.setBackground(Color.black);
    for (int i = 1; i <= 9; i++) {
      btnEmpty[i] = new JButton();
      btnEmpty[i].setBackground(new Color(220, 220, 220));
      btnEmpty[i].addActionListener(this);
      pnlPlayingField.add(btnEmpty[i]);
    }
    for (int i = 1; i < 10; i++) {
      btnEmpty[i].setText("");
      btnEmpty[i].setEnabled(true);
    }
      
    showGame();

    //Adicionar e mostrar a janela
    window.add(pnlSouth, BorderLayout.CENTER);
    window.setVisible(true);
  }

  
  /**
   * ActionPerformed method - respond to a button click.
   * 
   * This method responds to a button click, registering the index
   * of that button and calling the controller class who will later send that
   * button to the comunication layer to comunicate with the server
   * Altough the actionPerfomed method does not create or operate within a different
   * thread, even when the program is waiting for client input it records the button
   * pushed and later performs it's operation.
   * 
   * @param click 
   */
  @Override
  public void actionPerformed(ActionEvent click) {
    Object source = click.getSource();
    for (int i = 1; i <= 9; i++) {
      if (source == btnEmpty[i]) {
        btnEmptyClicked = true; 
          btnEmpty[i].setText("O");
          controlador.Client_move(i);
        
        btnEmpty[i].setEnabled(false);
        pnlPlayingField.requestFocus();
      }
    }
  }

  /**
  * Mostra o tabuleiro do jogo. Este metodo não cria o tabuleiro, apenas
  * mostra as componentes existentes
  */
  public void showGame() {
    pnlSouth.remove(pnlPlayingField);
    pnlSouth.setLayout(new BorderLayout());
    pnlSouth.add(pnlPlayingField, BorderLayout.CENTER);
    pnlPlayingField.requestFocus();
  }

  
  /**
  * Recebe e define a jogada recebida no tabuleiro.
  *
  * A casa fica bloqueada na jogada seguinte.
  * @param ti
  */
  public void setCasa(int ti)
  {
    btnEmpty[ti].setText("X");
    btnEmpty[ti].setEnabled(false);
  }

  
  public void clearboard()
  {
      for (int i = 1; i <= 9; i++) {
      btnEmpty[i].setText("");
      btnEmpty[i].setEnabled(true);
    }   
  }
}

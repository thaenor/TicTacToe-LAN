
package csheets.ext.jogos.ui;

import csheets.ext.jogo.Clientes;
import csheets.ext.jogo.GameController;
import csheets.ext.jogo.servidors;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @authors Alberto, Francisco Santos(1111315)
 * classe que cria o menu de ligacao do jogo
 */
public class GaloMenus extends JFrame{
    
    private File file;
    private javax.swing.JButton Cliente;
    private javax.swing.JButton Servidor;
    private JLabel Lnome;
    private JLabel Jogador2;
    private JLabel Jogador1;
    private JTextField TextNome;
    private JButton Pesquisa;
    private JLabel imagem;
    private JLabel imaGogador;
    private servidors serv;
    private Clientes clie;
    private String Jogo;
    
    /**
     * Menu de configuração inicial, antes do jogo do galo.
     * 
     * Este metodo configura e decide o servidor e cliente antes do jogo do Galo
     * começar.
     * 
     * @param jogo 
     */
    public GaloMenus(String jogo){
        //this.setSize(300,300);
        this.Jogo=jogo;
        
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setResizable(false);
        
        Lnome=new JLabel();
        TextNome=new JTextField();
        Cliente=new JButton();
        Servidor=new JButton();
        Jogador2=new JLabel();
        
        Pesquisa=new JButton();
        imagem=new JLabel();
        imaGogador= new JLabel();
        
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conectar Jogador");
        
        Lnome.setText("Insira o Seu Nome");
        
        Cliente.setText("Ligar-se");
        Servidor.setText("Ligar");
        
        Pesquisa.setText("Pesquisa");
        
        Cliente.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteButtonActionPerformed(evt);
            }
        });
        
        Servidor.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                servidorButtonActionPerformed(evt);
            }
        });
        
        Pesquisa.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisaButtonActionPerformed(evt);
            }
        });
        
        JPanel pt = new JPanel(new GridLayout(2,2));
        pt.add(Lnome);
        pt.add(TextNome);
        pt.add(Jogador2);
        add(pt,BorderLayout.NORTH);
        
        JPanel ima=new JPanel(new GridBagLayout());
        ima.add(Pesquisa);
        ima.add(imagem);
        ima.add(imaGogador);
        add(ima,BorderLayout.CENTER);
        
        JPanel botoes=new JPanel(new FlowLayout(FlowLayout.CENTER));
        botoes.add(Cliente);
        botoes.add(Servidor);
        add(botoes,BorderLayout.SOUTH);
        
        
        pack();
        Lnome.setVisible(true);
        Cliente.setVisible(false);
        Servidor.setVisible(false);
        Pesquisa.setVisible(true);
        imagem.setVisible(true);
        setVisible(true);
        
    }
    
    
    /**
     * botao que cria o servidor.
     * 
     * instancia o controllador entre camada de comunicação e ui.
     * 
     * @param evt 
     */
    private void servidorButtonActionPerformed(java.awt.event.ActionEvent evt) {
           try{
               //TextNome.setText("");
               GameController controlador = new GameController();
               if(CarregaDadosServidor()){
                   serv = controlador.CreateServer(TextNome.getText(),file.toString(),Jogo);
//                 serv=new servidors(TextNome.getText(),file.toString(),Jogo);
                    if(Jogo.compareToIgnoreCase(serv.getJogo())==0){
                        Jogador2.setText("Player2:"+serv.getNome());
                        imaGogador.setIcon(new javax.swing.ImageIcon(serv.getImagem()));
                    }else
                         JOptionPane.showMessageDialog(null,"Esse Jogo esta indisponivel no servidor");   
               }else
                   JOptionPane.showMessageDialog(null, "Insira uma imagem pre-definida ou Nome não definido");
               pack();
               controlador.IniciaJogoServidor();
           }catch(IOException e)
           {
               System.out.println("falha no servidor");
           }
        
        
    }
    
    /**
     * botao pesquisa para selecionar a foto.
     * 
     * The JFilleChooser is set to open a specific directory in the project folder
     * where the images are stored.
     * 
     * @param evt 
     */
    private void pesquisaButtonActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jfc = new JFileChooser("src\\csheets\\ext\\jogo\\Imagem");
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                file = jfc.getSelectedFile();
                imagem.setIcon(new javax.swing.ImageIcon(file.toString()));
                Servidor.setVisible(true);
                Cliente.setVisible(true);
                pack();
                //System.out.println(file.toString());
            }
        
    }
    
    
    /**
     * botao para ligar se ao servidor.
     * 
     * instancia o controllador entre camada de comunicação e ui.
     * 
     * @param evt 
     */
    private void clienteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
        try{
            GameController controlador = new GameController();
            if(CarregaDadosServidor()){
               clie = controlador.CreateClients(TextNome.getText(),file.toString(),Jogo, "1");
//             clie = new Clientes(TextNome.getText(),file.toString(),Jogo, "1");
               if(Jogo.compareToIgnoreCase(clie.getJogo())==0){
                    Jogador2.setText("Player2:"+clie.getNome());
                    imaGogador.setIcon(new javax.swing.ImageIcon(clie.getImagem()));
               }else
                   JOptionPane.showMessageDialog(null,"Esse Jogo esta indisponivel no servidor");
            }else
                   JOptionPane.showMessageDialog(null, "Insira uma imagem pre-definida ou Nome não definido");
             pack();  
             controlador.IniciaJogoCliente();
        }catch(IOException e){
               System.out.println("Falha no Cliente");
           }
        
    }
    
    
    
    /**
     * Carregar dados do servidor.
     * 
     * Metodo comum a os botoes cliente e servidor e faz com que apareca e desapareca os botoes de selecao
     * e os nomes sejam colocados visiveis na frame
     * @return 
     */
    private boolean CarregaDadosServidor()
    {
        if(TextNome.getText().compareToIgnoreCase("")!=0 && file.toString().compareToIgnoreCase("")!=0)
        {
               Cliente.setVisible(false);
               Servidor.setVisible(false);
               Jogador2.setVisible(true);
               imaGogador.setVisible(true);
               Lnome.setText("Player1:"+TextNome.getText());
               TextNome.setVisible(false);
               Pesquisa.setVisible(false);
               return true;
        }  
        return false;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csheets.ext.jogos.ui;

import csheets.ext.jogo.Clientes;
import csheets.ext.jogo.servidors;
import csheets.ui.ctrl.BaseAction;
import csheets.ui.ctrl.UIController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Alberto
 * 
 * classe que da a escolher os jogos e cria o sub-menu
 */
public class JogosAction extends BaseAction {
    
         /** Controlador da interface do utilizador */
	protected UIController uiController;
        private JButton galo;
        private JButton enforcado;
        private JFrame f;
	/**
	 * Criar a nova accção.
	 * @param uiController Controlador da interface do utilizador
	 */
	public JogosAction(UIController uiController) {
            this.uiController = uiController;
	}
        
        /**
         * Retornar o nome da acção
         * @return Nome da acção
         */
        
	protected String getName() {
            return "Jogo...";
	}

	protected void defineProperties() {
	}

	/**
	 * Acção de receber e executar uma macro.
	 * @param event Evento activado
	 */
	public void actionPerformed(ActionEvent event) {
            f = new JFrame("Jogo");
            f.setSize(new Dimension(220, 100));
            //f.pack();
            f.setAlwaysOnTop(true);
            f.setLocationRelativeTo(null);
            f.setResizable(false);
            
            //painel titulo
            JPanel pt = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pt.add(new JLabel("Jogos"));
            f.add(pt, BorderLayout.NORTH);
            
            //painel sequencias
            JPanel Emenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel titulo = new JLabel("Escolha o seu jogo");
                Emenu.add(titulo);
            JPanel Ebotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
                galo = new JButton("Galo");
                enforcado = new JButton("Enforcado");
                Ebotoes.add(galo);
                Ebotoes.add(enforcado);
                //ps.add(texfield);
            f.add(Emenu);
            f.add(Ebotoes);
           
       
            
            galo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaloButtonActionPerformed(evt);   
            }
        });
            enforcado.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OutroButtonActionPerformed(evt);   
            }
        });
            f.setVisible(true);
	}
        
       private void GaloButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
           galo.setVisible(false);
           enforcado.setVisible(false);
           GaloMenus galo=new GaloMenus("galo");
           f.setVisible(false);
           
    }
       private void OutroButtonActionPerformed (java.awt.event.ActionEvent evt){
           
           enforcado.setVisible(false);
           galo.setVisible(false);
           EnforcadoMenu enforcado=new EnforcadoMenu("enforcado");
           f.setVisible(false);
       }
    
}

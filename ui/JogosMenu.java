/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csheets.ext.jogos.ui;
import csheets.ext.jogos.ui.JogosAction;
import csheets.ui.ctrl.UIController;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;

/**
 *
 * @author Alberto
 */
public class JogosMenu extends JMenu{
    
    public JogosMenu(UIController uiController) {
		super("Jogo");
		setMnemonic(KeyEvent.VK_E);

		// Adds font actions
		add(new JogosAction(uiController));
	}
}

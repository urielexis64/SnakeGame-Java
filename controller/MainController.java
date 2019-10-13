package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import view.MainView;

public class MainController implements ActionListener{
	private MainView mainView;
	
	public MainController(MainView mainView) {
		this.mainView=mainView;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		
		if(e.getSource()==mainView.btnStartGame) {
			mainView.startGame();
			

		}
	}

}

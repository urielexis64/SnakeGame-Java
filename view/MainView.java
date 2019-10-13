package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Rutinas.Rutinas;
import controller.BoardController;
import controller.MainController;
import material.componentes.*;
import material.componentes.MaterialPanel;
import material.fonts.Roboto;

public class MainView extends JFrame {

	public MaterialButton btnStartGame, btnSettings, btnRecords;
	private MaterialPanel panelButtons;
	public JLabel lblImage;

	public Board board;
	public Game game;
	
	public MainView() {
		doInterface();
	}

	private void doInterface() {
		initComponents();
		doFrame();
	}

	private void initComponents() {
		lblImage = new JLabel(Rutinas.ajustaImagen("SnakeGame/resources/snakegame.jpg", 600, 400));

		board = new Board();
		
		panelButtons = new MaterialPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelButtons.setSize(400, 400);

		btnStartGame = new MaterialButton();
		btnStartGame.setText("START GAME");
		btnStartGame.setPreferredSize(new Dimension(300, 60));
		btnStartGame.setFont(Roboto.MEDIUM.deriveFont(16f));
		btnStartGame.setType(MaterialButton.Type.RAISED);
		btnStartGame.setRippleColor(MaterialColor.GREEN_500);
		btnStartGame.setBackground(MaterialColor.GREEN_300);
		btnStartGame.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnRecords = new MaterialButton();
		btnRecords.setText("RECORDS");
		btnRecords.setPreferredSize(new Dimension(300, 60));
		btnRecords.setFont(Roboto.MEDIUM.deriveFont(16f));
		btnRecords.setType(MaterialButton.Type.RAISED);
		btnRecords.setRippleColor(MaterialColor.GREEN_500);
		btnRecords.setBackground(MaterialColor.GREEN_300);
		btnRecords.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnSettings = new MaterialButton();
		btnSettings.setText("SETTINGS");
		btnSettings.setPreferredSize(new Dimension(300, 60));
		btnSettings.setFont(Roboto.MEDIUM.deriveFont(16f));
		btnSettings.setType(MaterialButton.Type.RAISED);
		btnSettings.setRippleColor(MaterialColor.GREEN_500);
		btnSettings.setBackground(MaterialColor.GREEN_300);
		btnSettings.setCursor(new Cursor(Cursor.HAND_CURSOR));

		panelButtons.add(btnStartGame);
		panelButtons.add(btnRecords);
		panelButtons.add(btnSettings);
	}

	private void doFrame() {
		setSize(600, 800);
		setLayout(new GridLayout(2, 1));
		setLocationRelativeTo(null);
		setUndecorated(true);

		add(lblImage);
		add(panelButtons);

		setVisible(true);
	}

	public void setMainController(MainController controller) {
		btnStartGame.addActionListener(controller);
	}
	
	public void setBoardController(BoardController controller) {
		board.setController(controller);
	}
	
	public void startGame() {
		setVisible(false);

		game = new Game(board);
	}

}

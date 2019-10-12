package view;

import java.awt.Graphics;

import javax.swing.JFrame;

import de.craften.ui.swingmaterial.MaterialButton;

public class MainView extends JFrame {

	private MaterialButton btnStartGame, btnSettings, btnRecords;

	public MainView() {

	}

	private void doInterface() {
		doFrame();
		initComponents();
	}

	private void initComponents() {
		btnStartGame
	}

	private void doFrame() {
		setSize(800, 1000);
		setLocationRelativeTo(null);
		setUndecorated(true);

	}

	private void doListeners() {

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(Rutinas.Rutinas.ajustaImagen("resources/snakegame.jpg", 725, 400).getImage(), 0, 0, null);
	}

}

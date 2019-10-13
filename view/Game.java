package view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ControlsController;
import material.fonts.Roboto;

public class Game extends JFrame {

	private JPanel panelControls;
	private Board board;

	public static JLabel score;

	public Game(Board board) {
		this.board = board;
		doInterface();
	}

	private void doInterface() {
		setSize(600, 700);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(null);

		score = new JLabel("Score: " + 0);
		score.setFont(Roboto.BOLD.deriveFont(26f));
		score.setBounds(10, 30, 150, 30);

		panelControls = new JPanel();
		panelControls.setBounds(5, 605, 590, 90);
		panelControls.setLayout(null);
		panelControls.setBorder(BorderFactory.createBevelBorder(0));
		panelControls.add(score);

		add(board);
		add(panelControls);
		
		setVisible(true);
		board.initGame();
	}

	public void setControllers(ControlsController controller) {

	}

}

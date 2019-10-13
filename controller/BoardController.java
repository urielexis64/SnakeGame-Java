package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import material.componentes.MaterialColor;
import view.MainView;

public class BoardController extends KeyAdapter implements ActionListener {

	private MainView view;

	public BoardController(MainView view) {
		this.view = view;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_P) {
			if (view.board.mainTimer.isRunning())
				view.board.mainTimer.stop();
			else
				view.board.mainTimer.start();
		}

		if (!view.board.mainTimer.isRunning())
			return;

		if ((key == KeyEvent.VK_LEFT) && (!view.board.rightDirection)) {
			view.board.leftDirection = true;
			view.board.upDirection = false;
			view.board.downDirection = false;
		}

		if ((key == KeyEvent.VK_RIGHT) && (!view.board.leftDirection)) {
			view.board.rightDirection = true;
			view.board.upDirection = false;
			view.board.downDirection = false;
		}

		if ((key == KeyEvent.VK_UP) && (!view.board.downDirection)) {
			view.board.upDirection = true;
			view.board.rightDirection = false;
			view.board.leftDirection = false;
		}

		if ((key == KeyEvent.VK_DOWN) && (!view.board.upDirection)) {
			view.board.downDirection = true;
			view.board.rightDirection = false;
			view.board.leftDirection = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == view.board.mainTimer) {

			if (view.board.inGame) {
				view.board.checkApple();
				view.board.checkCollision();
				view.board.move();
			}
			view.board.repaint();
			return;
		}
		if (view.board.timeStar == 0) {
			view.board.checkStar();
		} else {
			int r = (int) (Math.random() * 255);
			int g = (int) (Math.random() * 255);
			int b = (int) (Math.random() * 255);

			view.board.setBackground(new Color(r, g, b, 50));

			view.board.timeStar += 140;
			if (view.board.timeStar >= 10000) {
				view.board.starTimer.stop();
				view.board.mainTimer.setDelay(140);
				view.board.timeStar = 0;
				view.board.setBackground(MaterialColor.GREEN_900);
				view.board.loadImages(true);
			}
		}

	}

}

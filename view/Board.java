package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zetcode.Board.TAdapter;

import Rutinas.Rutinas;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Board extends JPanel{

	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = 1000;
	private final int DOT_SIZE = 25;
	private final int ALL_DOTS = 10000;
	private final int RAND_POS = 39;
	private final int DELAY = 140;

	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;

	private Timer timer;
	private Image apple;
	private Image currenthead, headUp, headDown, headLeft, headRight;

	private Image currentBall, ballUp, ballDown, ballSides;
	
	public Board() {
		try {
			player = new Player(new FileInputStream("SnakeGame\\resources\\bruh.mp3"));
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}

		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}

	private void loadImages() {

		ballSides = Rutinas.ajustaImagen("SnakeGame/resources/fantasma.png", 25, 25).getImage();
		ballUp = Rutinas.ajustaImagen("SnakeGame/resources/fantasma-arriba.png", 25, 25).getImage();
		ballDown = Rutinas.ajustaImagen("SnakeGame/resources/fantasma-abajo.png", 25, 25).getImage();
		currentBall = ballSides;

		apple = Rutinas.ajustaImagen("SnakeGame/resources/apple.png", 25, 25).getImage();

		headRight = Rutinas.ajustaImagen("SnakeGame/resources/pacman.png", 25, 25).getImage();
		headLeft = Rutinas.ajustaImagen("SnakeGame/resources/pacman-atras.png", 25, 25).getImage();
		headUp = Rutinas.ajustaImagen("SnakeGame/resources/pacman-arriba.png", 25, 25).getImage();
		headDown = Rutinas.ajustaImagen("SnakeGame/resources/pacman-abajo.png", 25, 25).getImage();
		currentBall = headRight;
	}

	private void initGame() {

		dots = 3;

		for (int z = 0; z < dots; z++) {
			x[z] = 25 - z * 10;
			y[z] = 25;
		}

		locateApple();

		timer = new Timer(DELAY, this);
//		t2 = new Timer(100, (e) -> {
//			int r = (int) (Math.random() * 255);
//			int g = (int) (Math.random() * 255);
//			int b = (int) (Math.random() * 255);
//
//			setBackground(new Color(r, g, b, 50));
//		});
//		t2.start();
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	public void doDrawing(Graphics g) {

		if (inGame) {

			g.drawImage(apple, apple_x, apple_y, this);

			for (int z = 0; z < dots; z++) {
				if (z == 0) {
					g.drawImage(currenthead, x[z], y[z], this);
				} else {
					g.drawImage(currentBall, x[z], y[z], this);
				}
			}

			Toolkit.getDefaultToolkit().sync();

		} else {
			gameOver(g);
		}
	}

	private void gameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	private void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}

	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		if (leftDirection) {
			x[0] -= DOT_SIZE;
			currentBall = ballSides;
			currenthead = headLeft;
		}

		if (rightDirection) {
			x[0] += DOT_SIZE;
			currentBall = ballSides;
			currenthead = headRight;
		}

		if (upDirection) {
			y[0] -= DOT_SIZE;
			currentBall = ballUp;
			currenthead = headUp;
		}

		if (downDirection) {
			y[0] += DOT_SIZE;
			currentBall = ballDown;
			currenthead = headDown;
		}
	}

	private void checkCollision() {

		for (int z = dots; z > 0; z--) {

			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}

		if (y[0] >= B_HEIGHT) {
			y[0] = 0;
		}

		if (y[0] < 0) {
			y[0] = B_HEIGHT;
		}

		if (x[0] >= B_WIDTH) {
			x[0] = 0;
		}

		if (x[0] < 0) {
			x[0] = B_WIDTH;
		}

		if (!inGame) {
			timer.stop();
			System.exit(0);
		}
	}

	private void locateApple() {

		int r = (int) (Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));

		r = (int) (Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
	}
}

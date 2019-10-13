package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JPanel;
import javax.swing.Timer;

import Rutinas.Rutinas;
import controller.BoardController;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import material.componentes.MaterialColor;

public class Board extends JPanel {

	private final int B_WIDTH = 600;
	private final int B_HEIGHT = 600;
	private final int DOT_SIZE = 20;
	private final int ALL_DOTS = 1800;
	private final int RAND_POS = 29;
	private int DELAY = 140;

	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;
	private int star_x;
	private int star_y;

	public int timeStar;

	public boolean leftDirection = false;
	public boolean rightDirection = true;
	public boolean upDirection = false;
	public boolean downDirection = false;
	public boolean inGame = true;

	public Timer mainTimer, starTimer;

	private Image apple, star;
	private Image currenthead, headUp, headDown, headLeft, headRight;
	private Image currentBall, ballUp, ballDown, ballSides;

	private Player bruh, starTheme;

	public Board() {
		try {
			timeStar = 0;
			bruh = new Player(new FileInputStream("SnakeGame/resources/bruh.mp3"));
			starTheme = new Player(new FileInputStream("SnakeGame/resources/star-theme.mp3"));
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
		initBoard();
	}

	private void initBoard() {
		setFocusable(true);
		setLocation(0, 0);
		setBackground(MaterialColor.GREEN_900);
		setSize(B_WIDTH, B_HEIGHT);
		loadImages(true);
	}

	public void loadImages(boolean normal) {
		if (normal) {
			ballSides = Rutinas.ajustaImagen("SnakeGame/resources/fantasmas/fantasma.png", 20, 20).getImage();
			ballUp = Rutinas.ajustaImagen("SnakeGame/resources/fantasmas/fantasma-arriba.png", 20, 20).getImage();
			ballDown = Rutinas.ajustaImagen("SnakeGame/resources/fantasmas/fantasma-abajo.png", 20, 20).getImage();
			currentBall = ballSides;

			apple = Rutinas.ajustaImagen("SnakeGame/resources/apple.png", 20, 20).getImage();
			star = Rutinas.ajustaImagen("SnakeGame/resources/star.png", 20, 20).getImage();

			headRight = Rutinas.ajustaImagen("SnakeGame/resources/pacmanNormal/derecha.png", 20, 20).getImage();
			headLeft = Rutinas.ajustaImagen("SnakeGame/resources/pacmanNormal/izquierda.png", 20, 20).getImage();
			headUp = Rutinas.ajustaImagen("SnakeGame/resources/pacmanNormal/arriba.png", 20, 20).getImage();
			headDown = Rutinas.ajustaImagen("SnakeGame/resources/pacmanNormal/abajo.png", 20, 20).getImage();
			currentBall = headRight;
		}else {
			headRight = Rutinas.ajustaImagen("SnakeGame/resources/pacmanStar/derecha.png", 20, 20).getImage();
			headLeft = Rutinas.ajustaImagen("SnakeGame/resources/pacmanStar/izquierda.png", 20, 20).getImage();
			headUp = Rutinas.ajustaImagen("SnakeGame/resources/pacmanStar/arriba.png", 20, 20).getImage();
			headDown = Rutinas.ajustaImagen("SnakeGame/resources/pacmanStar/abajo.png", 20, 20).getImage();
		}
	}

	public void setController(BoardController controller) {
		addKeyListener(controller);
		mainTimer = new Timer(DELAY, controller);
		starTimer = new Timer(DELAY, controller);
	}

	public void initGame() {
		dots = 1;

		for (int z = 0; z < dots; z++) {
			x[z] = 20 - z * 20;
			y[z] = 20;
		}

		locateApple();

		mainTimer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int randomNumber = (int) (Math.random() * 100);

		if (randomNumber == 69 && !starTimer.isRunning()) {
			locateStar();
			starTimer.start();
		}

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {

		if (inGame) {
			if (star_x != 0)
				g.drawImage(star, star_x, star_y, this);

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

	public void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			new Thread(() -> {
				try {
					bruh.play();
					bruh = new Player(new FileInputStream("SnakeGame\\resources\\bruh.mp3"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			dots++;

			Game.score.setText("Score: " + (dots - 1));

			locateApple();
		}
	}

	public void checkStar() {
		if ((x[0] == star_x) && (y[0] == star_y)) {
			new Thread(() -> {
				try {
					starTheme.play();
					starTheme = new Player(new FileInputStream("SnakeGame\\resources\\star-theme.mp3"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();

			loadImages(false);

			timeStar = 1;
			mainTimer.setDelay(DELAY / 2);

			star_x = 0;
		}
	}

	public void move() {

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

	public void checkCollision() {

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
			mainTimer.stop();
			inGame = false;
		}
	}

	private void locateApple() {

		int r = (int) (Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));

		r = (int) (Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
	}

	private void locateStar() {
		int r = (int) (Math.random() * RAND_POS);
		star_x = ((r * DOT_SIZE));

		r = (int) (Math.random() * RAND_POS);
		star_y = ((r * DOT_SIZE));
	}

}

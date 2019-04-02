/* 
 * 		Main class creates JFRAME, initializes textures, game objects and starts game loop thread. 
 */

package src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 320 / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "2D Space Game";
	
	private Random r = new Random();
	private static int SCORE;
	private static int RECORD;
	private static boolean running = false;
	private static Thread thread;
	private static STATE State = STATE.MENU;
	public static boolean isGameBegining = true;
		
	private void initInterface() {
		addKeyListener(new KeyInput());						// Catching key strokes in class KeyInput 
		addMouseListener(new MouseInput());
		Textures.initTextures();							// All images are loaded in Textures class
		requestFocus();										// Making game window active
	}
	
	@SuppressWarnings("resource")
	private void initGame() {
		try {
			RECORD = new DataInputStream(new FileInputStream("res/data")).readInt();			
		} catch (IOException e) {
			try {
				new DataOutputStream(new FileOutputStream("res/data")).writeInt(0);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		Player.resetPlayer();								// Setting player to null in case of restarting game
		Player.getPlayer();									// Creating new player with full health 
		Controller.initController();						// Creating list for game objects
		Game.setScore(0);
		Controller.createEnemy(5);							// Enemies are created in Controller class
	}
	
	// Game loop works in separate thread
	private synchronized void start() {
		if(running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private static synchronized void stop() {
		if(!running) return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	// Game loop synchronized by 60 ticks
	@Override
	public void run() {
		initInterface();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				if(!isGameBegining) tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("Updates = " + updates + ", frames = " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(State == STATE.GAME) {
			Controller.tick();								// All game objects move
	
			if(Controller.getEnemyNumber() < 5) {
				Controller.createEnemy(1 + r.nextInt(3));
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(Textures.background, 0, 0, null);
		
		if(State == STATE.GAME) {
			if(isGameBegining) {
				initGame();
				isGameBegining = false;
			}
			Controller.render(g);							// All game objects render
			Textures.energyBar(g);
			Textures.drawScore(g);
			Textures.temperatureBar(g);
		} else if(State == STATE.MENU) {
			Screens.drawMenu(g);
		} else if(State == STATE.HELP) {
			Screens.drawHelp(g);
		} else if(State == STATE.LOSE) {
			Screens.drawLose(g);
		}
		
		g.dispose();
		bs.show();
	}

	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
					
		game.start();	
	}
	
	public static STATE getState() {
		return State;
	}
	
	public static void setState(STATE value) {
		State = value;
	}
	
	public static int getScore() {
		return SCORE;
	}
	
	public static void setScore(int value) {
		SCORE = value;
	}
	
	public static int getRecord() {
		return RECORD;
	}
}

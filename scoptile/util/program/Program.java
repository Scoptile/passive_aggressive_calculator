package com.scoptile.util.program;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.scoptile.util.Console;
import com.scoptile.util.Draw;
import com.scoptile.util.program.input.Keyboard;
import com.scoptile.util.program.input.Mouse;
import com.scoptile.util.program.input.MouseWheel;

public class Program implements Runnable {
	// The thread that is run by the program when it is started
	private Thread thread;
	// The display that the game is run in
	protected Display display;
	
	// The mouse and keyboard listeners from the display
	protected Keyboard keyboard;
	protected Mouse mouse;
	protected MouseWheel mouseWheel;
	
	// The bufferstrategy taken from the display's canvas
	private BufferStrategy bs;
	
	// Whether or not the game is running
	private boolean running;
	// The maximum fps that the game will run at
	protected int fpsMax = 60;
	// The number of times the game ticks in a second
	private int fps;
	
	public Program (String title, int width, int height, float scaleX, float scaleY, Color background) {
		display = new Display(title, width, height, scaleX, scaleY, background);
		keyboard = display.getKeyboard();
		mouse = display.getMouse();
		mouseWheel = display.getMouseWheel();
	}
	
	public Program (int width, int height, float scaleX, float scaleY, Color background) {
		this("", width, height, scaleX, scaleY, background);
	}
	
	public Program (String title, int width, int height, float scaleX, float scaleY) {
		this(title, width, height, scaleX, scaleY, new Color(0xE0E0E0));
	}
	
	public Program (int width, int height, float scaleX, float scaleY) {
		this("", width, height, scaleX, scaleY);
	}
	
	public Program (String title, int width, int height, Color background) {
		this(title, width, height, 1.0F, 1.0F, background);
	}
	
	public Program (int width, int height, Color background) {
		this("", width, height, background);
	}
	
	public Program (String title, int width, int height) {
		this(title, width, height, new Color(0xE0E0E0));
	}
	
	public Program (int width, int height) {
		this("", width, height);
	}
	
	public Program (String title) {
		this(title, 900, 525);
	}
	
	public Program () {
		this("");
	}
	
	/**
	 * Sets up the display and other important thins
	 */
	public void setup () {
		display.getFrame().setVisible(true);
	}
	
	/**
	 * Called after the thread is started and other important stuff is initialized
	 */
	public void init () {
		
	}
	
	/**
	 * Called every tick to update variables and logic and all that stuff
	 */
	public void tick () {
		
	}
	
	/**
	 * Called after the {@code tick()} method to prepare the {@link Draw} class and call
	 * the {@code draw()} method
	 */
	public void render () {
		if (display.getCanvas().getBufferStrategy() == null) display.getCanvas().createBufferStrategy(3);
		bs = display.getCanvas().getBufferStrategy();
		Draw.g = (Graphics2D)bs.getDrawGraphics();
		Draw.setScaleX(display.getScaleX());
		Draw.setScaleY(display.getScaleY());
		Draw.setColor(display.getBackground());
		Draw.drawRectangle(0, 0, display.getWidth(), display.getHeight(), true);
		Draw.setColor(new Color(0x000000));
		
		draw();
		
		bs.show();
		Draw.g.dispose();
	}
	
	/**
	 * Called by the {@code render()} method to draw graphics to the screen. Graphics
	 * can only be drawn in this method. Other methods won't work.
	 */
	public void draw () {
		
	}
	
	/**
	 * Method called by the {@link Thread} when it is started using {@code start()}
	 */
	public final void run () {
		Console.print("Beginning setup...", "Game");
		setup();
		Console.print("Setup complete.", "Game");
		init();
		
		double timePerTick = 1000000000 / fpsMax;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		Console.print("Beginning main loop...", "Game");
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if (delta >= 1) {
				mouse.preTick();
				
				tick();
				render();
				
				ticks ++;
				delta --;
			}
			
			if (timer >= 1000000000) {
				fps = ticks;
				display.setTitle(fps + " FPS");
				
				ticks = 0;
				timer = 0;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				Console.error("There was a problem with the Thread.sleep() method", e, true);
			}
		}
	}
	
	/**
	 * Stops the program and the thread
	 */
	public final void stop () {
		Console.print("Stopping...", "Game");
		
		if (!running) {
			Console.print("Not running.", "Game", Console.TYPE_ERROR);
			return;
		}
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			Console.error("There was a problem joining the thread.", e, true);
		}
	}
	
	/**
	 * Starts the program and the thread
	 */
	public final void start () {
		Console.print("Starting...", "Game");
		
		if (running) {
			Console.print("Already running.", "Game", Console.TYPE_ERROR);
			return;
		}
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}

	public int getFpsMax () {
		return fpsMax;
	}

	public void setFpsMax (int fpsMax) {
		this.fpsMax = fpsMax;
	}

	public Thread getThread () {
		return thread;
	}

	public Display getDisplay () {
		return display;
	}

	public BufferStrategy getBs () {
		return bs;
	}

	public boolean isRunning () {
		return running;
	}

	public int getFps () {
		return fps;
	}

	public Keyboard getKeyboard () {
		return keyboard;
	}

	public Mouse getMouse () {
		return mouse;
	}
}

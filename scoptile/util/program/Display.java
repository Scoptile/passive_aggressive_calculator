package com.scoptile.util.program;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

import com.scoptile.util.program.input.Keyboard;
import com.scoptile.util.program.input.Mouse;
import com.scoptile.util.program.input.MouseWheel;

/**
 * Contains a {@link JFrame} and {@link Canvas} and methods to easily
 * manipulate both of them.
 * <p>Intended to be used by the {@link Program} but it can be used for
 * other things.
 * @author Scoptile
 *
 */
public class Display {
	private JFrame frame;
	private Canvas canvas;
	
	// The listeners for the canvas
	private Keyboard keyboard;
	private Mouse mouse;
	private MouseWheel mouseWheel;
	
	// The title of the frame
	private String title;
	
	// The width and height for the canvas (the actual size will be scaled by the scaleX and scaleY variables)
	private int width;
	private int height;
	
	// The scale of the canvas that determines the actual size as well as the size of the pixels
	private float scaleX;
	private float scaleY;
	
	// The actual width and height of the canvas after being scaled
	private int scaledWidth;
	private int scaledHeight;
	
	// The background color of the canvas
	private Color background;
	
	public Display (String title, int width, int height, float scaleX, float scaleY, Color background) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.background = background;
		
		updateVariables();
		initializeDisplay();
	}
	
	public Display (int width, int height, float scaleX, float scaleY, Color background) {
		this("", width, height, scaleX, scaleY, background);
	}
	
	public Display (String title, int width, int height, float scaleX, float scaleY) {
		this(title, width, height, scaleX, scaleY, new Color(0xE0E0E0));
	}
	
	public Display (int width, int height, float scaleX, float scaleY) {
		this("", width, height, scaleX, scaleY);
	}
	
	public Display (String title, int width, int height, Color background) {
		this(title, width, height, 1.0F, 1.0F, background);
	}
	
	public Display (int width, int height, Color background) {
		this("", width, height, background);
	}
	
	public Display (String title, int width, int height) {
		this(title, width, height, 1.0F, 1.0F);
	}
	
	public Display (int width, int height) {
		this("", width, height);
	}
	
	public Display (String title) {
		this(title, 900, 525);
	}
	
	public Display () {
		this("");
	}
	
	/**
	 * Initialize the {@link JFrame} and {@link Canvas} with the correct variables
	 */
	public void initializeDisplay () {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(scaledWidth, scaledHeight);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
		canvas.setMinimumSize(new Dimension(scaledWidth, scaledHeight));
		canvas.setMaximumSize(new Dimension(scaledWidth, scaledHeight));
		canvas.setBackground(new Color(0xFFFFFF));
		canvas.setFocusTraversalKeysEnabled(false);
		canvas.addComponentListener(new EventComponent());
		
		keyboard = new Keyboard();
		mouse = new Mouse(canvas);
		mouseWheel = new MouseWheel();
		
		canvas.addKeyListener(keyboard);
		canvas.addMouseListener(mouse);
		canvas.addMouseWheelListener(mouseWheel);
		
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	/**
	 * Updates the {@link JFrame}'s and {@link Canvas}'s properties (size, title, etc.)
	 */
	public void updateDisplay () {
		updateVariables ();
		
		canvas.setPreferredSize(new Dimension(scaledWidth, scaledHeight));
		canvas.setMinimumSize(new Dimension(scaledWidth, scaledHeight));
		canvas.setMaximumSize(new Dimension(scaledWidth, scaledHeight));
		
		frame.setTitle(title);
		frame.pack();
	}
	
	/**
	 * Updates the variables like the size, the scaled size and maybe some others.
	 */
	public void updateVariables () {
		scaledWidth = (int)(width * scaleX);
		scaledHeight = (int)(height * scaleY);
	}
	
	private class EventComponent implements ComponentListener {
		public void componentResized (ComponentEvent e) {
			width = (int)(e.getComponent().getWidth() / scaleX);
			height = (int)(e.getComponent().getHeight() / scaleY);
			
			updateVariables();
		}

		public void componentMoved (ComponentEvent e) {
			
		}

		public void componentShown (ComponentEvent e) {
			
		}
		
		public void componentHidden (ComponentEvent e) {
			
		}
		
	}
	
	public String getTitle () {
		return title;
	}

	public void setTitle (String title) {
		this.title = title;
		updateDisplay();
	}

	public int getWidth () {
		return width;
	}

	public void setWidth (int width) {
		this.width = width;
		updateDisplay();
	}

	public int getHeight () {
		return height;
	}

	public void setHeight (int height) {
		this.height = height;
		updateDisplay();
	}

	public float getScaleX () {
		return scaleX;
	}

	public void setScaleX (float scaleX) {
		this.scaleX = scaleX;
		updateDisplay();
	}

	public float getScaleY () {
		return scaleY;
	}

	public void setScaleY (float scaleY) {
		this.scaleY = scaleY;
		updateDisplay();
	}

	public Color getBackground () {
		return background;
	}

	public void setBackground (Color background) {
		this.background = background;
	}

	public JFrame getFrame () {
		return frame;
	}

	public Canvas getCanvas () {
		return canvas;
	}

	public int getScaledWidth () {
		return scaledWidth;
	}

	public int getScaledHeight () {
		return scaledHeight;
	}

	public Keyboard getKeyboard () {
		return keyboard;
	}

	public Mouse getMouse () {
		return mouse;
	}

	public MouseWheel getMouseWheel () {
		return mouseWheel;
	}
}

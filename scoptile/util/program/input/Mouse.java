package com.scoptile.util.program.input;

import java.awt.Canvas;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
	private static Canvas canvas;
	
	public int mouseX;
	public int mouseY;
	
	private boolean[] button = new boolean[4];
	private boolean[] buttonPressed = new boolean[4];
	private boolean[] buttonReleased = new boolean[4];
	
	public Mouse (Canvas canvas) {
		Mouse.canvas = canvas;
	}
	
	public void preTick () {
		mouseX = (int)(MouseInfo.getPointerInfo().getLocation().getX() - canvas.getLocationOnScreen().x);
		mouseY = (int)(MouseInfo.getPointerInfo().getLocation().getY() - canvas.getLocationOnScreen().y);
	}
	
	public void tick () {
		for (int i = 0; i < 4; i ++) {
			buttonPressed[i] = false;
			buttonReleased[i] = false;
		}
	}
	
	public boolean mouseCheck (int mouseButton) {
		return button[mouseButton];
	}
	
	public boolean mouseCheckPressed (int mouseButton) {
		return buttonPressed[mouseButton];
	}
	
	public boolean mouseCheckReleased (int mouseButton) {
		return buttonReleased[mouseButton];
	}
	
	public void mouseClicked (MouseEvent e) {
		
	}

	public void mousePressed (MouseEvent e) {
		buttonPressed[e.getButton()] = !button[e.getButton()];
		if (buttonPressed[e.getButton()]) buttonReleased[e.getButton()] = false;
		button[e.getButton()] = true;
	}

	public void mouseReleased (MouseEvent e) {
		buttonReleased[e.getButton()] = true;
		buttonPressed[e.getButton()] = false;
		button[e.getButton()] = false;
	}

	public void mouseEntered (MouseEvent e) {
		
	}

	public void mouseExited (MouseEvent e) {
		
	}
}

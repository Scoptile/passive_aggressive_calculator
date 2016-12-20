package com.scoptile.util.program.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.scoptile.util.MathUtils;

public class Keyboard implements KeyListener {
public static String typedString = "";
	
	private boolean[] keys = new boolean[257];
	private boolean[] keysPressed = new boolean[257];
	private boolean[] keysReleased = new boolean[257];
	private boolean[] keysTyped = new boolean[257];
	
	public boolean keyCheck (int key) {
		return keys[key];
	}
	
	public boolean keyCheckPressed (int key) {
		return keysPressed[key];
	}
	
	public boolean keyCheckReleased (int key) {
		return keysReleased[key];
	}
	
	public boolean keyCheckTyped (int key) {
		return keysTyped[key];
	}
	
	public void tick () {
		typedString = "";
		
		for (int i = 0; i < keys.length; i ++) {
			keysPressed[i] = false;
			keysReleased[i] = false;
			keysTyped[i] = false;
		}
	}
	
	public void keyPressed (KeyEvent e) {
		if (e.getKeyChar() < 256 && e.getKeyChar() >= 32) typedString += e.getKeyChar();
		
		if (!keys[MathUtils.clamp(e.getKeyCode(), 0, 256)]) keysPressed[MathUtils.clamp(e.getKeyCode(), 0, 256)] = true;
		keysTyped[MathUtils.clamp(e.getKeyCode(), 0, 256)] = true;
		
		keys[MathUtils.clamp(e.getKeyCode(), 0, 256)] = true;
	}

	public void keyReleased (KeyEvent e) {
		keysReleased[MathUtils.clamp(e.getKeyCode(), 0, 256)] = true;
		keys[MathUtils.clamp(e.getKeyCode(), 0, 256)] = false;
	}

	public void keyTyped (KeyEvent e) {
		
	}
}

package com.scoptile.util.program.game;

import java.awt.Color;

import com.scoptile.util.program.Program;
import com.scoptile.util.program.game.state.State;

public class Game extends Program {
	private State state;
	
	public Game (String title, int width, int height, float scaleX, float scaleY, Color background) {
		super(title, width, height, scaleX, scaleY, background);
	}
	
	public Game (int width, int height, float scaleX, float scaleY, Color background) {
		this("", width, height, scaleX, scaleY, background);
	}
	
	public Game (String title, int width, int height, float scaleX, float scaleY) {
		this(title, width, height, scaleX, scaleY, new Color(0xE0E0E0));
	}
	
	public Game (int width, int height, float scaleX, float scaleY) {
		this("", width, height, scaleX, scaleY);
	}
	
	public Game (String title, int width, int height, Color background) {
		this(title, width, height, 1.0F, 1.0F, background);
	}
	
	public Game (int width, int height, Color background) {
		this("", width, height, background);
	}
	
	public Game (String title, int width, int height) {
		this(title, width, height, new Color(0xE0E0E0));
	}
	
	public Game (int width, int height) {
		this("", width, height);
	}
	
	public Game (String title) {
		this(title, 900, 525);
	}
	
	public Game () {
		this("");
	}
	
	public void tick () {
		mouse.preTick();
		
		state.tick();
		
		keyboard.tick();
		mouse.tick();
	}
	
	public void draw () {
		state.draw();
	}

	public State getState () {
		return state;
	}

	public void setState (State state) {
		this.state = state;
	}
}

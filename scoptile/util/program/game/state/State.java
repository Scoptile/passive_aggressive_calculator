package com.scoptile.util.program.game.state;

import com.scoptile.util.program.game.Game;

public abstract class State {
	protected Game game;
	
	public State (Game game) {
		this.game = game;
	}
	
	public abstract void tick ();
	
	public abstract void draw ();

	public Game getGame () {
		return game;
	}

	public void setGame (Game game) {
		this.game = game;
	}
}

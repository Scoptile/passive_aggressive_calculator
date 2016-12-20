package com.scoptile.util.program.game.tile;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import com.scoptile.util.Console;
import com.scoptile.util.Draw;
import com.scoptile.util.MathUtils;
import com.scoptile.util.program.game.state.StateGame;

public abstract class Tile implements Cloneable {
	private StateGame state;
	
	private BufferedImage image;
	
	private int depth;
	
	private Polygon hitbox;
	
	private float x;
	private float y;
	
	public Tile (StateGame state) {
		this.state = state;
		
		depth = 0;
	}
	
	public void render () {
		if (image != null) Draw.drawImage(image, x, y);
		
		Draw.setColor(new Color(0xFF0000));
		Draw.drawPolygon(MathUtils.getMovedPolygon(hitbox, (int)x, (int)y));
	}
	
	public Tile getClonedInstance () {
		try {
			return (Tile)this.clone();
		} catch (CloneNotSupportedException e) {
			Console.error("Problem cloning tile.", e, true);
		}
		
		return null;
	}

	public BufferedImage getImage () {
		return image;
	}

	public void setImage (BufferedImage image) {
		this.image = image;
	}

	public float getX () {
		return x;
	}

	public void setX (float x) {
		this.x = x;
	}

	public float getY () {
		return y;
	}

	public void setY (float y) {
		this.y = y;
	}

	public StateGame getState () {
		return state;
	}

	public int getDepth () {
		return depth;
	}

	public void setDepth (int depth) {
		this.depth = depth;
	}

	public void setState (StateGame state) {
		this.state = state;
	}

	public Polygon getHitbox () {
		return hitbox;
	}

	public void setHitbox (Polygon hitbox) {
		this.hitbox = hitbox;
	}
}

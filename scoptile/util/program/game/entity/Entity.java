package com.scoptile.util.program.game.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.scoptile.util.Console;
import com.scoptile.util.Draw;
import com.scoptile.util.MathUtils;
import com.scoptile.util.MathUtils.FloatRectangle;
import com.scoptile.util.ObjectUtils;
import com.scoptile.util.program.game.state.StateGame;
import com.scoptile.util.program.game.tile.Tile;

public abstract class Entity implements Cloneable {
	protected StateGame state;
	
	public boolean usesNearest;
	
	private List<Entity> nearestEntities;
	private List<Tile> nearestTiles;
	private int nearestDistance;
	
	private Entity collidedEntity;
	private Tile collidedTile;
	
	private BufferedImage[] images;
	private int imageIndex;
	
	private float alpha;
	private float angle;
	
	private float anchorX;
	private float anchorY;
	
	private int depth;
	
	private float scaleX;
	private float scaleY;
	
	private int originX;
	private int originY;
	
	private FloatRectangle hitbox;
	
	private float x;
	private float y;
	
	public Entity (StateGame state) {
		this.state = state;
		
		usesNearest = false;
		
		nearestEntities = new ArrayList<Entity>();
		nearestTiles = new ArrayList<Tile>();
		nearestDistance = 32;
		
		imageIndex = 0;
		
		alpha = 1.0F;
		angle = 0;
		
		anchorX = 0;
		anchorY = 0;
		
		depth = 0;
		
		scaleX = 1.0F;
		scaleY = 1.0F;
		
		originX = 0;
		originY = 0;
		
		x = 0;
		y = 0;
	}
	
	public void tick () {
		
	}
	
	public void render () {
		if (images != null) {
			BufferedImage image = images[imageIndex];
			Draw.drawImage(image, x - originX * scaleX, y - originY * scaleY, image.getWidth() * scaleX, image.getHeight() * scaleY, alpha, angle, anchorX, anchorY);
		}
		
		Draw.setColor(new Color(0xFF0000));
		Draw.drawRectangle(hitbox.x + x - originX, hitbox.y + y - originY, hitbox.width, hitbox.height, false);
	}
	
	public void updateNearest () {
		nearestEntities.clear();
		nearestTiles.clear();
		
		for (int i = 0; i < state.getEntities().size(); i ++) {
			Entity e = state.getEntities().get(i);
			
			if (e.x >= x - nearestDistance / 2 && e.x <= x + nearestDistance / 2 && e.y >= y - nearestDistance / 2 && e.y <= y + nearestDistance) nearestEntities.add(e);
		}
		
		for (int i = 0; i < state.getTiles().size(); i ++) {
			Tile t = state.getTiles().get(i);
			
			if (t.getY() >= x - nearestDistance / 2 && t.getX() <= x + nearestDistance / 2 && t.getY() >= y - nearestDistance / 2 && t.getY() <= y + nearestDistance) nearestTiles.add(t);
		}
	}
	
	public boolean placeMeeting (float x, float y, Entity entity, boolean useOnlyNearest) {
		List<Entity> es = (useOnlyNearest ? nearestEntities : state.getEntities());
		
		for (int i = 0; i < es.size(); i ++) {
			Entity e = es.get(i);
			
			if (MathUtils.rectanglesOverlap(hitbox.getMovedInstance(x - originX, y - originY), e.hitbox.getMovedInstance(e.x - e.originX, e.y - e.originY))) {
				if (ObjectUtils.instanceOf(e, entity)) return true;
			}
		}
		
		return false;
	}
	
	public Entity getClonedInstance () {
		Entity e;
		try {
			e = (Entity)this.clone();
			return e;
		} catch (CloneNotSupportedException e1) {
			Console.error("Problem cloning Entity", e1, false);
			return null;
		}
	}
	
	public StateGame getState () {
		return state;
	}

	public BufferedImage[] getImages () {
		return images;
	}

	public void setImages (BufferedImage[] images) {
		this.images = images;
	}

	public int getImageIndex () {
		return imageIndex;
	}

	public void setImageIndex (int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public float getAlpha () {
		return alpha;
	}

	public void setAlpha (float alpha) {
		this.alpha = alpha;
	}

	public float getAngle () {
		return angle;
	}

	public void setAngle (float angle) {
		this.angle = angle;
	}

	public float getAnchorX () {
		return anchorX;
	}

	public void setAnchorX (float anchorX) {
		this.anchorX = anchorX;
	}

	public float getAnchorY () {
		return anchorY;
	}

	public void setAnchorY (float anchorY) {
		this.anchorY = anchorY;
	}

	public int getDepth () {
		return depth;
	}

	public void setDepth (int depth) {
		this.depth = depth;
		state.updateDepths();
	}

	public float getScaleX () {
		return scaleX;
	}

	public void setScaleX (float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY () {
		return scaleY;
	}

	public void setScaleY (float scaleY) {
		this.scaleY = scaleY;
	}

	public int getOriginX () {
		return originX;
	}

	public void setOriginX (int originX) {
		this.originX = originX;
	}

	public int getOriginY () {
		return originY;
	}

	public void setOriginY (int originY) {
		this.originY = originY;
	}

	public FloatRectangle getHitbox () {
		return hitbox;
	}

	public void setHitbox (FloatRectangle hitbox) {
		this.hitbox = hitbox;
	}

	public float getX () {
		return x;
	}

	public void setX (float x) {
		this.x = x;
		state.updateNearest();
	}

	public float getY () {
		return y;
	}

	public void setY (float y) {
		this.y = y;
		state.updateNearest();
	}

	public int getNearestDistance () {
		return nearestDistance;
	}

	public void setNearestDistance (int nearestDistance) {
		this.nearestDistance = nearestDistance;
	}

	public List<Entity> getNearestEntities () {
		return nearestEntities;
	}

	public boolean isUsesNearest () {
		return usesNearest;
	}

	public void setUsesNearest (boolean usesNearest) {
		this.usesNearest = usesNearest;
	}

	public Entity getCollidedEntity () {
		return collidedEntity;
	}

	public void setCollidedEntity (Entity collidedEntity) {
		this.collidedEntity = collidedEntity;
	}

	public void setState (StateGame state) {
		this.state = state;
	}

	public Tile getCollidedTile () {
		return collidedTile;
	}

	public List<Tile> getNearestTiles () {
		return nearestTiles;
	}

	public void setCollidedTile (Tile collidedTile) {
		this.collidedTile = collidedTile;
	}
}

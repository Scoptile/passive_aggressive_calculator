package com.scoptile.util.program.game.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.scoptile.util.ObjectUtils;
import com.scoptile.util.program.game.Game;
import com.scoptile.util.program.game.entity.Entity;
import com.scoptile.util.program.game.tile.Tile;

public abstract class StateGame extends State {
	protected float cameraX;
	protected float cameraY;
	
	private int depthLowest;
	private int depthHighest;
	
	protected List<Entity> entities;
	protected List<Tile> tiles;
	
	public StateGame (Game game) {
		super(game);
		
		cameraX = 0;
		cameraY = 0;
		
		depthLowest = 0;
		depthHighest = 0;
		
		entities = new ArrayList<Entity>();
		tiles = new ArrayList<Tile>();
	}
	
	public void tick () {
		for (int i = 0; i < entities.size(); i ++) entities.get(i).tick();
	}
	
	public void draw () {
		for (int i = depthHighest; i >= depthLowest; i --) {
			for (int j = 0; j < tiles.size(); j ++) {
				if (tiles.get(j).getDepth() == i) tiles.get(j).render();
			}
			
			for (int j = 0; j < entities.size(); j ++) {
				if (entities.get(j).getDepth() == i) entities.get(j).render();
			}
		}
	}
	
	public void updateNearest () {
		for (int i = 0; i < entities.size(); i ++) {
			if (entities.get(i).usesNearest) entities.get(i).updateNearest();
		}
	}
	
	public void updateDepths () {
		for (int i = 0; i < entities.size(); i ++) {
			int depth = entities.get(i).getDepth();
			
			depthLowest = (depth < depthLowest ? depth : depthLowest);
			depthHighest = (depth > depthHighest ? depth : depthHighest);
		}
	}
	
	/**
	 * Adds an {@link Entity} to the GameState. The entity added is a cloned instance of the
	 * Entity that is passed through.
	 * @param e The Entity to add
	 * @param x The x position of the Entity
	 * @param y The y position of the Entity
	 */
	public void add (Entity e, float x, float y) {
		Entity newEntity = e.getClonedInstance();
		newEntity.setState(this);
		newEntity.setX(x);
		newEntity.setY(y);
		
		entities.add(newEntity);
		
		updateDepths();
		updateNearest();
	}
	
	/**
	 * Adds a {@link Tile} to the GameState. The tile added is a cloned instance of the
	 * Tile passed through.
	 * @param t The Tile to add
	 * @param x The x position of the Tile
	 * @param y The y position of the Tile
	 */
	public void add (Tile t, float x, float y) {
		Tile newTile = t.getClonedInstance();
		newTile.setState(this);
		newTile.setX(x);
		newTile.setY(y);
		
		tiles.add(newTile);
		
		updateDepths();
		updateNearest();
	}
	
	/**
	 * Adds an {@link Entity} to the GameState. The entity added is a cloned instance of the
	 * Entity that is passed through.
	 * @param e The Entity to add
	 */
	public void add (Entity e) {
		add(e, e.getX(), e.getY());
	}
	
	/**
	 * Adds a {@link Tile} to the GameState. The tile added is a cloned instance of the
	 * Tile passed through.
	 * @param t The Tile to add
	 */
	public void add (Tile t) {
		add(t, t.getX(), t.getY());
	}
	
	/**
	 * Removes a specific {@link Entity} from the GameState. To remove a "not specific" instance, pass through the
	 * {@code first()}, {@code last()}, {@code random()} or {@code get()} methods from the state.
	 * @param e The Entity to remove
	 */
	public void remove (Entity e) {
		entities.remove(e);
		updateNearest();
		updateDepths();
	}
	
	/**
	 * Removes a specific {@link Tile} from the GameState. To remove a "not specific" instance, pass through the
	 * {@code first()}, {@code last()}, {@code random()} or {@code get()} methods from the state.
	 * @param e The Entity to remove
	 */
	public void remove (Tile t) {
		tiles.remove(t);
		updateNearest();
		updateDepths();
	}
	
	/**
	 * Returns the first instance of a given {@link Entity}
	 * @param e The Entity
	 * @return The first instance of the Entity
	 */
	public Entity first (Entity e) {
		for (int i = 0; i < entities.size(); i ++) {
			if (ObjectUtils.instanceOf(entities.get(i), e)) return e;
		}
		
		return null;
	}
	
	/**
	 * Returns the first instance of a given {@link Tile}
	 * @param t The Tile
	 * @return The first instance of the Tile
	 */
	public Tile first (Tile t) {
		for (int i = 0; i < tiles.size(); i ++) {
			if (ObjectUtils.instanceOf(tiles.get(i), t)) return t;
		}
		
		return null;
	}
	
	/**
	 * Returns the last instance of a given {@link Entity}
	 * @param e The Entity
	 * @return The last instance of the Entity
	 */
	public Entity last (Entity e) {
		Entity[] entities = getInstances(e);
		return entities[entities.length - 1];
	}
	
	/**
	 * Returns the last instance of a given {@link Tile}
	 * @param e The Tile
	 * @return The last instance of the Tile
	 */
	public Tile last (Tile t) {
		Tile[] tiles = getInstances(t);
		return tiles[tiles.length - 1];
	}
	
	/**
	 * Returns a random instance of a given {@link Entity}
	 * @param e The Entity
	 * @return A random instance of the Entity
	 */
	public Entity random (Entity e) {
		Random rand = new Random();
		
		Entity[] entities = getInstances(e);
		return entities[rand.nextInt(entities.length)];
	}
	
	/**
	 * Returns a random instance of a given {@link Tile}
	 * @param e The Tile
	 * @return A random instance of the Tile
	 */
	public Tile random (Tile t) {
		Random rand = new Random();
		
		Tile[] tiles = getInstances(t);
		return tiles[rand.nextInt(tiles.length)];
	}
	
	/**
	 * Gets an array of every instance of a given {@link Entity}
	 * @param e The Entity
	 * @return An array of Entities that contains all instances of the Entity passed through
	 */
	public Entity[] getInstances (Entity e) {
		List<Entity> instances = new ArrayList<Entity>();
		Entity[] instancesArray;
		
		for (int i = 0; i < entities.size(); i ++) {
			if (ObjectUtils.instanceOf(entities.get(i), e)) instances.add(e);
		}
		
		instancesArray = new Entity[instances.size()];
		
		for (int i = 0; i < instances.size(); i ++) instancesArray[i] = instances.get(i);
		
		return instancesArray;
	}
	
	/**
	 * Gets an array of every instance of a given {@link Tile}
	 * @param e The Tile
	 * @return An array of Tiles that contains all instances of the Tile passed through
	 */
	public Tile[] getInstances (Tile t) {
		List<Tile> instances = new ArrayList<Tile>();
		Tile[] instancesArray;
		
		for (int i = 0; i < tiles.size(); i ++) {
			if (ObjectUtils.instanceOf(tiles.get(i), t)) instances.add(t);
		}
		
		instancesArray = new Tile[instances.size()];
		
		for (int i = 0; i < instances.size(); i ++) instancesArray[i] = instances.get(i);
		
		return instancesArray;
	}

	public float getCameraX () {
		return cameraX;
	}

	public void setCameraX (float cameraX) {
		this.cameraX = cameraX;
	}

	public float getCameraY () {
		return cameraY;
	}

	public void setCameraY (float cameraY) {
		this.cameraY = cameraY;
	}

	public List<Entity> getEntities () {
		return entities;
	}

	public void setEntities (List<Entity> entities) {
		this.entities = entities;
	}

	public List<Tile> getTiles () {
		return tiles;
	}

	public void setTiles (List<Tile> tiles) {
		this.tiles = tiles;
	}
}

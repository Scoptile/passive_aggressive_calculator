package com.scoptile.util;

import java.awt.image.BufferedImage;

/**
 * Stores a {@code BufferedImage} and makes it easy to crop out certain parts of the image.
 */
public class SpriteSheet {
	private BufferedImage sheet;
	
	public SpriteSheet (BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	/**
	 * Crops a certain part of the {@code BufferedImage}
	 * @param x The x position of the part to crop
	 * @param y The y position of the part to crop
	 * @param width The width of the part to crop
	 * @param height The height of the part to crop
	 * @return The cropped image
	 */
	public BufferedImage crop (int x, int y, int width, int height) {
		if (sheet == null) return null;
		return sheet.getSubimage(x, y, width, height);
	}
}

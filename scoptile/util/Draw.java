package com.scoptile.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Draw {
	public static Graphics2D g;
	
	private static float scaleX = 1.0F;
	private static float scaleY = 1.0F;
	
	private static Color color = new Color(0x000000);
	
	/**
	 * Draws a line that connects two given points.
	 * @param x1 The first x position
	 * @param y1 The first y position
	 * @param x2 The second x position
	 * @param y2 The second y position
	 */
	public static void drawLine (float x1, float y1, float x2, float y2) {
		x2 = (float)(Math.floor(x2) + (x1 - Math.floor(x1)));
		y2 = (float)(Math.floor(y2) + (y1 - Math.floor(y1)));
		
		float decimalX = x1 - (float)Math.floor(x1);
		float decimalY = y1 - (float)Math.floor(y1);
		
		Point[] points = MathUtils.getLinePoints((int)Math.floor(x1), (int)Math.floor(y1), (int)Math.floor(x2), (int)Math.floor(y2));
		
		g.setColor(color);
		for (int i = 0; i < points.length; i ++) {
			for (int j = 0; j < scaleX; j ++) {	
				g.drawLine((int)(points[i].x * scaleX + j) + (int)(decimalX * scaleX), (int)(points[i].y * scaleY) + (int)(decimalY * scaleY), (int)(points[i].x * scaleX + j) + (int)(decimalX * scaleX), (int)(points[i].y * scaleY + scaleY - 1) + (int)(decimalY * scaleY));
			}
		}
	}
	
	/**
	 * Draw a polygon connected with lines
	 * @param p The polygon to draw
	 */
	public static void drawPolygon (Polygon p) {
		for (int i = 0; i < p.npoints; i ++) {
			int index1 = i;
			int index2 = i + 1;
			
			if (index2 > p.npoints - 1) index2 = 0;
			
			drawLine(p.xpoints[index1], p.ypoints[index1], p.xpoints[index2], p.ypoints[index2]);
		}
	}
	
	public static void drawPolygon (int[] xpoints, int[] ypoints, int npoints) {
		drawPolygon(new Polygon(xpoints, ypoints, npoints));
	}
	
	/**
	 * Draws a rectangle, either filled or just the outline, to the screen,
	 * given an x and y position and a width and height.
	 * @param x The x position of the rectangle 
	 * @param y The y position of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param filled Whether or not the rectangle is filled
	 */
	public static void drawRectangle (float x, float y, float width, float height, boolean filled) {
		Rectangle r = getScaledRectangle(x, y, width, height);
		g.setColor(color);
		
		if (filled) {
			g.fillRect(r.x, r.y, r.width, r.height);
			return;
		}
		
		for (int i = 0; i < Math.max(scaleX, scaleY); i ++) {
			g.drawRect(r.x + (int)MathUtils.clamp(i, 0, scaleX), r.y + (int)MathUtils.clamp(i, 0, scaleY), r.width - (int)MathUtils.clamp(i, 0, scaleX) * 2 - 1, r.height - (int)MathUtils.clamp(i, 0, scaleY) * 2 - 1);
		}
	}
	
	/**
	 * Draw a {@link BufferedImage} on the screen
	 * @param image The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param alpha The alpha of the image
	 * @param angle The angle to draw the image at
	 * @param anchorX The x position of the anchor for the angle
	 * @param anchorY The y position of the anchor for the angle
	 */
	public static void drawImage (BufferedImage image, float x, float y, float width, float height, float alpha, float angle, float anchorX, float anchorY) {
		Rectangle r = getScaledRectangle(x, y, width, height);
		
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		Composite originalComposite = g.getComposite();
		trans.setTransform(identity);
		trans.translate(r.x, r.y);
		trans.rotate(Math.toRadians(angle), anchorX * scaleX, anchorY * scaleY);
		trans.scale(width / image.getWidth() * scaleX, height / image.getHeight() * scaleY);
		
		g.setComposite(composite);
		g.drawImage(image, trans, null);
		g.setComposite(originalComposite);
	}
	
	/**
	 * Draw a {@link BufferedImage} on the screen
	 * @param image The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param alpha The alpha of the image
	 * @param angle The angle to draw the image at
	 */
	public static void drawImage (BufferedImage image, float x, float y, float width, float height, float alpha, float angle) {
		drawImage(image, x, y, width, height, alpha, angle, 0, 0);
	}
	
	/**
	 * Draw a {@link BufferedImage} on the screen
	 * @param image The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 * @param width The width of the image
	 * @param height The height of the image
	 * @param alpha The alpha of the image
	 */
	public static void drawImage (BufferedImage image, float x, float y, float width, float height, float alpha) {
		drawImage(image, x, y, width, height, alpha, 0);
	}
	
	/**
	 * Draw a {@link BufferedImage} on the screen
	 * @param image The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 * @param width The width of the image
	 * @param height The height of the image
	 */
	public static void drawImage (BufferedImage image, float x, float y, float width, float height) {
		drawImage(image, x, y, width, height, 1.0F);
	}
	
	/**
	 * Draw a {@link BufferedImage} on the screen
	 * @param image The image to draw
	 * @param x The x position of the image
	 * @param y The y position of the image
	 */
	public static void drawImage (BufferedImage image, float x, float y) {
		drawImage(image, x, y, image.getWidth(), image.getHeight());
	}
	
	public static void drawText (float x, float y, String text, BufferedImage[] font, boolean shadow, boolean raw) {
		int charWidth = font[0].getWidth();
		int charHeight = font[0].getHeight();
		
		int posX = 0;
		int posY = 0;
		
		float offsetX = 0;
		float offsetY = 0;
		
		boolean isCommand = false;
		String command = "";
		
		Color color = Draw.getColor();
		
		float shake = 0F;
		int shakeChance = 0;
		
		for (int i = 0; i < text.length(); i ++) {
			char c = text.charAt(i);
			
			float drawX = x + (charWidth) * posX + offsetX;
			float drawY = y + (charHeight) * posY + offsetY;
			
			if (isCommand) {
				if (c == '>') {
					isCommand = false;
					
					String[] args = command.split("\\s+");
					
					if (args[0].equals("color")) color = new Color(Integer.parseInt(args[1], 16));
					else if (args[0].equals("lt")) drawChar(drawX, drawY, '<', font, shadow, color, shake, shakeChance);
					else if (args[0].equals("gt")) drawChar(drawX, drawY, '>', font, shadow, color, shake, shakeChance);
					else if (args[0].equals("offset")) {
						if (args[1].equals("x")) offsetX = Float.parseFloat(args[2]);
						else if (args[1].equals("y")) offsetY = Float.parseFloat(args[2]);
					}
					else if (args[0].equals("shake")) shake = Float.parseFloat(args[1]);
					else if (args[0].equals("shakechance")) shakeChance = Integer.parseInt(args[1]);
					else if (args[0].equals("shakereset")) {
						shake = 0.5F;
						shakeChance = 100;
					}
					
					command = "";
				}
				else {
					command += c;
				}
			}
			else {
				if (c == '<' && !raw) isCommand = true;
				else if (c == '\n') {
					posX = 0;
					posY ++;
				}
				else if (c == '\t') {
					posX += 4;
				}
				else {
					drawChar(drawX, drawY, c, font, shadow, color, shake, shakeChance);
					posX ++;
				}
			}
		}
	}
	
	public static void drawChar (float x, float y, char c, BufferedImage[] font, boolean shadow, Color color, float shake, int shakeChance) {
		BufferedImage image = font[c];
		Random rand = new Random();
		
		if (rand.nextInt(MathUtils.clamp(shakeChance, 1, 100000)) == 0 && shakeChance != 0) {
			x += rand.nextFloat() * MathUtils.clamp(shake, 0, 10) * (rand.nextBoolean() ? 1 : -1);
			y += rand.nextFloat() * MathUtils.clamp(shake, 0, 10) * (rand.nextBoolean() ? 1 : -1);
		}
		
		float shadowX = x - 1;
		float shadowY = y - 1;
		
		if (shadow) {
			Draw.drawImage(image, shadowX, shadowY);
			Draw.g.setXORMode(new Color(0x000000));
			Draw.drawImage(image, shadowX, shadowY);
			Draw.g.setPaintMode();
		}
		
		Draw.drawImage(image, x, y);
		Draw.g.setXORMode(color);
		Draw.drawImage(image, x, y);
		Draw.g.setPaintMode();
	}
	
	private static Rectangle getScaledRectangle (float x, float y, float width, float height) {
		return new Rectangle((int)Math.ceil(x * scaleX), (int)Math.ceil(y * scaleY), (int)Math.ceil(width * scaleX), (int)Math.ceil(height * scaleY));
	}

	public static float getScaleX () {
		return scaleX;
	}

	public static void setScaleX (float scaleX) {
		Draw.scaleX = scaleX;
	}

	public static float getScaleY () {
		return scaleY;
	}

	public static void setScaleY (float scaleY) {
		Draw.scaleY = scaleY;
	}

	public static Color getColor () {
		return color;
	}

	public static void setColor (Color color) {
		Draw.color = color;
	}
}

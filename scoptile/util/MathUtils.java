package com.scoptile.util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class MathUtils {
	/**
	 * Clamps a given number, n, between a smaller and larger number,
	 * min and max.
	 * @param n The number to clamp
	 * @param min The minimum number
	 * @param max The maximum number
	 * @return The clamped number
	 */
	public static double clamp (double n, double min, double max) {
		return (n < min ? min : n > max ? max : n);
	}
	
	/**
	 * Clamps a given number, n, between a smaller and larger number,
	 * min and max.
	 * @param n The number to clamp
	 * @param min The minimum number
	 * @param max The maximum number
	 * @return The clamped number
	 */
	public static long clamp (long n, long min, long max) {
		return (n < min ? min : n > max ? max : n);
	}
	
	/**
	 * Clamps a given number, n, between a smaller and larger number,
	 * min and max.
	 * @param n The number to clamp
	 * @param min The minimum number
	 * @param max The maximum number
	 * @return The clamped number
	 */
	public static float clamp (float n, float min, float max) {
		return (n < min ? min : n > max ? max : n);
	}
	
	/**
	 * Clamps a given number, n, between a smaller and larger number,
	 * min and max.
	 * @param n The number to clamp
	 * @param min The minimum number
	 * @param max The maximum number
	 * @return The clamped number
	 */
	public static int clamp (int n, int min, int max) {
		return (n < min ? min : n > max ? max : n);
	}
	
	/**
	 * Returns whether or not two {@link FloatRectangle}s overlap
	 * @param r1 The first FloatRectangle
	 * @param r2 The second FloatRectangle
	 * @return Whether or not the FloatRectangles overlap
	 */
	public static boolean rectanglesOverlap (FloatRectangle r1, FloatRectangle r2) {
		return (r1.x + r1.width > r2.x &&
				r1.x < r2.x + r2.width &&
				r1.y + r1.height > r2.y &&
				r1.y < r2.y + r2.height);
	}
	
	/**
	 * Returns a {@link Point} array containing each point on a line, given
	 * two points.
	 * @param x1 The x position of the first point
	 * @param y1 The y position of the first point
	 * @param x2 The x position of the second point
	 * @param y2 The y position of the second point
	 * @return The points on the line
	 */
	public static Point[] getLinePoints (int x1, int y1, int x2, int y2) {
		int w = x2 - x1;
		int h = y2 - y1;
		
		int dx1 = 0;
		int dy1 = 0;
		int dx2 = 0;
		int dy2 = 0;
		
		if (w < 0) dx1 = -1;
		else if (w > 0) dx1 = 1;
		
		if (h < 0) dy1 = -1;
		else if (h > 0) dy1 = 1;
		
		if (w < 0) dx2 = -1;
		else if (w > 0) dx2 = 1;
		
		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		
		if (longest <= shortest) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			
			if (h < 0) dy2 = -1;
			else if (h > 0) dy2 = 1;
			
			dx2 = 0;
		}
		
		int numerator = longest >> 1;
		
		Point[] points = new Point[longest];
		
		for (int i = 0; i < longest; i ++) {
			points[i] = new Point(x1, y1);
			
			numerator += shortest;
			
			if (numerator >= longest) {
				numerator -= longest;
				
				x1 += dx1;
				y1 += dy1;
			}
			else {
				x1 += dx2;
				y1 += dy2;
			}
		}
		
		return points;
	}
	
	/**
	 * Creates a {@link Polygon} object from a given rectangle
	 * @param r The rectangle
	 * @return The created polygon
	 */
	public static Polygon getPolygonFromRectangle (Rectangle r) {
		return new Polygon(new int[] {r.x, r.x + r.width, r.x + r.width, r.x}, new int[] {r.y, r.y, r.y + r.height, r.y + r.height}, 4);
	}
	
	/**
	 * Creates a {@link Polygon} objcet from a given rectangle
	 * @param x The x position of the rectangle
	 * @param y The y position of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @return The created polygon
	 */
	public static Polygon getPolygonFromRectangle (int x, int y, int width, int height) {
		return getPolygonFromRectangle(new Rectangle(x, y, width, height));
	}
	
	/**
	 * Determines whether or not two given {@link Polygon}s overlap eachother
	 * @param p1 The first Polygon
	 * @param p2 The second Polygon
	 * @return Whether or not the two polygons overlap
	 */
	public static boolean polygonsOverlap (Polygon p1, Polygon p2) {
		Area area = new Area(p1);
		area.intersect(new Area(p2));
		return area.isEmpty();
	}
	
	/**
	 * Creates a moved instance of a given {@link Polygon}
	 * @param p The Polygon
	 * @param changeX How far to move the Polygon horizontally
	 * @param changeY How far to move the Polygon vertically
	 * @return The moved polygon
	 */
	public static Polygon getMovedPolygon (Polygon p, int changeX, int changeY) {
		Polygon newPolygon = new Polygon(p.xpoints, p.ypoints, p.npoints);
		
		for (int i = 0; i < newPolygon.xpoints.length; i ++) {
			newPolygon.xpoints[i] += changeX;
		}
		
		for (int i = 0; i < newPolygon.ypoints.length; i ++) {
			newPolygon.ypoints[i] += changeY;
		}
		
		return newPolygon;
	}
	
	/**
	 * Creates a {@link FloatRectangle} with the same dimensions as the
	 * given rectangle
	 * @param r The rectangle to convert
	 * @return The FloatRectangle created from the Rectangle
	 */
	public FloatRectangle rectangleToFloatRectangle (Rectangle r) {
		return new FloatRectangle(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * A rectangle that uses a float for the x, y, width, and height as opposed to integers
	 * like the normal {@link Rectangle}
	 * @author Scoptile
	 *
	 */
	public static class FloatRectangle {
		public float x;
		public float y;
		public float width;
		public float height;
		
		/**
		 * Creates a FloatRectangle object and initializes the dimensions
		 * @param x The x position of the rectangle
		 * @param y The y position of the rectangle
		 * @param width The width of the rectangle
		 * @param height The height of the rectangle
		 */
		public FloatRectangle (float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Creates a FloatRectangle object that has a position and a scale of 0, 0
		 */
		public FloatRectangle () {
			this(0, 0, 0, 0);
		}
		
		/**
		 * Returns a clone of the FloatRectangle with a modified x and y position
		 * @param changeX The change in x
		 * @param changeY The change in y
		 * @return The new FloatRectangle
		 */
		public FloatRectangle getMovedInstance (float changeX, float changeY) {
			return new FloatRectangle(x + changeX, y + changeY, width, height);
		}
		
		/**
		 * Returns a normal {@link Rectangle} with the (mostly) same dimensions as
		 * the FloatRectangle. The dimensions will, of course, be casted to integers.
		 * @return The normal rectangle
		 */
		public Rectangle toRectangle () {
			return new Rectangle((int)x, (int)y, (int)width, (int)height);
		}
	}
	
	/**
	 * A point that uses a float for the x and y positions as opposed to integers
	 * like the normal {@link Point}
	 * @author Scoptile
	 *
	 */
	public static class FloatPoint {
		public float x;
		public float y;
		
		/**
		 * Creates a {@link FloatPoint} and initializes the x and y position
		 * @param x The x position
		 * @param y The y position
		 */
		public FloatPoint (float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		/**
		 * Creates a {@link FloatPoint} with the position 0, 0
		 */
		public FloatPoint () {
			this(0, 0);
		}
	}
}

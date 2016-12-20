package com.scoptile.util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class ShapeUtils {
	public static Polygon createPolygonFromRectangle (Rectangle r) {
		return new Polygon(new int[] {r.x, r.x + r.width, r.x + r.width, r.x}, new int[] {r.y, r.y, r.y + r.height, r.y + r.height}, 4);
	}
	
	public static Polygon createPolygonFromRectangle (int x, int y, int width, int height) {
		return createPolygonFromRectangle(new Rectangle(x, y, width, height));
	}
	
	public static Polygon createPolygonFromTriangle (Point p1, Point p2, Point p3) {
		return new Polygon(new int[] {p1.x, p2.x, p3.x}, new int[] {p1.y, p2.y, p3.y}, 3);
	}
	
	public static Polygon createPolygonFromTriangle (int x1, int y1, int x2, int y2, int x3, int y3) {
		return createPolygonFromTriangle (new Point(x1, y1), new Point(x2, y2), new Point(x3, y3));
	}
}

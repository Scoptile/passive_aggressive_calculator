package com.scoptile.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage loadImage (String path) {
		BufferedImage image = null;
		
		if (ImageLoader.class.getResource(path) == null) {
			Console.print("Image '" + path + "' couldn't be found.", Console.TYPE_ERROR);
			return null;
		}
		
		try {
			image = ImageIO.read(new File(ImageLoader.class.getResource(path).toURI()));
		} catch (IOException | URISyntaxException e) {
			Console.error("Error loading image '" + path + "'.", e, false);
			return null;
		}
		
		Console.print("Image loaded '" + path + "'.");
		
		return image;
	}
}

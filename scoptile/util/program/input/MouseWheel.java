package com.scoptile.util.program.input;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseWheel implements MouseWheelListener {
	private int rotation;
	
	public void tick () {
		rotation = 0;
	}
	
	public void mouseWheelMoved (MouseWheelEvent e) {
		rotation = e.getWheelRotation();
	}

	public int getRotation () {
		return rotation;
	}
}

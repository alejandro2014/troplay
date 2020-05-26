package org.troplay.graphics;

import lombok.Getter;
import troplay.Drawable;
import troplay.Panel;

import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Scene {
	@Getter
	private BufferedImage buffer = new BufferedImage(946,644, BufferedImage.TYPE_INT_RGB);
	private Graphics2D g;
	private List<Drawable> drawables = new ArrayList<>();

	private Comparator<GraphicalUpdate> comparator = new GraphicalUpdateComparator();
	private PriorityQueue<GraphicalUpdate> queue = new PriorityQueue<GraphicalUpdate>(10, comparator);

	public void draw(Panel panel) {
		g = (Graphics2D) panel.getBufferActual().getGraphics();

		for(Drawable drawable : drawables) {
			Image image = drawable.getCurrentImage();
			int x = (int) drawable.getPoint().getX();
			int y = (int) drawable.getPoint().getY();

			g.drawImage(image, x, y, panel);
		}

		panel.repaint();
	}

	public void addToQueue(BufferedImage image, Point coords) {
		GraphicalUpdate graphicalUpdate = GraphicalUpdate.builder()
				.image(image)
				.point(coords)
				.z(0)
				.build();
		queue.add(graphicalUpdate);
	}

    public void addDrawable(Drawable drawable) {
		this.drawables.add(drawable);

		System.out.println(drawables);
    }
}

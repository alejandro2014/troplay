package org.troplay.graphics;

import troplay.Panel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Scene {
    Comparator<GraphicalUpdate> comparator = new GraphicalUpdateComparator();
    PriorityQueue<GraphicalUpdate> queue = new PriorityQueue<GraphicalUpdate>(10, comparator);

	public void update(BufferedImage image, Point coords) {
		GraphicalUpdate graphicalUpdate = GraphicalUpdate.builder()
				.image(image)
				.point(coords)
				.z(0)
				.build();
		queue.add(graphicalUpdate);
	}

	public void draw(Graphics2D g, Panel panel) {
		while(!queue.isEmpty()) {
			GraphicalUpdate graphicalUpdate = queue.poll();

			Image image = graphicalUpdate.getImage();
			int x = (int) graphicalUpdate.getPoint().getX();
			int y = (int) graphicalUpdate.getPoint().getY();

			g.drawImage(image, x, y,null);
			panel.setUltimaActualizacion(panel.getElementos()[0]);
		}
	}
}

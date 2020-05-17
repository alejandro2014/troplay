package org.troplay.graphics;

import troplay.Drawable;

import java.awt.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DrawQueue {
	Comparator<GraphicalUpdate> comparator = new GraphicalUpdateComparator();
	PriorityQueue<GraphicalUpdate> queue = new PriorityQueue<GraphicalUpdate>(10, comparator);
	
	public void insert(Drawable drawable) {
		/*GraphicalUpdate graphicalUpdate = new GraphicalUpdate();
		graphicalUpdate.setPoint(drawable.getPoint());
		graphicalUpdate.setImage(drawable.getImage());
		graphicalUpdate.setZ(drawable.getCz());
		
		queue.add(graphicalUpdate);*/
	}

	public Drawable getUpdate() {
		/*GraphicalUpdate graphicalUpdate = queue.poll();

		Image image = graphicalUpdate.getImage();
		int x = (int) graphicalUpdate.getPoint().getX();
		int y = (int) graphicalUpdate.getPoint().getY();*/

		return null;
	}
}

class GraphicalUpdateComparator implements Comparator<GraphicalUpdate> {
	@Override
	public int compare(GraphicalUpdate update1, GraphicalUpdate update2) {
		int update1z = update1.getZ();
		int update2z = update2.getZ();
		
		if(update1z < update2z) return -1;
		else if(update1z > update2z) return 1;
		
		return 0;
	}
}


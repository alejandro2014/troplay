package org.troplay.graphics;

import java.util.Comparator;
import java.util.PriorityQueue;
import troplay.Drawable;

public class DrawQueue {
	Comparator<GraphicalUpdate> comparator = new GraphicalUpdateComparator();
	PriorityQueue<GraphicalUpdate> queue = new PriorityQueue<GraphicalUpdate>(10, comparator);
	
	public void insertDrawable(Drawable drawable) {
		GraphicalUpdate graphicalUpdate = new GraphicalUpdate(drawable.getCoords(), drawable.getImage(), drawable.getCz());
		
		queue.add(graphicalUpdate);
	}
}

class GraphicalUpdateComparator implements Comparator<GraphicalUpdate> {
	@Override
	public int compare(GraphicalUpdate update1, GraphicalUpdate update2) {
		int update1z = update1.z();
		int update2z = update2.z();
		
		if(update1z < update2z) return -1;
		
		if(update1z > update2z) return 1;
		
		return 0;
	}
}


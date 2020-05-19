package org.troplay.graphics;

import java.util.Comparator;

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


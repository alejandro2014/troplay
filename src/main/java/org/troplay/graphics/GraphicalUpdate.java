package graphics;

import java.awt.Image;
import java.awt.Point;

public class GraphicalUpdate {
	private Point coords;
	private Image image;
	private int z;

	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
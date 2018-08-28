package troplay;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Dibujable {
    protected boolean mostrar = true;
	protected Point coords = new Point();
    protected Rectangle rectangulo = null;

    public int getCx() {
		return coords.x;
	}

    public int getCy() {
		return coords.y;
	}

    public int getCz() {
		return 0;
	}

	public Point getCoords() {
		return coords;
	}

    public boolean getMostrar() {
		return mostrar;
	}

    public void setXY(int nuevaX, int nuevaY) {
		coords.x = nuevaX;
		coords.y = nuevaY;
	}

	public void setCoords(Point coords) {
		this.coords = coords;
	}

    public void setCx(int nuevaX) {
		coords.x = nuevaX;
	}

    public void setCy(int nuevaY) {
		coords.y = nuevaY;
	}

    public void setMostrar(boolean nuevoMostrar) {
		mostrar = nuevoMostrar;
	}

    public void setRectangulo(Rectangle nuevoRectangulo) {
		rectangulo = nuevoRectangulo;
	}

	public boolean colision(Point ratonCoords) {
		return rectangulo.contains(ratonCoords);
	}

	public Image getImage() {
		return null;
	}
}

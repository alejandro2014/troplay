package troplay;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Clase de la que heredarán todas las que se puedan dibujar
 * @author alejandro
 */
public class Dibujable {
    protected boolean mostrar = true;
	protected Point coords = new Point();
    protected Rectangle rectangulo = null;
    
    public int getCx() {return coords.x;}
    public int getCy() {return coords.y;}
    public boolean getMostrar() {return mostrar;}
    
    public void setXY(int nuevaX, int nuevaY) {
		coords.x = nuevaX;
		coords.y = nuevaY;
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
    
    /**
     * Determinacion de la colision con el raton
     * @return true si hay colision con el raton, false si no
     */
    public boolean colision(int ratonX, int ratonY) {
		return rectangulo.contains(ratonX,ratonY);
	}
}

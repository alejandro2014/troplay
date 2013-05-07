package troplay;

import java.awt.Rectangle;

/**
 * Clase de la que heredarán todas las que se puedan dibujar
 * @author alejandro
 */
public class Dibujable {
    protected boolean mostrar = true;
    protected int cx = 0, cy = 0;
    protected Rectangle rectangulo = null;
    
    //Métodos get
    public int getCx() {return cx;}
    public int getCy() {return cy;}
    public boolean getMostrar() {return mostrar;}
    
    //Métodos set
    public void setXY(int nuevaX, int nuevaY) {cx=nuevaX; cy=nuevaY;}
    public void setCx(int nuevaX) {cx=nuevaX;}
    public void setCy(int nuevaY) {cy=nuevaY;}
    public void setMostrar(boolean nuevoMostrar) {mostrar = nuevoMostrar;}
    public void setRectangulo(Rectangle nuevoRectangulo) {rectangulo = nuevoRectangulo;}
    
    /**
     * Determinacion de la colision con el raton
     * @return true si hay colision con el raton, false si no
     */
    public boolean colision(int ratonX, int ratonY) {return rectangulo.contains(ratonX,ratonY);}
}

package troplay;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase utilizada para el control del ratón
 * @author alejandro
 */
public class Raton implements MouseListener {
    private Panel panel = null;
	private Point coords = new Point();
    private boolean ratonPulsado;
    
    /**
     * Añade el "escuchador" del ratón a la ventana del juego
     * @param refPanel Referencia al panel sobre el que trabajará
     */
    public Raton(Panel refPanel) {
        panel = refPanel;
        panel.addMouseListener(this);
        ratonPulsado = false;
    }
    
    /*
     * Pulsación y liberación del ratón
     */
    public void mousePressed(MouseEvent e) {
        //Sólo vale cuando se pulsa el botón izquierdo
        if(e.getButton() == MouseEvent.BUTTON1) {
			coords.x = e.getX();
			coords.y = e.getY();
            ratonPulsado = true;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
		coords.x = e.getX();
		coords.y = e.getY();
        ratonPulsado = false;
    }
    
    public void mouseExited(MouseEvent event) {}
    public void mouseClicked(MouseEvent event) {}    
    public void mouseEntered(MouseEvent event) {}
    
	public Point getCoords() {
		return coords;
	}
	
    public boolean getEstado() {
		return ratonPulsado;
	}
}

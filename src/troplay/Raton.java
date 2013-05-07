package troplay;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase utilizada para el control del ratón
 * @author alejandro
 */
public class Raton implements MouseListener {
    //private Ventana ventana;
    private Panel panel = null;
    private int coordx, coordy;
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
            coordx = e.getX();
            coordy = e.getY();
            ratonPulsado = true;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        coordx = e.getX();
        coordy = e.getY();
        ratonPulsado = false;
    }
    
    public void mouseExited(MouseEvent event) {}
    public void mouseClicked(MouseEvent event) {}    
    public void mouseEntered(MouseEvent event) {}
    
    public int getX() {return coordx;}
    public int getY() {return coordy;}
    public boolean getEstado() {return ratonPulsado;}
}

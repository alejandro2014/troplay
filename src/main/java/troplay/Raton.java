package troplay;

import lombok.Getter;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Raton implements MouseListener {
    private Panel panel = null;

    @Getter
	private Point coords = new Point();

    private boolean ratonPulsado;

    public Raton(Panel refPanel) {
        panel = refPanel;
        panel.addMouseListener(this);
        ratonPulsado = false;
    }

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
	
    public boolean getEstado() {
		return ratonPulsado;
	}
}

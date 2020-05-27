package troplay;

import lombok.Data;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Data
public class Mouse implements MouseListener {
    private Panel panel;
	private Point point;
    private Boolean mousePressed;

    public Mouse(Panel panel) {
        this.panel = panel;
        this.panel.addMouseListener(this);
        this.point = new Point();
        this.mousePressed = false;
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            point = e.getPoint();
            mousePressed = true;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
		point = e.getPoint();
        mousePressed = false;
    }
    
    public void mouseExited(MouseEvent event) {}
    public void mouseClicked(MouseEvent event) {}    
    public void mouseEntered(MouseEvent event) {}
}

package troplay;

import lombok.Data;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@Data
public class Mouse implements MouseListener {
	private Point point;
    private Boolean mouseClicked;

    public Mouse() {
        this.point = new Point();
        this.mouseClicked = false;
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            point = e.getPoint();
            this.mouseClicked = true;
        }
    }
    
    public void mouseReleased(MouseEvent e) {
		point = e.getPoint();
        this.mouseClicked = false;
    }
    
    public void mouseExited(MouseEvent event) {}
    public void mouseClicked(MouseEvent event) {}    
    public void mouseEntered(MouseEvent event) {}
}

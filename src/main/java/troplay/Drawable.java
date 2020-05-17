package troplay;

import lombok.Getter;
import lombok.Setter;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public class Drawable {
	@Getter @Setter
    protected Boolean show = true;

    @Getter @Setter
	protected Point point = new Point();

    @Setter
    protected Rectangle rectangle = null;

    public int getCx() {
		return point.x;
	}

    public int getCy() {
		return point.y;
	}

    public int getCz() {
		return 0;
	}

    public void setXY(int newX, int newY) {
		point.x = newX;
		point.y = newY;
	}

    public void setCx(int newX) {
		point.x = newX;
	}

    public void setCy(int newY) {
		point.y = newY;
	}

	public Boolean collision(Point mousePoint) {
		return rectangle.contains(mousePoint);
	}

	public Image getImage() {
		return null;
	}
}

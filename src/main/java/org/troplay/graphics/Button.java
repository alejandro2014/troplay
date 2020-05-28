package org.troplay.graphics;

import java.awt.*;

public class Button extends Drawable implements Clickable {
    private Boolean clicked = false;

    public Button(String graphicsPath, Rectangle rectangle) {
        super.loadGraphics(graphicsPath);

        this.setPoint(rectangle.getLocation());
        this.setRectangle(rectangle);

        int graphicIndex = (clicked ? 1 : 0);

        this.setCurrentImage(images.get(graphicIndex));
        this.setShow(true);
    }

    public void click() {

    }
}

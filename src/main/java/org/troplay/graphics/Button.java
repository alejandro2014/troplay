package org.troplay.graphics;

import java.awt.*;

public class Button extends Drawable {
    public Button(String graphicsPath, Rectangle rectangle) {
        super.loadGraphics(graphicsPath);

        this.setPoint(rectangle.getLocation());
        this.setRectangle(rectangle);
        this.setCurrentImage(images.get(0));
        this.setShow(true);
    }
}

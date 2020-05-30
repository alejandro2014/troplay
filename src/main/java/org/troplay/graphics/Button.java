package org.troplay.graphics;

import java.awt.*;

public class Button extends Drawable implements Clickable {
    public Button(String graphicsPath, Rectangle rectangle) {
        super.loadGraphics(graphicsPath);

        this.setPoint(rectangle.getLocation());
        this.setRectangle(rectangle);

        this.setCurrentImage(images.get(0));
        this.setShow(true);
    }

    public void click() {
        this.setCurrentImage(images.get(1));
    }

    public void release() {
        this.setCurrentImage(images.get(0));
    }

    public void sendEvent(String event, Point point) {
        if(event.equals("click") && collision(point)) {
            this.click();
        }

        if(event.equals("release") && collision(point)) {
            this.release();
        }
    }
}

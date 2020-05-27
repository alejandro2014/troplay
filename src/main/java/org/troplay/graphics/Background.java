package org.troplay.graphics;

import java.awt.*;
import java.io.IOException;

public class Background extends Drawable {
    public Background(String graphicsPath) throws IOException {
        super.loadGraphics(graphicsPath);

        this.setPoint(new Point(0, 0));
        this.setCurrentImage(images.get(0));
        this.setShow(true);
    }
}

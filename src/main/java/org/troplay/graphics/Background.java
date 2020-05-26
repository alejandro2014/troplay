package org.troplay.graphics;

import troplay.Drawable;

import java.awt.*;
import java.io.IOException;

public class Background extends Drawable {
    public Background(String graphicsPath) throws IOException {
        this.setGraphicsPath(graphicsPath);
        this.setPoint(new Point(0, 0));
        //this.setRefresh(false);

        super.loadGraphics();
        this.setCurrentImage(images.get(0));
        this.setShow(true);
    }
}

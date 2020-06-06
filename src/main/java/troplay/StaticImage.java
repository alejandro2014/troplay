package troplay;

import org.troplay.graphics.Drawable;

import java.awt.*;

public class StaticImage extends Drawable {

    public StaticImage(String imagePath, Point point) {
        super.loadGraphic(imagePath);

        this.point = point;
        this.currentImage = images.get(0);
        this.show = true;
    }
}

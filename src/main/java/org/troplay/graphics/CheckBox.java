package org.troplay.graphics;

import lombok.Data;

import java.awt.*;

@Data
public class CheckBox extends Drawable implements Clickable {
    private String value;
    private CheckboxContainer container;
    private Rectangle rectangle;

    public CheckBox(String value, Rectangle rectangle) {
        super.loadGraphics("common/checkbox");

        this.value = value;
        this.rectangle = rectangle;
        this.point = rectangle.getLocation();
        this.currentImage = images.get(0);
        this.show = true;
    }

    @Override
    public void click() {
        this.container.notifyChange(value);
        this.currentImage = images.get(1);
    }

    @Override
    public void release() {
        this.currentImage = images.get(0);
    }

    @Override
    public void sendEvent(String event, Point point) {

    }
}

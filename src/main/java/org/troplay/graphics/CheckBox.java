package org.troplay.graphics;

import lombok.Data;

import java.awt.*;

@Data
public class CheckBox extends Drawable implements Clickable {
    private String value;
    private CheckboxContainer container;
    private Rectangle rectangle;

    public CheckBox(String value, Rectangle rectangle) {
        this.value = value;

        this.rectangle = rectangle;
        this.rectangle.setSize(new Dimension(19, 19));

        super.loadGraphics("common/checkbox");

        this.setCurrentImage(images.get(0));
        this.setShow(true);
    }

    @Override
    public void click() {
        this.container.notifyChange(value);
        this.setCurrentImage(this.images.get(1));
    }

    @Override
    public void release() {
        this.setCurrentImage(this.images.get(0));
    }

    @Override
    public void sendEvent(String event, Point point) {

    }
}

package org.troplay.graphics;

import lombok.Data;

import java.awt.*;

@Data
public class CheckBox extends Drawable implements Clickable {
    private String value;
    private CheckboxContainer container;
    private Rectangle rectangle;

    public CheckBox(String value, Rectangle rectangle, CheckboxContainer container) {
        this.value = value;
        this.rectangle = rectangle;
        this.container = container;
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

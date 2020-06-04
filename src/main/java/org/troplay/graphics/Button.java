package org.troplay.graphics;

import troplay.GameStatus;
import troplay.enums.MainEvents;
import troplay.handlers.ButtonHandler;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Button extends Drawable implements Clickable {
    private GameStatus gameStatus;
    private Class actionClass;

    public Button(String graphicsPath, Rectangle rectangle, GameStatus gameStatus) {
        this(graphicsPath, rectangle, gameStatus,null);
    }

    public Button(String graphicsPath, Rectangle rectangle, GameStatus gameStatus, Class actionClass) {
        super.loadGraphics(graphicsPath);

        this.setPoint(rectangle.getLocation());
        this.setRectangle(rectangle);

        this.setCurrentImage(images.get(0));
        this.setShow(true);

        this.gameStatus = gameStatus;
        this.actionClass = actionClass;
    }

    public void click() {
        this.setCurrentImage(images.get(1));
    }

    public void release() {
        this.setCurrentImage(images.get(0));
        triggerEvent();
    }

    public void sendEvent(String event, Point point) {
        if(event.equals("click") && collision(point)) {
            this.click();
        }

        if(event.equals("release") && collision(point)) {
            this.release();
        }
    }

    public void triggerEvent() {
        if(actionClass == null) {
            return;
        }

        try {
            ButtonHandler instance = (ButtonHandler) actionClass
                    .getConstructor(GameStatus.class)
                    .newInstance(gameStatus);

            instance.triggerEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

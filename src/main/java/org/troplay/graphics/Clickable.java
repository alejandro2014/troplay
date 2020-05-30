package org.troplay.graphics;

import java.awt.*;

public interface Clickable {
    void click();

    void release();

    void sendEvent(String event, Point point);
}

package troplay;

import org.troplay.graphics.Scene;

import java.io.IOException;

public interface SubgameInterface {
    Scene createScene() throws IOException;
    void loop();
    void inputControl();
}

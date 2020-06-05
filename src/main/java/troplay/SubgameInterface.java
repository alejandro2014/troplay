package troplay;

import java.io.IOException;

public interface SubgameInterface {
    void createScene() throws IOException;
    void loop();
    void inputControl();
}

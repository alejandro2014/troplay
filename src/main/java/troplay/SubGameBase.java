package troplay;

import org.troplay.graphics.Scene;

public abstract class SubGameBase {
    public void frame(Scene scene, Panel panel) {
        scene.draw(panel);

        try {
            Thread.sleep(Const.MILLIS_SLEEP);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

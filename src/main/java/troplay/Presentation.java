package troplay;

import lombok.Data;
import org.troplay.graphics.Background;
import org.troplay.graphics.Scene;
import java.io.IOException;

@Data
public class Presentation implements Subgame {
    private final GameStatus gameStatus;
    private Scene scene;

    public Presentation(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;
        this.scene = createScene();

        createScene();
    }

    public Scene createScene() throws IOException {
        Scene scene = new Scene();

        Background background = new Background("common/background/presentation");
        scene.addDrawable(background);

        return scene;
    }

    public void loop() {
        int finalMeter = 60;
        int meter = 0;

        while (meter < finalMeter) {
            frame();
            meter++;
        }
    }

    public Boolean finalBucle() {
        return true;
    }

    private void frame() {
        scene.draw(gameStatus.getPanel());

        try {
            Thread.sleep(Const.MILLIS_SLEEP);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void controlEntrada() {

    }
}

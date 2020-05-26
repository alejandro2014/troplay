package troplay;

import lombok.Data;
import org.troplay.graphics.Background;
import org.troplay.graphics.Scene;
import java.io.IOException;

@Data
public class Presentation extends SubGameBase implements SubgameInterface {
    private GameStatus gameStatus;
    private Scene scene;
    private Panel panel;

    public Presentation(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;
        this.scene = createScene();
        this.panel = gameStatus.getPanel();
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
            frame(scene, panel);
            meter++;
        }
    }

    public void inputControl() {

    }
}

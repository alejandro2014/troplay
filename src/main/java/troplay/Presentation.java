package troplay;

import lombok.Data;
import org.troplay.graphics.Background;
import org.troplay.graphics.Scene;

import java.awt.*;
import java.io.IOException;

@Data
public class Presentation implements Subgame {
    private final GameStatus gameStatus;
    private Scene scene = new Scene();

    public Presentation(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;

        createScene();
    }

    public void createScene() throws IOException {
        Background background = new Background("common/background/presentation");

        scene.addDrawable(background);
    }

    public void loop() {
        int contadorFinal = 60;
        int contador = 0;

        while (contador < contadorFinal) {
            scene.draw(gameStatus.getPanel());
            //gameStatus.getPanel().paint(g);
            contador++;
            frame();
        }
    }

    public Boolean finalBucle() {
        return true;
    }

    private void frame() {
        try {
            Thread.sleep(70);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void controlEntrada() {

    }
}

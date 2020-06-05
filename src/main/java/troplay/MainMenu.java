package troplay;

import org.troplay.graphics.Background;
import org.troplay.graphics.Button;
import org.troplay.graphics.Scene;
import troplay.enums.MainEvents;
import troplay.handlers.ExitHandler;
import troplay.handlers.OptionsHandler;

import java.awt.*;

public class MainMenu extends SubGameBase implements SubgameInterface {
    private GameStatus gameStatus;
    private Scene scene;
    private Panel panel;
    private Mouse mouse;

    public MainMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.panel = gameStatus.getPanel();
        this.mouse = gameStatus.getMouse();

        this.gameStatus.setCurrentEvent(MainEvents.NULL);

        createScene();
    }

    @Override
    public void createScene() {
        scene = new Scene();

        Background background = new Background("common/background/presentation");
        scene.addDrawable(background);

        String language = "ES";

        Button startButton = new Button(language + "/buttons/start", new Rectangle(389,234, 165,46), gameStatus);
        Button optionsButton = new Button(language + "/buttons/options", new Rectangle(389,303, 165,46), gameStatus, OptionsHandler.class);
        Button exitButton = new Button(language + "/buttons/exit", new Rectangle(389,372, 165,46), gameStatus, ExitHandler.class);

        scene.addDrawable(startButton);
        scene.addDrawable(optionsButton);
        scene.addDrawable(exitButton);

        gameStatus.addClickable(startButton);
        gameStatus.addClickable(optionsButton);
        gameStatus.addClickable(exitButton);
    }

    public void loop() {
        boolean finish = false;

        while(!finish) {
            inputControl();

            frame(scene, panel);

            finish = endOfLoop();
        }
    }

    public void inputControl() {
        Boolean mouseClicked = mouse.getMouseClicked();
        Point mousePoint = mouse.getPoint();
        String eventType = mouseClicked ? "click" : "release";

        gameStatus.sendEvent(eventType, mousePoint);
    }

    public Boolean endOfLoop() {
        MainEvents currentEvent = gameStatus.getCurrentEvent();

        return (currentEvent == MainEvents.EXIT ||
                currentEvent == MainEvents.START ||
                currentEvent == MainEvents.BACK ||
                currentEvent == MainEvents.OPTIONS);
    }
}

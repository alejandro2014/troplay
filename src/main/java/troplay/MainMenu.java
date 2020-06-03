package troplay;

import org.troplay.graphics.*;
import org.troplay.graphics.Button;
import troplay.enums.Language;
import troplay.enums.MainEvents;
import troplay.handlers.ExitHandler;
import troplay.handlers.OptionsHandler;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;
    private final Scene scene;
    private Panel panel;

    private Language idioma;

    private Mouse mouse = null;
    private boolean acabar = false;

    public MainMenu(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;
        this.scene = createScene();
        this.panel = gameStatus.getPanel();
        this.idioma = gameStatus.getLanguage();
        this.mouse = gameStatus.getMouse();
    }

    @Override
    public Scene createScene() {
        Scene scene = new Scene();

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

        return scene;
    }

    public void loop() {
        while(!acabar) {
            manageInput();

            acabar = endOfLoop();

            frame(scene, panel);
        }
    }

    private void manageInput() {
        Boolean mouseClicked = mouse.getMouseClicked();
        Point mousePoint = mouse.getPoint();
        String eventType = mouseClicked ? "click" : "release";

        gameStatus.sendEvent(eventType, mousePoint);
    }

    public void inputControl() {

    }

    public Boolean endOfLoop() {
        MainEvents currentEvent = gameStatus.getCurrentEvent();

        return (currentEvent == MainEvents.EXIT ||
                currentEvent == MainEvents.START ||
                currentEvent == MainEvents.BACK ||
                currentEvent == MainEvents.OPTIONS);
    }


    /*private void createSceneOptionsMenu() {
        ArrayList conjCbxActual = null;

        botones[3] = createDrawable(new Rectangle(574,220, 165,46), false);

        checkboxes[0] = new CheckBox(conjCbxActual);
        checkboxes[0].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[0].getLocation());
        checkboxes[0].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[0]);
        checkboxes[0].setShow(false);

        checkboxes[1] = new CheckBox(conjCbxActual);
        checkboxes[1].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[1].getLocation());
        checkboxes[1].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[1]);
        checkboxes[1].setShow(false);

        checkboxes[2] = new CheckBox(conjCbxActual);
        checkboxes[2].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[2].getLocation());
        checkboxes[2].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[2]);
        checkboxes[2].setShow(false);

        checkboxes[3] = new CheckBox(conjCbxActual);
        checkboxes[3].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[3].getLocation());
        checkboxes[3].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[3]);
        checkboxes[3].setShow(false);

        checkboxes[4] = new CheckBox(conjCbxActual);
        checkboxes[4].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[4].getLocation());
        checkboxes[4].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[4]);
        checkboxes[4].setShow(false);

        checkboxes[5] = new CheckBox(conjCbxActual);
        checkboxes[5].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[5].getLocation());
        checkboxes[5].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[5]);
        checkboxes[5].setShow(false);
    }*/
}

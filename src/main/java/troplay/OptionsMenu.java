package troplay;

import org.troplay.graphics.Button;
import org.troplay.graphics.*;
import troplay.enums.MainEvents;
import troplay.handlers.BackHandler;

import java.awt.*;

public class OptionsMenu extends SubGameBase implements SubgameInterface {
    private GameStatus gameStatus;
    private Scene scene;
    private Panel panel;
    private Mouse mouse;

    public OptionsMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.panel = gameStatus.getPanel();
        this.mouse = gameStatus.getMouse();

        this.createScene();
    }

    @Override
    public void createScene() {
        scene = new Scene();

        Background background = new Background("common/background/optionsmenu");
        scene.addDrawable(background);

        String language = "ES";

        Button backButton = new Button(language + "/buttons/back", new Rectangle(574,220,165,46), gameStatus, BackHandler.class);
        scene.addDrawable(backButton);
        gameStatus.addClickable(backButton);

        CheckboxContainer containerLanguage = new CheckboxContainer();
        addCheckboxToScene(new CheckBox("ES", new Rectangle(346, 226, 19, 19)), containerLanguage);
        addCheckboxToScene(new CheckBox("EN", new Rectangle(513, 225, 19, 19)), containerLanguage);

        CheckboxContainer containerPlayers = new CheckboxContainer();
        addCheckboxToScene(new CheckBox("1", new Rectangle(317,384, 19, 19)), containerPlayers);
        addCheckboxToScene(new CheckBox("2", new Rectangle(476,384, 19, 19)), containerPlayers);
        addCheckboxToScene(new CheckBox("3", new Rectangle(317,484, 19, 19)), containerPlayers);
        addCheckboxToScene(new CheckBox("4", new Rectangle(480,485, 19, 19)), containerPlayers);

        StaticImage optionsText = new StaticImage(language + "/static-images/espTituloOpciones", new Point(310,66));
        scene.addDrawable(optionsText);

        StaticImage languageText = new StaticImage(language + "/static-images/espTituloIdioma", new Point(320,161));
        scene.addDrawable(languageText);

        StaticImage playersText = new StaticImage(language + "/static-images/espTituloJugadores", new Point(318,299));
        scene.addDrawable(playersText);
    }

    private void addCheckboxToScene(CheckBox checkbox, CheckboxContainer container) {
        container.add(checkbox);
        scene.addDrawable(checkbox);
        gameStatus.addClickable(checkbox);
    }

    public void loop() {
        boolean finish = false;

        while(!finish) {
            inputControl();

            frame(scene, panel);

            finish = endOfLoop();
        }

        /*new Point(574,220),
        new Point(346,226),
        new Point(513,225)*/

        //gameStatus.setPlayersNo(numJugadores);
        //gameStatus.setCurrentEvent(eventoRealizado);
    }

    public Boolean endOfLoop() {
        MainEvents currentEvent = gameStatus.getCurrentEvent();

        return (currentEvent == MainEvents.BACK);
    }

    public void inputControl() {
        Boolean mouseClicked = mouse.getMouseClicked();
        Point mousePoint = mouse.getPoint();
        String eventType = mouseClicked ? "click" : "release";

        gameStatus.sendEvent(eventType, mousePoint);
    }
}

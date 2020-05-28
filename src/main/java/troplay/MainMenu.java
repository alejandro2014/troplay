package troplay;

import org.troplay.graphics.Background;
import org.troplay.graphics.Button;
import org.troplay.graphics.Drawable;
import org.troplay.graphics.Scene;
import troplay.enums.Language;
import troplay.enums.MainEvents;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static troplay.enums.Language.ENGLISH;
import static troplay.enums.Language.SPANISH;

public class MainMenu extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;
    private final Scene scene;
    private Panel panel;

    private Language idioma;

    private Window window = null;
    private Mouse mouse = null;
    private boolean acabar = false;
    private MainEvents eventoRealizado = MainEvents.NULL;

    private final int NUM_BOTONES = 4;
    private final int NUM_CHECKBOXES = 6;

    private Drawable[] botones = new Drawable[NUM_BOTONES];
    private CheckBox[] checkboxes = new CheckBox[NUM_CHECKBOXES];
    private ArrayList conjCbxIdioma = new ArrayList();
    private ArrayList conjCbxJugadores = new ArrayList();

    private boolean mousePressed = false;

	private Point mousePoint = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    private boolean cambiadoCheckbox = false;
    private boolean cambiadoBoton = false;

    private List<Drawable> drawableList = new ArrayList<>();

    public MainMenu(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;
        this.scene = createScene();
        this.panel = gameStatus.getPanel();

        ArrayList conjCbxActual = null;
        int longBotones = botones.length;
        int longCbxIdioma = 2;
        int longCheckBox = checkboxes.length;
        int i;

        idioma = gameStatus.getLanguage();
        window = gameStatus.getWindow();

        this.mouse = gameStatus.getMouse();

        botones[3] = createDrawable(new Rectangle(574,220, 165,46), false);

        for(i = 0; i < longCheckBox; i++) {
            conjCbxActual = (i < longCbxIdioma ? conjCbxIdioma : conjCbxJugadores);

            checkboxes[i] = new CheckBox(conjCbxActual);
			checkboxes[i].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[i].getLocation());
            checkboxes[i].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[i]);
        }

        for(i = 0; i < longCheckBox; i++) {
            checkboxes[i].setShow(false);
        }
    }

    private Drawable createDrawable(Rectangle rectangle, Boolean show) {
        /*return Drawable.builder()
                .point(rectangle.getLocation())
                .rectangle(rectangle)
                .show(show)
                .build();*/
        return null;
    }

    @Override
    public Scene createScene() throws IOException {
        Scene scene = new Scene();

        Background background = new Background("common/background/presentation");
        scene.addDrawable(background);

        Button startButton = new Button("ES/buttons/start", new Rectangle(389,234, 165,46));
        Button optionsButton = new Button("ES/buttons/options", new Rectangle(389,303, 165,46));
        Button exitButton = new Button("ES/buttons/exit", new Rectangle(389,372, 165,46));

        scene.addDrawable(startButton);
        scene.addDrawable(optionsButton);
        scene.addDrawable(exitButton);

        return scene;
    }

    public void loop() {
        while(!acabar) {
            manageInput();

            acabar = endOfLoop();

            frame(scene, panel);
        }

        gameStatus.setCurrentEvent(eventoRealizado);
    }

    private void manageInput() {
        inputControl();

        if (mousePressed) {
            procesarEntrada();
        } else {
            cambiadoCheckbox = false;
        }

        if (botonPulsado != -1 && !mousePressed) {
            desencadenarAccion(botonPulsado);
            botonPulsado = -1;
        }
    }

    public Boolean endOfLoop() {
        return (eventoRealizado == MainEvents.EXIT ||
                eventoRealizado == MainEvents.START ||
                eventoRealizado == MainEvents.BACK ||
                eventoRealizado == MainEvents.OPTIONS);
    }

    public void inputControl() {
        mousePressed = mouse.getMousePressed();

        if (mousePressed) {
            controlColision();
        } /*else
            mousePressed = false;*/
    }

    private Boolean drawableClicked(Drawable drawable, Point mousePoint) {
        return drawable.getShow() && drawable.collision(mousePoint);
    }

    public void controlColision() {
        mousePoint = mouse.getPoint();

        scene.checkCollision(mousePoint);

        /*for(int i = 0; i < botones.length; i++) {
            if(drawableClicked(botones[i], mousePoint)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }

        for(int i = 0; i < checkboxes.length; i++) {
            if(drawableClicked(checkboxes[i], mousePoint)) {
                tipoColision = "checkBox";
                indiceColision = i;
                return;
            }
        }*/

        mousePressed = false;
    }

    public void procesarEntrada() {
        if (tipoColision.equals("checkBox")) {
            checkboxes[indiceColision].setActivado(true);

            if (indiceColision < 2) {
                if(!cambiadoCheckbox) {
                    idioma = (indiceColision == 0) ? SPANISH : ENGLISH;
                    gameStatus.setLanguage(idioma);
                    cambiadoCheckbox = true;
                }
            } else if(indiceColision < 6) {
                if(!cambiadoCheckbox) {

                    for(int i = 0; i < 4; i++) {
                        //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[6][(indiceColision-2 == i ? 1 : 0)], Const.ARR_RECTS_CHECKBOXES_MENU[i + 2].getLocation());
                    }

                    cambiadoCheckbox = true;
                }
            }
        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                int subind = (idioma == SPANISH) ? 1 : 3;
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
                botonPulsado = indiceColision;
                cambiadoBoton = true;
            }
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (idioma == SPANISH) ? 0 : 2;
        //panel.getScene().addToQueue(panel.getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
        cambiadoBoton = false;

        switch(numBoton) {
            case 0: eventoRealizado = MainEvents.START;  break;
            case 1: eventoRealizado = MainEvents.OPTIONS; break;
            case 2: eventoRealizado = MainEvents.EXIT;    break;
            case 3: eventoRealizado = MainEvents.BACK;   break;
        }
    }
}

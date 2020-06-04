package troplay;

import org.troplay.graphics.Background;
import org.troplay.graphics.Button;
import org.troplay.graphics.Drawable;
import org.troplay.graphics.Scene;
import troplay.enums.MainEvents;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static troplay.enums.Language.ENGLISH;
import static troplay.enums.Language.SPANISH;

public class OptionsMenu extends SubGameBase implements SubgameInterface {
    private int numJugadores;

    private Window window = null;
    private boolean acabar = false;
    private MainEvents eventoRealizado = MainEvents.NULL;

    private final int NUM_BOTONES = 4;
    private final int NUM_CHECKBOXES = 6;

    private Drawable[] botones = new Drawable[NUM_BOTONES];
    private CheckBox[] checkboxes = new CheckBox[NUM_CHECKBOXES];
    private ArrayList conjCbxIdioma = new ArrayList();
    private ArrayList conjCbxJugadores = new ArrayList();

    private boolean ratonPulsado = false;

	private Point coordsRaton = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    private boolean cambiadoCheckbox = false;
    private boolean cambiadoBoton = false;

    private GameStatus gameStatus;
    private Scene scene;
    private Panel panel;
    private Mouse mouse;

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

    public OptionsMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        this.scene = createScene();
        this.panel = gameStatus.getPanel();
        this.mouse = gameStatus.getMouse();

        //this.gameStatus.setCurrentEvent(MainEvents.NULL);

        /*ArrayList conjCbxActual = null;
        int longBotones = botones.length;
        int longCbxIdioma = 2;
        int longCheckBox = checkboxes.length;
        int i;

        numJugadores = gameStatus.getPlayersNo();

        window = gameStatus.getWindow();
        //panel = ventana.getPanel();

        //panel.setBuffer(2);
        //panel.setModo(Const.MODOOPCION);

        this.mouse = gameStatus.getMouse();

        for(i = 0; i < longBotones; i++) {
            botones[i] = new Drawable();
			botones[i].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[i].getLocation());
            botones[i].setRectangle(Const.ARR_RECTS_BUTTONS_MAIN_MENU[i]);
        }

        for(i = 0; i < longCheckBox; i++) {
            conjCbxActual = (i < longCbxIdioma ? conjCbxIdioma : conjCbxJugadores);

            checkboxes[i] = new CheckBox(conjCbxActual);
			checkboxes[i].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[i].getLocation());
            checkboxes[i].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[i]);
        }

        botones[0].setShow(false);
        botones[1].setShow(false);
        botones[2].setShow(false);
        botones[3].setShow(true);

        for(i = 0; i < longCheckBox; i++) {
            checkboxes[i].setShow(true);
        }

        int checkboxId = (gameStatus.getLanguage() == SPANISH) ? 0 : 1;
        checkboxes[checkboxId].setActivado(true);

        checkboxes[numJugadores + 1].setActivado(true);*/
    }

    @Override
    public Scene createScene() {
        Scene scene = new Scene();

        Background background = new Background("common/background/optionsmenu");
        scene.addDrawable(background);

        String language = "ES";

        Button backButton = new Button(language + "/buttons/back", new Rectangle(574,220, 165,46), gameStatus);

        scene.addDrawable(backButton);

        gameStatus.addClickable(backButton);

        return scene;
    }

    public void loop() {
        boolean finish = false;

        while(!finish) {
            inputControl();

            frame(scene, panel);

            finish = endOfLoop();
        }

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
        //ratonPulsado = mouse.getMousePressed();

        /*if (ratonPulsado) {
			coordsRaton = mouse.getPoint();
            controlColision();
        } else
            ratonPulsado = false;*/
    }

    /*private void controlColision() {
        int longitud = botones.length, i;

        for(i=0; i< longitud; i++) {
            if(botones[i].getShow() && botones[i].collision(coordsRaton)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }

        longitud = checkboxes.length;
        for(i=0; i<longitud; i++) {
            if (checkboxes[i].getShow() && checkboxes[i].collision(coordsRaton)) {
                tipoColision = "checkBox";
                indiceColision = i;
                return;
            }
        }

        ratonPulsado = false;
    }

    public void procesarEntrada() {
        if (tipoColision.equals("checkBox")) {
            checkboxes[indiceColision].setActivado(true);

            if (indiceColision < 2) {
                if(!cambiadoCheckbox) {
                    gameStatus.setLanguage(indiceColision == 0 ? SPANISH : ENGLISH);
                    cambiadoCheckbox = true;
                }
            } else if(indiceColision < 6) {
                if(!cambiadoCheckbox) {
                    numJugadores = indiceColision - 1;

                    for(int i = 0; i < 4; i++) {
                        //panel.getScene().addToQueue(panel.getArrayGraficos()[6][(indiceColision - 2 == i ? 1 : 0)], Const.ARR_RECTS_CHECKBOXES_MENU[i + 2].getLocation());
                    }

                    cambiadoCheckbox = true;
                }
            }

        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                int subind = (gameStatus.getLanguage() == SPANISH) ? 1 : 3;
                //panel.getScene().addToQueue(panel.getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
                botonPulsado = indiceColision;
                cambiadoBoton = true;
            }
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (gameStatus.getLanguage() == SPANISH) ? 0 : 2;
        //panel.getScene().addToQueue(panel.getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
        cambiadoBoton = false;

        switch(numBoton) {
            case 0: eventoRealizado = MainEvents.START;  break;
            case 1: eventoRealizado = MainEvents.OPTIONS; break;
            case 2: eventoRealizado = MainEvents.EXIT;    break;
            case 3: eventoRealizado = MainEvents.BACK;   break;
        }
    }*/
}

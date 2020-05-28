package troplay;

import org.troplay.graphics.*;
import org.troplay.graphics.Button;
import troplay.enums.Language;
import troplay.enums.MainEvents;

import java.awt.*;
import java.io.IOException;

import static troplay.enums.Language.ENGLISH;
import static troplay.enums.Language.SPANISH;

public class MainMenu extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;
    private final Scene scene;
    private Panel panel;

    private Language idioma;

    private Mouse mouse = null;
    private boolean acabar = false;
    private MainEvents eventoRealizado = MainEvents.NULL;

    private CheckBox[] checkboxes = new CheckBox[6];

    private boolean mousePressed = false;

	private Point mousePoint = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    private boolean cambiadoCheckbox = false;
    private boolean cambiadoBoton = false;

    public MainMenu(GameStatus gameStatus) throws IOException {
        this.gameStatus = gameStatus;
        this.scene = createScene();
        this.panel = gameStatus.getPanel();
        this.idioma = gameStatus.getLanguage();
        this.mouse = gameStatus.getMouse();
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
            //manageInput();

            acabar = endOfLoop();

            frame(scene, panel);
        }

        gameStatus.setCurrentEvent(eventoRealizado);
    }

    private void manageInput() {
        mousePressed = mouse.getMousePressed();

        if (!mousePressed) {
            mousePressed = false;
            cambiadoCheckbox = false;
            return;
        }

        mousePoint = mouse.getPoint();

        scene.checkCollision(mousePoint);

        //Clickable clickedDrawable = scene.getClickedDrawable();

        /*if(clickedDrawable != null) {
            clickedDrawable.click();
            clickedDrawable.setCurrentImage(clickedDrawable.getImages().get(1));
        }

        mousePressed = false;

        if (tipoColision.equals("checkBox")) {
            checkboxes[indiceColision].setActivado(true);

            if (!cambiadoCheckbox && (indiceColision == 0 && indiceColision == 1)) {
                idioma = (indiceColision == 0) ? SPANISH : ENGLISH;
                gameStatus.setLanguage(idioma);
                cambiadoCheckbox = true;
            }

            if(!cambiadoCheckbox && (indiceColision == 2 && indiceColision == 3 && indiceColision == 4 && indiceColision == 5)) {
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[6][indiceColision == 2 ? 1 : 0], Const.ARR_RECTS_CHECKBOXES_MENU[2].getLocation());
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[6][indiceColision == 3 ? 1 : 0], Const.ARR_RECTS_CHECKBOXES_MENU[3].getLocation());
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[6][indiceColision == 4 ? 1 : 0], Const.ARR_RECTS_CHECKBOXES_MENU[4].getLocation());
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[6][indiceColision == 5 ? 1 : 0], Const.ARR_RECTS_CHECKBOXES_MENU[5].getLocation());

                cambiadoCheckbox = true;
            }
        }

        if (!cambiadoBoton && tipoColision.equals("boton")) {
            int subind = (idioma == SPANISH) ? 1 : 3;
            //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
            botonPulsado = indiceColision;
            cambiadoBoton = true;
        }*/

        /*if (botonPulsado != -1 && !mousePressed) {
            desencadenarAccion(botonPulsado);
            botonPulsado = -1;
        }*/
    }

    public void inputControl() {

    }

    public Boolean endOfLoop() {
        return (eventoRealizado == MainEvents.EXIT ||
                eventoRealizado == MainEvents.START ||
                eventoRealizado == MainEvents.BACK ||
                eventoRealizado == MainEvents.OPTIONS);
    }

    private Boolean drawableClicked(Drawable drawable, Point mousePoint) {
        return drawable.getShow() && drawable.collision(mousePoint);
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

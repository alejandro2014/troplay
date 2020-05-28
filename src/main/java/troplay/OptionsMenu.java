package troplay;

import org.troplay.graphics.Drawable;
import org.troplay.graphics.Scene;
import troplay.enums.MainEvents;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static troplay.enums.Language.ENGLISH;
import static troplay.enums.Language.SPANISH;

public class OptionsMenu extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;
    private int numJugadores;

    private Window window = null;
    private Panel panel = null;
    private Mouse mouse = null;
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

    public OptionsMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        ArrayList conjCbxActual = null;
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

        checkboxes[numJugadores + 1].setActivado(true);
    }

    @Override
    public Scene createScene() throws IOException {
        return null;
    }

    public void loop() {
        while(!acabar) {
            inputControl();

            if (ratonPulsado)
                procesarEntrada();
            else
                cambiadoCheckbox = false;

            if (botonPulsado != -1 && !ratonPulsado) {
                desencadenarAccion(botonPulsado);
                botonPulsado = -1;
            }

            acabar = endOfLoop();

            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        gameStatus.setPlayersNo(numJugadores);
        gameStatus.setCurrentEvent(eventoRealizado);
    }

    public Boolean endOfLoop() {
        return (eventoRealizado == MainEvents.EXIT || eventoRealizado == MainEvents.START ||
                eventoRealizado == MainEvents.BACK || eventoRealizado == MainEvents.OPTIONS);
    }

    public void inputControl() {
        ratonPulsado = mouse.getMousePressed();

        if (ratonPulsado) {
			coordsRaton = mouse.getPoint();
            controlColision();
        } else
            ratonPulsado = false;
    }

    private void controlColision() {
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
    }
}

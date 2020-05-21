package troplay;

import troplay.enums.Language;
import troplay.enums.MainEvents;

import java.awt.*;
import java.util.ArrayList;

import static troplay.enums.Language.ENGLISH;
import static troplay.enums.Language.SPANISH;

public class MainMenu implements Subgame {
    private final GameStatus gameStatus;
    private Language idioma;

    private Ventana ventana = null;
    private Panel panel = null;
    private Raton raton = null;
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

    public MainMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        ArrayList conjCbxActual = null;
        int longBotones = botones.length;
        int longCbxIdioma = 2;
        int longCheckBox = checkboxes.length;
        int i;

        idioma = gameStatus.getLanguage();

        ventana = gameStatus.getWindow();
        panel = ventana.getPanel();

        panel.setBuffer(1);
        panel.setModo(Const.MODOMENU);

        this.raton = gameStatus.getMouse();

        for(i = 0; i < longBotones; i++) {
            botones[i] = new Drawable();
			botones[i].setPoint(Const.ARR_RECTS_BUTTONS_MAIN_MENU[i].getLocation());
            botones[i].setRectangle(Const.ARR_RECTS_BUTTONS_MAIN_MENU[i]);
        }

        for(i = 0; i < longCheckBox; i++) {
            conjCbxActual = (i < longCbxIdioma ? conjCbxIdioma : conjCbxJugadores);

            checkboxes[i] = new CheckBox(conjCbxActual);
			checkboxes[i].setPoint(Const.ARR_RECTS_CHECKBOXES_MENU[i].getLocation());
            checkboxes[i].setRectangle(Const.ARR_RECTS_CHECKBOXES_MENU[i]);
        }

        botones[0].setShow(true);
        botones[1].setShow(true);
        botones[2].setShow(true);
        botones[3].setShow(!true);

        for(i = 0; i < longCheckBox; i++) {
            checkboxes[i].setShow(false);
        }
    }

    public void loop() {
        while(!acabar) {
            controlEntrada();

            if (ratonPulsado) {
                procesarEntrada();
            } else {
                cambiadoCheckbox = false;
            }

            if (botonPulsado != -1 && !ratonPulsado) {
                desencadenarAccion(botonPulsado);
                botonPulsado = -1;
            }

            acabar = finalBucle();

            frame();
        }

        gameStatus.setCurrentEvent(eventoRealizado);
    }

    private void frame() {
        try {
            Thread.sleep(70);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public Boolean finalBucle() {
        return (eventoRealizado == MainEvents.EXIT || eventoRealizado == MainEvents.START ||
                eventoRealizado == MainEvents.BACK || eventoRealizado == MainEvents.OPTIONS);
    }

    public void controlEntrada() {
        ratonPulsado = raton.getEstado();

        if (ratonPulsado) {
			coordsRaton = raton.getCoords();
            controlColision();
        } else
            ratonPulsado = false;
    }

    public void controlColision() {
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

            if (indiceColision < 2) { //Selección del idioma
                if(!cambiadoCheckbox) {
                    if (indiceColision == 0) {
                        idioma = SPANISH;
                    } else {
                        idioma = ENGLISH;
                    }

                    gameStatus.setLanguage(idioma);
                    cambiadoCheckbox = true;
                }
            } else if(indiceColision < 6) { //Selección del número de jugadores
                if(!cambiadoCheckbox) {

                    for(int i = 0; i < 4; i++) {
                        panel.getScene().update(gameStatus.getPanel().getArrayGraficos()[6][(indiceColision-2 == i ? 1 : 0)], Const.ARR_RECTS_CHECKBOXES_MENU[i + 2].getLocation());
                    }

                    cambiadoCheckbox = true;
                }
            }
        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                int subind = (idioma == SPANISH) ? 1 : 3;
                panel.getScene().update(gameStatus.getPanel().getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
                botonPulsado = indiceColision;
                cambiadoBoton = true;
            }
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (idioma == SPANISH) ? 0 : 2;
        panel.getScene().update(panel.getArrayGraficos()[indiceColision][subind], Const.ARR_RECTS_BUTTONS_MAIN_MENU[indiceColision].getLocation());
        cambiadoBoton = false;

        switch(numBoton) {
            case 0: eventoRealizado = MainEvents.START;  break;
            case 1: eventoRealizado = MainEvents.OPTIONS; break;
            case 2: eventoRealizado = MainEvents.EXIT;    break;
            case 3: eventoRealizado = MainEvents.BACK;   break;
        }
    }
}

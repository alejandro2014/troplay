package troplay;

import java.awt.*;
import java.util.ArrayList;

import static troplay.Language.ENGLISH;
import static troplay.Language.SPANISH;

public class MainMenu extends ClaseControladora {
    private final GameStatus gameStatus;
    private Language idioma;

    private ControlFlujo controladora = null;
    private Ventana ventana = null;
    private Panel panel = null;
    private Raton raton = null;
    private boolean acabar = false;
    private int eventoRealizado = Const.EVENTO_NULO;

    private final int NUM_BOTONES = 4;
    private final int NUM_CHECKBOXES = 6;

    private Dibujable[] botones = new Dibujable[NUM_BOTONES];
    private CheckBox[] checkboxes = new CheckBox[NUM_CHECKBOXES];
    private ArrayList conjCbxIdioma = new ArrayList();
    private ArrayList conjCbxJugadores = new ArrayList();

    private Point[] coords = null;

    private boolean ratonPulsado = false;

	private Point coordsRaton = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    private boolean cambiadoCheckbox = false;
    private boolean cambiadoBoton = false;

    public MainMenu(GameStatus gameStatus, ControlFlujo control) {
        this.gameStatus = gameStatus;
        ArrayList conjCbxActual = null;
        int longBotones = botones.length;
        int longCbxIdioma = 2;
        int longCheckBox = checkboxes.length;
        int i;

        controladora = control;
        idioma = gameStatus.getLanguage();

        ventana = gameStatus.getWindow();
        panel = ventana.getPanel();
        this.raton = gameStatus.getMouse();
		coords = Const.ARR_COORDS_MENU;

        for(i = 0; i < longBotones; i++) {
            botones[i] = new Dibujable();
			botones[i].setCoords(coords[i]);
            botones[i].setRectangulo(Const.ARR_RECTS[i]);
        }

        for(i = 0; i < longCheckBox; i++) {
            conjCbxActual = (i < longCbxIdioma ? conjCbxIdioma : conjCbxJugadores);

            checkboxes[i] = new CheckBox(conjCbxActual);
			checkboxes[i].setCoords(coords[i + longBotones]);
            checkboxes[i].setRectangulo(Const.ARR_RECTS[i + 6]);
        }

        boolean valorVerdad = true;

        botones[0].setMostrar(valorVerdad);
        botones[1].setMostrar(valorVerdad);
        botones[2].setMostrar(valorVerdad);
        botones[3].setMostrar(!valorVerdad);

        for(i = 0; i < longCheckBox; i++) {
            checkboxes[i].setMostrar(!valorVerdad);
        }
    }

    public void bucleJuego() {
        while(!acabar) {
            controlEntrada();
            if (ratonPulsado)
                procesarEntrada();
            else
                cambiadoCheckbox = false;

            if (botonPulsado != -1 && !ratonPulsado) {
                desencadenarAccion(botonPulsado);
                botonPulsado = -1;
            }

            acabar = finalBucle();

            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {}
        }

        controladora.setEvento(eventoRealizado);
    }

    public boolean finalBucle() {
        return (eventoRealizado == Const.EVENTO_SALIR || eventoRealizado == Const.EVENTO_EMPEZAR ||
                eventoRealizado == Const.EVENTO_VOLVER || eventoRealizado == Const.EVENTO_OPCIONES);
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
            if(botones[i].getMostrar() && botones[i].colision(coordsRaton)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }

        longitud = checkboxes.length;
        for(i=0; i<longitud; i++) {
            if (checkboxes[i].getMostrar() && checkboxes[i].colision(coordsRaton)) {
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

                    for(int i = 0; i < 4; i++)
                        panel.insActualizacion(6, (indiceColision-2 == i ? 1 : 0), Const.ARR_COORDS_MENU[i+6]);

                    cambiadoCheckbox = true;
                }
            }
        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                int subind = (idioma == SPANISH) ? 1 : 3;
                panel.insActualizacion(indiceColision, subind, Const.ARR_COORDS_MENU[indiceColision]);
                botonPulsado = indiceColision;
                cambiadoBoton = true;
            }
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (idioma == SPANISH) ? 0 : 2;
        panel.insActualizacion(indiceColision, subind, Const.ARR_COORDS_MENU[indiceColision]);
        cambiadoBoton = false;

        switch(numBoton) {
            case 0: eventoRealizado = Const.EVENTO_EMPEZAR;  break;
            case 1: eventoRealizado = Const.EVENTO_OPCIONES; break;
            case 2: eventoRealizado = Const.EVENTO_SALIR;    break;
            case 3: eventoRealizado = Const.EVENTO_VOLVER;   break;
        }
    }
}

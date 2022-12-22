package troplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Clase abstracta que contiene las constantes del juego
 * @author alejandro
 */
public abstract class Const {
    public static final int NUM_IDIOMAS = 2;
    public static final int ESPAÑOL = 0;
    public static final int INGLES = 1;

    public static final int MAX_JUGADORES = 4;
    public static final int NUM_CASILLAS = 70;
    public static final int PREGS_POR_CASILLA = 3;
    public static final int NUM_RESPUESTAS = 3;

    public static final int NUM_DIFICULTADES = 3;
    public static final int BAJA = 0;
    public static final int MEDIA = 1;
    public static final int ALTA = 2;
    public static final int NUMCASIFACIL = 23;
    public static final int NUMCASIMEDIO = 23;
    public static final int NUMCASIDIFICIL = 23;

    public static final String DIRECTORIO_GRAFICOS = "./src/main/resources/graficos/";

    //Array con los nombres de los gráficos del juego
    public static final String[][] ARR_GRAFS = {
        {"espBotonEmpezar1.png","espBotonEmpezar2.png","ingBotonEmpezar1.png","ingBotonEmpezar2.png"}, //Botones del menú [0-3]
        {"espBotonOpciones1.png","espBotonOpciones2.png","ingBotonOpciones1.png","ingBotonOpciones2.png"},
        {"espBotonSalir1.png","espBotonSalir2.png","ingBotonSalir1.png","ingBotonSalir2.png"},
        {"espVolverMenu1.png","espVolverMenu2.png","ingVolverMenu1.png","ingVolverMenu2.png"},

        {"espBotonRespon1.png","espBotonRespon2.png","ingBotonRespon1.png","ingBotonRespon2.png"}, //Botones del juego [4-5]
        {"espBotonVolver1.png","espBotonVolver2.png","ingBotonVolver1.png","ingBotonVolver2.png"},
        {"checkBoxNo.png","checkBoxSi.png"}, //CheckBoxes [6]

        {"jugador1.png"},{"jugador2.png"},{"jugador3.png"},{"jugador4.png"}, //Elementos propios de la partida en sí [7-11]
        {"dado1.png","dado2.png","dado3.png","dado4.png","dado5.png","dado6.png"},

        //Letreros fijos del menú de opciones [12-15]
        {"espTituloIdioma.png","ingTituloIdioma.png"}, {"espTituloJugadores.png","ingTituloJugadores.png"},
        {"espTituloConexion.png","ingTituloConexion.png"}, {"espTituloOpciones.png","ingTituloOpciones.png"},

        {"jug11.png","jug12.png","jug13.png","jug14.png","jug15.png"}, //Indicadores del jugador actual [16-19]
        {"jug21.png","jug22.png","jug23.png","jug24.png","jug25.png"},
        {"jug31.png","jug32.png","jug33.png","jug34.png","jug35.png"},
        {"jug41.png","jug42.png","jug43.png","jug44.png","jug45.png"},

        {"fondoSabias.png"},{"sabiasEsp.png","sabiasIng.png"}, //Otros elementos del juego [20-21]

        {"presentacion.png"},{"inicio3.png"},{"tableroc.png"}, //Fondos [22-25]
        {"tablero1.png","tableroN.png"}
    };

	public static final Point[] ARR_COORDS_MENU = {
		new Point(389,234),
		new Point(389,303),
		new Point(389,372),
		new Point(574,220),
		new Point(346,226),
		new Point(513,225),
		new Point(317,384),
		new Point(476,384),
		new Point(317,484),
		new Point(480,485)
	};

    public static final Point[] ARR_COORDS_OPCIONES = {
		new Point(320,161),
		new Point(318,299),
		new Point(314,437),
		new Point(310,66)
	};

	public static final Point[] ARR_COORDS_JUEGO = {
		new Point(343,543),
		new Point(343,543),
        new Point(343,543),
		new Point(343,543),
        new Point(698,67), /**/
		new Point(746,470),
		new Point(746,512),
		new Point(703,20),
		new Point(703,50),
		new Point(703,80)
	};

    //Rectángulos correspondientes a los elementos pulsables
    public static final Rectangle ARR_RECTS[] = {
        new Rectangle(ARR_COORDS_MENU[0], new Dimension(165,46)), //Botones del menú principal
        new Rectangle(ARR_COORDS_MENU[1], new Dimension(165,46)),
        new Rectangle(ARR_COORDS_MENU[2], new Dimension(165,46)),
        new Rectangle(ARR_COORDS_MENU[3], new Dimension(165,46)),
        new Rectangle(ARR_COORDS_JUEGO[5], new Dimension(133,37)), //Botones del juego
        new Rectangle(ARR_COORDS_JUEGO[6], new Dimension(133,37)),

        new Rectangle(ARR_COORDS_MENU[4], new Dimension(19,19)), //Checkboxes del menú
        new Rectangle(ARR_COORDS_MENU[5], new Dimension(19,19)),
        new Rectangle(ARR_COORDS_MENU[6], new Dimension(19,19)),
        new Rectangle(ARR_COORDS_MENU[7], new Dimension(19,19)),
        new Rectangle(ARR_COORDS_MENU[8], new Dimension(19,19)),
        new Rectangle(ARR_COORDS_MENU[9], new Dimension(19,19)),

        new Rectangle(ARR_COORDS_JUEGO[7], new Dimension(19,19)), //Checkboxes del juego
        new Rectangle(ARR_COORDS_JUEGO[8], new Dimension(19,19)),
        new Rectangle(ARR_COORDS_JUEGO[9], new Dimension(19,19))
	};

    //Eventos de la máquina de estados
    public static final int EVENTO_EMPEZAR = 0;
    public static final int EVENTO_SALIR = 1;
    public static final int EVENTO_NULO = 2;
    public static final int EVENTO_OPCIONES = 3;
    public static final int EVENTO_VOLVER = 5;

    //Anchos de los textos en caracteres
    public static final int ANCHOPREGUNTA = 40;
    public static final int ANCHORESPUESTA = 28;
    public static final int ANCHOCURIOSIDAD = 44;

    public static final int MODOPRESEN = 0;
    public static final int MODOMENU = 1;
    public static final int MODOOPCION = 2;
    public static final int MODOJUEGO  = 3;
}

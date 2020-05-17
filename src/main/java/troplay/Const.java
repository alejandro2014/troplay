package troplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Const {
    public static final int ESPAÑOL = 0;

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

    public static final String DIRECTORIO_GRAFICOS = "/home/alejandro/programs/troplay/src/main/resources/graficos/";
    //public static final String DIRECTORIO_GRAFICOS = "/Users/alejandro/programs/troplay/src/main/resources/graficos/";

    //Coordenadas de los centros de cada casilla
	public static final Point arrayCasillas[] = {
		new Point(343, 543), new Point(401, 545), new Point(449, 545), new Point(499, 545), new Point(544, 547), new Point(585, 531),
		new Point(595, 488), new Point(595, 439), new Point(595, 389), new Point(595, 340), new Point(595, 293), new Point(595, 243),
		new Point(595, 194), new Point(595, 146), new Point(597, 103), new Point(582, 58), new Point(541, 54), new Point(494, 54),
		new Point(445, 54), new Point(395, 54), new Point(346, 54), new Point(296, 54), new Point(249, 54), new Point(199, 54),
		new Point(151, 54), new Point(107, 51), new Point(67, 71), new Point(55, 112), new Point(55, 160), new Point(55, 211),
		new Point(55, 259), new Point(55, 309), new Point(55, 358), new Point(53, 400), new Point(69, 438), new Point(112, 446),
		new Point(158, 447), new Point(207, 447), new Point(256, 447), new Point(305, 447), new Point(352, 447), new Point(402, 447),
		new Point(446, 447), new Point(487, 431), new Point(496, 391), new Point(497, 341), new Point(497, 291), new Point(497, 243),
		new Point(499, 201), new Point(486, 161), new Point(443, 152), new Point(394, 152), new Point(347, 152), new Point(295, 152),
		new Point(249, 152), new Point(205, 149), new Point(166, 167), new Point(154, 212), new Point(154, 261), new Point(150, 302),
		new Point(169, 339), new Point(210, 349), new Point(256, 349), new Point(305, 348), new Point(348, 351), new Point(388, 332),
		new Point(401, 296), new Point(385, 262), new Point(346, 251), new Point(285, 248)
	};

    //Array con los nombres de los gráficos del juego
    public static final String[][] ARR_GRAFS = {
        {"espBotonEmpezar1","espBotonEmpezar2","ingBotonEmpezar1","ingBotonEmpezar2"}, //Botones del menú [0-3]
        {"espBotonOpciones1","espBotonOpciones2","ingBotonOpciones1","ingBotonOpciones2"},
        {"espBotonSalir1","espBotonSalir2","ingBotonSalir1","ingBotonSalir2"},
        {"espVolverMenu1","espVolverMenu2","ingVolverMenu1","ingVolverMenu2"},

        {"espBotonRespon1","espBotonRespon2","ingBotonRespon1","ingBotonRespon2"}, //Botones del juego [4-5]
        {"espBotonVolver1","espBotonVolver2","ingBotonVolver1","ingBotonVolver2"},
        {"checkBoxNo","checkBoxSi"}, //CheckBoxes [6]

        {"jugador1"},{"jugador2"},{"jugador3"},{"jugador4"}, //Elementos propios de la partida en sí [7-11]
        {"dado1","dado2","dado3","dado4","dado5","dado6"},

        //Letreros fijos del menú de opciones [12-15]
        {"espTituloIdioma","ingTituloIdioma"}, {"espTituloJugadores","ingTituloJugadores"},
        {"espTituloConexion","ingTituloConexion"}, {"espTituloOpciones","ingTituloOpciones"},

        {"jug11","jug12","jug13","jug14","jug15"}, //Indicadores del jugador actual [16-19]
        {"jug21","jug22","jug23","jug24","jug25"},
        {"jug31","jug32","jug33","jug34","jug35"},
        {"jug41","jug42","jug43","jug44","jug45"},

        {"fondoSabias"},{"sabiasEsp","sabiasIng"}, //Otros elementos del juego [20-21]

        {"presentacion"},{"inicio3"},{"tableroc"}, //Fondos [22-25]
        {"tablero1","tableroN"}
    };

    //Diferentes fondos del juego
    public static final int FONDOPRES = 22;
    public static final int FONDOINIC = 23;
    public static final int FONDOTABL = 24;

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

	public static final Rectangle ARR_RECTS_BUTTONS_MAIN_MENU[] = {
		new Rectangle(new Point(389,234), new Dimension(165,46)),
		new Rectangle(new Point(389,303), new Dimension(165,46)),
		new Rectangle(new Point(389,372), new Dimension(165,46)),
		new Rectangle(new Point(574,220), new Dimension(165,46))
	};

	public static final Rectangle ARR_RECTS_BUTTONS_GAME[] = {
		new Rectangle(new Point(746,470), new Dimension(133,37)),
		new Rectangle(new Point(746,512), new Dimension(133,37))
	};

	public static final Rectangle ARR_RECTS_CHECKBOXES_MENU[] = {
		new Rectangle(new Point(346,226), new Dimension(19,19)),
		new Rectangle(new Point(513,225), new Dimension(19,19)),
		new Rectangle(new Point(317,384), new Dimension(19,19)),
		new Rectangle(new Point(476,384), new Dimension(19,19)),
		new Rectangle(new Point(317,484), new Dimension(19,19)),
		new Rectangle(new Point(480,485), new Dimension(19,19))
	};

	public static final Rectangle ARR_RECTS_CHECKBOXES_JUEGO[] = {
		new Rectangle(new Point(703,20), new Dimension(19,19)),
		new Rectangle(new Point(703,50), new Dimension(19,19)),
		new Rectangle(new Point(703,80), new Dimension(19,19))
	};

    public static final int ANCHOPREGUNTA = 40;
    public static final int ANCHORESPUESTA = 28;
    public static final int ANCHOCURIOSIDAD = 44;

    public static final int MODOPRESEN = 0;
    public static final int MODOMENU = 1;
    public static final int MODOOPCION = 2;
    public static final int MODOJUEGO  = 3;
}

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
    
    public static final String DIRECTORIO_GRAFICOS = "graficos/";
    
    //Coordenadas de los centros de cada casilla
    public static final int arrX[] = {343,401,449,499,544,585,595,595,595,595,595,595,595,595,597,582,541,494,445,
                                      395,346,296,249,199,151,107,67,55,55,55,55,55,55,53,69,112,158,207,256,305,
                                      352,402,446,487,496,497,497,497,499,486,443,394,347,295,249,205,166,154,154,
                                      150,169,210,256,305,348,388,401,385,346,285};
    
    public static final int arrY[] = {543,545,545,545,547,531,488,439,389,340,293,243,194,146,103,58,54,54,54,54,
                                      54,54,54,54,54,51,71,112,160,211,259,309,358,400,438,446,447,447,447,447,447,
                                      447,447,431,391,341,291,243,201,161,152,152,152,152,152,149,167,212,261,302,
                                      339,349,349,348,351,332,296,262,251,248};
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
    
    //Diferentes fondos del juego
    public static final int FONDOPRES = 22;
    public static final int FONDOINIC = 23;
    public static final int FONDOTABL = 24;

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
        new Point(698,67),
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
    
    //Estados del juego
    public static final int ESTADO_MENU = 0;
    public static final int ESTADO_PRESENTACION = 1;
    public static final int ESTADO_OPCIONES = 2;
    public static final int ESTADO_MENU_PRINCIPAL = 4;
    public static final int ESTADO_JUEGO = 5;
    public static final int ESTADO_FINAL = 6;
    
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
    
    //Dimensiones de la ventana
    public static final int ANCHOVENTANA = 946;
    public static final int ALTOVENTANA = 644;
    
    //Posiciones del bocadillo
    public static final int ARRIBA = 1;
    public static final int ABAJO = 2;
    public static final int IZQUIERDA = 3;
    public static final int DERECHA = 4;
    public static final int ARRIBAIZQ = 5;
    public static final int ARRIBADER = 6;
    public static final int ABAJOIZQ = 7;
    public static final int ABAJODER = 8;
    
    //Array con las posiciones
    public static final int arrBocad[] = {1,5,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,7,7,
                                          7,7,8,8,8,8,8,8,8,8,8,8,6,6,6,6,6,6,6,
                                          6,6,5,5,5,5,5,5,7,7,7,7,7,7,7,8,8,8,8,
                                          8,8,8,8,8,8,8,7,7,7,7,7,7};
    public static final int MODOPRESEN = 0;
    public static final int MODOMENU = 1;
    public static final int MODOOPCION = 2;
    public static final int MODOJUEGO  = 3;
}
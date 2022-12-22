package troplay;

import java.awt.Point;

public abstract class Const {
    public static final int NUM_IDIOMAS = 2;
    public static final int ESPAÑOL = 0;
    public static final int INGLES = 1;

    public static final int NUM_CASILLAS = 70;
    public static final int PREGS_POR_CASILLA = 3;

    public static final String DIRECTORIO_GRAFICOS = "./src/main/resources/graficos/";

	public static final Point[] ARR_COORDS_MENU = {
		new Point(389,234), //0
		new Point(389,303), //1
		new Point(389,372), //2
		new Point(574,220), //3
		new Point(346,226), //4
		new Point(513,225), //5
		new Point(317,384), //6
		new Point(476,384), //7
		new Point(317,484), //8
		new Point(480,485)  //9
	};

	public static final Point[] ARR_COORDS_JUEGO = {
		new Point(343,543), //0
		new Point(343,543), //1
        new Point(343,543), //2
		new Point(343,543), //3
        new Point(698,67),  //4
		new Point(746,470), //5
		new Point(746,512), //6
		new Point(703,20),  //7
		new Point(703,50),  //8
		new Point(703,80)   //9
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

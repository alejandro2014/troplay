package troplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Const {
    public static final int PREGS_POR_CASILLA = 3;

    public static final String DIRECTORIO_GRAFICOS = "/home/alejandro/programs/troplay/src/main/resources/graficos";
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

	public static final Rectangle ARR_RECTS_BUTTONS_MAIN_MENU[] = {
		new Rectangle(new Point(389,234), new Dimension(165,46)),
		new Rectangle(new Point(389,303), new Dimension(165,46)),
		new Rectangle(new Point(389,372), new Dimension(165,46)),
		new Rectangle(new Point(574,220), new Dimension(165,46))
	};

	public static final Rectangle ARR_RECTS_CHECKBOXES_MENU[] = {
		new Rectangle(new Point(346,226), new Dimension(19,19)),
		new Rectangle(new Point(513,225), new Dimension(19,19)),
		new Rectangle(new Point(317,384), new Dimension(19,19)),
		new Rectangle(new Point(476,384), new Dimension(19,19)),
		new Rectangle(new Point(317,484), new Dimension(19,19)),
		new Rectangle(new Point(480,485), new Dimension(19,19))
	};

    public static final int ANCHOPREGUNTA = 40;
    public static final int ANCHORESPUESTA = 28;

    public static final int MODOPRESEN = 0;
    public static final int MODOMENU = 1;
    public static final int MODOOPCION = 2;
    public static final int MODOJUEGO  = 3;

    public static final int SCREEN_WIDTH = 946;
    public static  final int SCREEN_HEIGHT = 644;
	public static final long MILLIS_SLEEP = 70;

	public static final String BASE_DIR_LINUX = "/home/alejandro/programs/troplay";
	public static final String BASE_DIR_MAC = "/Users/alejandroruperez/personal/troplay";
    public static final String BASE_DIR = BASE_DIR_LINUX;
}

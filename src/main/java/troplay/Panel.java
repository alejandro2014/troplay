package troplay;

import lombok.Getter;
import lombok.Setter;
import org.troplay.graphics.GraphicalUpdate;
import org.troplay.graphics.Scene;
import troplay.enums.BalloonPosition;
import troplay.enums.Language;
import troplay.game.Casilla;
import troplay.game.Jugador;
import troplay.game.Pregunta;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import static troplay.enums.BalloonPosition.ARRIBA;

public class Panel extends JPanel implements ActionListener {
    @Getter
    private Scene scene = new Scene();

    //Array con los nombres de los gráficos del juego
    private final String[][] ARR_GRAFS = {
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

            null,
            null,
            null,
            null,

            {"fondoSabias"},{"sabiasEsp","sabiasIng"}, //Otros elementos del juego [20-21]

            {"presentacion"},{"inicio3"},{"tableroc"}, //Fondos [22-25]
            {"tablero1","tableroN"}
    };

    //Diferentes fondos del juego
    private final int FONDOPRES = 22;
    private final int FONDOINIC = 23;
    private final int FONDOTABL = 24;

    private Font fuentePreguntas = new Font("LuciSans",Font.PLAIN,14);
    private Font fuenteCursiva = new Font("LuciSans",Font.ITALIC,14);
    private Font fuentePequeña = new Font("LuciSans",Font.PLAIN,10);
    private Font[] fuentes = {fuentePreguntas, fuenteCursiva, fuentePequeña, fuentePequeña};

    private Font fuenteCuriosidad = new Font("Lucida Bright", Font.PLAIN,18);
    private Font fuenteCurCuriosidad = new Font("Lucida Bright",Font.ITALIC,18);
    private Font fuentePeqCuriosidad = new Font("Lucida Bright",Font.PLAIN,14);
    private Font[] fuentesCuriosidad = {fuenteCuriosidad, fuenteCurCuriosidad, fuentePeqCuriosidad, fuentePeqCuriosidad};

    private Font fuenteEstado = new Font("LuciSans",Font.ITALIC,20);

    private final Color BLANCO = new Color(255,255,255);
    private final Color NEGRO = new Color(0,0,0);
    private final Color AMARILLO = new Color(240,240,0);

    private BalloonPosition posicionBocad = ARRIBA;

    @Setter
    private Integer numJugadores = 1;

    private int animEstado = -1, despAnim = 1;

    @Setter
    private Pregunta preguntaActual = null;

    public int lineaActual = 0;
    public int despX = 0;
    private int desp0;
    private int quedan;

    //Imagenes del juego
    @Getter
    private BufferedImage[][] arrayGraficos = null;

    //private BufferedImage bufferMenu = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    //private BufferedImage bufferOpciones = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    //private BufferedImage bufferJuego = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);

    private BufferedImage bufferActual = null;
    private BufferedImage bufferTablero = null;
    private BufferedImage bufferRespuestas = null;
    private BufferedImage bufferTablero1 = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferTableroN = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);

    @Setter
    private Game refGame = null;

    @Setter
    private Pregunta pregCuriosidad = null;

    private String cadenaEstado = "";
    private Timer timer = null;

    private final int ESPAÑOL = 0;

    private int idiomaJuego = ESPAÑOL;
    private int contadorTimer = 0;

    //Modos diferentes representando momentos del juego
    private int tipoDibujo = Const.MODOPRESEN;

    //Indicadores diciendo que se ha de crear la escena desde 0
    private boolean[] nuevoDibujo  = {true, true, true, true};
    private LinkedList colaActualizar = new LinkedList();

    @Getter
    private Integer[] elementos = {0,0,0,0};

    @Setter
    private int ultimaActualizacion = -1;

    @Setter
    private Boolean dibujadaCuriosidad = false;

    @Setter
    private Boolean dibujarPregunta = false;

    private boolean refrescarTablero = false;

    private Graphics2D g2d = null;
    private Graphics2D g3d = null;

    public Panel() {
        setBackground(Color.BLACK);
        this.cargaGraficos();
        setDoubleBuffered(true);

        timer = new Timer(70, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        actualizar();
        g2d = (Graphics2D)g;
        g2d.drawImage(bufferActual, 0, 0, this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void actualizar() {
        if(bufferActual == null) {
            return;
        }


        if(refrescarTablero) dibujarTablero();

        if(dibujarPregunta) dibujarEscenaPregunta();
        if(tipoDibujo == Const.MODOJUEGO) {
            dibujarEstado();
        }

        Graphics2D g = (Graphics2D) bufferActual.getGraphics();
        scene.draw(g, this);
    }

    public void actionPerformed(ActionEvent e) {
        contadorTimer++;
        repaint();
    }

    void dibujarEscenaPregunta() {
        Jugador jugadorActual = null;
        int i;

        g3d = (Graphics2D) bufferActual.getGraphics();

        //Dibujado de los jugadores
        for(i = 0; i < numJugadores; i++) {
            jugadorActual = refGame.getJugador(i);
            scene.update(arrayGraficos[i+7][0], new Point(jugadorActual.getCx(),jugadorActual.getCy()));
            //g3d.drawImage(arrayGraficos[i+7][0],jugadorActual.getCx(),jugadorActual.getCy(),null);
        }

        dibujarPregunta(g3d); //Bocadillo con la pregunta

        //Dibujado de los checkboxes con las respuestas
        refGame.setRespuestaSeleccionada(0);
        for(i = 0; i < 3; i++) {
            scene.update(arrayGraficos[6][(i == 0 ? 1 : 0)], refGame.getCheckBoxCoords(i));
        }

        dibujarPregunta = false;
    }

    void dibujarTablero() {
        Graphics2D g = (Graphics2D) bufferActual.getGraphics();

        if(bufferRespuestas == null) bufferRespuestas = arrayGraficos[24][0].getSubimage(684, 118, 256, 340);

        g.drawImage(bufferTablero, 6, 6, null);
        g.drawImage(bufferRespuestas, 684, 118, null);

        for(int i = 0; i < numJugadores; i++) {
			g.drawImage(arrayGraficos[i+7][0], refGame.getJugadorX(i), refGame.getJugadorY(i), null);
		}

        refrescarTablero = false;
    }

    public void dibujarEstado() {
          int jugadorActual = refGame.getJugadorActual();
          int desplaz;

          animEstado += despAnim;

          switch(animEstado) {
              case 5:  animEstado = 3; despAnim = -1; break;
              case -1: animEstado = 1; despAnim = 1; break;
          }

          for(int i = 0; i < numJugadores; i++) {
              desplaz = (i == jugadorActual ? animEstado: 0);
              //g.drawImage(playerGraphics[i][desplaz], 691 + i*63, 554, null);
              scene.update(playerGraphics[i][desplaz], new Point(i * 63 + 691, 554));
          }
      }

    public boolean dibujarCuriosidad() {
        ArrayList trozosCadena = pregCuriosidad.getTrozosCadena(0);
        ArrayList tiposCadena = pregCuriosidad.getTiposCadena(0);
        Graphics2D g = (Graphics2D) bufferActual.getGraphics();
        int numCadenas = pregCuriosidad.getNumTrozosCadena(trozosCadena);
        String cadenaActual = null;
        FontMetrics metrica = null;
        Font fuente = null;
        int x = 125, y = 190;
        boolean devuelto = true;
        int tipoFuente = -1;

        if(!dibujadaCuriosidad) {
            scene.update(arrayGraficos[20][0], new Point(100,100));
            scene.update(arrayGraficos[21][idiomaJuego], new Point(110,105));
            dibujadaCuriosidad = true;
        }

        //Escribe todas las cadenas que forman parte de la pregunta
        desp0 = despX = x;
        lineaActual = 0;

        quedan = 44;

        if(ultimaActualizacion == 21) {
            for(int i = 0; i < numCadenas; i++) {
                cadenaActual = (String)trozosCadena.get(i);
                tipoFuente = (Integer)tiposCadena.get(i);
                fuente = fuentesCuriosidad[tipoFuente];
                g.setFont(fuente);
                g.setColor(AMARILLO);

                metrica = g.getFontMetrics(fuente);

                switch(tipoFuente) {
                    case 2: y += 5; break;
                    case 3: y -= 5; break;
                }

                escribirCadena((Graphics2D) g,cadenaActual, y, 44, metrica);

                switch(tipoFuente) {
                    case 2: y += 5; break;
                    case 3: y -= 5; break;
                }
            }
            devuelto = true;
        } else
            devuelto = false;

        return devuelto;
    }

    public void dibujarPregunta(Graphics2D g) {
        Casilla casillaActual = refGame.getCasillaActual();
        int casx = casillaActual.getX() + 4;
        int casy = casillaActual.getY() + 24;
        int xBocad = 0,yBocad = 0;
        int arrX[] = new int[3], arrY[] = new int[3];
        int numLineas = preguntaActual.getLineasPregunta();

        FontMetrics metrics = g.getFontMetrics(fuentePreguntas);
        int longActual = preguntaActual.getTextoPregunta().length();
        int anchoBocad = metrics.stringWidth(preguntaActual.getTextoPregunta().
                substring(0, (longActual < Const.ANCHOPREGUNTA ? longActual: Const.ANCHOPREGUNTA))) + 20;
        int altoBocad = numLineas*16 + (numLineas-1)*7 + 20;
        int desplaz = 50;

        arrX[2] = casx; arrY[2] = casy;
        posicionBocad = casillaActual.getPosicionBocad();

        this.setFont(fuentePreguntas);

        //Las constantes indican la posición del bocadillo con respecto a la casilla
        switch(posicionBocad) {
            case ARRIBAIZQ:
                xBocad = casx - desplaz - anchoBocad; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad + anchoBocad; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + 2*altoBocad/3; arrY[1] = yBocad + altoBocad;
                break;

            case ARRIBADER:
                xBocad = casx + desplaz; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad; arrX[1] = xBocad + anchoBocad/3;
                arrY[0] = yBocad + 2*altoBocad/3; arrY[1] = yBocad + altoBocad;
                break;

            case ABAJODER:
                xBocad = casx + desplaz; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad;
                arrY[0] = yBocad; arrY[1] = yBocad + altoBocad/3;
                break;

            case ABAJOIZQ:
                xBocad = casx - desplaz - anchoBocad; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + altoBocad/3; arrY[1] = yBocad;
                break;

            case ARRIBA:
                xBocad = casx - desplaz; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + altoBocad; arrY[1] = yBocad + altoBocad;
                break;

            case DERECHA:
                xBocad = casx + desplaz; yBocad = casy - desplaz;
                arrX[0] = xBocad; arrX[1] = xBocad;
                arrY[0] = yBocad + altoBocad/3; arrY[1] = yBocad + 2*altoBocad/3;
                break;

            case ABAJO:
                xBocad = casx - desplaz; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad; arrY[1] = yBocad;
                break;

            case IZQUIERDA:
                xBocad = casx - desplaz - anchoBocad; yBocad = casy - desplaz;
                arrX[0] = xBocad + anchoBocad; arrX[1] = xBocad + anchoBocad;
                arrY[0] = yBocad + altoBocad/3; arrY[1] = yBocad + 2*altoBocad/3;
                break;
        }

        RoundRectangle2D rectangulo = new RoundRectangle2D.Double(xBocad,yBocad,anchoBocad,altoBocad,10,10);
        Polygon triangulo = new Polygon(arrX,arrY,3);
        Graphics2D superf = (Graphics2D)g;

        //Dibujado del bocadillo
        superf.setColor(BLANCO);
        superf.fill(rectangulo);
        superf.setColor(NEGRO);
        superf.draw(rectangulo);

        superf.setColor(BLANCO);
        superf.fill(triangulo);
        superf.setColor(NEGRO);
        superf.drawLine(arrX[0],arrY[0],arrX[2],arrY[2]);
        superf.drawLine(arrX[1],arrY[1],arrX[2],arrY[2]);

        escribirPregunta(superf, xBocad + 10, yBocad + 25);
        escribirRespuestas(superf);
    }

    public void escribirPregunta(Graphics2D superf, int x, int y) {
        ArrayList trozosCadena = preguntaActual.getTrozosCadena(0);
        ArrayList tiposCadena = preguntaActual.getTiposCadena(0);
        int numCadenas = preguntaActual.getNumTrozosCadena(trozosCadena);
        String cadenaActual = null;
        FontMetrics metrica = null;
        Font fuente = null;
        int tipoFuente = -1;

        //Escribe todas las cadenas que forman parte de la pregunta
        desp0 = despX = x;
        lineaActual = 0;

        quedan = Const.ANCHOPREGUNTA;
        for(int i = 0; i < numCadenas; i++) {
            cadenaActual = (String)trozosCadena.get(i);
            tipoFuente = (Integer)tiposCadena.get(i);
            fuente = fuentes[tipoFuente];
            superf.setFont(fuente);

            metrica = superf.getFontMetrics(fuente);

            switch(tipoFuente) {
                case 2: y += 4; break;
                case 3: y -= 4; break;
            }

            escribirCadena(superf, cadenaActual, y, Const.ANCHOPREGUNTA, metrica);

            switch(tipoFuente) {
                case 2: y -= 4; break;
                case 3: y += 4; break;
            }
        }
    }

    public void escribirCadena(Graphics2D superf, String cadenaActual, int despVr, int anchoCad, FontMetrics metrica) {
        int long1;
        int despFinal;

        //Escribe la cadena por trozos, lo que quepa en cada línea
        while(cadenaActual.length() > 0) {
            long1 = cadenaActual.length();
            despFinal = (long1 <= quedan ? long1 : quedan);
            superf.drawString(cadenaActual.substring(0, despFinal), despX, lineaActual * 25 + despVr);

            if(long1 <= quedan) {
                quedan -= long1;
                despX += metrica.stringWidth(cadenaActual);
                cadenaActual = "";
            } else {
                despX = desp0;
                cadenaActual = cadenaActual.substring(quedan, long1);
                quedan = anchoCad;
                lineaActual++;
            }
        }
    }

    public void escribirRespuestas(Graphics2D superf) {
        ArrayList trozosCadena = null;
        ArrayList tiposCadena = null;
        String cadenaActual = null;
        FontMetrics metrica = null;
        Font fuente = null;

        int desp2 = 0;
        int numCadenas = 0;
        int numLineas = preguntaActual.getLineasResp(0) +
                        preguntaActual.getLineasResp(1) +
                        preguntaActual.getLineasResp(2);
        int[] arrDesplaz = {4,3,3,2,2,1,1,0,0,-1};
        int tipoFuente = -1;

        lineaActual = 0;

        //Desplazamiento de la pregunta en función del número de líneas
        desp2 = arrDesplaz[numLineas-3];

        int despVr = 417 - (numLineas-1 + desp2) * 25;

        refGame.setCheckBoxVert(0, despVr-13);
        refGame.setCheckBoxVert(1, preguntaActual.getLineasResp(0) * 25 + (despVr-13));
        refGame.setCheckBoxVert(2, (preguntaActual.getLineasResp(0) + preguntaActual.getLineasResp(1)) * 25 + (despVr-13));

        //Escritura de las tres respuestas
        for(int respActual = 0; respActual < 3; respActual++) {
            trozosCadena = preguntaActual.getTrozosCadena(respActual + 1);
            tiposCadena = preguntaActual.getTiposCadena(respActual + 1);
            numCadenas = preguntaActual.getNumTrozosCadena(trozosCadena);
            quedan = Const.ANCHORESPUESTA;

            //Escribe todas las cadenas que forman parte de la respuesta
            desp0 = despX = 726;
            for(int i = 0; i < numCadenas; i++) {
                cadenaActual = (String)trozosCadena.get(i);
                tipoFuente = (Integer)tiposCadena.get(i);
                fuente = fuentes[tipoFuente];
                superf.setFont(fuente);

                metrica = superf.getFontMetrics(fuente);

                switch(tipoFuente) {
                    case 2: despVr += 4; break;
                    case 3: despVr -= 4; break;
                }

                escribirCadena(superf, cadenaActual, despVr, Const.ANCHORESPUESTA, metrica);

                switch(tipoFuente) {
                    case 2: despVr -= 4; break;
                    case 3: despVr += 4; break;
                }
            }

            lineaActual++;
        }
    }

    public void setModo(int modo) {
        tipoDibujo = modo;

        switch(modo) {
            case Const.MODOMENU: //Menú principal 1
                addMainMenuGraphicalUpdates();
                break;

            case Const.MODOOPCION: //Menú de opciones 2
                addOptionsMenuGraphicalUpdates();
                break;

            case Const.MODOJUEGO: //Juego en sí 3
                bufferTablero = (refGame.getNumJugadores() == 1 ? bufferTablero1 : bufferTableroN);
                addGameGraphicalUpdates();
                break;
        }
    }

    private void addMainMenuGraphicalUpdates() {
        scene.update(arrayGraficos[0][2*idiomaJuego], Const.ARR_RECTS_BUTTONS_MAIN_MENU[0].getLocation()); //Botones
        scene.update(arrayGraficos[1][2*idiomaJuego], Const.ARR_RECTS_BUTTONS_MAIN_MENU[1].getLocation());
        scene.update(arrayGraficos[2][2*idiomaJuego], Const.ARR_RECTS_BUTTONS_MAIN_MENU[2].getLocation());
    }

    private void addOptionsMenuGraphicalUpdates() {
        scene.update(arrayGraficos[3][0], Const.ARR_RECTS_BUTTONS_MAIN_MENU[3].getLocation()); //Boton volver
        scene.update(arrayGraficos[6][1], Const.ARR_RECTS_CHECKBOXES_MENU[0].getLocation()); //Checkboxes idioma
        scene.update(arrayGraficos[6][0], Const.ARR_RECTS_CHECKBOXES_MENU[1].getLocation());
        scene.update(arrayGraficos[6][1], Const.ARR_RECTS_CHECKBOXES_MENU[2].getLocation()); //Checkboxes jugadores
        scene.update(arrayGraficos[6][0], Const.ARR_RECTS_CHECKBOXES_MENU[3].getLocation());
        scene.update(arrayGraficos[6][0], Const.ARR_RECTS_CHECKBOXES_MENU[4].getLocation());
        scene.update(arrayGraficos[6][0], Const.ARR_RECTS_CHECKBOXES_MENU[5].getLocation());
        scene.update(arrayGraficos[12][0], new Point(320,161)); //Letreros
        scene.update(arrayGraficos[13][0], new Point(318,299));
        scene.update(arrayGraficos[15][0], new Point(310,66));
    }

    private void addGameGraphicalUpdates() {
        scene.update(arrayGraficos[24][0],new Point()); //Marco del tablero
        scene.update(arrayGraficos[25][(refGame.getNumJugadores() == 1 ? 0 : 1)], new Point(6,6)); //Tablero

        scene.update(arrayGraficos[11][0], new Point(698,67));
        scene.update(arrayGraficos[4][2*idiomaJuego], new Point(746,470));
        scene.update(arrayGraficos[5][2*idiomaJuego], new Point(746,512));
    }

    public void setBuffer(int bufferType) {
        System.out.println("Set buffer to " + bufferType);
        bufferActual = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
        int backGroundIndex = 0;

        switch(bufferType) {
            case 0: backGroundIndex = FONDOPRES; break;
            case 1: backGroundIndex = FONDOPRES; break;
            case 2: backGroundIndex = FONDOINIC; break;
            case 3: backGroundIndex = FONDOTABL; break;
        }

        bufferActual.getGraphics().drawImage(arrayGraficos[backGroundIndex][0], 0, 0, this);
    }

    @Getter
    private BufferedImage[][] playerGraphics;

    private void cargaGraficos() {
        String[][] arrGrafs = ARR_GRAFS;
        int longitud = arrGrafs.length;
        int longitudSub = 0;
        int i = 0, j = 0;

        String filePath = "NONE";

        arrayGraficos = new BufferedImage[longitud][0];

        String[][] playerGraphicPaths = {
                {"jug11", "jug12", "jug13", "jug14", "jug15"}, //Indicadores del jugador actual [16-19]
                {"jug21", "jug22", "jug23", "jug24", "jug25"},
                {"jug31", "jug32", "jug33", "jug34", "jug35"},
                {"jug41", "jug42", "jug43", "jug44", "jug45"}
        };

        playerGraphics = new BufferedImage[4][5];

        for(i = 0; i < 4; i++) {
            for (j = 0; j < 5; j++) {
                filePath = Const.DIRECTORIO_GRAFICOS + playerGraphicPaths[i][j] + ".png";
                System.out.println("Loading " + filePath);

                try {
                    playerGraphics[i][j] = ImageIO.read(new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            for(i = 0; i < longitud; i++) {
                if (arrGrafs[i] == null) {
                    continue;
                }

                longitudSub = arrGrafs[i].length;
                arrayGraficos[i] = new BufferedImage[longitudSub];

                for(j = 0; j < longitudSub; j++) {
                    filePath = Const.DIRECTORIO_GRAFICOS + arrGrafs[i][j] + ".png";
                    System.out.println("Loading " + filePath);
                    arrayGraficos[i][j] = ImageIO.read(new File(filePath));
				}
            }
        } catch(Exception e) {
            System.err.println("Error en la carga de gráficos: " + e.toString() + " -- " + filePath + "[" + i + "][" + j + "]");
        }

        bufferTablero1 = arrayGraficos[25][0]; //Tableros para uno y varios jugadores
        bufferTableroN = arrayGraficos[25][1];
    }

    public int getContadorTimer() {
        return contadorTimer;
    }

    public void setCadenaEstado(String cadena) {
        Graphics2D g = (Graphics2D) bufferActual.getGraphics();
        BufferedImage trozo = arrayGraficos[24][0].getSubimage(748, 55, 192, 57);

        if(!cadena.equals(cadenaEstado)) {

            cadenaEstado = cadena;
            g.drawImage(trozo, 748, 55, null);

            if(cadenaEstado.length() != 0) {
                g.setColor(AMARILLO);
                g.setFont(fuenteEstado);
                g.drawString(cadenaEstado, 754, 88);
                g.setColor(NEGRO);
            }
        }
    }

    public void setIdioma(Language nuevoIdioma) {
        idiomaJuego = nuevoIdioma == Language.SPANISH ? 0 : 1;

        nuevoDibujo[Const.MODOMENU] = true;

        scene.update(arrayGraficos[12][idiomaJuego], new Point(320,161)); //Letreros
        scene.update(arrayGraficos[13][idiomaJuego], new Point(318,299));
        scene.update(arrayGraficos[15][idiomaJuego], new Point(310,66));
        scene.update(arrayGraficos[6][(idiomaJuego == 0 ? 1 : 0)], Const.ARR_RECTS_CHECKBOXES_MENU[0].getLocation());
        scene.update(arrayGraficos[6][(idiomaJuego == 0 ? 0 : 1)], Const.ARR_RECTS_CHECKBOXES_MENU[1].getLocation());
        scene.update(arrayGraficos[3][2*idiomaJuego], Const.ARR_RECTS_BUTTONS_MAIN_MENU[3].getLocation());
    }

    public void setRefrescarTablero() {refrescarTablero = true;}
    public void setNuevoDibujado(int i, boolean b) {nuevoDibujo[i] = b;}
}

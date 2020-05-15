package troplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Clase Panel utilizada para mostrar los gráficos del juego
 * @author alejandro
 */
public class Panel extends JPanel implements ActionListener {
    //Fuentes utilizadas en el juego
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

    private int posicionBocad = Const.ARRIBA;

    private int numJugadores = 1;
    private int animEstado = -1, despAnim = 1;

    private Pregunta preguntaActual = null;
    public int lineaActual = 0;
    public int despX = 0;
    private int desp0;
    private int quedan;

    //Imagenes del juego
    private BufferedImage[][] arrayGraficos = null;
    private BufferedImage bufferMenu = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferOpciones = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferJuego = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferActual = null;
    private BufferedImage bufferTablero = null;
    private BufferedImage bufferRespuestas = null;
    private BufferedImage bufferTablero1 = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);
    private BufferedImage bufferTableroN = new BufferedImage(946,644,BufferedImage.TYPE_INT_RGB);

    private Juego refJuego = null;
    private Pregunta pregCuriosidad = null;
    private String cadenaEstado = "";
    private Timer timer = null;

    private int idiomaJuego = Const.ESPAÑOL;
    private int contadorTimer = 0;

    //Modos diferentes representando momentos del juego
    private int tipoDibujo = Const.MODOPRESEN;

    //Indicadores diciendo que se ha de crear la escena desde 0
    private boolean[] nuevoDibujo  = {true, true, true, true};
    private LinkedList colaActualizar = new LinkedList();

    private Integer[] elementos = {0,0,0,0};
    private int ultimaActualizacion = -1;
    private boolean dibujadaCuriosidad = false;
    private boolean dibujarPregunta = false;
    private boolean refrescarTablero = false;

    private Graphics2D g2d = null;
    private Graphics2D g3d = null;

    /**
     * Crea el panel donde se mostrarán los gráficos del juego
     */
    public Panel() {
        setBackground(Color.BLACK);
        this.cargaGraficos();
        setDoubleBuffered(true);

        timer = new Timer(70, this);
        timer.start();
    }

    /**
     * Método de dibujado de la pantalla
     * @param g Gráfico donde se dibujará
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        actualizar();
        g2d = (Graphics2D)g;
        g2d.drawImage(bufferActual, 0, 0, this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * Se lleva a cabo con cada tick del timer
     * @param e Evento
     */
    public void actionPerformed(ActionEvent e) {
        contadorTimer++;
        repaint();
    }

    /**
     * Dibujado de la pregunta cuando es necesaria
     */
    void dibujarEscenaPregunta() {
        Jugador jugadorActual = null;
        int i;

        g3d = (Graphics2D) bufferActual.getGraphics();

        //Dibujado de los jugadores
        for(i = 0; i < numJugadores; i++) {
            jugadorActual = refJuego.getJugador(i);
            g3d.drawImage(arrayGraficos[i+7][0],jugadorActual.getCx(),jugadorActual.getCy(),null);
        }

        dibujarPregunta(g3d); //Bocadillo con la pregunta

        //Dibujado de los checkboxes con las respuestas
        refJuego.setRespuestaSeleccionada(0);
        for(i = 0; i < 3; i++)
            insActualizacion(6,(i == 0 ? 1 : 0),refJuego.getCheckBoxCoords(i));

        dibujarPregunta = false;
    }

    /**
     * Dibujado del tablero, hace falta cuando el jugador avanza
     */
    void dibujarTablero() {
        Graphics2D g = (Graphics2D) bufferActual.getGraphics();

        if(bufferRespuestas == null) bufferRespuestas = arrayGraficos[24][0].getSubimage(684, 118, 256, 340);

        g.drawImage(bufferTablero, 6, 6, null);
        g.drawImage(bufferRespuestas, 684, 118, null);

        for(int i = 0; i < numJugadores; i++) {
			g.drawImage(arrayGraficos[i+7][0], refJuego.getJugadorX(i), refJuego.getJugadorY(i), null);
		}

        refrescarTablero = false;
    }

    /**
     * Sirve para destacar el jugador que se encuentra jugando en un momento dado
     * @param g Superficie en la que se dibujará
     */
    public void dibujarEstado(Graphics2D g) {
          int jugadorActual = refJuego.getJugadorActual();
          int desplaz;

          animEstado += despAnim;

          switch(animEstado) {
              case 5:  animEstado = 3; despAnim = -1; break;
              case -1: animEstado = 1; despAnim = 1; break;
          }

          for(int i = 0; i < numJugadores; i++) {
              desplaz = (i == jugadorActual ? animEstado: 0);
              g.drawImage(arrayGraficos[i+16][desplaz], 691 + i*63, 554, null);
          }
      }

    /**
     * Utilizado para presentar la curiosidad que aparece al final del juego
     * @return Verdadero si la curiosidad se tiene que dibujar
     */
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
            insActualizacion(20,0, new Point(100,100));
            insActualizacion(21,idiomaJuego, new Point(110,105));
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

    /**
     * Método que se encarga de dibujar una pregunta en pantalla
     */
    public void dibujarPregunta(Graphics2D g) {
        Casilla casillaActual = refJuego.getCasillaActual();
        int casx = casillaActual.getX() + 4;
        int casy = casillaActual.getY() + 24;
        int xBocad = 0,yBocad = 0;
        int arrX[] = new int[3], arrY[] = new int[3];
        int numLineas = preguntaActual.getLineasPreg();

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
            case Const.ARRIBAIZQ:
                xBocad = casx - desplaz - anchoBocad; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad + anchoBocad; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + 2*altoBocad/3; arrY[1] = yBocad + altoBocad;
                break;

            case Const.ARRIBADER:
                xBocad = casx + desplaz; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad; arrX[1] = xBocad + anchoBocad/3;
                arrY[0] = yBocad + 2*altoBocad/3; arrY[1] = yBocad + altoBocad;
                break;

            case Const.ABAJODER:
                xBocad = casx + desplaz; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad;
                arrY[0] = yBocad; arrY[1] = yBocad + altoBocad/3;
                break;

            case Const.ABAJOIZQ:
                xBocad = casx - desplaz - anchoBocad; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + altoBocad/3; arrY[1] = yBocad;
                break;

            case Const.ARRIBA:
                xBocad = casx - desplaz; yBocad = casy - desplaz - altoBocad;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad + altoBocad; arrY[1] = yBocad + altoBocad;
                break;

            case Const.DERECHA:
                xBocad = casx + desplaz; yBocad = casy - desplaz;
                arrX[0] = xBocad; arrX[1] = xBocad;
                arrY[0] = yBocad + altoBocad/3; arrY[1] = yBocad + 2*altoBocad/3;
                break;

            case Const.ABAJO:
                xBocad = casx - desplaz; yBocad = casy + desplaz;
                arrX[0] = xBocad + anchoBocad/3; arrX[1] = xBocad + 2*anchoBocad/3;
                arrY[0] = yBocad; arrY[1] = yBocad;
                break;

            case Const.IZQUIERDA:
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

    /**
     * Escritura del texto de la pregunta
     * @param superf Superficie de dibujo
     * @param x Coordenada x
     * @param y Coordenada y
     */
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

    /**
     * Escritura de una cadena determinada
     * @param superf Superficie sobre la que se escribira la cadena
     * @param cadenaActual Cadena que se quiere escribir
     * @param despVr Coordenada vertical
     * @param anchoCad Ancho de la cadena
     * @param metrica Métrica de la fuente con la que se escribirá
     */
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

    /**
     * Escritura de las respuestas en función de su longitud
     * @param superf Superficie de dibujo
     */
    public void escribirRespuestas(Graphics2D superf) {
        ArrayList trozosCadena = null;
        ArrayList tiposCadena = null;
        String cadenaActual = null;
        FontMetrics metrica = null;
        Font fuente = null;

        int desp2 = 0;
        int numCadenas = 0;
        int numLineas = preguntaActual.getLineasResp(0) + preguntaActual.getLineasResp(1) +
                        preguntaActual.getLineasResp(2);
        int[] arrDesplaz = {4,3,3,2,2,1,1,0,0,-1};
        int tipoFuente = -1;

        lineaActual = 0;

        //Desplazamiento de la pregunta en función del número de líneas
        desp2 = arrDesplaz[numLineas-3];

        int despVr = 417 - (numLineas-1 + desp2) * 25;

        refJuego.setCheckBoxVert(0, despVr-13);
        refJuego.setCheckBoxVert(1, preguntaActual.getLineasResp(0) * 25 + (despVr-13));
        refJuego.setCheckBoxVert(2, (preguntaActual.getLineasResp(0) + preguntaActual.getLineasResp(1)) * 25 + (despVr-13));

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

    /**
     * Cambio en el modo de dibujo del juego
     * @param modo Modo de dibujo
     */
    public void setModo(int modo) {
        tipoDibujo = modo;

        switch(modo) {
            case Const.MODOPRESEN: //Presentación
                bufferActual = bufferMenu;
                if(nuevoDibujo[tipoDibujo]) nuevoDibujo[tipoDibujo] = false;
                break;

            case Const.MODOMENU: //Menú principal
                bufferActual = bufferMenu;
                if(nuevoDibujo[tipoDibujo]) {
                    nuevoDibujo[tipoDibujo] = false;

                    insActualizacion(0,2*idiomaJuego,Const.ARR_COORDS_MENU[0]); //Botones
                    insActualizacion(1,2*idiomaJuego,Const.ARR_COORDS_MENU[1]);
                    insActualizacion(2,2*idiomaJuego,Const.ARR_COORDS_MENU[2]);
                }
                break;

            case Const.MODOOPCION: //Menú de opciones
                bufferActual = bufferOpciones;
                if(nuevoDibujo[tipoDibujo]) {
                    nuevoDibujo[tipoDibujo] = false;
                    insActualizacion(3,0,Const.ARR_COORDS_MENU[3]); //Boton volver
                    insActualizacion(6,1,Const.ARR_COORDS_MENU[4]); //Checkboxes idioma
                    insActualizacion(6,0,Const.ARR_COORDS_MENU[5]);
                    insActualizacion(6,1,Const.ARR_COORDS_MENU[6]); //Checkboxes jugadores
                    insActualizacion(6,0,Const.ARR_COORDS_MENU[7]);
                    insActualizacion(6,0,Const.ARR_COORDS_MENU[8]);
                    insActualizacion(6,0,Const.ARR_COORDS_MENU[9]);
                    insActualizacion(12,0,Const.ARR_COORDS_OPCIONES[0]); //Letreros
                    insActualizacion(13,0,Const.ARR_COORDS_OPCIONES[1]);
                    insActualizacion(15,0,Const.ARR_COORDS_OPCIONES[3]);
                }
                break;

            case Const.MODOJUEGO: //Juego en sí
                bufferActual = bufferJuego;
                bufferTablero = (refJuego.getNumJugadores() == 1 ? bufferTablero1 : bufferTableroN);

                if(nuevoDibujo[tipoDibujo]) {
                    nuevoDibujo[tipoDibujo] = false;
                    insActualizacion(24,0,new Point()); //Marco del tablero
                    insActualizacion(25,(refJuego.getNumJugadores() == 1 ? 0 : 1), new Point(6,6)); //Tablero
                    insActualizacion(11,0,Const.ARR_COORDS_JUEGO[4]);
                    insActualizacion(4,2*idiomaJuego,Const.ARR_COORDS_JUEGO[5]);
                    insActualizacion(5,2*idiomaJuego,Const.ARR_COORDS_JUEGO[6]);
                }
                break;
        }
    }

    /**
     * Actualiza el buffer de dibujo para dibujar lo menos posible
     */
    private void actualizar() {
        if(bufferActual != null) {
            Graphics2D g = (Graphics2D) bufferActual.getGraphics();
            if(refrescarTablero) dibujarTablero();

            /* Establecimiento de un gráfico determinado dentro de un array,
            con unas coordenadas determinadas */
            while(!colaActualizar.isEmpty()) {
                elementos[0] = (Integer)colaActualizar.poll();
                elementos[1] = (Integer)colaActualizar.poll();
                elementos[2] = (Integer)colaActualizar.poll();
                elementos[3] = (Integer)colaActualizar.poll();

                g.drawImage(arrayGraficos[elementos[0]][elementos[1]],elementos[2],elementos[3],null);
                ultimaActualizacion = elementos[0];
            }

            if(dibujarPregunta) dibujarEscenaPregunta();
            if(bufferActual == bufferJuego) dibujarEstado(g);
        }
    }

    /**
     * Carga de los diferentes gráficos del juego
     */
    private void cargaGraficos() {
        String[][] arrGrafs = Const.ARR_GRAFS;
        int longitud = arrGrafs.length;
        int longitudSub = 0;
        int i = 0, j = 0;

        String filePath = "NONE";

        arrayGraficos = new BufferedImage[longitud][0];

        try {
            for(i = 0; i < longitud; i++) {
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

        //Inicialización de los diferentes gráficos (presentacion, menu de opciones, juego)
        bufferMenu.getGraphics().drawImage(arrayGraficos[Const.FONDOPRES][0], 0, 0, this);
        bufferOpciones.getGraphics().drawImage(arrayGraficos[Const.FONDOINIC][0], 0, 0, this);
        bufferJuego.getGraphics().drawImage(arrayGraficos[Const.FONDOTABL][0], 0, 0, this);

        bufferTablero1 = arrayGraficos[25][0]; //Tableros para uno y varios jugadores
        bufferTableroN = arrayGraficos[25][1];
    }

    /**
     * Realiza una descarga de los gráficos del juego
     */
    public void descargarGraficos() {
        int longitudArray = arrayGraficos.length;
        int longitudElem = 0;

        for(int i=0; i<longitudArray; i++) {
            longitudElem = arrayGraficos[i].length;
            for(int j = 0; j < longitudElem; j++)
                arrayGraficos[i][j] = null;

            arrayGraficos[i] = null;
        }

        arrayGraficos = null;
    }

    /**
     * Inserción de nuevos elementos en la cola de actualizaciones
     * @param indice Array dentro del array de gráficos
     * @param subind Posición dentro del array anterior
     */
	public void insActualizacion(int indice, int subind, Point coords) {
        colaActualizar.add(indice);
		colaActualizar.add(subind);
        colaActualizar.add(coords.x);
		colaActualizar.add(coords.y);
    }

    public int getContadorTimer() {return contadorTimer;}

    /**
     * Establece el estado del juego
     * @param cadena Cadena informativa
     */
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

    public void setCuriosidad(Pregunta pregCuriosidad) {this.pregCuriosidad = pregCuriosidad;}
    public void setDibujadaCuriosidad(boolean valor) {dibujadaCuriosidad = valor;}
    public void setDibujarPregunta(boolean valor) {dibujarPregunta = valor;}

    /**
     * Cambio del idioma del juego. Como sólo se hace desde el menú controla también
     * el cambio de las opciones del juego.
     * @param nuevoIdioma Idioma al que se cambia (español o inglés)
     */
    public void setIdioma(int nuevoIdioma) {
        idiomaJuego = nuevoIdioma;

        nuevoDibujo[Const.MODOMENU] = true;

        insActualizacion(12,idiomaJuego,Const.ARR_COORDS_OPCIONES[0]);
        insActualizacion(13,idiomaJuego,Const.ARR_COORDS_OPCIONES[1]);
        insActualizacion(15,idiomaJuego,Const.ARR_COORDS_OPCIONES[3]);
        insActualizacion(6,(idiomaJuego == 0 ? 1 : 0),Const.ARR_COORDS_MENU[4]);
        insActualizacion(6,(idiomaJuego == 0 ? 0 : 1),Const.ARR_COORDS_MENU[5]);
        insActualizacion(3,2*idiomaJuego,Const.ARR_COORDS_MENU[3]);
    }

    public void setNuevoDibujado(int i, boolean b) {nuevoDibujo[i] = b;}
    public void setNumJugadores(int numJugadores) {this.numJugadores = numJugadores;}
    public void setPregunta(Pregunta preguntaActual) {this.preguntaActual = preguntaActual;}
    public void setRefJuego(Juego refJuego) {this.refJuego = refJuego;}
    public void setRefrescarTablero() {refrescarTablero = true;}
}
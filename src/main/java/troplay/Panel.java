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

public class Panel extends JPanel implements ActionListener {
	private static final Point[] ARR_COORDS_OPCIONES = {
		new Point(320,161), //0
		new Point(318,299), //1
		new Point(314,437), //2
		new Point(310,66)   //3
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
    
    private final Point[] ARR_COORDS_MENU = {
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
    
    private final Point[] ARR_COORDS_JUEGO = {
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
	
	//Diferentes fondos del juego
    public final int FONDOPRES = 22;
    public final int FONDOINIC = 23;
    public final int FONDOTABL = 24;
	
	//Posiciones del bocadillo
    private final int ARRIBA = 1;
    private final int ABAJO = 2;
    private final int IZQUIERDA = 3;
    private final int DERECHA = 4;
    private final int ARRIBAIZQ = 5;
    private final int ARRIBADER = 6;
    private final int ABAJOIZQ = 7;
    private final int ABAJODER = 8;

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

    private int posicionBocad = ARRIBA;
      
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
    
    private final int ESPAÑOL = 0;
    private final int INGLES = 1;
    
    private int idiomaJuego = ESPAÑOL;
    private int contadorTimer = 0;
    
    private LinkedList colaActualizar = new LinkedList();
    
    private Integer[] elementos = {0,0,0,0};
    private int ultimaActualizacion = -1;
    private boolean dibujadaCuriosidad = false;
    private boolean dibujarPregunta = false;
    private boolean refrescarTablero = false;
    
    private Graphics2D g2d = null;
    private Graphics2D g3d = null;
    
    public Panel(String graphicsDir) {
        setBackground(Color.BLACK);
        this.cargaGraficos(graphicsDir);
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

    public void actionPerformed(ActionEvent e) {
        contadorTimer++;
        repaint();  
    }

    void dibujarEscenaPregunta() {
        int i;
        
        g3d = (Graphics2D) bufferActual.getGraphics();
        
        //Dibujado de los jugadores
        for(i = 0; i < numJugadores; i++) {
        	Point coords = refJuego.getJugador(i).getCoords();
            g3d.drawImage(arrayGraficos[i+7][0],coords.x,coords.y,null);
        }
            
        dibujarPregunta(g3d); //Bocadillo con la pregunta
            
        //Dibujado de los checkboxes con las respuestas
        refJuego.setRespuestaSeleccionada(0);
        for(i = 0; i < 3; i++)
            insActualizacion(6,(i == 0 ? 1 : 0),refJuego.getCheckBoxCoords(i));
            
        dibujarPregunta = false;
    }
    
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
      
    public void dibujarPregunta(Graphics2D g) {
        Casilla casillaActual = refJuego.getCasillaActual();
        int casx = casillaActual.getX() + 4;
        int casy = casillaActual.getY() + 24;
        int xBocad = 0,yBocad = 0;
        int arrX[] = new int[3], arrY[] = new int[3];
        int numLineas = preguntaActual.getLineasPreg();
        int anchoPregunta = 40;
          
        FontMetrics metrics = g.getFontMetrics(fuentePreguntas);
        int longActual = preguntaActual.getTextoPregunta().length();
        int anchoBocad = metrics.stringWidth(preguntaActual.getTextoPregunta().
                substring(0, (longActual < anchoPregunta ? longActual: anchoPregunta))) + 20;
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
        int anchoPregunta = 40;
          
        //Escribe todas las cadenas que forman parte de la pregunta
        desp0 = despX = x;
        lineaActual = 0;
          
        quedan = anchoPregunta;
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
            
            escribirCadena(superf, cadenaActual, y, anchoPregunta, metrica);
            
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
        int numLineas = preguntaActual.getLineasResp(0) + preguntaActual.getLineasResp(1) + 
                        preguntaActual.getLineasResp(2);
        int[] arrDesplaz = {4,3,3,2,2,1,1,0,0,-1};
        int tipoFuente = -1;
        int anchoRespuesta = 28;
        
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
            quedan = anchoRespuesta;
          
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
            
                escribirCadena(superf, cadenaActual, despVr, anchoRespuesta, metrica);
                
                switch(tipoFuente) {
                    case 2: despVr -= 4; break;
                    case 3: despVr += 4; break;
                }
            }

            lineaActual++;
        }
    }
   
    public void setModo(GameMode modo) {
        switch(modo) {
            case PRESEN: //Presentación
                bufferActual = bufferMenu;
                break;
                
            case MENU: //Menú principal
                bufferActual = bufferMenu;
                    
                insActualizacion(0,2*idiomaJuego, ARR_COORDS_MENU[0]); //Botones
                insActualizacion(1,2*idiomaJuego, ARR_COORDS_MENU[1]);
                insActualizacion(2,2*idiomaJuego, ARR_COORDS_MENU[2]);
                break;
                
            case OPCION: //Menú de opciones
                bufferActual = bufferOpciones;
                
                insActualizacion(3,0, ARR_COORDS_MENU[3]); //Boton volver
                insActualizacion(6,1, ARR_COORDS_MENU[4]); //Checkboxes idioma
                insActualizacion(6,0, ARR_COORDS_MENU[5]);
                insActualizacion(6,1, ARR_COORDS_MENU[6]); //Checkboxes jugadores
                insActualizacion(6,0, ARR_COORDS_MENU[7]);
                insActualizacion(6,0, ARR_COORDS_MENU[8]);
                insActualizacion(6,0, ARR_COORDS_MENU[9]);
                insActualizacion(12,0, ARR_COORDS_OPCIONES[0]); //Letreros
                insActualizacion(13,0, ARR_COORDS_OPCIONES[1]);
                insActualizacion(15,0, ARR_COORDS_OPCIONES[3]);
                break;
                
            case JUEGO: //Juego en sí
                bufferActual = bufferJuego;
                bufferTablero = (refJuego.getNumJugadores() == 1 ? bufferTablero1 : bufferTableroN);
                
                insActualizacion(24,0,new Point()); //Marco del tablero
                insActualizacion(25,(refJuego.getNumJugadores() == 1 ? 0 : 1), new Point(6,6)); //Tablero
                insActualizacion(11,0, ARR_COORDS_JUEGO[4]);
                insActualizacion(4,2*idiomaJuego, ARR_COORDS_JUEGO[5]);
                insActualizacion(5,2*idiomaJuego, ARR_COORDS_JUEGO[6]);
                break;
        }
    }

    private void actualizar() {
        if(bufferActual != null) {
            Graphics2D g = (Graphics2D) bufferActual.getGraphics();
            if(refrescarTablero) dibujarTablero();
            
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
    
    private void cargaGraficos(String graphicsDir) {
        String[][] arrGrafs = ARR_GRAFS;
        int longitud = arrGrafs.length;
        int longitudSub = 0;
        int i = 0, j = 0;
        
        String direct = "NONE";
          
        arrayGraficos = new BufferedImage[longitud][0];
          
        System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        try {
            for(i = 0; i < longitud; i++) {
                longitudSub = arrGrafs[i].length;
                arrayGraficos[i] = new BufferedImage[longitudSub];
            
                for(j = 0; j < longitudSub; j++) {
                    arrayGraficos[i][j] = ImageIO.read(new File(graphicsDir + arrGrafs[i][j]));
                    direct = graphicsDir + arrGrafs[i][j];
				}
            }
        } catch(Exception e) {
            System.err.println("Error en la carga de gráficos: " + e.toString() + " -- " + direct + "[" + i + "][" + j + "]");
        }
        
        //Inicialización de los diferentes gráficos (presentacion, menu de opciones, juego)
        bufferMenu.getGraphics().drawImage(arrayGraficos[FONDOPRES][0], 0, 0, this);
        bufferOpciones.getGraphics().drawImage(arrayGraficos[FONDOINIC][0], 0, 0, this);
        bufferJuego.getGraphics().drawImage(arrayGraficos[FONDOTABL][0], 0, 0, this);
        
        bufferTablero1 = arrayGraficos[25][0]; //Tableros para uno y varios jugadores
        bufferTableroN = arrayGraficos[25][1];
    }
    
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
    
	public void insActualizacion(int indice, int subind, Point coords) {
        colaActualizar.add(indice);
		colaActualizar.add(subind);
        colaActualizar.add(coords.x);
		colaActualizar.add(coords.y);
    }
    
    public int getContadorTimer() {return contadorTimer;}
    
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
    
    public void setIdioma(int nuevoIdioma) {
        idiomaJuego = nuevoIdioma;
            
        insActualizacion(12,idiomaJuego, ARR_COORDS_OPCIONES[0]);
        insActualizacion(13,idiomaJuego, ARR_COORDS_OPCIONES[1]);
        insActualizacion(15,idiomaJuego, ARR_COORDS_OPCIONES[3]);
        insActualizacion(6,(idiomaJuego == 0 ? 1 : 0), ARR_COORDS_MENU[4]);
        insActualizacion(6,(idiomaJuego == 0 ? 0 : 1), ARR_COORDS_MENU[5]);
        insActualizacion(3,2*idiomaJuego, ARR_COORDS_MENU[3]);
    }
    
    public void setNumJugadores(int numJugadores) {this.numJugadores = numJugadores;}
    public void setPregunta(Pregunta preguntaActual) {this.preguntaActual = preguntaActual;}
    public void setRefJuego(Juego refJuego) {this.refJuego = refJuego;}
    public void setRefrescarTablero() {refrescarTablero = true;}
}

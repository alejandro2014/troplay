package troplay;

import java.awt.Point;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Juego extends ClaseControladora {
	private static final int NUM_DIFICULTADES = 3;
	private static final int BAJA = 0;
	private static final int MEDIA = 1;
	private static final int ALTA = 2;
	private static final int NUMCASIFACIL = 23;
	private static final int NUMCASIMEDIO = 23;
	private static final int NUMCASIDIFICIL = 23;
    
	private static final int MAX_JUGADORES = 4;
	
	private final int ESPAÑOL = 0;
    private final int INGLES = 1;
    
    private int idiomaJuego = ESPAÑOL;
    private int numJugadores = 1;
    
    private int casillaActual = 0;
    private ConexionJDBC consultasJDBC;
    
    private int jugadorActual = -1;
    private Pregunta preguntaActual = null;
    private int respuestaMarcada = 0;
    
    private Dado dado = null;
    private Jugador[] jugadores = new Jugador[MAX_JUGADORES];
    
    private int squaresNo = 70;
    private Casilla[] tablero = new Casilla[this.squaresNo];
    private int[] numPreguntas = new int[NUM_DIFICULTADES];
    
    private boolean[] asigFacil, asigMedio, asigDificil;
    private int ganador = -1;
    
    private ControlFlujo controladora = null;
    public Ventana ventana = null;
    
    //Control de los gráficos
    //private Rectangle[] rectangulos = null;
    
    //Elementos dinámicos del juego (botones y checkboxes)
    private Drawable[] botones = new Drawable[2];
    private CheckBox[] checkboxes = new CheckBox[3];
    
    private Raton raton = null;
    private int longBotones = 0;
    private ArrayList conjCbxActual = new ArrayList();
    
    //Control de colisiones
    private boolean ratonPulsado = false;
	private Point coordsRaton = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    
    private boolean acabar = false;
    
    //Elementos del autómata de estados del juego en sí
    private final int ESTADO_INICIAL = 0;
    private final int ESTADO_PREGUNTANDO = 1;
    private final int ESTADO_LANZANDO = 2;
    private final int ESTADO_AVANZANDO = 3;
    private final int ESTADO_FINAL = 4;
    private final int ESTADO_ESCALERA = 5;
    private final int ESTADO_CURIOSIDAD = 6;
    
    private final int EVENTO_NULO = 0;
    private final int EVENTO_ACIERTO = 1;
    private final int EVENTO_FALLO = 2;
    private final int EVENTO_SALIR = 3;
    private final int EVENTO_PARAR = 4;
    private final int EVENTO_ESCALERA = 5;
    
    private int estadoActual = ESTADO_INICIAL;
    private int eventoActual = EVENTO_NULO;

    private Pregunta pregCuriosidad = null; //Pregunta utilizada para la curiosidad del final
    private Panel panel = null;
    private boolean cambiadoBoton = false;
    private boolean cambiadoCheckbox = false;
    
    private int contadorInicial, contadorFinal, contadorMas1;
    private boolean dibujadaCuriosidad = false;
    
    private Rectangle[] rectangles = null;
	private int questionsBySquare = 3;
	
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
    
    public Juego(Panel panel, Raton raton, ControlFlujo control, Rectangle[] rectangles) throws SQLException {
        int i;
        
        this.panel = panel;
        this.rectangles = rectangles;
        
        panel.setRefJuego(this);
        ///panel.setNuevoDibujado(3,true);
        
        controladora = control;
        this.raton = raton;
        
        idiomaJuego = control.getIdioma();
        consultasJDBC = new ConexionJDBC(idiomaJuego);
        
        for(i=0; i < this.squaresNo; i++) tablero[i] = new Casilla(i, this.questionsBySquare);
        
		dado = createDice();
        	
        initPlayers();
		initButtons();
		initCheckboxes();
		initQuestions();
        asignarPreguntas();
        getCuriosity();
        panel.setCuriosidad(pregCuriosidad);
        
        consultasJDBC.cerrarConexion();
        
        panel.setModo(GameMode.JUEGO);
        bucleJuego();
    }
	
	private void initPlayers() {
		int i;
		
		numJugadores = controladora.getNumJugadores();
        jugadorActual = (numJugadores == 1 ? 0 : -1);
		
		for(i=0; i < MAX_JUGADORES; i++) jugadores[i] = null;
		
        for(i=0; i<numJugadores; i++) {
            jugadores[i] = new Jugador(this,i);
            jugadores[i].setMostrar(true);
        }
	}
	
	private void initButtons() {
		longBotones = botones.length;
        for(int i = 0; i < longBotones; i++) {
            botones[i] = new Drawable();
            botones[i].setCoords(ARR_COORDS_JUEGO[i+5]);
            botones[i].setMostrar(true);
            botones[i].setRectangulo(rectangles[i+4]);
        }
	}
	
	private void initCheckboxes() {
		for(int i = 0; i < 3; i++) {
            checkboxes[i] = new CheckBox(conjCbxActual);
            checkboxes[i].setCoords(ARR_COORDS_JUEGO[i+7]);
            checkboxes[i].setMostrar(false);
            checkboxes[i].setRectangulo(rectangles[i+7]);
        }
        checkboxes[0].setActivado(true);
	}
	
	private void initQuestions() throws SQLException {
		int i;
		
        for(i=0; i < NUM_DIFICULTADES; i++)numPreguntas[i] = getNumPreguntas(i,idiomaJuego);
        asigFacil = new boolean[numPreguntas[BAJA]];
        asigMedio = new boolean[numPreguntas[MEDIA]];
        asigDificil = new boolean[numPreguntas[ALTA]];
        
        for(i=0; i<numPreguntas[BAJA]; i++) asigFacil[i] = false;
        for(i=0; i<numPreguntas[MEDIA]; i++) asigMedio[i] = false;
        for(i=0; i<numPreguntas[ALTA]; i++) asigDificil[i] = false;
	}
	
	private Pregunta getCuriosity() throws SQLException {
		String textCuriosity = consultasJDBC.getTextoCuriosidad();
		textCuriosity = textCuriosity.substring(textCuriosity.indexOf("?")+1);
		Pregunta question = new Pregunta(44);
		question.setTextoPregunta(textCuriosity);
		
		return question;
	}
    
	private Dado createDice() {
		Dado dice = new Dado();
		dice.setCoords(new Point(698,67));
		dice.setMostrar(true);
		
		return dice;
	}
	
    public void bucleJuego() {
        while(!acabar) {            
            estadoActual = cambiarEstado(estadoActual, eventoActual);
            
            if(estadoActual == ESTADO_PREGUNTANDO) {
                controlEntrada();
            
                if (ratonPulsado)
                    procesarEntrada();
                else
                    cambiadoCheckbox = false;
            
                if (botonPulsado != -1 && !ratonPulsado) {
                    desencadenarAccion(botonPulsado);
                    botonPulsado = -1;
                }
            }

            ganador = hayGanador();
            acabar = finalBucle();
            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {}
        }
        
        panel.setCadenaEstado("");
        panel.setDibujadaCuriosidad(false);
        
        controladora.setEvento(GameEvent.SALIR);
        }

    public int cambiarEstado(int estado, int evento) {
        int nuevoEstado = estado;
        
        switch(estado) {
            //Estado normal en el juego
            case ESTADO_INICIAL:
                switch(evento) {
                    case EVENTO_NULO:
                        if(numJugadores != 1) {                            
                            if((jugadorActual++) == numJugadores-1) jugadorActual = 0;
                                
                                //Cuando está en un pozo pasa el turno pero ya puede tirar la siguiente
                                while(!jugadores[jugadorActual].getPuedoTirar()) {
                                    jugadores[jugadorActual].setPuedoTirar(true);
                                    if((jugadorActual++) == numJugadores-1) jugadorActual = 0;
                                }
                        }
                        
                        //Obtención de la pregunta correspondiente por casilla y jugador
                        casillaActual = jugadores[jugadorActual].getCasilla();
                        preguntaActual = tablero[casillaActual].getPregActual();
            
                        //Obtención de la pregunta
                        panel.setPregunta(preguntaActual);
                        panel.setRefrescarTablero();
                        panel.setDibujarPregunta(true);
                        
                        nuevoEstado = ESTADO_PREGUNTANDO;
                        break;
                }
                
                break;
                
            //Estado del juego en el que se hace la pregunta
            case ESTADO_PREGUNTANDO:
                switch(evento) {
                    //Acierta la pregunta
                    case EVENTO_ACIERTO:
                        switch(idiomaJuego) {
                            case ESPAÑOL: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " acierta"); break;
                            case INGLES: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " is right"); break;
                        }
                        
                        panel.setPregunta(null);
                        tablero[casillaActual].preguntaResuelta();
                        nuevoEstado = ESTADO_LANZANDO;
                        contadorInicial = panel.getContadorTimer();
                        contadorMas1 = contadorInicial+1;
                        contadorFinal = contadorInicial + 30;
                        break;
                        
                    //Falla la pregunta
                    case EVENTO_FALLO:
                        switch(idiomaJuego) {
                            case ESPAÑOL: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " falla"); break;
                            case INGLES: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " fails"); break;
                        }
                        
                        panel.setPregunta(null);
                        eventoActual = EVENTO_NULO;
                        nuevoEstado = ESTADO_INICIAL;
                        break;
                        
                    case EVENTO_SALIR:
                        nuevoEstado = ESTADO_FINAL;
                        break;
                }
                break;
            
            //Estado en el que se lanza el dado y se muestra la animación
            case ESTADO_LANZANDO:
                switch(evento) {
                    case EVENTO_NULO: //Una vez que ya se ha puesto un tiempo de incertidumbre se muestra el resultado
                        int incCasilla = dado.getNuevoValor();
                        
                        jugadores[jugadorActual].avanzarCasilla(incCasilla);
                        
                        Casilla casillaNueva = tablero[jugadores[jugadorActual].getCasilla()];
                        if (casillaNueva.getEspecial() && casillaNueva.getComplementaria() == -1 && numJugadores > 1) {
                            jugadores[jugadorActual].setPuedoTirar(false);
                            
                            switch(idiomaJuego) {
                                case ESPAÑOL: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " frena"); break;
                                case INGLES: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " brakes"); break;
                            }
                        }
                        
                        eventoActual = EVENTO_PARAR;
                        panel.insActualizacion(11, dado.getValor() - 1, ARR_COORDS_JUEGO[4]);
                        
                        nuevoEstado = ESTADO_AVANZANDO;
                        break;
                    default: //Se mueve el dado...
                        int contador = panel.getContadorTimer();
                        
                        if(contador == contadorMas1) {
                            panel.insActualizacion(11, contador % 6, ARR_COORDS_JUEGO[4]);
                            contadorMas1++;
                        }
                        
                        if(contador == contadorFinal) {
                            contadorInicial = -1;
                            eventoActual = EVENTO_NULO;
                        }
                        break;
                }
                break;
                
            //Estado de muestra de la animación del avance del jugador
            case ESTADO_AVANZANDO:
                switch(evento) {
                    case EVENTO_PARAR:
                        int contador = panel.getContadorTimer();
                        
                        if (contadorInicial == -1) {
                            contadorInicial = contador;
                            contadorMas1 = contador+1;
                        }
                        
                        if(contador == contadorMas1) {
                            eventoActual = jugadores[jugadorActual].setCoordsAnim();
                            panel.setRefrescarTablero();
                            panel.insActualizacion(jugadorActual+7, 0, jugadores[jugadorActual].getCoords());
                            contadorMas1++;
                        }
                        break;
                        
                    case EVENTO_NULO:
                        if (ganador == -1) {
                            nuevoEstado = ESTADO_INICIAL;
                            contadorInicial = -1;
                        } else {
                            nuevoEstado = ESTADO_CURIOSIDAD;
                            contadorInicial = panel.getContadorTimer();
                            contadorFinal = contadorInicial + 200;
                        }
                        
                        break;
                        
                    case EVENTO_ESCALERA:
                        nuevoEstado = ESTADO_ESCALERA;
                        contadorInicial = -1;
                        break;
                }
                break;
                
            //Controla el movimiento por la escalera
            case ESTADO_ESCALERA:
                switch(evento) {
                    case EVENTO_ESCALERA:
                        int contador = panel.getContadorTimer();
                        
                        if (contadorInicial == -1) {
                            contadorInicial = contador;
                            contadorFinal = contador+1;
                        }
                        
                        if(contador == contadorFinal) {
                            eventoActual = jugadores[jugadorActual].avanzarEscalera();
                            panel.setRefrescarTablero();
                            panel.insActualizacion(jugadorActual+7, 0, jugadores[jugadorActual].getCoords());
                            contadorFinal++;
                        }
                        break;
                        
                    case EVENTO_NULO:
                        nuevoEstado = ESTADO_INICIAL;
                        break;
                }
                
                break;
            
            //Muestra la curiosidad
            case ESTADO_CURIOSIDAD:
                int contador = panel.getContadorTimer();
                
                while(!dibujadaCuriosidad)
                    dibujadaCuriosidad = panel.dibujarCuriosidad();
                
                if(contador == contadorFinal) nuevoEstado = ESTADO_FINAL;
               
                break;
                
            case ESTADO_FINAL:
                break;
        }
        
        return nuevoEstado;
    }
    
    public boolean finalBucle() {return (eventoActual == EVENTO_SALIR || estadoActual == ESTADO_FINAL);}

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
            if(botones[i].colision(coordsRaton)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }
        
        longitud = checkboxes.length;
        for(i=0; i<longitud; i++) {
            if (checkboxes[i].colision(coordsRaton)) {
                tipoColision = "checkBox";
                indiceColision = i;
                return;
            }
        }
        
        ratonPulsado = false;
    }
    
    public void procesarEntrada() {
        if (tipoColision.equals("checkBox")) {
            if(!cambiadoCheckbox) {
                checkboxes[indiceColision].setActivado(true);
                respuestaMarcada = indiceColision;
                for(int i = 0; i < 3; i++)
                    panel.insActualizacion(6,(respuestaMarcada == i ? 1 : 0), checkboxes[i].getCoords());
                
                cambiadoCheckbox = true;
            }
            
        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                //botones[indiceColision].setPulsado(true);
                panel.insActualizacion(indiceColision+4,2*idiomaJuego+1, ARR_COORDS_JUEGO[indiceColision+5]);
                cambiadoBoton = true;
            }
            botonPulsado = indiceColision;
        }
    }
    
    public void desencadenarAccion(int numBoton) {
        panel.insActualizacion(numBoton+4,2*idiomaJuego, ARR_COORDS_JUEGO[numBoton+5]);
        cambiadoBoton = false;
        
        switch(numBoton) {
            case 0: //Botón responder
                eventoActual = preguntaActual.compruebaCorrecta(respuestaMarcada) ? EVENTO_ACIERTO : EVENTO_FALLO;
                break;
            case 1: //Botón volver al menú
                panel.setPregunta(null);
                eventoActual = EVENTO_SALIR;
                break;
        }
    }
    
    public void asignarPreguntas() throws SQLException {
        Random rnd = new Random();
        boolean[] asig = asigFacil;
        int dificultad = BAJA;
        Pregunta[] pregun = new Pregunta[this.questionsBySquare];
        int[] idPreg = new int[this.questionsBySquare];
        int limite1 = NUMCASIFACIL;
        int limite2 = NUMCASIFACIL + NUMCASIMEDIO;
        
        //Se rellenan todas las casillas con preguntas
        for(int i=0; i < this.squaresNo - 1; i++) {
            //Ajusta la dificultad de la pregunta en función de la casilla
            if(i>-1 && i<limite1) {dificultad = BAJA; asig = asigFacil;}
            else if(i>limite1 - 1 && i<limite2) {dificultad = MEDIA; asig = asigMedio;}
            else if(i> limite2-1) {dificultad = ALTA; asig = asigDificil;}
            
            //Dentro de cada casilla se rellenan cuatro preguntas
            for(int j=0; j < this.questionsBySquare; j++) {
                do {
                    idPreg[j] = rnd.nextInt() % numPreguntas[dificultad];
                    if(idPreg[j] < 0) idPreg[j] *= -1;
                } while(asig[idPreg[j]]);
                asig[idPreg[j]] = true;
                
                try {
                    pregun[j] = consultasJDBC.obtenerPregunta(idPreg[j], dificultad+1);
                } catch (SQLException ex) {}
            }

            tablero[i].setPreguntas(pregun);
        }
    }
    
    public int hayGanador() {
        int jugadorGanador = -1;
        
        for(int i=0; i<numJugadores && jugadorGanador == -1; i++) {
            if(jugadores[i].getCasilla() == (this.squaresNo - 1)) {
                switch(idiomaJuego) {
                    case ESPAÑOL: panel.setCadenaEstado("Jugador " + (i+1) + " gana"); break;
                    case INGLES: panel.setCadenaEstado("Player " + (i+1) + " wins"); break;
                }
                jugadorGanador = i;
                break;
            }  
        }
        return jugadorGanador;
    }
    
    public Casilla getCasilla(int numero) {return tablero[numero];}
    public Casilla getCasillaActual() {return tablero[casillaActual];}
	public Point getCheckBoxCoords(int numCheckbox) {
		return checkboxes[numCheckbox].getCoords();
	}
    public Jugador getJugador(int i) {return jugadores[i];}
    public int getJugadorActual() {return jugadorActual;}
    public int getJugadorX(int i) {return jugadores[i].getCoords().x;}
    public int getJugadorY(int i) {return jugadores[i].getCoords().y;}

    public int getNumPreguntas(int dificultad, int idioma) throws SQLException {
        return consultasJDBC.obtenerNumPreguntas(dificultad+1);
    }
    
    public int getNumJugadores() {return numJugadores;}
    
    public void setCheckBoxVert(int numCheck, int despVr) {
        checkboxes[numCheck].setCy(despVr);
        checkboxes[numCheck].setRectangulo(new Rectangle(703,despVr,19,19));
    }
    
    void setRespuestaSeleccionada(int i) {respuestaMarcada = i;}
}

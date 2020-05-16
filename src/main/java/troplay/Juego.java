package troplay;

import java.awt.Point;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static troplay.Language.SPANISH;

public class Juego extends ClaseControladora {
    private final GameStatus gameStatus;
    private Language idiomaJuego;
    private int numJugadores = 1;

    private int casillaActual = 0;
    private ConexionJDBC consultasJDBC;

    private int jugadorActual = -1;
    private Pregunta preguntaActual = null;
    private int respuestaMarcada = 0;

    private Dado dado = null;
    private Jugador[] jugadores = new Jugador[Const.MAX_JUGADORES];

    private Casilla[] tablero = new Casilla[Const.NUM_CASILLAS];
    private int[] numPreguntas = new int[Const.NUM_DIFICULTADES];

    private boolean[] asigFacil, asigMedio, asigDificil;
    private int ganador = -1;

    private ControlFlujo controladora = null;
    public Ventana ventana = null;

    //Control de los gráficos
    private Rectangle[] rectangulos = null;

    //Elementos dinámicos del juego (botones y checkboxes)
    private Dibujable[] botones = new Dibujable[2];
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

    public Juego(GameStatus gameStatus, ControlFlujo control) {
        int i;

        this.gameStatus = gameStatus;
        Ventana ventana = gameStatus.getWindow();
        this.panel = ventana.getPanel();
        panel.setRefJuego(this);
        panel.setNuevoDibujado(3,true);
        panel.setDibujadaCuriosidad(false);

        controladora = control;
        this.raton = gameStatus.getMouse();

        rectangulos = Const.ARR_RECTS;

        idiomaJuego = gameStatus.getLanguage();
        consultasJDBC = new ConexionJDBC(idiomaJuego);

        for(i=0; i<Const.NUM_CASILLAS; i++) tablero[i] = new Casilla(i);

		dado = createDice();

        initPlayers();
		initButtons();
		initCheckboxes();

		try {
            initQuestions();
            asignarPreguntas();
            pregCuriosidad = getCuriosity();
        } catch (SQLException ex) {
		    ex.printStackTrace();
        }

        panel.setCuriosidad(pregCuriosidad);

        consultasJDBC.cerrarConexion();

        panel.setModo(3);
    }

	private void initPlayers() {
		int i;

		numJugadores = gameStatus.getPlayersNo();
        jugadorActual = (numJugadores == 1 ? 0 : -1);

		for(i=0; i<Const.MAX_JUGADORES; i++) jugadores[i] = null;

        //Inicializa el número de jugadores y el tipo de juego
        for(i=0; i<numJugadores; i++) {
            jugadores[i] = new Jugador(this,i);
            jugadores[i].setMostrar(true);
        }
	}

	private void initButtons() {
		longBotones = botones.length;
        for(int i = 0; i < longBotones; i++) {
            //botones[i] = new Boton(idiomaJuego);
            botones[i] = new Dibujable();
            botones[i].setCoords(Const.ARR_COORDS_JUEGO[i+5]);
            botones[i].setMostrar(true);
            botones[i].setRectangulo(rectangulos[i+4]);
        }
	}

	private void initCheckboxes() {
		for(int i = 0; i < 3; i++) {
            checkboxes[i] = new CheckBox(conjCbxActual);
            checkboxes[i].setCoords(Const.ARR_COORDS_JUEGO[i+7]);
            checkboxes[i].setMostrar(false);
            checkboxes[i].setRectangulo(rectangulos[i+7]);
        }
        checkboxes[0].setActivado(true);
	}

	private void initQuestions() throws SQLException {
		int i;

        for(i=0; i<Const.NUM_DIFICULTADES; i++)numPreguntas[i] = getNumPreguntas(i, idiomaJuego);
        asigFacil = new boolean[numPreguntas[Const.BAJA]];
        asigMedio = new boolean[numPreguntas[Const.MEDIA]];
        asigDificil = new boolean[numPreguntas[Const.ALTA]];

        for(i=0; i<numPreguntas[Const.BAJA]; i++) asigFacil[i] = false;
        for(i=0; i<numPreguntas[Const.MEDIA]; i++) asigMedio[i] = false;
        for(i=0; i<numPreguntas[Const.ALTA]; i++) asigDificil[i] = false;
	}

	private Pregunta getCuriosity() throws SQLException {
		String textCuriosity = consultasJDBC.getTextoCuriosidad();
		textCuriosity = textCuriosity.substring(textCuriosity.indexOf("?")+1);
		Pregunta question = new Pregunta(Const.ANCHOCURIOSIDAD);
		question.setTextoPregunta(textCuriosity);

		return question;
	}

	private Dado createDice() {
		Dado dice = new Dado();
		dice.setCoords(new Point(698,67));
		dice.setMostrar(true);

		return dice;
	}

    /**
     * Control del bucle de juego de la partida
     */
    public void bucleJuego() {
        while(!acabar) {
            estadoActual = cambiarEstado(estadoActual, eventoActual);

            //Controla la entrada del usuario sólo si se está preguntando
            if(estadoActual == ESTADO_PREGUNTANDO) {
                //Lectura de la entrada
                controlEntrada();

                //Procesado de la entrada
                if (ratonPulsado)
                    procesarEntrada();
                else
                    cambiadoCheckbox = false;

                //Se actúa cuando se deja de pulsar el botón, no antes
                if (botonPulsado != -1 && !ratonPulsado) {
                    //botones[botonPulsado].setPulsado(false);
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

        controladora.setEvento(Const.EVENTO_SALIR);
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

            case ESTADO_PREGUNTANDO:
                switch(evento) {
                    case EVENTO_ACIERTO:
                        switch(idiomaJuego) {
                            case SPANISH: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " acierta"); break;
                            case ENGLISH: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " is right"); break;
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
                            case SPANISH: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " falla"); break;
                            case ENGLISH: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " fails"); break;
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
                                case SPANISH: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " frena"); break;
                                case ENGLISH: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " brakes"); break;
                            }
                        }

                        eventoActual = EVENTO_PARAR;
                        panel.insActualizacion(11, dado.getValor() - 1, Const.ARR_COORDS_JUEGO[4]);

                        nuevoEstado = ESTADO_AVANZANDO;
                        break;
                    default: //Se mueve el dado...
                        int contador = panel.getContadorTimer();

                        if(contador == contadorMas1) {
                            panel.insActualizacion(11, contador % 6, Const.ARR_COORDS_JUEGO[4]);
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
                int subind = (idiomaJuego == SPANISH) ? 1 : 3;
                panel.insActualizacion(indiceColision + 4, subind, Const.ARR_COORDS_JUEGO[indiceColision + 5]);
                cambiadoBoton = true;
            }
            botonPulsado = indiceColision;
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (idiomaJuego == SPANISH) ? 0 : 2;
        panel.insActualizacion(numBoton+4,subind, Const.ARR_COORDS_JUEGO[numBoton+5]);
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
        int dificultad = Const.BAJA;
        Pregunta[] pregun = new Pregunta[Const.PREGS_POR_CASILLA];
        int[] idPreg = new int[Const.PREGS_POR_CASILLA];
        int limite1 = Const.NUMCASIFACIL;
        int limite2 = Const.NUMCASIFACIL + Const.NUMCASIMEDIO;

        //Se rellenan todas las casillas con preguntas
        for(int i=0; i<Const.NUM_CASILLAS-1; i++) {
            //Ajusta la dificultad de la pregunta en función de la casilla
            if(i>-1 && i<limite1) {dificultad = Const.BAJA; asig = asigFacil;}
            else if(i>limite1 - 1 && i<limite2) {dificultad = Const.MEDIA; asig = asigMedio;}
            else if(i> limite2-1) {dificultad = Const.ALTA; asig = asigDificil;}

            //Dentro de cada casilla se rellenan cuatro preguntas
            for(int j=0; j<Const.PREGS_POR_CASILLA; j++) {
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

    /**
     * Comprueba la condición de final de juego
     * @return Verdadero si el juego ha acabado, falso en caso contrario
     */
    public int hayGanador() {
        int jugadorGanador = -1;

        for(int i=0; i<numJugadores && jugadorGanador == -1; i++) {
            if(jugadores[i].getCasilla() == (Const.NUM_CASILLAS-1)) {
                switch(idiomaJuego) {
                    case SPANISH: panel.setCadenaEstado("Jugador " + (i+1) + " gana"); break;
                    case ENGLISH: panel.setCadenaEstado("Player " + (i+1) + " wins"); break;
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
    public int getJugadorX(int i) {return jugadores[i].getCx();}
    public int getJugadorY(int i) {return jugadores[i].getCy();}

    public int getNumPreguntas(int dificultad, Language idioma) throws SQLException {
        return consultasJDBC.obtenerNumPreguntas(dificultad+1);
    }

    public int getNumJugadores() {return numJugadores;}

    public void setCheckBoxVert(int numCheck, int despVr) {
        checkboxes[numCheck].setCy(despVr);
        checkboxes[numCheck].setRectangulo(new Rectangle(703,despVr,19,19));
    }

    void setRespuestaSeleccionada(int i) {respuestaMarcada = i;}
}

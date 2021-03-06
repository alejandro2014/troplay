package troplay;

import org.troplay.graphics.CheckBox;
import org.troplay.graphics.Drawable;
import troplay.enums.Language;
import troplay.game.Casilla;
import troplay.game.Dice;
import troplay.game.Jugador;
import troplay.game.Pregunta;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import static troplay.enums.Language.SPANISH;

public class Game extends SubGameBase implements SubgameInterface {
    private final int PREGS_POR_CASILLA = 3;
    private final int ANCHOCURIOSIDAD = 44;

    private final int MAX_JUGADORES = 4;
    private final int NUM_CASILLAS = 70;

    private final int NUM_DIFICULTADES = 3;
    private final int BAJA = 0;
    private final int MEDIA = 1;
    private final int ALTA = 2;
    private final int NUMCASIFACIL = 23;
    private final int NUMCASIMEDIO = 23;

    //private final GameStatus gameStatus;
    private Language idiomaJuego;
    private int numJugadores = 1;

    private int casillaActual = 0;
    private ConexionJDBC consultasJDBC;

    private int jugadorActual = -1;
    private Pregunta preguntaActual = null;
    private int respuestaMarcada = 0;

    private Dice dice = null;
    private Jugador[] jugadores = new Jugador[MAX_JUGADORES];

    private Casilla[] tablero = new Casilla[NUM_CASILLAS];
    private int[] numPreguntas = new int[NUM_DIFICULTADES];

    private boolean[] asigFacil, asigMedio, asigDificil;
    private int ganador = -1;

    public Window window = null;

    private final Rectangle ARR_RECTS_BUTTONS_GAME[] = {
            new Rectangle(new Point(746,470), new Dimension(133,37)),
            new Rectangle(new Point(746,512), new Dimension(133,37))
    };

    private final Rectangle ARR_RECTS_CHECKBOXES_JUEGO[] = {
            new Rectangle(new Point(703,20), new Dimension(19,19)),
            new Rectangle(new Point(703,50), new Dimension(19,19)),
            new Rectangle(new Point(703,80), new Dimension(19,19))
    };

    private Drawable[] botones = new Drawable[2];
    private CheckBox[] checkboxes = new CheckBox[3];

    private Mouse mouse = null;
    private int longBotones = 0;
    private ArrayList conjCbxActual = new ArrayList();

    //Control de colisiones
    private boolean ratonPulsado = false;
	private Point coordsRaton = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;

    private boolean acabar = false;

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

    public Game(GameStatus gameStatus) {
        /*int i;

        this.gameStatus = gameStatus;
        Ventana ventana = gameStatus.getWindow();
        //this.panel = ventana.getPanel();
        panel.setRefGame(this);
        panel.setNuevoDibujado(3,true);
        panel.setDibujadaCuriosidad(false);

        this.raton = gameStatus.getMouse();

        //rectangulos = Const.ARR_RECTS;

        idiomaJuego = gameStatus.getLanguage();
        consultasJDBC = new ConexionJDBC(idiomaJuego);

        for(i=0; i < NUM_CASILLAS; i++) {
            tablero[i] = new Casilla(i);
        }

		dice = createDice();

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

        panel.setPregCuriosidad(pregCuriosidad);

        consultasJDBC.cerrarConexion();

        panel.setBuffer(3);
        panel.setModo(3);*/
    }

    private Drawable createDrawable(Rectangle rectangle, Boolean show) {
        /*return Drawable.builder()
                .point(rectangle.getLocation())
                .rectangle(rectangle)
                .show(show)
                .build();*/
        return null;
    }

	/*private void initPlayers() {
		int i;

		numJugadores = gameStatus.getPlayersNo();
        jugadorActual = (numJugadores == 1 ? 0 : -1);

		for(i=0; i< MAX_JUGADORES; i++) jugadores[i] = null;

        for(i=0; i<numJugadores; i++) {
            jugadores[i] = new Jugador(this,i);
            //jugadores[i].setShow(true);
        }
	}

	private void initButtons() {
        botones[0] = createDrawable(new Rectangle(746,470, 133,37), true);
        botones[1] = createDrawable(new Rectangle(746,512, 133,37), true);
	}

	private void initCheckboxes() {
        checkboxes[0] = createCheckbox(conjCbxActual, new Rectangle(703,20, 19,19));
        checkboxes[0].setActivado(true);

        checkboxes[1] = createCheckbox(conjCbxActual, new Rectangle(703,50, 19,19));

        checkboxes[2] = createCheckbox(conjCbxActual, new Rectangle(703,80, 19,19));
	}*/

	private CheckBox createCheckbox(ArrayList<CheckBox> conjCbxActual, Rectangle rectangle) {
        //CheckBox checkbox = new CheckBox(conjCbxActual);

        /*checkbox.setPoint(rectangle.getLocation());
        checkbox.setRectangle(rectangle);
        checkbox.setShow(false);*/

        //return checkbox;
        return null;
    }

	/*private void initQuestions() throws SQLException {
		int i;

        for(i=0; i < NUM_DIFICULTADES; i++)numPreguntas[i] = getNumPreguntas(i, idiomaJuego);
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
		Pregunta question = new Pregunta(ANCHOCURIOSIDAD);
		question.setTextoPregunta(textCuriosity);

		return question;
	}

	private Dice createDice() {
		Dice dice = new Dice();
		//dice.setPoint(new Point(698,67));
		//dice.setShow(true);

		return dice;
	}

    public void loop() {
        while(!acabar) {
            estadoActual = cambiarEstado(estadoActual, eventoActual);

            if(estadoActual == ESTADO_PREGUNTANDO) {
                //Lectura de la entrada
                controlEntrada();

                //Procesado de la entrada
                if (ratonPulsado)
                    procesarEntrada();
                else
                    cambiadoCheckbox = false;

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

        gameStatus.setCurrentEvent(MainEvents.EXIT);
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

                                while(!jugadores[jugadorActual].getPuedoTirar()) {
                                    jugadores[jugadorActual].setPuedoTirar(true);
                                    if((jugadorActual++) == numJugadores-1) jugadorActual = 0;
                                }
                        }

                        casillaActual = jugadores[jugadorActual].getCasilla();
                        preguntaActual = tablero[casillaActual].getPregActual();

                        panel.setPreguntaActual(preguntaActual);
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

                        panel.setPreguntaActual(null);
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

                        panel.setPreguntaActual(null);
                        eventoActual = EVENTO_NULO;
                        nuevoEstado = ESTADO_INICIAL;
                        break;

                    case EVENTO_SALIR:
                        nuevoEstado = ESTADO_FINAL;
                        break;
                }
                break;

            case ESTADO_LANZANDO:
                Point diceLocation = new Point(698, 67);

                switch(evento) {
                    case EVENTO_NULO: //Una vez que ya se ha puesto un tiempo de incertidumbre se muestra el resultado
                        int incCasilla = dice.getNewValue();

                        jugadores[jugadorActual].avanzarCasilla(incCasilla);

                        Casilla casillaNueva = tablero[jugadores[jugadorActual].getCasilla()];
                        if (casillaNueva.getCasillaEspecial() && casillaNueva.getComplementaria() == -1 && numJugadores > 1) {
                            jugadores[jugadorActual].setPuedoTirar(false);

                            switch(idiomaJuego) {
                                case SPANISH: panel.setCadenaEstado("Jugador " + (jugadorActual + 1) + " frena"); break;
                                case ENGLISH: panel.setCadenaEstado("Player " + (jugadorActual + 1) + " brakes"); break;
                            }
                        }

                        eventoActual = EVENTO_PARAR;
                        //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[11][dice.getValue() - 1], diceLocation);

                        nuevoEstado = ESTADO_AVANZANDO;
                        break;
                    default: //Se mueve el dado...
                        int contador = panel.getContadorTimer();

                        if(contador == contadorMas1) {
                            //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[11][contador % 6], diceLocation);
                            contadorMas1++;
                        }

                        if(contador == contadorFinal) {
                            contadorInicial = -1;
                            eventoActual = EVENTO_NULO;
                        }
                        break;
                }
                break;

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
                            //panel.getScene().update(gameStatus.getPanel().getArrayGraficos()[jugadorActual+7][0], jugadores[jugadorActual].getPoint());
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
                            //panel.getScene().update(gameStatus.getPanel().getArrayGraficos()[jugadorActual+7][0], jugadores[jugadorActual].getPoint());
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
    }*/

    @Override
    public void createScene() throws IOException {

    }

    @Override
    public void loop() {

    }

    public Boolean endOfLoop() {return (eventoActual == EVENTO_SALIR || estadoActual == ESTADO_FINAL);}

    public void inputControl() {
        //ratonPulsado = mouse.getMousePressed();

        if (ratonPulsado) {
			coordsRaton = mouse.getPoint();
            controlColision();
        } else
            ratonPulsado = false;
    }

    public void controlColision() {
        /*int longitud = botones.length, i;

        for(i=0; i< longitud; i++) {
            if(botones[i].collision(coordsRaton)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }

        longitud = checkboxes.length;
        for(i=0; i<longitud; i++) {
            if (checkboxes[i].collision(coordsRaton)) {
                tipoColision = "checkBox";
                indiceColision = i;
                return;
            }
        }

        ratonPulsado = false;*/
    }

    public void procesarEntrada() {
        if (tipoColision.equals("checkBox")) {
            if(!cambiadoCheckbox) {
                //checkboxes[indiceColision].setActivated(true);
                respuestaMarcada = indiceColision;
                for(int i = 0; i < 3; i++) {
                    //panel.getScene().update(gameStatus.getPanel().getArrayGraficos()[6][(respuestaMarcada == i ? 1 : 0)], checkboxes[i].getPoint());
                }

                cambiadoCheckbox = true;
            }

        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                int subind = (idiomaJuego == SPANISH) ? 1 : 3;
                //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[indiceColision + 4][subind], ARR_RECTS_BUTTONS_GAME[indiceColision].getLocation());
                cambiadoBoton = true;
            }
            botonPulsado = indiceColision;
        }
    }

    public void desencadenarAccion(int numBoton) {
        int subind = (idiomaJuego == SPANISH) ? 0 : 2;
        //panel.getScene().addToQueue(gameStatus.getPanel().getArrayGraficos()[numBoton+4][subind], ARR_RECTS_BUTTONS_GAME[numBoton].getLocation());
        cambiadoBoton = false;

        switch(numBoton) {
            case 0:
                eventoActual = preguntaActual.compruebaCorrecta(respuestaMarcada) ? EVENTO_ACIERTO : EVENTO_FALLO;
                break;
            case 1:
                //panel.setPreguntaActual(null);
                eventoActual = EVENTO_SALIR;
                break;
        }
    }

    public void asignarPreguntas() throws SQLException {
        Random rnd = new Random();
        boolean[] asig = asigFacil;
        int dificultad = BAJA;
        Pregunta[] pregun = new Pregunta[PREGS_POR_CASILLA];
        int[] idPreg = new int[PREGS_POR_CASILLA];
        int limite1 = NUMCASIFACIL;
        int limite2 = NUMCASIFACIL + NUMCASIMEDIO;

        //Se rellenan todas las casillas con preguntas
        for(int i=0; i < NUM_CASILLAS - 1; i++) {
            if(i>-1 && i<limite1) {
                dificultad = BAJA;
                asig = asigFacil;
            } else if(i>limite1 - 1 && i<limite2) {
                dificultad = MEDIA;
                asig = asigMedio;
            } else if(i> limite2-1) {
                dificultad = ALTA;
                asig = asigDificil;
            }

            //Dentro de cada casilla se rellenan cuatro preguntas
            for(int j=0; j < PREGS_POR_CASILLA; j++) {
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
            if(jugadores[i].getCasilla() == (NUM_CASILLAS - 1)) {
                /*switch(idiomaJuego) {
                    case SPANISH: panel.setCadenaEstado("Jugador " + (i+1) + " gana"); break;
                    case ENGLISH: panel.setCadenaEstado("Player " + (i+1) + " wins"); break;
                }*/
                jugadorGanador = i;
                break;
            }
        }
        return jugadorGanador;
    }

    public Casilla getCasilla(int numero) {return tablero[numero];}
    public Casilla getCasillaActual() {return tablero[casillaActual];}
	public Point getCheckBoxCoords(int numCheckbox) {
		//return checkboxes[numCheckbox].getPoint();
        return null;
	}
    public Jugador getJugador(int i) {return jugadores[i];}
    public int getJugadorActual() {return jugadorActual;}
    //public int getJugadorX(int i) {return jugadores[i].getCx();}
    //public int getJugadorY(int i) {return jugadores[i].getCy();}
    public int getJugadorX(int i) {return 0;}
    public int getJugadorY(int i) {return 0;}

    public int getNumPreguntas(int dificultad, Language idioma) throws SQLException {
        return consultasJDBC.obtenerNumPreguntas(dificultad+1);
    }

    public int getNumJugadores() {return numJugadores;}

    public void setCheckBoxVert(int numCheck, int despVr) {
        //checkboxes[numCheck].setCy(despVr);
        //checkboxes[numCheck].setRectangle(new Rectangle(703,despVr,19,19));
    }

    void setRespuestaSeleccionada(int i) {respuestaMarcada = i;}
}

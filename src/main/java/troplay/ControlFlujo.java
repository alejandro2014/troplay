package troplay;

import java.sql.SQLException;

public class ControlFlujo {
    private int estadoActual = Const.ESTADO_PRESENTACION;
    private int eventoEntrada = Const.EVENTO_NULO;

    private int idiomaJuego = Const.ESPAÑOL;
    private int numJugadores = 1;
    private int nuevoEstado;

    private GameVariables gameVariables = null;

    private GameStatus gameStatus;

    public ControlFlujo() {
        gameStatus = new GameStatus();

        gameVariables = new GameVariables();
        setVariables(idiomaJuego, numJugadores);

        while (estadoActual != Const.ESTADO_FINAL) {
            estadoActual = cambiarEstado(estadoActual, eventoEntrada);
        }

        gameStatus.getPanel().descargarGraficos();
        System.exit(0);
    }

    public int cambiarEstado(int estado, int evento) {
        switch(estado) {
            case Const.ESTADO_PRESENTACION:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        gameStatus.getPanel().setModo(Const.MODOPRESEN);

                        runControlClass(ControlPresentacion.class);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;

                        gameStatus.getPanel().setModo(Const.MODOMENU);
                        break;
                }
                break;

            case Const.ESTADO_MENU_PRINCIPAL:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        runControlClass(MainMenu.class);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        break;

                    case Const.EVENTO_EMPEZAR:
                        gameStatus.getPanel().setNumJugadores(numJugadores);
                        gameStatus.getPanel().setDibujadaCuriosidad(false);

                        runControlClass(Juego.class);
                        nuevoEstado = Const.ESTADO_JUEGO;

                        break;

                    case Const.EVENTO_OPCIONES:
                        nuevoEstado = Const.ESTADO_OPCIONES;

                        eventoEntrada = Const.EVENTO_NULO;
                        break;

                    case Const.EVENTO_SALIR:
                        nuevoEstado = Const.ESTADO_FINAL;
                        break;
                }
                break;

            case Const.ESTADO_OPCIONES:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        gameStatus.getPanel().setModo(Const.MODOOPCION);

                        runControlClass(OptionsMenu.class);
                        nuevoEstado = Const.ESTADO_OPCIONES;

                        break;

                    case Const.EVENTO_VOLVER:
                        gameStatus.getPanel().setModo(Const.MODOMENU);

                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;

                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;

            case Const.ESTADO_JUEGO:
                switch(evento) {
                    case Const.EVENTO_SALIR:
                        gameStatus.getPanel().setModo(Const.MODOMENU);

                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;

                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;
        }

        return nuevoEstado;
    }

    private void runControlClass(Class clazz) {
        ClaseControladora controllerClass = null;

        try {
            controllerClass = (ClaseControladora) clazz
                    .getConstructor(GameStatus.class, ControlFlujo.class)
                    .newInstance(gameStatus, this);
        } catch (Exception ex) {
            System.err.println("Can't load the class " + clazz);
            System.exit(1);
        }

        controllerClass.bucleJuego();
    }

    public int getIdioma() {return idiomaJuego;}
    public int getNumJugadores() {return numJugadores;}

    public void setEvento(int eventoNuevo) {eventoEntrada = eventoNuevo;}

    public void setVariables(int idioma, int jugadores) {
        idiomaJuego = idioma;
        numJugadores = jugadores;
    }

    public static void main(String[] args) throws SQLException {
        new ControlFlujo();
    }
}

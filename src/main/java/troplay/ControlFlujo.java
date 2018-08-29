package troplay;

import java.sql.SQLException;

/**
 * Se encarga de controlar el flujo de ejecución del juego
 * @author alejandro
 */
public class ControlFlujo {
    public Ventana ventana = null;
    private Raton raton = null;

    private int estadoActual = Const.ESTADO_PRESENTACION;
    private int eventoEntrada = Const.EVENTO_NULO;

    private int idiomaJuego = Const.ESPAÑOL;
    private int numJugadores = 1;
    private int nuevoEstado;
    private Panel panel = null;

    /**
     * Clase que dirige el flujo de los acontecimientos del juego
     */
    public ControlFlujo() {
        setVariables(idiomaJuego,numJugadores);
        ventana = new Ventana();
        panel = ventana.getPanel();
        raton = new Raton(panel);

        while (estadoActual != Const.ESTADO_FINAL) {
            estadoActual = cambiarEstado(estadoActual, eventoEntrada);
        }

        panel.descargarGraficos();
        System.exit(0);
    }

    /**
     * Implementación de una máquina de estados para poder cambiar
     * el estado del juego. Éste es el método que coordina todo el programa
     * @param estado Estado desde el que se llama a la máquina de estados
     * @param evento Evento que se utiliza para cambiar el estado
     * @return Estado nuevo del juego
     */
    public int cambiarEstado(int estado, int evento) {
        switch(estado) {
            case Const.ESTADO_PRESENTACION:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        panel.setModo(Const.MODOPRESEN);
                        ClaseControladora claseControladora = new ControlPresentacion(ventana, this);
                        claseControladora.bucleJuego();
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        panel.setModo(Const.MODOMENU);
                        break;
                }
                break;

            case Const.ESTADO_MENU_PRINCIPAL:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        new Menu(ventana,raton,this,0);
                        break;

                    case Const.EVENTO_EMPEZAR:
                        panel.setNumJugadores(numJugadores);
                        panel.setDibujadaCuriosidad(false);
                        try {
                            new Juego(panel, raton, this);
                        } catch (SQLException ex) {}

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
                        panel.setModo(Const.MODOOPCION);

                        new Menu(ventana,raton,this,1);
                        break;

                    case Const.EVENTO_VOLVER:
                        panel.setModo(Const.MODOMENU);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;

            case Const.ESTADO_JUEGO:
                switch(evento) {
                    case Const.EVENTO_SALIR:
                        panel.setModo(Const.MODOMENU);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;
        }

        return nuevoEstado;
    }

    //Para que la clase Juego pueda obtener los parámetros necesarios
    public int getIdioma() {return idiomaJuego;}
    public int getNumJugadores() {return numJugadores;}

    public void setEvento(int eventoNuevo) {eventoEntrada = eventoNuevo;}

    /**
     * Variables pasadas desde la clase Menu utilizadas para configurar el juego
     * @param idioma Español o inglés
     * @param jugadores Número de jugadores
     */
    public void setVariables(int idioma, int jugadores) {
        idiomaJuego = idioma;
        numJugadores = jugadores;
    }

    public static void main(String[] args) throws SQLException {
        new ControlFlujo();
    }
}

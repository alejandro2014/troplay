package troplay;

import java.sql.SQLException;

public class ControlFlujo {
    public Ventana ventana = null;
    private Raton raton = null;
    
    private int estadoActual = Const.ESTADO_PRESENTACION;
    private int eventoEntrada = Const.EVENTO_NULO;
    
    private int idiomaJuego = Const.ESPAÑOL;
    private int numJugadores = 1;
    private int nuevoEstado;
    private Panel panel = null;
    
    public ControlFlujo() {
        setVariables(idiomaJuego,numJugadores);
        ventana = new Ventana();
        panel = ventana.getPanel();
        raton = new Raton(panel);
        
        while (estadoActual != Const.ESTADO_FINAL)
            estadoActual = cambiarEstado(estadoActual, eventoEntrada);
        
        panel.descargarGraficos();
        System.exit(0);
    }
    
    public int cambiarEstado(int estado, int evento) {

        switch(estado) {
            //Pantalla de presentación
            case Const.ESTADO_PRESENTACION:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        panel.setModo(Const.MODOPRESEN);
                        new ControlPresentacion(ventana,this);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        panel.setModo(Const.MODOMENU);
                        break;
                }
                break;
                
            //Antes de empezar el juego
            case Const.ESTADO_MENU_PRINCIPAL:
                switch(evento) {
                    //Nada más arrancar el programa
                    case Const.EVENTO_NULO:
                        new Menu(ventana,raton,this,0);
                        break;
                    
                    //Empezar el juego
                    case Const.EVENTO_EMPEZAR:
                        panel.setNumJugadores(numJugadores);
                        panel.setDibujadaCuriosidad(false);
                        try {
                            new Juego(panel, raton, this);
                        } catch (SQLException ex) {}
                        
                        nuevoEstado = Const.ESTADO_JUEGO;
                        break;
                    
                    //Ir al menú de opciones
                    case Const.EVENTO_OPCIONES:
                        nuevoEstado = Const.ESTADO_OPCIONES;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                        
                    //Salir del juego
                    case Const.EVENTO_SALIR:
                        nuevoEstado = Const.ESTADO_FINAL;
                        break;
                }
                break;
            
            //Menú de opciones
            case Const.ESTADO_OPCIONES:
                switch(evento) {
                    //Apertura del menú de opciones
                    case Const.EVENTO_NULO:
                        panel.setModo(Const.MODOOPCION);
                        
                        new Menu(ventana,raton,this,1);
                        break;
                        
                    //Volver al menú principal
                    case Const.EVENTO_VOLVER:
                        panel.setModo(Const.MODOMENU);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;
            
            //En el juego
            case Const.ESTADO_JUEGO:
                switch(evento) {
                    //Salida del juego
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


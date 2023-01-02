package troplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.SQLException;


public class ControlFlujo {
	//Rectángulos correspondientes a los elementos pulsables
    private static final Rectangle ARR_RECTS[] = {
        new Rectangle(new Point(389,234), new Dimension(165,46)), //Botones del menú principal
        new Rectangle(new Point(389,303), new Dimension(165,46)),
        new Rectangle(new Point(389,372), new Dimension(165,46)),
        new Rectangle(new Point(574,220), new Dimension(165,46)),
        new Rectangle(new Point(746,470), new Dimension(133,37)), //Botones del juego
        new Rectangle(new Point(746,512), new Dimension(133,37)),

        new Rectangle(new Point(346,226), new Dimension(19,19)), //Checkboxes del menú
        new Rectangle(new Point(513,225), new Dimension(19,19)),
        new Rectangle(new Point(317,384), new Dimension(19,19)),
        new Rectangle(new Point(476,384), new Dimension(19,19)),
        new Rectangle(new Point(317,484), new Dimension(19,19)),
        new Rectangle(new Point(480,485), new Dimension(19,19)),

        new Rectangle(new Point(703,20), new Dimension(19,19)), //Checkboxes del juego
        new Rectangle(new Point(703,50), new Dimension(19,19)),
        new Rectangle(new Point(703,80), new Dimension(19,19))
	};
    
    private static final Rectangle rectsJuego[] = {
        new Rectangle(new Point(746,470), new Dimension(133,37)), //Botones del juego
        new Rectangle(new Point(746,512), new Dimension(133,37)),
        new Rectangle(new Point(703,20), new Dimension(19,19)), //Checkboxes del juego
        new Rectangle(new Point(703,50), new Dimension(19,19)),
        new Rectangle(new Point(703,80), new Dimension(19,19))
	};
	
	//Estados del juego
    public final int ESTADO_MENU = 0;
    public final int ESTADO_PRESENTACION = 1;
    public final int ESTADO_OPCIONES = 2;
    public final int ESTADO_MENU_PRINCIPAL = 4;
    public final int ESTADO_JUEGO = 5;
    public final int ESTADO_FINAL = 6;
	
    public Ventana ventana = null;
    private Raton raton = null;
    
    private int estadoActual = ESTADO_PRESENTACION;
    private GameEvent eventoEntrada = GameEvent.NULO;
    
    private final int ESPAÑOL = 0;
    private final int INGLES = 1;
    
    private int idiomaJuego = ESPAÑOL;
    private int numJugadores = 1;
    private int nuevoEstado;
    private Panel panel = null;
    
    public ControlFlujo() {
        setVariables(idiomaJuego,numJugadores);
        
        ventana = new Ventana();
        panel = ventana.getPanel();
        raton = new Raton(panel);
        
        while (estadoActual != ESTADO_FINAL)
            estadoActual = cambiarEstado(estadoActual, eventoEntrada);
        
        panel.descargarGraficos();
        System.exit(0);
    }
    
    public int cambiarEstado(int estado, GameEvent evento) {

        switch(estado) {
            //Pantalla de presentación
            case ESTADO_PRESENTACION:
                switch(evento) {
                    case NULO:
                        panel.setModo(GameMode.PRESEN);
                        new ControlPresentacion(ventana,this);
                        nuevoEstado = ESTADO_MENU_PRINCIPAL;
                        panel.setModo(GameMode.MENU);
                        break;
                }
                break;
                
            //Antes de empezar el juego
            case ESTADO_MENU_PRINCIPAL:
                switch(evento) {
                    //Nada más arrancar el programa
                    case NULO:
                        new Menu(ventana,raton,this,0);
                        break;
                    
                    //Empezar el juego
                    case EMPEZAR:
                        panel.setNumJugadores(numJugadores);
                        panel.setDibujadaCuriosidad(false);
                        try {
                            new Juego(panel, raton, this, ARR_RECTS);
                        } catch (SQLException ex) {}
                        
                        nuevoEstado = ESTADO_JUEGO;
                        break;
                    
                    //Ir al menú de opciones
                    case OPCIONES:
                        nuevoEstado = ESTADO_OPCIONES;
                        eventoEntrada = GameEvent.NULO;
                        break;
                        
                    //Salir del juego
                    case SALIR:
                        nuevoEstado = ESTADO_FINAL;
                        break;
                }
                break;
            
            //Menú de opciones
            case ESTADO_OPCIONES:
                switch(evento) {
                    //Apertura del menú de opciones
                    case NULO:
                        panel.setModo(GameMode.OPCION);
                        
                        new Menu(ventana,raton,this,1);
                        break;
                        
                    //Volver al menú principal
                    case VOLVER:
                        panel.setModo(GameMode.MENU);
                        nuevoEstado = ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = GameEvent.NULO;
                        break;
                }
                break;
            
            //En el juego
            case ESTADO_JUEGO:
                switch(evento) {
                    //Salida del juego
                    case SALIR:
                        panel.setModo(GameMode.MENU);
                        nuevoEstado = ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = GameEvent.NULO;
                        break;
                }
                break;
        }
        
        return nuevoEstado;
    }
    
    public int getIdioma() {return idiomaJuego;}
    public int getNumJugadores() {return numJugadores;}
    
    public void setEvento(GameEvent eventoNuevo) {eventoEntrada = eventoNuevo;}
    
    public void setVariables(int idioma, int jugadores) {
        idiomaJuego = idioma;
        numJugadores = jugadores;
    }
    
    public static void main(String[] args) throws SQLException {
        new ControlFlujo();
    }
}


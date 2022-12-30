package troplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Menu extends troplay.ClaseControladora {
    //Variables que se le pasarán al controlador del juego
    private int idioma;
    private int numJugadores;
    
    private ControlFlujo controladora = null;
    private Ventana ventana = null;
    private Panel panel = null;
    private Raton raton = null;
    private boolean acabar = false;
    private GameEvent eventoRealizado = GameEvent.NULO;
    
    private final int NUM_BOTONES = 4;
    private final int NUM_CHECKBOXES = 6;
    
    //Elementos dinámicos del menú (botones y checkboxes)
    private Drawable[] botones = new Drawable[NUM_BOTONES];
    private CheckBox[] checkboxes = new CheckBox[NUM_CHECKBOXES];
    private ArrayList conjCbxIdioma = new ArrayList();
    private ArrayList conjCbxJugadores = new ArrayList();
    
    //Control de colisiones
    private boolean ratonPulsado = false;
	private Point coordsRaton = new Point();
    private String tipoColision = "";
    private int indiceColision = 0;
    private int botonPulsado = -1;
    private int tipoMenu;
    private boolean cambiadoCheckbox = false;
    private boolean cambiadoBoton = false;
    
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
    
    public Menu(Ventana vent, Raton raton, ControlFlujo control, int tipoMenu) {
    	boolean valorVerdad = (tipoMenu == 0 ? true : false);
    	
        controladora = control;
        idioma = controladora.getIdioma();
        numJugadores = controladora.getNumJugadores();
        
        ventana = vent;
        panel = ventana.getPanel();
        this.raton = raton;
        
        this.tipoMenu = tipoMenu;
          
        botones[0] = new Drawable();
		botones[0].setCoords(new Point(389,234));
        botones[0].setRectangulo(new Rectangle(new Point(389,234), new Dimension(165,46)));
        botones[0].setMostrar(valorVerdad);
        
        botones[1] = new Drawable();
		botones[1].setCoords(new Point(389,303));
        botones[1].setRectangulo(new Rectangle(new Point(389,303), new Dimension(165,46)));
        botones[1].setMostrar(valorVerdad);
        
        botones[2] = new Drawable();
		botones[2].setCoords(new Point(389,372));
        botones[2].setRectangulo(new Rectangle(new Point(389,372), new Dimension(165,46)));
        botones[2].setMostrar(valorVerdad);
        
        botones[3] = new Drawable();
		botones[3].setCoords(new Point(574,220));
        botones[3].setRectangulo(new Rectangle(new Point(574,220), new Dimension(165,46)));
        botones[3].setMostrar(!valorVerdad);
        
        checkboxes[0] = new CheckBox(conjCbxIdioma);
		checkboxes[0].setCoords(new Point(346,226));
        checkboxes[0].setRectangulo(new Rectangle(new Point(346,226), new Dimension(19,19)));
        checkboxes[0].setMostrar(!valorVerdad);
        
        checkboxes[1] = new CheckBox(conjCbxIdioma);
		checkboxes[1].setCoords(new Point(513,225));
        checkboxes[1].setRectangulo(new Rectangle(new Point(513,225), new Dimension(19,19)));
        checkboxes[1].setMostrar(!valorVerdad);
        
        checkboxes[2] = new CheckBox(conjCbxJugadores);
		checkboxes[2].setCoords(new Point(317,384));
        checkboxes[2].setRectangulo(new Rectangle(new Point(317,384), new Dimension(19,19)));
        checkboxes[2].setMostrar(!valorVerdad);
        
        checkboxes[3] = new CheckBox(conjCbxJugadores);
		checkboxes[3].setCoords(new Point(476,384));
        checkboxes[3].setRectangulo(new Rectangle(new Point(476,384), new Dimension(19,19)));
        checkboxes[3].setMostrar(!valorVerdad);
        
        checkboxes[4] = new CheckBox(conjCbxJugadores);
		checkboxes[4].setCoords(new Point(317,484));
        checkboxes[4].setRectangulo(new Rectangle(new Point(317,484), new Dimension(19,19)));
        checkboxes[4].setMostrar(!valorVerdad);
        
        checkboxes[5] = new CheckBox(conjCbxJugadores);
		checkboxes[5].setCoords(new Point(480,485));
        checkboxes[5].setRectangulo(new Rectangle(new Point(480,485), new Dimension(19,19)));
        checkboxes[5].setMostrar(!valorVerdad);
        
        if(tipoMenu == 1) {
            checkboxes[idioma].setActivado(true);
            checkboxes[numJugadores + 1].setActivado(true);
        }
        
        bucleJuego();
    }
    
    public void bucleJuego() {
        while(!acabar) {
            //Lectura y procesado de la entrada
            controlEntrada();
            if (ratonPulsado)
                procesarEntrada();
            else
                cambiadoCheckbox = false;
            
            //Se actúa cuando se deja de pulsar el botón, no antes
            if (botonPulsado != -1 && !ratonPulsado) {
                desencadenarAccion(botonPulsado);
                botonPulsado = -1;
            }
            
            acabar = finalBucle();
            
            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {}
        }
        
        //Asigna las variables si no lo están ya
        if (tipoMenu == 1)
            controladora.setVariables(idioma, numJugadores);
        
        controladora.setEvento(eventoRealizado);
    }
    
    public boolean finalBucle() {
        return (eventoRealizado == GameEvent.SALIR || eventoRealizado == GameEvent.EMPEZAR ||
                eventoRealizado == GameEvent.VOLVER || eventoRealizado == GameEvent.OPCIONES);
    }

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
            if(botones[i].getMostrar() && botones[i].colision(coordsRaton)) {
                tipoColision = "boton";
                indiceColision = i;
                return;
            }
        }
        
        longitud = checkboxes.length;
        for(i=0; i<longitud; i++) {
            if (checkboxes[i].getMostrar() && checkboxes[i].colision(coordsRaton)) {
                tipoColision = "checkBox";
                indiceColision = i;
                return;
            }
        }
        
        ratonPulsado = false;
    }
    
    public void procesarEntrada() {
        //Pulsado en un checkbox con una opción
        if (tipoColision.equals("checkBox")) {
            checkboxes[indiceColision].setActivado(true);
            
            if (indiceColision < 2) { //Selección del idioma
                if(!cambiadoCheckbox) {
                    idioma = indiceColision;
                    panel.setIdioma(idioma);
                    cambiadoCheckbox = true;
                }
            } else if(indiceColision < 6) { //Selección del número de jugadores
                if(!cambiadoCheckbox) {
                    numJugadores = indiceColision - 1;
                
                    for(int i = 0; i < 4; i++)
                        panel.insActualizacion(6, (indiceColision-2 == i ? 1 : 0), ARR_COORDS_MENU[i+6]);
                   
                    cambiadoCheckbox = true;
                }
            }
            
        //Pulsado de un botón
        } else if (tipoColision.equals("boton")) {
            if(!cambiadoBoton) {
                panel.insActualizacion(indiceColision,2*idioma+1, ARR_COORDS_MENU[indiceColision]);
                botonPulsado = indiceColision;
                cambiadoBoton = true;
            }
        }
    }
    
    public void desencadenarAccion(int numBoton) {
        panel.insActualizacion(indiceColision, 2*idioma, ARR_COORDS_MENU[indiceColision]);
        cambiadoBoton = false;
        
        switch(numBoton) {
            case 0: eventoRealizado = GameEvent.EMPEZAR;  break;
            case 1: eventoRealizado = GameEvent.OPCIONES; break;
            case 2: eventoRealizado = GameEvent.SALIR;    break;
            case 3: eventoRealizado = GameEvent.VOLVER;   break;
        }
    }
}

package troplay;

/**
 * Clase jugador, encaargada de manejar cada uno de los jugadores
 * que existan en el juego
 * @author alejandro
 */
public class Jugador extends troplay.Dibujable {
    private Juego juego;
    private int casilla = 0;
    private int casillaVieja, casillaNueva;
    private int casillaInicial;
    private int fotogrTotales = 0;
    private int fotogrActual = 0;
    private int desplX, desplY;
    private boolean puedoTirar = true;
    
    //Desplazamientos de los jugadores con respecto a las casillas
    private int[][] desplaz = {{6,0},{12,6},{6,12},{0,6}};
    private int idJugador;
    
    //Eventos de la clase Juego (necesarios para su flujo normal)
    private final int EVENTO_SEGUIR_ANIMACION = 4;
    private final int EVENTO_PARAR_ANIMACION = 0;
    private final int EVENTO_ESCALERA = 5;
    
    /**
     * Constructor del jugador
     * @param referJuego Referencia del juego
     */
    public Jugador(Juego referJuego,int idJug) {
        juego = referJuego;
        this.idJugador = idJug;
        this.setXY(Const.arrX[casilla] + desplaz[idJugador][0],
                   Const.arrY[casilla] + desplaz[idJugador][1]);
    }
    
    public void mover(int pos) {casilla = pos;}
    
    /**
     * Avanza un determinado número de casillas. Comprueba que no
     * se pase del final.
     * @param numCasillas Las casillas que ha de avanzar
     */
    public void avanzarCasilla(int numCasillas) {
        casillaInicial = casilla;
        if(casilla + numCasillas >= Const.NUM_CASILLAS) //Para que no se salga del tablero
            numCasillas = Const.NUM_CASILLAS - casilla - 1;
        
        casilla += numCasillas;
        
        fotogrTotales = numCasillas * 7;
        fotogrActual = 0;
    }

    /**
     * Avanza las coordenadas con animación cuando acierta la pregunta
     * @return Devuelve el siguiente evento de la clase Juego
     */
    public int setCoordsAnim() {
        int eventoRetorno = EVENTO_SEGUIR_ANIMACION;
        int casillasCompletas = fotogrActual / 7;
        int despl = fotogrActual % 7;
        int casillaActual = casillaInicial + casillasCompletas;
        int xVieja = Const.arrX[casillaActual] + desplaz[idJugador][0];
        int yVieja = Const.arrY[casillaActual] + desplaz[idJugador][1];
        int xActual, yActual;
        
        if (despl == 0 && casillasCompletas > 0) {
            casillaVieja = casillaActual;
            casillaNueva = casillaVieja + 1;
            
            if(casillaNueva < Const.NUM_CASILLAS) {
                desplX = Const.arrX[casillaNueva] - Const.arrX[casillaVieja];
                desplY = Const.arrY[casillaNueva] - Const.arrY[casillaVieja];
            }
        }
        
        //Actualiza las coordenadas o termina la animación
        if(fotogrActual < fotogrTotales && casillaVieja < Const.NUM_CASILLAS - 1) {
            xActual = xVieja + (despl * desplX) / 7;
            yActual = yVieja + (despl * desplY) / 7;
            this.setXY(xActual,yActual);
        } else {
            this.setCx(Const.arrX[casillaNueva-1] + desplaz[idJugador][0]);
            fotogrActual = 0;
            
            if(juego.getNumJugadores() > 1 && juego.getCasilla(casillaNueva-1).getComplementaria() != -1) {
                eventoRetorno = EVENTO_ESCALERA;
                this.setXY(Const.arrX[casillaNueva-1], Const.arrY[casillaNueva-1]);
            } else {
                eventoRetorno = EVENTO_PARAR_ANIMACION;
            }
        }
                
        fotogrActual++;
        return eventoRetorno;
    }
    
    /**
     * Controla la animación de la escalera
     * @return Evento a realizar para controlar la máquina de estados
     * de la partida
     */
    public int avanzarEscalera() {
        int eventoRetorno = EVENTO_ESCALERA;
        
        //Determinación de la "salida" y la "meta"
        if (fotogrActual == 1) {
            casillaVieja = casilla;
            
            switch(casilla) {
                case 15: casillaNueva = 49; break;
                case 49: casillaNueva = 15; break;
                case 30: casillaNueva = 60; break;
                case 60: casillaNueva = 30; break;
            }
            
            desplX = Const.arrX[casillaNueva] - Const.arrX[casillaVieja];
            desplY = Const.arrY[casillaNueva] - Const.arrY[casillaVieja];
        }
        
        /* Avance por la escalera en cada fotograma avanza un poco más
        hasta llegar al final */
        if(fotogrActual != 15) {
            fotogrActual++;
            this.setXY(Const.arrX[casillaVieja] + fotogrActual * desplX/15,
                       Const.arrY[casillaVieja] + fotogrActual * desplY/15);
        } else {
            mover(casillaNueva + 1);
            this.setXY(Const.arrX[casillaNueva + 1] + desplaz[idJugador][0],
                       Const.arrY[casillaNueva + 1] + desplaz[idJugador][1]);
            desplX = desplY = 0;
            fotogrActual = 0;
            eventoRetorno = EVENTO_PARAR_ANIMACION;
        }
        
        return eventoRetorno;
    }
    
    public int getCasilla() {return casilla;}
    public boolean getPuedoTirar() {return puedoTirar;}
    public void setPuedoTirar(boolean valor) {puedoTirar = valor;}
}

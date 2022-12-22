package troplay;

public class Jugador extends Drawable {
    private Juego juego;
    private int casilla = 0;
    private int casillaVieja, casillaNueva;
    private int casillaInicial;
    private int fotogrTotales = 0;
    private int fotogrActual = 0;
    private int desplX, desplY;
    private boolean puedoTirar = true;
    
    private int[][] desplaz = {{6,0},{12,6},{6,12},{0,6}};
    private int idJugador;
    
    private final int EVENTO_SEGUIR_ANIMACION = 4;
    private final int EVENTO_PARAR_ANIMACION = 0;
    private final int EVENTO_ESCALERA = 5;
    
    public Jugador(Juego referJuego,int idJug) {
        juego = referJuego;
        this.idJugador = idJug;
        this.setXY(Const.arrX[casilla] + desplaz[idJugador][0],
                   Const.arrY[casilla] + desplaz[idJugador][1]);
    }
    
    public void mover(int pos) {casilla = pos;}
    
    public void avanzarCasilla(int numCasillas) {
        casillaInicial = casilla;
        if(casilla + numCasillas >= Const.NUM_CASILLAS) //Para que no se salga del tablero
            numCasillas = Const.NUM_CASILLAS - casilla - 1;
        
        casilla += numCasillas;
        
        fotogrTotales = numCasillas * 7;
        fotogrActual = 0;
    }

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

package troplay;

public class Jugador extends Drawable {
	//Coordenadas de los centros de cada casilla
    private static final int arrX[] = {343,401,449,499,544,585,595,595,595,595,595,595,595,595,597,582,541,494,445,
                                      395,346,296,249,199,151,107,67,55,55,55,55,55,55,53,69,112,158,207,256,305,
                                      352,402,446,487,496,497,497,497,499,486,443,394,347,295,249,205,166,154,154,
                                      150,169,210,256,305,348,388,401,385,346,285};

    private static final int arrY[] = {543,545,545,545,547,531,488,439,389,340,293,243,194,146,103,58,54,54,54,54,
                                      54,54,54,54,54,51,71,112,160,211,259,309,358,400,438,446,447,447,447,447,447,
                                      447,447,431,391,341,291,243,201,161,152,152,152,152,152,149,167,212,261,302,
                                      339,349,349,348,351,332,296,262,251,248};
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
    
    private int squaresNo = 70;
    
    public Jugador(Juego referJuego,int idJug) {
        juego = referJuego;
        this.idJugador = idJug;
        this.setXY(Jugador.arrX[casilla] + desplaz[idJugador][0],
        		Jugador.arrY[casilla] + desplaz[idJugador][1]);
    }
    
    public void mover(int pos) {casilla = pos;}
    
    public void avanzarCasilla(int numCasillas) {
        casillaInicial = casilla;
        if(casilla + numCasillas >= this.squaresNo) //Para que no se salga del tablero
            numCasillas = this.squaresNo - casilla - 1;
        
        casilla += numCasillas;
        
        fotogrTotales = numCasillas * 7;
        fotogrActual = 0;
    }

    public int setCoordsAnim() {
        int eventoRetorno = EVENTO_SEGUIR_ANIMACION;
        int casillasCompletas = fotogrActual / 7;
        int despl = fotogrActual % 7;
        int casillaActual = casillaInicial + casillasCompletas;
        int xVieja = Jugador.arrX[casillaActual] + desplaz[idJugador][0];
        int yVieja = Jugador.arrY[casillaActual] + desplaz[idJugador][1];
        int xActual, yActual;
        
        if (despl == 0 && casillasCompletas > 0) {
            casillaVieja = casillaActual;
            casillaNueva = casillaVieja + 1;
            
            if(casillaNueva < this.squaresNo) {
                desplX = Jugador.arrX[casillaNueva] - Jugador.arrX[casillaVieja];
                desplY = Jugador.arrY[casillaNueva] - Jugador.arrY[casillaVieja];
            }
        }
        
        //Actualiza las coordenadas o termina la animación
        if(fotogrActual < fotogrTotales && casillaVieja < this.squaresNo - 1) {
            xActual = xVieja + (despl * desplX) / 7;
            yActual = yVieja + (despl * desplY) / 7;
            this.setXY(xActual,yActual);
        } else {
            this.setCx(Jugador.arrX[casillaNueva-1] + desplaz[idJugador][0]);
            fotogrActual = 0;
            
            if(juego.getNumJugadores() > 1 && juego.getCasilla(casillaNueva-1).getComplementaria() != -1) {
                eventoRetorno = EVENTO_ESCALERA;
                this.setXY(Jugador.arrX[casillaNueva-1], Jugador.arrY[casillaNueva-1]);
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
            
            desplX = Jugador.arrX[casillaNueva] - Jugador.arrX[casillaVieja];
            desplY = Jugador.arrY[casillaNueva] - Jugador.arrY[casillaVieja];
        }
        
        if(fotogrActual != 15) {
            fotogrActual++;
            this.setXY(Jugador.arrX[casillaVieja] + fotogrActual * desplX/15,
            		Jugador.arrY[casillaVieja] + fotogrActual * desplY/15);
        } else {
            mover(casillaNueva + 1);
            this.setXY(Jugador.arrX[casillaNueva + 1] + desplaz[idJugador][0],
            		Jugador.arrY[casillaNueva + 1] + desplaz[idJugador][1]);
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

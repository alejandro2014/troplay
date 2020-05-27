package troplay.game;

import lombok.Getter;
import lombok.Setter;
import troplay.Const;
import troplay.Drawable;
import troplay.Game;

//public class Jugador extends Drawable {
public class Jugador {
    private Game game;

    @Getter
    private int casilla = 0;
    private int casillaVieja, casillaNueva;
    private int casillaInicial;
    private int fotogrTotales = 0;
    private int fotogrActual = 0;
    private int desplX, desplY;

    @Getter @Setter
    private Boolean puedoTirar = true;

    //Desplazamientos de los jugadores con respecto a las casillas
    private int[][] desplaz = {{6,0},{12,6},{6,12},{0,6}};
    private int idJugador;

    private final int EVENTO_SEGUIR_ANIMACION = 4;
    private final int EVENTO_PARAR_ANIMACION = 0;
    private final int EVENTO_ESCALERA = 5;

    private int MAX_CASILLAS = 69;

    public Jugador(Game referGame, int idJug) {
        game = referGame;
        this.idJugador = idJug;
        //this.setXY((int) Const.arrayCasillas[casilla].getX() + desplaz[idJugador][0],
          //         (int)Const.arrayCasillas[casilla].getY() + desplaz[idJugador][1]);
    }

    public void mover(int pos) {casilla = pos;}

    public void avanzarCasilla(int numCasillas) {
        casillaInicial = casilla;
        if(casilla + numCasillas >= MAX_CASILLAS) //Para que no se salga del tablero
            numCasillas = MAX_CASILLAS - casilla - 1;

        casilla += numCasillas;

        fotogrTotales = numCasillas * 7;
        fotogrActual = 0;
    }

    public int setCoordsAnim() {
        int eventoRetorno = EVENTO_SEGUIR_ANIMACION;
        int casillasCompletas = fotogrActual / 7;
        int despl = fotogrActual % 7;
        int casillaActual = casillaInicial + casillasCompletas;
        int xVieja = (int)Const.arrayCasillas[casillaActual].getX() + desplaz[idJugador][0];
        int yVieja = (int)Const.arrayCasillas[casillaActual].getY() + desplaz[idJugador][1];
        int xActual, yActual;

        if (despl == 0 && casillasCompletas > 0) {
            casillaVieja = casillaActual;
            casillaNueva = casillaVieja + 1;

            if(casillaNueva < MAX_CASILLAS) {
                desplX = (int)Const.arrayCasillas[casillaNueva].getX() - (int)Const.arrayCasillas[casillaVieja].getX();
                desplY = (int)Const.arrayCasillas[casillaNueva].getY() - (int)Const.arrayCasillas[casillaVieja].getY();
            }
        }

        if(fotogrActual < fotogrTotales && casillaVieja < MAX_CASILLAS - 1) {
            xActual = xVieja + (despl * desplX) / 7;
            yActual = yVieja + (despl * desplY) / 7;
            //this.setXY(xActual,yActual);
        } else {
            //this.setCx((int)Const.arrayCasillas[casillaNueva - 1].getX() + desplaz[idJugador][0]);
            fotogrActual = 0;

            if(game.getNumJugadores() > 1 && game.getCasilla(casillaNueva-1).getComplementaria() != -1) {
                eventoRetorno = EVENTO_ESCALERA;
                //this.setXY((int)Const.arrayCasillas[casillaNueva - 1].getX(), (int)Const.arrayCasillas[casillaNueva - 1].getY());
            } else {
                eventoRetorno = EVENTO_PARAR_ANIMACION;
            }
        }

        fotogrActual++;
        return eventoRetorno;
    }

    public int avanzarEscalera() {
        int eventoRetorno = EVENTO_ESCALERA;

        if (fotogrActual == 1) {
            casillaVieja = casilla;

            switch(casilla) {
                case 15: casillaNueva = 49; break;
                case 49: casillaNueva = 15; break;
                case 30: casillaNueva = 60; break;
                case 60: casillaNueva = 30; break;
            }

            desplX = (int)Const.arrayCasillas[casillaNueva].getX() - (int)Const.arrayCasillas[casillaVieja].getX();
            desplY = (int)Const.arrayCasillas[casillaNueva].getY() - (int)Const.arrayCasillas[casillaVieja].getY();
        }

        if(fotogrActual != 15) {
            fotogrActual++;

            //this.setXY((int)Const.arrayCasillas[casillaVieja].getX() + fotogrActual * desplX / 15,
              //         (int)Const.arrayCasillas[casillaVieja].getY() + fotogrActual * desplY / 15);
        } else {
            mover(casillaNueva + 1);
            //this.setXY((int)Const.arrayCasillas[casillaNueva + 1].getX() + desplaz[idJugador][0],
              //         (int)Const.arrayCasillas[casillaNueva + 1].getY() + desplaz[idJugador][1]);
            desplX = desplY = 0;
            fotogrActual = 0;
            eventoRetorno = EVENTO_PARAR_ANIMACION;
        }

        return eventoRetorno;
    }
}

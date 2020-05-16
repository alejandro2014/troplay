package troplay.game;

import lombok.Getter;
import troplay.Const;

import java.awt.Point;

public class Casilla {
    private Pregunta[] arrayPreguntas;

    @Getter
    private Pregunta pregActual;

    @Getter
    private Boolean casillaEspecial = false;

    @Getter
    private int complementaria = -1;

    private int numCasilla = -1;
    private int numPregunta = 0;
	private Point coordsRef = new Point();

	@Getter
    private int posicionBocad = 1;

    public Casilla(int cas){
        numCasilla = cas;
        posicionBocad = Const.arrBocad[cas];
		coordsRef = Const.arrayCasillas[numCasilla];
        
        //Determinación de las casillas especiales, pozos y escaleras
        switch(cas) {
            //Escaleras
            case 15: casillaEspecial = true; complementaria = 49; break;
            case 49: casillaEspecial = true; complementaria = 15; break;
            case 30: casillaEspecial = true; complementaria = 60; break;
            case 60: casillaEspecial = true; complementaria = 30; break;
            
            //Paradas
            case 7: casillaEspecial = true; complementaria = -1; break;
            case 26: casillaEspecial = true; complementaria = -1; break;
            case 43: casillaEspecial = true; complementaria = -1; break;
            case 65: casillaEspecial = true; complementaria = -1; break;
        }
    }
   
    //Métodos get
    public int getX() {
		return coordsRef.x;
	}
    public int getY() {
		return coordsRef.y;
	}
    
    /**
     * Asigna las preguntas a la casilla
     * @param array Array con las preguntas
     */
    public void setPreguntas(Pregunta array[]){
        arrayPreguntas = array;
        pregActual = arrayPreguntas[0];
    }
    
    /**
     * Determina si una pregunta se encuentra resuelta
     */
    public void preguntaResuelta() {
        numPregunta++;
        if(numPregunta == Const.PREGS_POR_CASILLA) numPregunta = 0;
        pregActual = arrayPreguntas[numPregunta];
    }
}

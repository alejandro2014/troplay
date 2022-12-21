package troplay;

import java.awt.Point;

public class Casilla {
    private Pregunta[] arrayPreguntas;
    private Pregunta pregActual;
    private int numCasilla = -1;
    private boolean casillaEspecial = false;
    private int complementaria = -1;
    private int numPregunta = 0;
	private Point coordsRef = new Point();
    private int posicionBocad = 1;
    
    public Casilla(int cas){
        numCasilla = cas;
        posicionBocad = Const.arrBocad[cas];
		coordsRef = Const.arrayCasillas[numCasilla];
        
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
   
    public int getComplementaria() {return complementaria;}
    public boolean getEspecial() {return casillaEspecial;}
    public Pregunta getPregActual() {return pregActual;}
	
    public int getX() {
		return coordsRef.x;
	}
	
    public int getY() {
		return coordsRef.y;
	}
	
    public int getPosicionBocad() {return posicionBocad;}
    
    public Pregunta getPregunta(int num){
        return arrayPreguntas[num < Const.PREGS_POR_CASILLA ? num : 0];
    }
    
    public void setPreguntas(Pregunta array[]){
        arrayPreguntas = array;
        pregActual = arrayPreguntas[0];
    }
    
    public void preguntaResuelta() {
        numPregunta++;
        if(numPregunta == Const.PREGS_POR_CASILLA) numPregunta = 0;
        pregActual = arrayPreguntas[numPregunta];
    }
}

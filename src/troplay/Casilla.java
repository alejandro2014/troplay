package troplay;

/**
 * Clase Casilla, encargada de representar una de las celdas del juego
 * @author alejandro
 */
public class Casilla {
    private Pregunta[] arrayPreguntas;
    private Pregunta pregActual;
    private int numCasilla = -1;
    private boolean casillaEspecial = false;
    private int complementaria = -1;
    private int numPregunta = 0;
    private int xref, yref;
    private int posicionBocad = 1;
    
    /**
     * Creación de la nueva casilla
     * @param cas Número de casilla
     */
    public Casilla(int cas){
        numCasilla = cas;
        posicionBocad = Const.arrBocad[cas];
        xref = Const.arrX[numCasilla];
        yref = Const.arrY[numCasilla];
        
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
    public int getComplementaria() {return complementaria;}
    public boolean getEspecial() {return casillaEspecial;}
    public Pregunta getPregActual() {return pregActual;}
    public int getX() {return xref;}
    public int getY() {return yref;}
    public int getPosicionBocad() {return posicionBocad;}
    
    public Pregunta getPregunta(int num){
        return arrayPreguntas[num < Const.PREGS_POR_CASILLA ? num : 0];
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

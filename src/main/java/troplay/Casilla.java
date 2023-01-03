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
    
    private int questionsBySquare = 0;
    
    //Array con las posiciones
    private static final int arrBocad[] = {1,5,5,5,5,5,5,5,5,5,5,7,7,7,7,7,7,7,7,
                                          7,7,8,8,8,8,8,8,8,8,8,8,6,6,6,6,6,6,6,
                                          6,6,5,5,5,5,5,5,7,7,7,7,7,7,7,8,8,8,8,
                                          8,8,8,8,8,8,8,7,7,7,7,7,7};
    
    private static final Point arrayCasillas[] = {
		new Point(343, 543), new Point(401, 545), new Point(449, 545), new Point(499, 545), new Point(544, 547), new Point(585, 531),
		new Point(595, 488), new Point(595, 439), new Point(595, 389), new Point(595, 340), new Point(595, 293), new Point(595, 243),
		new Point(595, 194), new Point(595, 146), new Point(597, 103), new Point(582, 58), new Point(541, 54), new Point(494, 54),
		new Point(445, 54), new Point(395, 54), new Point(346, 54), new Point(296, 54), new Point(249, 54), new Point(199, 54),
		new Point(151, 54), new Point(107, 51), new Point(67, 71), new Point(55, 112), new Point(55, 160), new Point(55, 211),
		new Point(55, 259), new Point(55, 309), new Point(55, 358), new Point(53, 400), new Point(69, 438), new Point(112, 446),
		new Point(158, 447), new Point(207, 447), new Point(256, 447), new Point(305, 447), new Point(352, 447), new Point(402, 447),
		new Point(446, 447), new Point(487, 431), new Point(496, 391), new Point(497, 341), new Point(497, 291), new Point(497, 243),
		new Point(499, 201), new Point(486, 161), new Point(443, 152), new Point(394, 152), new Point(347, 152), new Point(295, 152),
		new Point(249, 152), new Point(205, 149), new Point(166, 167), new Point(154, 212), new Point(154, 261), new Point(150, 302),
		new Point(169, 339), new Point(210, 349), new Point(256, 349), new Point(305, 348), new Point(348, 351), new Point(388, 332),
		new Point(401, 296), new Point(385, 262), new Point(346, 251), new Point(285, 248)
    };
    
    public Casilla(int cas, int questionsBySquare) {
    	this.questionsBySquare = questionsBySquare;
    	
        numCasilla = cas;
        posicionBocad = Casilla.arrBocad[cas];
		coordsRef = Casilla.arrayCasillas[numCasilla];
        
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
    
    public Point getCoords() {
    	return coordsRef;
    }
	
    public int getPosicionBocad() {return posicionBocad;}
    
    public Pregunta getPregunta(int num){
        return arrayPreguntas[num < this.questionsBySquare ? num : 0];
    }
    
    public void setPreguntas(Pregunta array[]){
        arrayPreguntas = array;
        pregActual = arrayPreguntas[0];
    }
    
    public void preguntaResuelta() {
        numPregunta++;
        if(numPregunta == this.questionsBySquare) numPregunta = 0;
        pregActual = arrayPreguntas[numPregunta];
    }
}

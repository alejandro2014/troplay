package troplay;

import java.util.ArrayList;

/**
 * Implementación de los controles checkbox
 * @author alejandro
 */
public class CheckBox extends Drawable {
    private ArrayList conjCasillas = null;
    private CheckBox actual = null;
    public boolean activado = false;
    private int tamaño = 0;
    
    /**
     * Constructor del checkbox
     * @param conjunto Es el grupo al que pertenece la casilla para marcar
     */
    public CheckBox(ArrayList conjunto) {
        conjCasillas = conjunto;
        conjCasillas.add(this);
        tamaño = conjCasillas.size();
        
        /* Actualiza el tamaño que tienen los otros controles (así todos
        los checkboxes del grupo saben el tamaño) */
        for(int i=0; i<tamaño; i++) {
            actual = (CheckBox)conjCasillas.get(i);
            actual.setTamaño(tamaño);
        }
        
        if(tamaño == 1) setActivado(true);
        else setActivado(false);
    }
    
    /**
     * Determina si el checkbox está activado o no
     * @param estado Verdadero si hay que activarlo, falso si no
     */
    public void setActivado(boolean estado) {
        CheckBox checkActual = null;
        int longitud = conjCasillas.size();
        
        if (estado) {
            for(int i = 0; i< longitud; i++) {
                checkActual = (CheckBox)conjCasillas.get(i);
                if (checkActual != this) checkActual.setActivado(false);
            }
        }
        
        activado = estado;
    }
    
    public void setTamaño(int nuevoTamaño) {tamaño = nuevoTamaño;}
}

package troplay;

import java.util.ArrayList;

public class CheckBox extends Dibujable {
    private ArrayList conjCasillas = null;
    private CheckBox actual = null;
    public boolean activado = false;
    private int tamaño = 0;
    
    public CheckBox(ArrayList conjunto) {
        conjCasillas = conjunto;
        conjCasillas.add(this);
        tamaño = conjCasillas.size();
        
        for(int i=0; i<tamaño; i++) {
            actual = (CheckBox)conjCasillas.get(i);
            actual.setTamaño(tamaño);
        }
        
        if(tamaño == 1) setActivado(true);
        else setActivado(false);
    }
    
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

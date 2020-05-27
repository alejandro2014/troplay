package troplay;

import lombok.Setter;

import java.util.ArrayList;

public class CheckBox extends Drawable {
    private ArrayList conjCasillas = null;
    private CheckBox actual = null;
    public boolean activado = false;

    @Setter
    private int size = 0;

    public CheckBox() {
        super();
    }
    public CheckBox(ArrayList conjunto) {
        conjCasillas = conjunto;
        conjCasillas.add(this);
        size = conjCasillas.size();

        for(int i = 0; i< size; i++) {
            actual = (CheckBox)conjCasillas.get(i);
            actual.setSize(size);
        }
        
        if(size == 1) setActivado(true);
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
}

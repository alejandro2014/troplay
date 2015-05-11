package troplay;

import java.util.Date;
import java.util.Random;

/**
 * La clase dado se encarga de generar un número aleatorio entre 1 y 6
 * @author alejandro
 */
public class Dado extends Dibujable{
    private Random rnd;
    private int valor = 0;

    /**
     * Inicializa la clase con una semilla diferente cada vez para
     * que no se repitan las secuencias de números
     */
    public Dado() {
        rnd = new Random();
        Date fecha = new Date();
        rnd.setSeed(fecha.getTime());
    }
    
    /**
     * Devuelve el valor actual del dado
     * @return Valor del dado
     */
    public int getValor() {return valor + 1;}
    
    /**
     * Se encarga de generar un valor resultado de lanzar el dado
     * @return El valor generado por el dado virtual
     */
    public int getNuevoValor() {
        if((valor = rnd.nextInt() % 6) < 0) valor *= -1;
        return valor + 1;
    }
}

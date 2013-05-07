package troplay;

/**
 * Se llama clase controladora a aquella que cuenta con un bucle
 * capaz de manejar el flujo del juego, como pueden ser la clase
 * Menu o la clase Juego
 */
abstract public class ClaseControladora {
    //Método que controla el bucle de juego
    abstract public void bucleJuego();
    
    //Condición de parada del bucle
    abstract public boolean finalBucle();
    
    //Interacción con el usuario
    abstract public void controlEntrada();
}

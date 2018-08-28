package troplay;

import java.util.Date;
import java.util.Random;

public class Dado extends Dibujable {
    private Random rnd;
    private int valor = 0;

    public Dado() {
        rnd = new Random();
        Date fecha = new Date();
        rnd.setSeed(fecha.getTime());
    }

    public int getValor() {
        //return valor + 1;
        return 1;
    }

    public int getNuevoValor() {
        //if((valor = rnd.nextInt() % 6) < 0) valor *= -1;
        //return valor + 1;
        return 1;
    }
}

package troplay.game;

import troplay.Drawable;

import java.util.Date;
import java.util.Random;

public class Dice extends Drawable {
    private Random rnd;
    private int value = 0;

    public Dice() {
        rnd = new Random();
        Date date = new Date();
        rnd.setSeed(date.getTime());
    }

    public int getValue() {
        return value + 1;
    }

    public int getNewValue() {
        if((value = rnd.nextInt() % 6) < 0) value *= -1;
        return value + 1;
    }
}

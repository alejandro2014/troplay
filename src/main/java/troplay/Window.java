package troplay;

import lombok.Getter;

import java.awt.Image;
import javax.swing.*;

public class Window extends JFrame {
    private final int WINDOW_WIDTH = Const.SCREEN_WIDTH + 8;
    private final int WINDOW_HEIGHT = Const.SCREEN_HEIGHT + 27;

    @Getter
    private Panel panel;

    public Window() {
        this.panel = new Panel();

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setTitle("TROPLAY - El juego de la tenia");
        setResizable(false);
        setVisible(true);

        Image icon = loadApplicationIcon();
        setIconImage(icon);
    }

    private Image loadApplicationIcon() {
        Image icon = null;

        try {
            icon = getToolkit().getImage(Const.DIRECTORIO_GRAFICOS + "/icono.png");
        } catch (Exception e) {
            System.err.println("Error en la carga de imágenes" + e.toString());
        }

        return icon;
    }
}

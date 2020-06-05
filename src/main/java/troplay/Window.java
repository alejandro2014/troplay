package troplay;

import lombok.Getter;

import java.awt.Image;
import javax.swing.*;

public class Window extends JFrame {
    private final int WINDOW_WIDTH = Const.SCREEN_WIDTH + 8;
    private final int WINDOW_HEIGHT = Const.SCREEN_HEIGHT + 27;

    @Getter
    private Panel panel;

    public Window(Panel panel) {
        this.panel = panel;

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
            String graphicsDirectory = System.getProperty("user.dir") + "/src/main/resources/graphics";
            icon = getToolkit().getImage(graphicsDirectory + "/icono.png");
        } catch (Exception e) {
            System.err.println("Error en la carga de imagenes" + e.toString());
        }

        return icon;
    }
}

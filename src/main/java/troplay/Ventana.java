package troplay;

import com.sun.prism.paint.Color;
import lombok.Getter;

import java.awt.Image;
import javax.swing.*;

import static java.awt.Color.BLACK;

public class Ventana extends JFrame {
    private final int ANCHOVENTANA = 946;
    private final int ALTOVENTANA = 644;
    private final int ANCHO = ANCHOVENTANA + 8;
    private final int ALTO = ALTOVENTANA + 27;
      
      private Image icono = null;

      @Getter
      private Panel panel = new Panel();
      //private JPanel panel = new JPanel();

      public Ventana() {
          //panel.setBackground(BLACK);
          //panel.setDoubleBuffered(true);

          add(panel);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(ANCHO, ALTO);
          setLocationRelativeTo(null);
          setTitle("TROPLAY - El juego de la tenia");
          setResizable(false);
          setVisible(true);
              
          try {
              icono = getToolkit().getImage(Const.DIRECTORIO_GRAFICOS + "icono.png");
          } catch (Exception e) {
              System.err.println("Error en la carga de imágenes" + e.toString());
          }
          
          this.setIconImage(icono);
      }
}

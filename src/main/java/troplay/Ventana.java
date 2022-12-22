package troplay;

import java.awt.Image;
import javax.swing.JFrame;

public class Ventana extends JFrame {
      public static final int ANCHOVENTANA = 946;
      public static final int ALTOVENTANA = 644;
      public static final int ANCHO = ANCHOVENTANA + 8;
      public static final int ALTO = ALTOVENTANA + 27;
      
      private Image icono = null;
      private Panel panel = null;
     
      public Ventana() {
          panel = new Panel();
          add(panel);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setSize(ANCHO,ALTO);
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
      
      public Panel getPanel() {return panel;}
}

package troplay;

import lombok.Data;

@Data
public class GameStatus {
    private Ventana window;
    private Panel panel;
    private Raton mouse;

    public GameStatus() {
        window = new Ventana();
        panel = window.getPanel();
        mouse = new Raton(panel);
    }
}

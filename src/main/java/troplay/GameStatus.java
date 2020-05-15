package troplay;

import lombok.Data;

@Data
public class GameStatus {
    private Ventana window;
    private Panel panel;
    private Raton mouse;
    private GameVariables variables;

    public GameStatus() {
        window = new Ventana();
        panel = window.getPanel();
        mouse = new Raton(panel);
        variables = new GameVariables();
    }
}

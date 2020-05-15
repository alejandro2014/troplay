package troplay;

import lombok.Data;

@Data
public class GameStatus {
    private Ventana window;
    private Panel panel;
    private Raton mouse;
    private Language language = Language.SPANISH;
    private Integer playersNo = 1;

    public GameStatus() {
        window = new Ventana();
        panel = window.getPanel();
        mouse = new Raton(panel);
    }

    public void setLanguage(Language language) {
        this.language = language;
        panel.setIdioma(language);
    }

    public void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
        panel.setNumJugadores(playersNo);
    }
}

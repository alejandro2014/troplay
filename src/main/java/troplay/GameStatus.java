package troplay;

import lombok.Data;

import troplay.enums.Language;
import troplay.enums.MainEvents;

@Data
public class GameStatus {
    private Window window;
    private Panel panel;
    private Mouse mouse;
    private Language language = Language.SPANISH;
    private Integer playersNo = 1;
    private MainEvents currentEvent = MainEvents.NULL;

    public GameStatus() {
        window = new Window();
        panel = window.getPanel();
        mouse = new Mouse(panel);
    }

    public void setLanguage(Language language) {
        this.language = language;
        //panel.setIdioma(language);
    }

    public void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
        //panel.setNumJugadores(playersNo);
    }
}

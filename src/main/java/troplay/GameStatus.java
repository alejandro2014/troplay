package troplay;

import lombok.Data;

import org.troplay.graphics.Button;
import org.troplay.graphics.Clickable;
import troplay.enums.Language;
import troplay.enums.MainEvents;
import troplay.enums.MainStatuses;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class GameStatus {
    private String absolutePath;
    private Window window;
    private Panel panel;
    private Mouse mouse;
    private Language language = Language.SPANISH;
    private Integer playersNo = 1;
    private MainEvents currentEvent = MainEvents.NULL;
    private MainStatuses currentStatus = MainStatuses.INIT;

    private List<Clickable> clickables;

    public GameStatus() {
        panel = new Panel();
        window = new Window(panel);
        mouse = new Mouse();
        clickables = new ArrayList<>();

        panel.addMouseListener(mouse);
    }

    public void setEvent(MainEvents event) {
        this.currentEvent = event;
    }

    public void setLanguage(Language language) {
        this.language = language;
        //panel.setIdioma(language);
    }

    public void setPlayersNo(int playersNo) {
        this.playersNo = playersNo;
        //panel.setNumJugadores(playersNo);
    }

    public void addClickable(Clickable clickable) {
        this.clickables.add(clickable);
    }

    public void sendEvent(String event, Point point) {
        this.clickables.forEach(c -> c.sendEvent(event, point));
    }
}

package troplay.handlers;

import troplay.GameStatus;
import troplay.enums.MainEvents;

import static troplay.enums.MainEvents.BACK;

public class BackHandler implements ButtonHandler {
    private GameStatus gameStatus;

    public BackHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void triggerEvent() {
        gameStatus.setCurrentEvent(BACK);
    }
}

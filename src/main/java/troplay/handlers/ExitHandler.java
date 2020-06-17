package troplay.handlers;

import troplay.GameStatus;
import troplay.enums.MainEvents;

import static troplay.enums.MainEvents.EXIT;

public class ExitHandler implements ButtonHandler {
    private GameStatus gameStatus;

    public ExitHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void triggerEvent() {
        gameStatus.setCurrentEvent(EXIT);
    }
}

package troplay.handlers;

import troplay.GameStatus;

import static troplay.enums.MainEvents.OPTIONS;

public class OptionsHandler implements ButtonHandler {
    private GameStatus gameStatus;

    public OptionsHandler(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void triggerEvent() {
        gameStatus.setCurrentEvent(OPTIONS);
    }
}

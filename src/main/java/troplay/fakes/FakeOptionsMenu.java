package troplay.fakes;

import troplay.GameStatus;
import troplay.SubGameBase;
import troplay.SubgameInterface;

import java.io.IOException;

import static troplay.enums.MainEvents.BACK;

public class FakeOptionsMenu extends SubGameBase implements SubgameInterface {
    private GameStatus gameStatus;

    public FakeOptionsMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void createScene() throws IOException {

    }

    @Override
    public void loop() {
        gameStatus.setCurrentEvent(BACK);
    }

    @Override
    public void inputControl() {

    }
}

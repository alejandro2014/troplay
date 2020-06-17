package troplay.fakes;

import troplay.GameStatus;
import troplay.SubGameBase;
import troplay.SubgameInterface;

import java.io.IOException;

import static troplay.enums.MainEvents.NULL;
import static troplay.enums.MainEvents.START;

public class FakePresentation extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;

    public FakePresentation(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void createScene() throws IOException {

    }

    @Override
    public void loop() {

    }

    @Override
    public void inputControl() {

    }
}

package troplay.fakes;

import troplay.GameStatus;
import troplay.SubGameBase;
import troplay.SubgameInterface;

import java.io.IOException;

import static troplay.enums.MainEvents.*;

public class FakeMainMenu extends SubGameBase implements SubgameInterface {
    private final GameStatus gameStatus;
    private static int stepNo = 0;

    public FakeMainMenu(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void createScene() throws IOException {

    }

    public void loop() {
        System.out.println("Step number: " + stepNo);

        switch(stepNo) {
            case 0: gameStatus.setCurrentEvent(OPTIONS); break;
            case 1: gameStatus.setCurrentEvent(START); break;
            case 2: gameStatus.setCurrentEvent(EXIT); break;
        }

        stepNo++;
    }

    @Override
    public void inputControl() {

    }
}

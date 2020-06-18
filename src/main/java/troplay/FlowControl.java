package troplay;

import lombok.Getter;
import troplay.enums.MainEvents;
import troplay.enums.MainStatuses;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static troplay.enums.MainEvents.*;
import static troplay.enums.MainStatuses.*;

public class FlowControl {
    @Getter
    private GameStatus gameStatus;

    private List<TransitionInfo> transitionsList = new ArrayList<>();

    public FlowControl() {
        this.gameStatus = new GameStatus();

        String absolutePath = Paths.get(".").toAbsolutePath().normalize().toString();
        gameStatus.setAbsolutePath(absolutePath);
    }

    private void addTransitions() {
        addTransition(INIT, NULL, PRESENTATION, Presentation.class);

        addTransition(PRESENTATION, NULL, MAIN_MENU, MainMenu.class);

        addTransition(MAIN_MENU, START, GAME, Game.class);
        addTransition(MAIN_MENU, OPTIONS, OPTIONS_MENU, OptionsMenu.class);
        addTransition(MAIN_MENU, EXIT, FINAL);

        addTransition(OPTIONS_MENU, BACK, MAIN_MENU, MainMenu.class);

        addTransition(GAME, BACK, MAIN_MENU, MainMenu.class);
    }

    private void addTransition(MainStatuses currentStatus, MainEvents event, MainStatuses nextStatus) {
        addTransition(currentStatus, event, nextStatus, null);
    }

    private void addTransition(MainStatuses currentStatus, MainEvents event, MainStatuses nextStatus, Class classToExecute) {
        System.out.println("Adding > status: " + currentStatus + " event: " + event + " next status: " + nextStatus + " class: " + classToExecute);
        TransitionInfo transitionInfo = TransitionInfo.builder()
                .currentStatus(currentStatus)
                .event(event)
                .nextStatus(nextStatus)
                .classToExecute(classToExecute)
                .build();

        transitionsList.add(transitionInfo);
    }

    public void statusCycle() {
        MainStatuses currentStatus = INIT;
        MainStatuses nextStatus;
        MainEvents event = NULL;
        Class classToExecute;

        while (currentStatus != MainStatuses.FINAL) {
            TransitionInfo transitionInfo = getTransitionInfo(currentStatus, event);

            classToExecute = transitionInfo.getClassToExecute();
            nextStatus = transitionInfo.getNextStatus();

            runControlClass(classToExecute);

            currentStatus = nextStatus;
            event = gameStatus.getCurrentEvent();
        }
    }

    private TransitionInfo getTransitionInfo(MainStatuses currentStatus, MainEvents event) {
        System.out.println("----------------");
        System.out.println("currentStatus = " + currentStatus);
        System.out.println("event = " + event);

        TransitionInfo transitionInfo = transitionsList.stream()
                .filter(t -> t.getCurrentStatus() == currentStatus && t.getEvent() == event)
                .findFirst()
                .orElse(null);

        System.out.println("Retrieved transition info: " + transitionInfo);

        return transitionInfo;
    }

    private void runControlClass(Class clazz) {
        System.out.println("Executing " + clazz);

        if(clazz == null) {
            return;
        }

        SubgameInterface subgameInterface = null;

        try {
            subgameInterface = (SubgameInterface) clazz
                    .getConstructor(GameStatus.class)
                    .newInstance(gameStatus);
        } catch (Exception ex) {
            System.err.println("Can't load controller " + clazz);
            ex.printStackTrace();
            System.exit(1);
        }

        subgameInterface.loop();
    }

    public static void main(String[] args) {
        FlowControl flowControl = new FlowControl();
        flowControl.addTransitions();
        flowControl.statusCycle();

        System.exit(0);
    }
}

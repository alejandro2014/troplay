package troplay;

import lombok.Getter;
import troplay.enums.MainEvents;
import troplay.enums.MainStatuses;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FlowControl {
    @Getter
    private GameStatus gameStatus;

    private List<TransitionInfo> transitionsList = new ArrayList<>();

    public FlowControl() {
        this.gameStatus = new GameStatus();

        String absolutePath = Paths.get(".").toAbsolutePath().normalize().toString();
        gameStatus.setAbsolutePath(absolutePath);
    }

    //TODO Maybe there are redundant statuses
    private void addTransitions() {
        /*addTransition(MainStatuses.PRESENTATION, MainEvents.NULL, MainStatuses.MAIN_MENU, Presentation.class);

        addTransition(MainStatuses.MAIN_MENU, MainEvents.NULL, MainStatuses.MAIN_MENU, MainMenu.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.START, MainStatuses.GAME, Game.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.OPTIONS, MainStatuses.OPTIONS_MENU, OptionsMenu.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.EXIT, MainStatuses.FINAL);

        //addTransition(MainStatuses.OPTIONS_MENU, MainEvents.NULL, MainStatuses.OPTIONS_MENU);
        addTransition(MainStatuses.OPTIONS_MENU, MainEvents.BACK, MainStatuses.MAIN_MENU, MainMenu.class);

        addTransition(MainStatuses.GAME, MainEvents.EXIT, MainStatuses.MAIN_MENU);*/

        addTransition(MainStatuses.INIT, MainEvents.NULL, MainStatuses.PRESENTATION, Presentation.class);

        addTransition(MainStatuses.PRESENTATION, MainEvents.NULL, MainStatuses.MAIN_MENU, MainMenu.class);

        addTransition(MainStatuses.MAIN_MENU, MainEvents.OPTIONS, MainStatuses.OPTIONS_MENU, OptionsMenu.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.EXIT, MainStatuses.FINAL);

        addTransition(MainStatuses.OPTIONS_MENU, MainEvents.BACK, MainStatuses.MAIN_MENU, MainMenu.class);
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
        //MainStatuses currentStatus = MainStatuses.INIT;
        //MainEvents event = MainEvents.NULL;

        while (currentStatus != MainStatuses.FINAL) {
            System.out.println("----------------");
            System.out.println("currentStatus = " + currentStatus);
            System.out.println("currentEvent = " + event);

            currentStatus = gameStatus.getCurrentStatus();
            Class controlClass = getControlClass(currentStatus, event);

            runControlClass(controlClass);
            //currentStatus = transitionInfo.getNextStatus();
            //event = gameStatus.getCurrentEvent();
        }
    }

    private Class getControlClass(MainStatuses currentStatus, MainEvents event) {
        return transitionsList.stream()
                .filter(t -> t.getCurrentStatus() == currentStatus && t.getEvent() == event)
                .findFirst()
                .get()
                .getClassToExecute();
    }

    private void runControlClass(Class clazz) {
        gameStatus.setCurrentEvent(MainEvents.NULL);

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

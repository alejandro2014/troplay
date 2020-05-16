package troplay;

import lombok.Getter;
import troplay.enums.MainEvents;
import troplay.enums.MainStatuses;

import java.util.ArrayList;
import java.util.List;

public class ControlFlujo {
    @Getter
    private GameStatus gameStatus = new GameStatus();
    private List<TransitionInfo> transitionsList = new ArrayList<>();

    private void addTransitions() {
        addTransition(MainStatuses.PRESENTATION, MainEvents.NULL, MainStatuses.MAIN_MENU, ControlPresentacion.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.NULL, MainStatuses.MAIN_MENU, MainMenu.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.START, MainStatuses.GAME, Juego.class);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.OPTIONS, MainStatuses.OPTIONS_MENU, null);
        addTransition(MainStatuses.MAIN_MENU, MainEvents.EXIT, MainStatuses.FINAL, null);
        addTransition(MainStatuses.OPTIONS_MENU, MainEvents.NULL, MainStatuses.OPTIONS_MENU, OptionsMenu.class);
        addTransition(MainStatuses.OPTIONS_MENU, MainEvents.BACK, MainStatuses.MAIN_MENU, null);
        addTransition(MainStatuses.GAME, MainEvents.EXIT, MainStatuses.MAIN_MENU, null);
    }

    private void addTransition(MainStatuses currentStatus, MainEvents event, MainStatuses nextStatus, Class classToExecute) {
        TransitionInfo transitionInfo = TransitionInfo.builder()
                .currentStatus(currentStatus)
                .event(event)
                .nextStatus(nextStatus)
                .classToExecute(classToExecute)
                .build();

        transitionsList.add(transitionInfo);
    }

    public void statusCycle() {
        MainStatuses estadoActual = MainStatuses.PRESENTATION;
        MainEvents eventoEntrada = MainEvents.NULL;

        while (estadoActual != MainStatuses.FINAL) {
            MainStatuses finalEstadoActual = estadoActual;
            MainEvents finalEventoEntrada = eventoEntrada;

            TransitionInfo transitionInfo = transitionsList.stream()
                    .filter(t -> t.getCurrentStatus() == finalEstadoActual && t.getEvent() == finalEventoEntrada)
                    .findFirst()
                    .get();

            runControlClass(transitionInfo.getClassToExecute());
            estadoActual = transitionInfo.getNextStatus();
            eventoEntrada = gameStatus.getCurrentEvent();
        }
    }

    private void runControlClass(Class clazz) {
        if(clazz == null) {
            gameStatus.setCurrentEvent(MainEvents.NULL);
            return;
        }

        ClaseControladora controllerClass = null;

        try {
            controllerClass = (ClaseControladora) clazz.getConstructor(GameStatus.class).newInstance(gameStatus);
        } catch (Exception ex) {
            System.err.println("Can't load the class " + clazz);
            System.exit(1);
        }

        controllerClass.bucleJuego();
    }

    public static void main(String[] args) {
        ControlFlujo flowControl = new ControlFlujo();
        flowControl.addTransitions();
        flowControl.statusCycle();

        flowControl.getGameStatus().getPanel().descargarGraficos();
        System.exit(0);
    }
}

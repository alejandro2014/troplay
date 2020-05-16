package troplay;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ControlFlujo {
    @Getter
    private GameStatus gameStatus = new GameStatus();;
    private List<TransitionInfo> transitionsList = new ArrayList<>();

    private void addTransitions() {
        addTransition(Const.ESTADO_PRESENTACION, Const.EVENTO_NULO, Const.ESTADO_MENU_PRINCIPAL, ControlPresentacion.class);
        addTransition(Const.ESTADO_MENU_PRINCIPAL, Const.EVENTO_NULO, Const.ESTADO_MENU_PRINCIPAL, MainMenu.class);
        addTransition(Const.ESTADO_MENU_PRINCIPAL, Const.EVENTO_EMPEZAR, Const.ESTADO_JUEGO, Juego.class);
        addTransition(Const.ESTADO_MENU_PRINCIPAL, Const.EVENTO_OPCIONES, Const.ESTADO_OPCIONES, null);
        addTransition(Const.ESTADO_MENU_PRINCIPAL, Const.EVENTO_SALIR, Const.ESTADO_FINAL, null);
        addTransition(Const.ESTADO_OPCIONES, Const.EVENTO_NULO, Const.ESTADO_OPCIONES, OptionsMenu.class);
        addTransition(Const.ESTADO_OPCIONES, Const.EVENTO_VOLVER, Const.ESTADO_MENU_PRINCIPAL, null);
        addTransition(Const.ESTADO_JUEGO, Const.EVENTO_SALIR, Const.ESTADO_MENU_PRINCIPAL, null);
    }

    private void addTransition(int currentStatus, int event, int nextStatus, Class classToExecute) {
        TransitionInfo transitionInfo = TransitionInfo.builder()
                .currentStatus(currentStatus)
                .event(event)
                .nextStatus(nextStatus)
                .classToExecute(classToExecute)
                .build();

        transitionsList.add(transitionInfo);
    }

    public void statusCycle() {
        int estadoActual = Const.ESTADO_PRESENTACION;
        int eventoEntrada = Const.EVENTO_NULO;

        while (estadoActual != Const.ESTADO_FINAL) {
            int finalEstadoActual = estadoActual;
            int finalEventoEntrada = eventoEntrada;

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
            gameStatus.setCurrentEvent(Const.EVENTO_NULO);
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

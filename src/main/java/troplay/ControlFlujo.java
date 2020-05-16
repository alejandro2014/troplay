package troplay;

import lombok.Getter;

public class ControlFlujo {
    private int estadoActual = Const.ESTADO_PRESENTACION;
    private int eventoEntrada = Const.EVENTO_NULO;

    @Getter
    private GameStatus gameStatus;

    public ControlFlujo() {
        gameStatus = new GameStatus();
    }

    public void statusCycle() {
        while (estadoActual != Const.ESTADO_FINAL) {
            switch (estadoActual) {
                case Const.ESTADO_PRESENTACION:
                    switch (eventoEntrada) {
                        case Const.EVENTO_NULO:
                            runControlClass(ControlPresentacion.class);
                            estadoActual = Const.ESTADO_MENU_PRINCIPAL;
                            break;
                    }
                    break;

                case Const.ESTADO_MENU_PRINCIPAL:
                    switch (eventoEntrada) {
                        case Const.EVENTO_NULO:
                            runControlClass(MainMenu.class);
                            estadoActual = Const.ESTADO_MENU_PRINCIPAL;
                            break;

                        case Const.EVENTO_EMPEZAR:
                            runControlClass(Juego.class);
                            estadoActual = Const.ESTADO_JUEGO;
                            break;

                        case Const.EVENTO_OPCIONES:
                            runControlClass(null);
                            estadoActual = Const.ESTADO_OPCIONES;
                            break;

                        case Const.EVENTO_SALIR:
                            runControlClass(null);
                            estadoActual = Const.ESTADO_FINAL;
                            break;
                    }
                    break;

                case Const.ESTADO_OPCIONES:
                    switch (eventoEntrada) {
                        case Const.EVENTO_NULO:
                            runControlClass(OptionsMenu.class);
                            estadoActual = Const.ESTADO_OPCIONES;
                            break;

                        case Const.EVENTO_VOLVER:
                            runControlClass(null);
                            estadoActual = Const.ESTADO_MENU_PRINCIPAL;
                            break;
                    }
                    break;

                case Const.ESTADO_JUEGO:
                    switch (eventoEntrada) {
                        case Const.EVENTO_SALIR:
                            runControlClass(null);
                            estadoActual = Const.ESTADO_MENU_PRINCIPAL;
                            break;
                    }
                    break;
            }

            eventoEntrada = gameStatus.getCurrentEvent();
        }
    }

    private void runControlClass(Class clazz) {
        if(clazz == null) {
            eventoEntrada = Const.EVENTO_NULO;
            gameStatus.setCurrentEvent(Const.EVENTO_NULO);
            return;
        }

        ClaseControladora controllerClass = null;

        try {
            controllerClass = (ClaseControladora) clazz
                    .getConstructor(GameStatus.class)
                    .newInstance(gameStatus);
        } catch (Exception ex) {
            System.err.println("Can't load the class " + clazz);
            System.exit(1);
        }

        controllerClass.bucleJuego();
    }

    public static void main(String[] args) {
        ControlFlujo flowControl = new ControlFlujo();
        flowControl.statusCycle();

        flowControl.getGameStatus().getPanel().descargarGraficos();
        System.exit(0);
    }
}

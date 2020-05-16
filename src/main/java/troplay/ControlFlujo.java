package troplay;

public class ControlFlujo {
    private int estadoActual = Const.ESTADO_PRESENTACION;
    private int eventoEntrada = Const.EVENTO_NULO;
    private int nuevoEstado;

    private GameStatus gameStatus;

    public ControlFlujo() {
        gameStatus = new GameStatus();

        while (estadoActual != Const.ESTADO_FINAL) {
            estadoActual = cambiarEstado(estadoActual, eventoEntrada);
        }

        gameStatus.getPanel().descargarGraficos();
        System.exit(0);
    }

    public int cambiarEstado(int estado, int evento) {
        switch(estado) {
            case Const.ESTADO_PRESENTACION:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        runControlClass(ControlPresentacion.class);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        break;
                }
                break;

            case Const.ESTADO_MENU_PRINCIPAL:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        runControlClass(MainMenu.class);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        break;

                    case Const.EVENTO_EMPEZAR:
                        runControlClass(Juego.class);
                        nuevoEstado = Const.ESTADO_JUEGO;
                        break;

                    case Const.EVENTO_OPCIONES:
                        runControlClass(null);
                        nuevoEstado = Const.ESTADO_OPCIONES;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;

                    case Const.EVENTO_SALIR:
                        runControlClass(null);
                        nuevoEstado = Const.ESTADO_FINAL;
                        break;
                }
                break;

            case Const.ESTADO_OPCIONES:
                switch(evento) {
                    case Const.EVENTO_NULO:
                        runControlClass(OptionsMenu.class);
                        nuevoEstado = Const.ESTADO_OPCIONES;
                        break;

                    case Const.EVENTO_VOLVER:
                        runControlClass(null);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;

            case Const.ESTADO_JUEGO:
                switch(evento) {
                    case Const.EVENTO_SALIR:
                        runControlClass(null);
                        nuevoEstado = Const.ESTADO_MENU_PRINCIPAL;
                        eventoEntrada = Const.EVENTO_NULO;
                        break;
                }
                break;
        }

        return nuevoEstado;
    }

    private void runControlClass(Class clazz) {
        if(clazz == null) {
            return;
        }

        ClaseControladora controllerClass = null;

        try {
            controllerClass = (ClaseControladora) clazz
                    .getConstructor(GameStatus.class, ControlFlujo.class)
                    .newInstance(gameStatus, this);
        } catch (Exception ex) {
            System.err.println("Can't load the class " + clazz);
            System.exit(1);
        }

        controllerClass.bucleJuego();
    }

    public void setEvento(int eventoNuevo) {
        eventoEntrada = eventoNuevo;
    }

    public static void main(String[] args) {
        new ControlFlujo();
    }
}

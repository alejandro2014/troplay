package troplay;

class Presentation extends ControllerClass {
    private final GameStatus gameStatus;
    private boolean acabar = false;
    private int contador = 0;
    private Panel panel = null;
    private int contadorInicial, contadorFinal;

    public Presentation(GameStatus gameStatus) {
        this.gameStatus = gameStatus;

        Ventana vent = gameStatus.getWindow();
        panel = vent.getPanel();
        panel.setModo(Const.MODOPRESEN);
    }

    public void loop() {
        contadorInicial = panel.getContadorTimer();
        contadorFinal = contadorInicial + 60;
        while (!acabar) {
            acabar = finalBucle();
            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean finalBucle() {
        contador = panel.getContadorTimer();
        return (contador >= contadorFinal);
    }

    public void controlEntrada() {

    }
}
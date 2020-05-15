package troplay;

/**
 * Control de la pantalla de presentación
 * @author alejandro
 */
class ControlPresentacion extends ClaseControladora {
    private boolean acabar = false;
    private int contador = 0;
    private Panel panel = null;
    private int contadorInicial, contadorFinal;

    /**
     * Clase que controla la pantalla de presentación
     * @param vent Referencia a la ventana
     * @param control Clase controladora de los estados del juego
     */
    public ControlPresentacion (Ventana vent, ControlFlujo control) {
        panel = vent.getPanel();
    }

    /**
     * El bucle de la presentación lo único que hace es esperar unos segundos
     * antes de empezar
     */
    public void bucleJuego() {
        contadorInicial = panel.getContadorTimer();
        contadorFinal = contadorInicial + 60;
        while (!acabar) {
            acabar = finalBucle();
            try {
                Thread.sleep(70);
            } catch (InterruptedException ex) {}
        }
    }

    /**
     * Se consigue la espera en la pantalla de presentación
     * @return Verdadero si ha acabado el tiempo, falso si no
     */
    public boolean finalBucle() {
        contador = panel.getContadorTimer();
        return (contador >= contadorFinal);
    }

    public void controlEntrada() {}
}

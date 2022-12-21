package troplay;

class ControlPresentacion extends troplay.ClaseControladora {
    private boolean acabar = false;
    private int contador = 0;
    private Panel panel = null;
    private int contadorInicial, contadorFinal;
    
    public ControlPresentacion (Ventana vent, ControlFlujo control) {
        panel = vent.getPanel();
        this.bucleJuego();
    }
    
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
    
    public boolean finalBucle() {
        contador = panel.getContadorTimer();
        return (contador >= contadorFinal);
    }

    public void controlEntrada() {}
}

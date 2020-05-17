package troplay.game;

import lombok.Getter;
import lombok.Setter;
import troplay.Const;
import troplay.Drawable;

import java.util.ArrayList;

public class Pregunta extends Drawable {
    private final int NUM_RESPUESTAS = 3;

    @Getter
    private String textoPregunta;

    private String respuestas[] = new String[NUM_RESPUESTAS];

    @Setter
    private Integer respCorrecta = -1;

    @Setter
    private Integer idPregunta;
    
    //Variables para la correcta maquetación de las preguntas
    private final int NORMAL = 0;
    private final int CURSIVA = 1;
    private final int SUBINDICE = 2;
    private final int SUPERINDICE = 3;

    @Getter
    private Integer lineasPregunta = 0;

    private int[] lineasRespuesta = {0,0,0};
    
    private ArrayList trozosPregunta = new ArrayList();
    private ArrayList tiposPregunta = new ArrayList();
    private ArrayList trozosRespuesta[] = new ArrayList[3];
    private ArrayList tiposRespuesta[] = new ArrayList[3];
    
    private int estado = NORMAL;
    private char caracter;
    private int limiteAnterior = 0;
    private int ancho = Const.ANCHOPREGUNTA;
    
    public Pregunta(int ancho) {
        this.ancho = ancho;
        for(int contador = 0; contador < 3; contador++) {
            trozosRespuesta[contador] = new ArrayList<String>();
            tiposRespuesta[contador] = new ArrayList<Integer>();
        }
    }
    
    public void añadirCadena(String texto, ArrayList trozosCadena, ArrayList tiposCadena, int contador, int tipo) {
        trozosCadena.add(texto.substring(limiteAnterior,contador));
        tiposCadena.add(tipo);
        limiteAnterior = contador + 1;
    }

    public void formatearPregunta() {
        int nuevaLong = textoPregunta.length();
        lineasPregunta = nuevaLong / ancho + (nuevaLong % ancho != 0 ? 1 : 0);
        formateo(textoPregunta, trozosPregunta, tiposPregunta);
    }
    
    public void formatearRespuesta(int numRespuesta) {
        int nuevaLong = respuestas[numRespuesta].length();
        lineasRespuesta[numRespuesta] = nuevaLong / Const.ANCHORESPUESTA +
                                        (nuevaLong % Const.ANCHORESPUESTA != 0 ? 1 : 0);
        formateo(respuestas[numRespuesta], trozosRespuesta[numRespuesta], tiposRespuesta[numRespuesta]);
    }

    public void formateo(String texto, ArrayList<String> trozosCadena, ArrayList<Integer> tiposCadena) {
        int longitud = texto.length();
        int contador;
        
        caracter = texto.charAt(0);
        switch(caracter) { //Determinación del estado inicial
            case '$': estado = CURSIVA;     limiteAnterior = 1; break;
            case '#': estado = SUBINDICE;   limiteAnterior = 1; break;
            case '@': estado = SUPERINDICE; limiteAnterior = 1; break;
        }
            
        //Análisis de la cadena por caracteres
        for(contador = 1; contador < longitud; contador++) {
            caracter = texto.charAt(contador);
            
            switch(estado) {
                case NORMAL:
                    switch(caracter) {
                        case '$': añadirCadena(texto,trozosCadena,tiposCadena,contador,estado); estado = CURSIVA; break;
                        case '#': añadirCadena(texto,trozosCadena,tiposCadena,contador,estado); estado = SUBINDICE; break;
                        case '@': añadirCadena(texto,trozosCadena,tiposCadena,contador,estado); estado = SUPERINDICE; break;
                    }
                    
                    break;
                    
                default:
                    if(caracter == '$' || caracter == '#' || caracter == '@') {
                        añadirCadena(texto,trozosCadena,tiposCadena,contador,estado); estado = NORMAL; 
                    }
                    break;
            }
        }
        trozosCadena.add(texto.substring(limiteAnterior,contador));
        tiposCadena.add(estado);
        limiteAnterior = 0;
    }

    public ArrayList getTrozosCadena(int tipo) {
        return (tipo == 0 ? trozosPregunta : trozosRespuesta[tipo-1]);
    }
    public ArrayList getTiposCadena(int tipo) {
        return (tipo == 0 ? tiposPregunta : tiposRespuesta[tipo-1]);
    }
    
    public int getNumTrozosCadena(ArrayList trozosCadena) {return trozosCadena.size();}
    public int getLineasResp(int numResp) {return lineasRespuesta[numResp];}

    public void setTextoPregunta(String texto) {
        textoPregunta = texto;
        formatearPregunta();
    }

    public void setTextoRespuesta(int numRespuesta, String texto) {
        int longitud = texto.length();
        
        respuestas[numRespuesta] = texto.substring(0,1).toUpperCase() + texto.substring(1,longitud);
        formatearRespuesta(numRespuesta);
    }

    public boolean compruebaCorrecta(int opcion) {
        return (opcion == respCorrecta);
    }
}

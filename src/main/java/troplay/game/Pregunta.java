package troplay.game;

import troplay.Const;
import troplay.Dibujable;

import java.util.ArrayList;

/**
 * Clase que representa una pregunta
 * @author alejandro
 */
public class Pregunta extends Dibujable {
    private String textoPregunta;
    private String respuestas[] = new String[Const.NUM_RESPUESTAS];
    private int respCorrecta = -1;
    
    private int idPregunta;
    
    //Variables para la correcta maquetación de las preguntas
    private final int NORMAL = 0;
    private final int CURSIVA = 1;
    private final int SUBINDICE = 2;
    private final int SUPERINDICE = 3;
    
    private int lineasPregunta = 0;
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
    
    /* Los dos métodos siguientes sirven para formatear correctamente el texto de
     * las preguntas y las respuestas */
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
    
    /**
     * Separa la cadena en trozos de cada tipo (normal, cursiva, subíndice y superíndice)
     * @param texto Texto que se quiere separar
     * @param trozosCadena Array donde se pondrán los trozos de la cadena
     * @param tiposCadena Igual pero con los tipos de cada trozo
     */
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
    
    /**
     * Devuelve un array con trozos de cadena
     * @param tipo [1-3]: Número de respuesta, 0: Pregunta
     * @return Trozos de cadena solicitados
     */    
    public ArrayList getTrozosCadena(int tipo) {
        return (tipo == 0 ? trozosPregunta : trozosRespuesta[tipo-1]);
    }
    
    /**
     * Lo mismo que getTrozosCadena pero con los tipos
     */
    public ArrayList getTiposCadena(int tipo) {
        return (tipo == 0 ? tiposPregunta : tiposRespuesta[tipo-1]);
    }
    
    public int getNumTrozosCadena(ArrayList trozosCadena) {return trozosCadena.size();}
    public String getTextoPregunta() {return textoPregunta;}
    public String getTextoRespuesta(int numResp) {return respuestas[numResp];}
    public int getLineasPreg() {return lineasPregunta;}
    public int getLineasResp(int numResp) {return lineasRespuesta[numResp];}
    
    public void setIdPregunta(int valor) {idPregunta = valor;}
    public void setRespCorrecta(int correcta) {respCorrecta = correcta;}
    
    /**
     * Establece y formatea el texto de la pregunta
     * @param texto Texto de la pregunta
     */
    public void setTextoPregunta(String texto) {
        textoPregunta = texto;
        formatearPregunta();
    }
    
    /**
     * Establece y formatea el texto de la respuesta
     * @param numRespuesta Número de la respuesta a formatear
     * @param texto Texto de la respuesta
     */
    public void setTextoRespuesta(int numRespuesta, String texto) {
        int longitud = texto.length();
        
        respuestas[numRespuesta] = texto.substring(0,1).toUpperCase() + texto.substring(1,longitud);
        formatearRespuesta(numRespuesta);
    }
    
    /**
     * Comprobación de la respuesta correcta
     * @param opcion Opción que ha marcado el jugador
     * @return Verdadero si la respuesta marcada es correcta
     */
    public boolean compruebaCorrecta(int opcion) {return (opcion == respCorrecta);}
}

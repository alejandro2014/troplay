package troplay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control del acceso a la base de datos
 * @author alejandro
 */
public class ConexionJDBC {
    private Connection conexion;
    private ResultSet resultado,resultado2,resultado3;
    private Statement comando;
    private int idioma;
    private String cadConexion, driver;
    
    /**
     * Conexión local con la base de datos
     * @param idio Idioma utilizado
     */
    public ConexionJDBC(int idio) {
        cadConexion = "jdbc:sqlite:tenia.sqlite";
        driver = "org.sqlite.JDBC";
        idioma = idio+1;
        conectar();
    }
    
    /**
     * Conexión con una base de datos
     */
    public void conectar() {
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(cadConexion);
            comando = conexion.createStatement();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Obtención del número de preguntas de la base de datos en función
     * de la dificultad y del idioma
     * @param dificultad Dificultad de la pregunta
     * @return El número de preguntas
     * @throws java.sql.SQLException
     */
    public int obtenerNumPreguntas(int dificultad) throws SQLException {
        resultado = ejecutarConsulta("select count(*) from respuesta where" +
                " dificultad = " + dificultad + " and id_idioma = " + idioma);
        if(!resultado.next()) return -1;
        
        String cadena = resultado.getString(1);
        
        return Integer.parseInt(cadena) / 3;
    }
    
    /**
     * Obtención de una pregunta con un identificador y una dificultad determinados
     * @param num Número de la pregunta dentro de la base de datos
     * @param dificultad Dificultad de la pregunta
     * @return La pregunta completa
     * @throws java.sql.SQLException
     */
    public Pregunta obtenerPregunta(int num, int dificultad) throws SQLException {
        Pregunta  pregunta = new Pregunta(Const.ANCHOPREGUNTA);
        int idPregunta=0;
        int contador = 0;
        
        //Obtención del identificador de la pregunta
        resultado = ejecutarConsulta("select distinct id_pregunta from respuesta where " +
                                     "id_idioma = " + idioma + " and dificultad = " +
                                     dificultad + " limit 1 offset " + num);
        if(resultado.next()) {
            idPregunta = resultado.getInt("id_pregunta");
            pregunta.setIdPregunta(idPregunta);
        }
        resultado.close();
        
        //Obtención del texto de las respuestas
        resultado2 = ejecutarConsulta("select respuesta, correcta from respuesta where id_pregunta = " + idPregunta);
        while(resultado2.next()) {
            pregunta.setTextoRespuesta(contador,resultado2.getString("respuesta"));
            if(resultado2.getString("correcta").equals("true")) pregunta.setRespCorrecta(contador);
            
            contador++;
        }
        resultado2.close();
        
        if (pregunta != null) {
            resultado3 = ejecutarConsulta("select enunciado from preguntas " +
                                          "where id_pregunta = " + idPregunta);
            if(resultado3.next()) pregunta.setTextoPregunta(resultado3.getString("enunciado"));
            resultado3.close();
        }
        
        return pregunta;
    }
    
    /**
     * Obtención del número de curiosidades del final del juego
     * @return Número de curiosidades
     */
    public int getNumeroCuriosidades() throws SQLException {
        resultado = ejecutarConsulta("select count(*) from curiosidadtropical where id_idioma = " + idioma);
        resultado.next();
        
        String cadena = resultado.getString(1);
        return Integer.parseInt(cadena);
    }
    
    /**
     * Obtención del texto de la curiosidad que aparece al final
     * @return El texto de la curiosidad
     * @throws java.sql.SQLException
     */
    public String getTextoCuriosidad() throws SQLException {
        int cantidad = getNumeroCuriosidades();
        int valor;
        Random rnd = new Random();
        Date fecha = new Date();
        rnd.setSeed(fecha.getTime());
        
        if((valor = rnd.nextInt() % cantidad) < 0) valor *= -1;
        
        resultado = ejecutarConsulta("SELECT curiosidad from curiosidadtropical where id_idioma = " +
                                     idioma + " limit 1 offset " + valor);

        resultado.next();
        return resultado.getString("curiosidad");
    }
    
    /**
     * Ejecución de una consulta SQL determinada
     * @param consulta Cadena con la consulta que se queire realizar
     * @return Resultado de la consulta
     */
    public ResultSet ejecutarConsulta(String consulta) {
        ResultSet result = null;
        
        try {
            result = comando.executeQuery(consulta);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    /**
     * Realiza una inserción determinada
     * @param accion SQL a ejecutar
     */
    public void ejecutarInsercion(String accion) {
        try {
            comando.executeUpdate(accion);
        } catch (SQLException ex) {
            System.err.println();
            Logger.getLogger(ConexionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Finalización de la clase cerrando la conexión
     */
    protected void cerrarConexion() {
        try {
            comando.close();
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

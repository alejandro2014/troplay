package troplay;

import troplay.enums.Language;
import troplay.game.Pregunta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionJDBC {
    private Connection conexion;
    private ResultSet resultado,resultado2,resultado3;
    private Statement comando;
    private int idioma;
    private String cadConexion, driver;

    public ConexionJDBC(Language idio) {
        String baseDir = System.getProperty("user.dir");

        cadConexion = "jdbc:sqlite:" + baseDir + "/src/main/resources/db/tenia.sqlite";
        driver = "org.sqlite.JDBC";
        idioma = idio == Language.SPANISH ? 1 : 2;

        conectar();
    }

    public void conectar() {
		System.out.println("conectar()");

        try {
			System.out.println("Class.forName - " + driver.toString());
            Class.forName(driver);

            System.out.println("DriverManager.getConnection - " + cadConexion);
            conexion = DriverManager.getConnection(cadConexion);

            System.out.println("conexion - " + conexion);
            comando = conexion.createStatement();

			if(comando == null) {
				System.out.println("Comando = null");
			} else {
				System.out.println(">>>>>>>>>>>");
			}
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int obtenerNumPreguntas(int dificultad) throws SQLException {
        resultado = ejecutarConsulta("select count(*) from respuesta where" +
                " dificultad = " + dificultad + " and id_idioma = " + idioma);
        if(!resultado.next()) return -1;

        String cadena = resultado.getString(1);

        return Integer.parseInt(cadena) / 3;
    }

    public Pregunta obtenerPregunta(int num, int dificultad) throws SQLException {
        Pregunta  pregunta = new Pregunta(Const.ANCHOPREGUNTA);
        int idPregunta=0;
        int contador = 0;

        resultado = ejecutarConsulta("select distinct id_pregunta from respuesta where " +
                                     "id_idioma = " + idioma + " and dificultad = " +
                                     dificultad + " limit 1 offset " + num);
        if(resultado.next()) {
            idPregunta = resultado.getInt("id_pregunta");
            pregunta.setIdPregunta(idPregunta);
        }
        resultado.close();

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

    public int getNumeroCuriosidades() throws SQLException {
        resultado = ejecutarConsulta("select count(*) from curiosidadtropical where id_idioma = " + idioma);
        resultado.next();

        String cadena = resultado.getString(1);
        return Integer.parseInt(cadena);
    }

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

    public ResultSet ejecutarConsulta(String consulta) {
        ResultSet result = null;

        try {
			System.out.println(">>" + consulta + " - " + comando);
            result = comando.executeQuery(consulta);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return result;
    }

    protected void cerrarConexion() {
        try {
            comando.close();
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

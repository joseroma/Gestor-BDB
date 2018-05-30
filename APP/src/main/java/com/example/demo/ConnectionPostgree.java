package com.example.demo;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.sql.*;

public class ConnectionPostgree {

    //Definimos la URL con la que vamos a trabajar
    String connectionString = "jdbc:postgresql://localhost:5432/bdb_sica?user=postgres&password=name&autoReconnect=true&useSSL=false" ;

    //Inicializamos los parámetros necesarios
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    String selectSql="";
   List<Integer> tiempos = new ArrayList<Integer>();
   public List<Integer> HacerConsulta(String consulta) {

       try {
           //Iniciamos el driver
           Class.forName("org.postgresql.Driver");
           //Abrimos la conexión
           connection = DriverManager.getConnection(connectionString);
           //Crea un objeto SQLServerStatement para enviar instrucciones SQL a la base de datos.
           statement = connection.createStatement();
           //Guardamos la consulta que queremos en una variable
           selectSql = consulta;
           //Ejecuta la instrucción SQL especificada y devuelve una sola SQLServerResultSet objeto.
           result = statement.executeQuery(selectSql);

           //Recorremos y añadimos los resultados a la lista de salida
           int cont = 0;
           while (result.next()){
               tiempos.add(result.getInt(cont));
               System.out.println(result.getInt(1)+ " "+ result.getString(2));
               cont++;
           }
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (result != null) try {
               result.close();
           } catch (Exception e) {
           }
           if (statement != null) try {
               statement.close();
           } catch (Exception e) {
           }
           if (connection != null) try {
               connection.close();
           } catch (Exception e) {
           }
       }
       return tiempos;
   }
}  
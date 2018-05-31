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


public class ConnectionNoSQL {

	
	//Definimos la URL con la que vamos a trabajar
	String connectionString = "jdbc:mongo://127.0.0.1:27017/bdb_sica";
	                   
	
   //Inicializamos los parámetros necesarios
	Connection connection = null;
	    Statement statement = null;
	    ResultSet result = null;
	    String selectSql="";
	    List<Float> tiempos = new ArrayList<Float>();
	    public List<Float> HacerConsulta(String consulta) {

	       try {
	           
	    	   Class.forName("mongodb.jdbc.MongoDriver");
	           connection = DriverManager.getConnection(connectionString, "root", "name");
	           statement = connection.createStatement();
	           selectSql = consulta;
	           result = statement.executeQuery(selectSql);
	           
	           /* ESTO NO VALEEEEE
	           int cont = 0;
	           while (result.next()){
	               tiempos.add(result.getInt(cont));
	               System.out.println(result.getInt(1)+ " "+ result.getString(2));
	               cont++;
	              */
	           }
	          
	       
	    	catch (Exception e) { e.printStackTrace(); }
	       
	    	finally {
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
	


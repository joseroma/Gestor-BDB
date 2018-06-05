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
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

public class ConnectionNoSQL {

	
	//Definimos la URL con la que vamos a trabajar
	String connectionString = "jdbc:mongo://localhost:27017/bdb_sica?user=root&password=name&autoReconnect=true&useSSL=false";

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
			   long start = System.currentTimeMillis();
			   Thread.sleep(2000);
	           selectSql = consulta;
	           result = statement.executeQuery(selectSql);

			   while (result.next()) {}

			   long elapsedTimeMillis = System.currentTimeMillis() - start;
			   float elapsedTimeSec = elapsedTimeMillis/1000F;
			   tiempos.add(elapsedTimeSec);

	           }
	          
	       
	    	catch (Exception e) {
	       	System.out.println("ERROR: Unable to load SQLServer JDBC Driver");
	       	e.printStackTrace(); }
	       
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
	


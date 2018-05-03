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

public class ConectionDatabase {
	public static void main(String[] args) {  
        String connectionString = "jdbc:mysql://localhost:3306/bdb_cica?user=root&password=MyNewPass&autoReconnect=true&useSSL=false" ;
      
        Connection connection = null;
        Statement statement = null;   
        ResultSet result = null;  
        String selectSql="";
        
        
         try { 
        
        	Class.forName("com.mysql.jdbc.Driver"); 
        	connection = DriverManager.getConnection(connectionString);
        	statement = connection.createStatement(); 
        	
        	//Guardar los nombres de todas las tablas
        	selectSql = "select * from information_schema.tables where table_schema = '<bdb_cica>';";
        	List<String> nombreTablas = new ArrayList<>();  
            result = statement.executeQuery(selectSql);
            while (result.next())  { nombreTablas.add(result.getString(1));}
            //Realizar consulta a todas las tablas
            for (int i = 0; i < nombreTablas.size(); i++) {
            	String table = nombreTablas.get(i);
            	/*
            	selectSql =  "ALTER TABLE “ + table + “ ENGINE=InnoDB";
      			statement = connection.createStatement();  
                result = statement.executeQuery(selectSql);
                */
            }
            
           
            long start = System.currentTimeMillis();
            Thread.sleep(2000);
            //REALIZAR CONSULTA
            selectSql = "SELECT ex.exon_id, end_phase, biotype\n" + 
            		"FROM transcript tr, exon_transcript extr, exon ex\n"+
            		"WHERE tr.transcript_id = extr.transcript_id AND extr.exon_id = ex.exon_id AND biotype='miRNA'\n"+
            		"ORDER BY exon_id;";

            result = statement.executeQuery(selectSql);
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            float elapsedTimeSec = elapsedTimeMillis/1000F;
            
       
            while (result.next())  { 
            	System.out.println(result.getInt(1)+ " "+ result.getInt(2)+ " "+ result.getString(3));
              //columnas, una por cada atributo pedido
    
            } 
            
            System.out.println("execution time: " + elapsedTimeSec + " seconds");   
       } 
        
        catch (Exception e) {  e.printStackTrace(); } 
        
        finally {  
        	if (result != null) try { result.close(); } catch(Exception e) {}  
            if (statement != null) try { statement.close(); } catch(Exception e) {}  
            if (connection != null) try { connection.close(); } catch(Exception e){}    
        }  
    } 
	
	
	
	
	
	/* CREAR CONSULTA CUANDO TENGAMOS JSON
	public static String crearConsulta(String name, String table, List<String> whr) {
		//confirmar que * en name tmbn funciona
		String consulta = "select " + name + " from " + table + " where " + advancedSearch(whr);
		return consulta;
	}
	
	public static String advancedSearch(List<String> whr) {
		String whre="";
		 for (int i = 0; i < whr.size(); i++)
			    whre += whr.get(i) + "AND, ";
		 return whre;
	}
	
	*/
	
}  




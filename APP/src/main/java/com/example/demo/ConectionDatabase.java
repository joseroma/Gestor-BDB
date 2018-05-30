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
        String connectionString = "jdbc:psql://localhost:3306/bdb_sica?user=jose&password=name&autoReconnect=true&useSSL=false" ;
      
        Connection connection = null;
        Statement statement = null;   
        ResultSet result = null;  
        String selectSql="";
        
        //PONER BIEN CUANDO TENGA JSON
        /*String name="*";
        String table = "allele";
        List<String> whr = new ArrayList<String>();
        whr.add("dato1");
        whr.add("dato2");
        */
                
        
         try { 
        
        	Class.forName("com.mysql.jdbc.Driver"); //Class.forName("org.postgresql.Driver");
        	connection = DriverManager.getConnection(connectionString);
        	statement = connection.createStatement(); 
        	
        	
        	//ELIMINAR INDICES
        		//Guardar todos los nombres de las tablas en lista nombreTablas
        	selectSql = "select * from information_schema.tables where table_schema = '<BDB>';";
        	List<String> nombreTablas = new ArrayList<>();  
            result = statement.executeQuery(selectSql);
            while (result.next())  { nombreTablas.add(result.getString(1));}
            	//Eliminar los indices de la lista de tabals
            for (int i = 0; i < nombreTablas.size(); i++) {
            	String table = nombreTablas.get(i);
            	selectSql =  "ALTER TABLE " + table + "DROP COLUMN " + table+ "_id" ;
            			//"ALTER TABLE '" + table + "' DISABLE KEYS;";	
      			statement = connection.createStatement();  
                result = statement.executeQuery(selectSql);
            }
            
           
            long start = System.currentTimeMillis();
            Thread.sleep(2000);
            //REALIZAR CONSULTA
            selectSql = "SELECT gene_id, ex.exon_id\n" + 
            		"FROM transcript tr, exon_transcript extr, exon ex\n" + 
            		"WHERE tr.transcript_id = extr.transcript_id AND extr.exon_id = ex.exon_id;";
            result = statement.executeQuery(selectSql);
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            float elapsedTimeSec = elapsedTimeMillis/1000F;
            
        
            
            //System.out.println(result.getInt(1));
            while (result.next())  { 
            	//String host = result.getString(1);
            	//String user =result.getString("User");
                System.out.println(result.getInt(1)+ " "+ result.getString(2));
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


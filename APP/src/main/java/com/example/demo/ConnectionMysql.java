package com.example.demo;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.sql.*;

public class ConnectionMysql {

    List<Float> tiempos =new ArrayList<>() ;

	public List<Float> HacerConsulta(String consulta) {

        List<String> dbList = Arrays.asList("bdb_cica", "bdb_cisa", "bdb_sica", "bdb_sisa");

        for(int i=0;i<dbList.size();i++){
            String connectionString = "jdbc:mysql://localhost:3307/"+dbList.get(i)+"?user=root&password=name&autoReconnect=true&useSSL=false" ;
            Connection connection = null;
            Statement statement = null;
            ResultSet result = null;
            String selectSql="";


		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionString);
			statement = connection.createStatement();
			long start = System.currentTimeMillis();
			Thread.sleep(2000);
			selectSql = consulta;
			result = statement.executeQuery(selectSql);
			while (result.next())  {}
			long elapsedTimeMillis = System.currentTimeMillis() - start;
			float elapsedTimeSec = elapsedTimeMillis/1000F;
			tiempos.add(elapsedTimeSec);

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

	}
        return tiempos;
}
}

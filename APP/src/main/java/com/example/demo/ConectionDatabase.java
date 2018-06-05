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
        String connectionString = "jdbc:mysql://localhost:3307/bdb_cisa?user=root&password=name&autoReconnect=true&useSSL=false";

        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        String selectSql = "";

        Statement statement2 = null;
        ResultSet result2 = null;
        String selectSql2 = "";

        //sisa ->myisam(default)
        //sica -> innodb(set inno)
        //cisa-> myisam + indices(para las consultas): poner indices
        //cica->inno + indices

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString);
            statement = connection.createStatement();


            selectSql = "SELECT ex.exon_id, end_phase, biotype\n" +
                    "FROM transcript tr, exon_transcript extr, exon ex\n" +
                    "WHERE tr.transcript_id = extr.transcript_id\n" +
                    "    AND extr.exon_id = ex.exon_id\n" +
                    "    AND biotype='miRNA';";
            List<String> nombreTablas = new ArrayList<>();
            long start = System.currentTimeMillis();
            Thread.sleep(2000);
            result = statement.executeQuery(selectSql);
            while (result.next()) {
                nombreTablas.add(result.getString(1));
            }


            while (result.next())  {}
            long elapsedTimeMillis = System.currentTimeMillis() - start;
            float elapsedTimeSec = elapsedTimeMillis/1000F;
            System.out.println(elapsedTimeSec);

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
}


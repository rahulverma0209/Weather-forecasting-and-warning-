package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static Connection getConnection(){
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/weather?autoReconnect=true&useSSL=false";
            con = DriverManager.getConnection(url, "root", "0209");
            System.out.println("connected to DB");
        }
        catch(Exception e)
        {
            AlertBox.error("Error while connecting to DB");
            System.out.println("Error while connecting to DB");
        }
        return con;
    }
}
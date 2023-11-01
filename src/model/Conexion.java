package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Joaquín
 */
public class Conexion {
    
        public static Connection conectar(){
        try{
            Connection cn = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net/sql7350929", "sql7350929", "cMvpvLEkXg");
            return cn;
        }catch(SQLException e){
            System.out.println("Error en la conexión local de mierda. " + e);
            
        }
        return (null);
    }
    
}

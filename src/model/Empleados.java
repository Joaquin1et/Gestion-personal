package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * @author Joaquín
 */

public class Empleados {
    
    private IntegerProperty Id;
    private StringProperty Nombre;
    private StringProperty Telefono;
    private StringProperty Centro;

    public Empleados(Integer Id, String Nombre, String Telefono, String Centro) {
        this.Id = new SimpleIntegerProperty(Id);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Telefono = new SimpleStringProperty(Telefono);
        this.Centro = new SimpleStringProperty(Centro);
    }

    public Integer getId() {
        return Id.get();
    }

    public void setId(Integer Id) {
        this.Id = new SimpleIntegerProperty(Id);
    }

    public String getNombre() {
        return Nombre.get();
    }

    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    public String getTelefono() {
        return Telefono.get();
    }

    public void setTelefono(String Telefono) {
        this.Telefono = new SimpleStringProperty(Telefono);
    }

    public String getCentro() {
        return Centro.get();
    }

    public void setCentro(String Centro) {
        this.Centro = new SimpleStringProperty(Centro);
    }
    
    
    public static void llenarInformacionEmpleados(Connection conectar, ObservableList<Empleados> lista){
        
        try {
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery("select id_empleado, nombre_empleado, telefono_empleado, centro_de_trab from empleados");
            
            while(resultado.next()){
                
                lista.add(
                        new Empleados(
                                resultado.getInt("id_empleado"),
                                resultado.getString("nombre_empleado"),
                                resultado.getString("telefono_empleado"),
                                resultado.getString("centro_de_trab")
                                
                        
                        )
                );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      
}

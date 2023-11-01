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
public class Pedidos {
    
    private IntegerProperty Id;
    private StringProperty Fecha;
    private StringProperty Nombre;
    private StringProperty Pedido;
    
    public Pedidos(Integer Id, String Fecha, String Nombre, String Pedido){
        
        this.Id = new SimpleIntegerProperty(Id);
        this.Fecha = new SimpleStringProperty(Fecha);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Pedido = new SimpleStringProperty(Pedido);
       
    }

    public Integer getId() {
        return Id.get();
    }

    public void setId(Integer Id) {
        this.Id = new SimpleIntegerProperty(Id);
    }

    public String getFecha() {
        return Fecha.get();
    }

    public void setFecha(String Fecha) {
        this.Fecha = new SimpleStringProperty(Fecha);
    }

    public String getNombre() {
        return Nombre.get();
    }

    public void setNombre(String Nombre) {
        this.Nombre = new SimpleStringProperty(Nombre);
    }

    public String getPedido() {
        return Pedido.get();
    }

    public void setPedido(String Pedido) {
        this.Pedido = new SimpleStringProperty(Pedido);
    }
    
    public static void llenarInformacionPedidos(Connection conectar, ObservableList<Pedidos> lista){
        
        try {
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery("select id, fecha, proveedor, pedido from pedidos_provee");
            
            while(resultado.next()){
                
                lista.add(
                        new Pedidos(
                                resultado.getInt("id"),
                                resultado.getString("fecha"),
                                resultado.getString("proveedor"),
                                resultado.getString("pedido")
                                
                                
                        
                        )
                );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
  
}

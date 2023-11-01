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
public class Productos {
    
    private IntegerProperty Id;
    private StringProperty Nombre;
    private StringProperty Articulo;
    private StringProperty Precio;
    
    public Productos(Integer Id, String Nombre, String Articulo, String Precio){
        
        this.Id = new SimpleIntegerProperty(Id);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Articulo = new SimpleStringProperty(Articulo);
        this.Precio = new SimpleStringProperty(Precio);
       
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

    public String getArticulo() {
        return Articulo.get();
    }

    public void setArticulo(String Articulo) {
        this.Articulo = new SimpleStringProperty(Articulo);
    }

    public String getPrecio() {
        return Precio.get();
    }

    public void setPrecio(String Precio) {
        this.Precio = new SimpleStringProperty(Precio);
    }
    
    
    public static void llenarInformacionArticulos(Connection conectar, ObservableList<Productos> lista){
        
        try {
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery("select id, nombreRs, articulo, precio from precios");
            
            while(resultado.next()){
                
                lista.add(
                        new Productos(
                                resultado.getInt("id"),
                                resultado.getString("nombreRs"),
                                resultado.getString("articulo"),
                                resultado.getString("precio")
                                
                                
                        
                        )
                );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Productos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}

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
public class Usuario {
    
    private IntegerProperty Id;
    private StringProperty Nombre;
    private StringProperty Usuario;
    private StringProperty Permiso;
    private StringProperty Estatus;
    
    public Usuario(Integer Id, String Nombre, String Usuario, String Permiso, String Estatus){
        
        this.Id = new SimpleIntegerProperty(Id);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Usuario = new SimpleStringProperty(Usuario);
        this.Permiso = new SimpleStringProperty(Permiso);
        this.Estatus = new SimpleStringProperty(Estatus);
        
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

    public String getUsuario() {
        return Usuario.get();
    }

    public void setUsuario(String Usuario) {
        this.Usuario = new SimpleStringProperty(Usuario);
    }

    public String getPermiso() {
        return Permiso.get();
    }

    public void setPermiso(String Permiso) {
        this.Permiso = new SimpleStringProperty(Permiso);
    }

    public String getEstatus() {
        return Estatus.get();
    }

    public void setEstatus(String Estatus) {
        this.Estatus = new SimpleStringProperty(Estatus);
    }
    
    public static void llenarInformacionUsuarios(Connection conectar, ObservableList<Usuario> lista){
        
        try {
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery("select id_usuario, nombre_usuario, username, tipo_nivel, estatus from usuarios");
            
            while(resultado.next()){
                
                lista.add(
                        new Usuario(
                                resultado.getInt("id_usuario"),
                                resultado.getString("nombre_usuario"),
                                resultado.getString("username"),
                                resultado.getString("tipo_nivel"),
                                resultado.getString("estatus")
                                
                        
                        )
                );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}

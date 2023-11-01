/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Sonido;

/**
 *
 * @author joaqu
 */
public class LimpiezaElMolarController implements Initializable {
    
    @FXML
    private PasswordField txt_passwordLogin;
    @FXML
    private Button btn_login;
    @FXML
    private TextField txt_usuarioLogin;
    
    public static String user = "";
    String pass = "";
    MediaPlayer mediaplayer;
    
    Sonido errorLogin = new Sonido();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void eventActionEntrar(ActionEvent event) {
        
        user = txt_usuarioLogin.getText().trim();
        pass  = txt_passwordLogin.getText().trim();
        
        if(!user.equals("") || !pass.equals("")){
            
            try{
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "select tipo_nivel, estatus from usuarios where username = '" + user
                            + "' and password = '" + pass + "'");
                
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    
                    String tipo_nivel = rs.getString("tipo_nivel");
                    String estatus = rs.getString("estatus");
                    
                    if(tipo_nivel.equalsIgnoreCase("Administrador") && estatus.equalsIgnoreCase("Activo")){
                      // dispose();
                       
                       //ABRIR LA VENTANA PRINCIPAL PARA EL ADMINISTRADOR
                      
                        try {
                            
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Principal.fxml"));
                           
                            Parent root = loader.load();
                            PrincipalController controlador = loader.getController();
                            
                            Scene scene = new Scene(root);
                            Stage stage =new Stage();
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Administrador - Sesión de "+ user);
                            stage.setResizable(false);
                            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
                            
                            stage.show();
                            
                            stage =(Stage)this.btn_login.getScene().getWindow();
                            stage.close();
                            
                            
                        }catch (IOException ex) {
                            Logger.getLogger(LimpiezaElMolarController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }else if(tipo_nivel.equalsIgnoreCase("Autorizado") && estatus.equalsIgnoreCase("Activo")){
                       
                        try {
                            
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PrincipalAutorizado.fxml"));
                           
                            Parent root = loader.load();
                            PrincipalAutorizadoController controlador = loader.getController();
                            
                            Scene scene = new Scene(root);
                            Stage stage =new Stage();
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Autorizado - Sesión de "+ user);
                            stage.setResizable(false);
                            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
                            
                            stage.show();
                            
                            stage =(Stage)this.btn_login.getScene().getWindow();
                            stage.close();
                            
                            
                        }catch (IOException ex) {
                            Logger.getLogger(LimpiezaElMolarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }    
                }else{
                    
                    errorLogin.SonidoErrorInicio();
                   
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("Datos de acceso incorrectos.");
                        alert.showAndWait();
                   txt_usuarioLogin.setText("");
                   txt_passwordLogin.setText("");
                
                }
                
            }catch(SQLException e){
                System.err.println("Error en el boton Acceder." + e);
                JOptionPane.showMessageDialog(null, "¡¡Error al iniciar la sesión!! Contacte con el administrador. ");
            }
            
        }else{
        
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Información");
                        alert.setContentText("Debe de rellenar todos los campos.");
                        alert.showAndWait();
        }
        
    }

    @FXML
    private void evenKey(KeyEvent event) {
    }
    
}

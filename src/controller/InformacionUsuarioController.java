package controller;

import static controller.LimpiezaElMolarController.user;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class InformacionUsuarioController implements Initializable {
    
    String update_user = "";
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_registradoUsuario;
    @FXML
    private Button btn_elinimarUsuario;
    @FXML
    private Button btn_actualizarUsuario;
    @FXML
    private Button btn_restaurarPassword;
    @FXML
    private TextField txt_estatus;
    @FXML
    private TextField txt_permisos;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update_user = UsuariosRegistradosController.update_user;
        
        //MOSTRAR LA INFORMACIÓN DEL USUARIO------------
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select * from usuarios where nombre_usuario = '" + update_user + "'");
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);

            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_nombre.setText(rs.getString("nombre_usuario"));
                txt_email.setText(rs.getString("email"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_usuario.setText(rs.getString("username"));
                txt_permisos.setText(rs.getString("tipo_nivel"));
                txt_estatus.setText(rs.getString("estatus"));
                txt_registradoUsuario.setText(rs.getString("registrado_por"));
                
            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar empleado." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
    }    

    @FXML
    private void EliminarUsuario(ActionEvent event) {
        
        int validacion = 0;
        String email = "";
        
        try {

            Connection cn = Conexion.conectar();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Seguro que quiere eliminarlo?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() != ButtonType.OK) {

                cn.close();

            } else {
                
                PreparedStatement pst = cn.prepareStatement(
                    "delete from usuarios where nombre_usuario = ?");

            pst.setString(1, txt_nombre.getText().trim());
            pst.executeUpdate();
            
            Limpiar();

                        txt_nombre.setStyle("-fx-background-color: #6DF80C ;");
                        txt_email.setStyle("-fx-background-color: #6DF80C ;");
                        txt_telefono.setStyle("-fx-background-color: #6DF80C ;");
                        txt_usuario.setStyle("-fx-background-color: #6DF80C ;");
                        txt_permisos.setStyle("-fx-background-color: #6DF80C ;");
                        txt_estatus.setStyle("-fx-background-color: #6DF80C ;");
                        txt_registradoUsuario.setStyle("-fx-background-color: #6DF80C ;");

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El usuario ha sido borrado correctamente.");
            alert1.showAndWait();

            }

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El usuario no ha sido eliminado.");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void ActualizarUsuario(ActionEvent event) {
        
        try {

            int validacion = 0;
            String nombre, email, telefono, usuario, permisos, estatus;

            nombre = txt_nombre.getText().trim();
            email = txt_email.getText().trim();
            telefono = txt_telefono.getText().trim();
            usuario = txt_usuario.getText().trim();
            permisos = txt_permisos.getText().trim();
            estatus = txt_estatus.getText().trim();
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "update usuarios set nombre_usuario=?, email=?, telefono=?,username=?, tipo_nivel=?, estatus=? "
                    + "where nombre_usuario = '" + update_user + "'");

            pst2.setString(1, nombre);
            pst2.setString(2, email);
            pst2.setString(3, telefono);
            pst2.setString(4, usuario);
            pst2.setString(5, permisos);
            pst2.setString(6, estatus);
            
            

            pst2.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Modificación correcta.");
            alert.showAndWait();

            //JOptionPane.showMessageDialog(null, "Modificación correcta. ");
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al actualizar." + e);
        }
        
    }

    @FXML
    private void RestaurarPassword(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CambioPassword.fxml"));

            Parent root = loader.load();
            CambioPasswordController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cambio de password - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
            
            

        } catch (IOException ex) {
            Logger.getLogger(CambioPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void Limpiar() {
        txt_nombre.setText("");
        txt_email.setText("");
        txt_telefono.setText("");
        txt_usuario.setText("");
        txt_permisos.setText("");
        txt_estatus.setText("");
        txt_registradoUsuario.setText("");

    }
    
}

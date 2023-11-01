package controller;

import static controller.LimpiezaElMolarController.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

    @FXML
    private Button btn_registroEmpleado;
    @FXML
    private Button btn_empleRegistrados;
    @FXML
    private Button btn_registroUsuario;
    @FXML
    private Button btn_mdPrincipal;
    @FXML
    private ImageView png_charco;
    @FXML
    private Button btn_UsuariosRegistrados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RegistroEmpleado(ActionEvent event) {
        
        user = LimpiezaElMolarController.user;

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistroEmpleado.fxml"));

            Parent root = loader.load();
            RegistroEmpleadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar nuevo emplesdo - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            
            Stage myStage = (Stage) this.btn_registroEmpleado.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void EmpleadosRegistrados(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmpleadosRegistrados.fxml"));

            Parent root = loader.load();
            EmpleadosRegistradosController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Empleados Registrados - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            
            Stage myStage = (Stage) this.btn_empleRegistrados.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosRegistradosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void RegistroUsuario(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistroUsuario.fxml"));

            Parent root = loader.load();
            RegistroUsuarioController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registro de usuarios - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosRegistradosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void MdPrincipal(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mdPrincipal.fxml"));

            Parent root = loader.load();
            MdPrincipalController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Almacén - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(MdPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void UsuariosRegistrados(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UsuariosRegistrados.fxml"));

            Parent root = loader.load();
            UsuariosRegistradosController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Usuarios Registrados - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosRegistradosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

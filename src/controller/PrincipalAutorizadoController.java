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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Sonido;

/**
 * @author Joaquín
 */
public class PrincipalAutorizadoController implements Initializable {

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
    
    MediaPlayer mediaplayer;
    Sonido accesoNoAutorizado = new Sonido();

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RegistroEmpleadoAutorizado.fxml"));

            Parent root = loader.load();
            RegistroEmpleadoAutorizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar nuevo emplesdo - Sesión de " + user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

            stage.setOnCloseRequest(e -> controlador.closeWindows());

            Stage myStage = (Stage) this.btn_empleRegistrados.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoAutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void EmpleadosRegistrados(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmpleadosRegistradosAutorizado.fxml"));

            Parent root = loader.load();
            EmpleadosRegistradosAutorizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Empleados Registrados  - Sesión de " + user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

            stage.setOnCloseRequest(e -> controlador.closeWindows());

            Stage myStage = (Stage) this.btn_registroEmpleado.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(EmpleadosRegistradosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void RegistroUsuario(ActionEvent event) {
        
        accesoNoAutorizado.SonidoNoAutorizadoEntrar();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText(" Acceso denegado.\n Sólo para usuarios autorizados.\n Consulte con el Administrador.");
        alert.showAndWait();

    }

    @FXML
    private void MdPrincipal(ActionEvent event) {
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mdPrincipalAutorizado.fxml"));

            Parent root = loader.load();
            MdPrincipalAutorizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Almacén - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            
            Stage myStage = (Stage) this.btn_registroEmpleado.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MdPrincipalAutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void UsuariosRegistrados(ActionEvent event) {
        
        accesoNoAutorizado.SonidoNoAutorizadoEntrar();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText(" Acceso denegado.\n Sólo para usuarios autorizados.\n Consulte con el Administrador.");
        alert.showAndWait();

    }

}

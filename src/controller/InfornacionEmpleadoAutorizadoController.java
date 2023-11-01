package controller;

import static controller.LimpiezaElMolarController.user;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Sonido;

/**
 * @author Joaquín
 */
public class InfornacionEmpleadoAutorizadoController implements Initializable {

    String update_user = "";
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_vaca1;
    @FXML
    private TextArea txtArea_vaca1;
    @FXML
    private TextField txt_vaca2;
    @FXML
    private TextArea txtArea_vaca2;
    @FXML
    private TextArea txt_centro;
    @FXML
    private TextField txt_totalMoscosos;
    @FXML
    private TextArea txt_moscososCogi;
    @FXML
    private TextArea txt_extras;
    @FXML
    private TextArea txt_sustituciones;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_eliminarEmpleado;
    @FXML
    private Button btn_actualizarEmpleado;
    
    MediaPlayer mediaplayer;
    Sonido noAutorizadoEliminarEmpleado = new Sonido();
    Sonido noAutorizadoActualizarEmpleado = new Sonido();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = EmpleadosRegistradosAutorizadoController.update_user;

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select * from empleados where nombre_empleado = '" + update_user + "'");
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);

            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_nombre.setText(rs.getString("nombre_empleado"));
                txt_email.setText(rs.getString("email_empleado"));
                txt_telefono.setText(rs.getString("telefono_empleado"));
                txt_centro.setText(rs.getString("centro_de_trab"));
                txt_observaciones.setText(rs.getString("observaciones_empleado"));
                txt_totalMoscosos.setText(rs.getString("moscosos_total"));
                txt_moscososCogi.setText(rs.getString("moscosos_cogidos"));
                txt_extras.setText(rs.getString("horas_extras"));
                txt_sustituciones.setText(rs.getString("sustituciones"));
                txtArea_vaca1.setText(rs.getString("vacaciones1"));
                txt_vaca1.setText(rs.getString("vacaciones"));
                txtArea_vaca2.setText(rs.getString("mes1"));
                txt_vaca2.setText(rs.getString("mes"));
                

            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar empleado." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
        
    }    

    @FXML
    private void EliminarEmpleado(ActionEvent event) {
        
        noAutorizadoEliminarEmpleado.SonidoNoAutorizadoEliminarEmpleado();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("No está autorizado para eliminar empleados.\n Consulte con el administrador..");
            alert.showAndWait();
        
    }

    @FXML
    private void ActualizarEmpleado(ActionEvent event) {
        
        noAutorizadoActualizarEmpleado.SonidoNoAutorizadoActualizarEmpleado();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("No está autorizado para actualizar empleados.\n Consulte con el administrador");
            alert.showAndWait();
        
    }
    
    public void closeWindows(){
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Empleados_Registrados_Autorizados.fxml"));

            Parent root = loader.load();
            EmpleadosRegistradosAutorizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registrar nuevo emplesdo - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
            
            stage.show();
           
        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

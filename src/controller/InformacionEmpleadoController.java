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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author joaqu
 */
public class InformacionEmpleadoController implements Initializable {

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
    @FXML
    private TextField txt_centroInformePdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = EmpleadosRegistradosController.update_user;

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
                txt_centroInformePdf.setText(rs.getString("centroinforme"));

            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar empleado." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
        
    }    

    @FXML
    private void EliminarEmpleado(ActionEvent event) {
        
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

            }

            PreparedStatement pst = cn.prepareStatement(
                    "delete from empleados where nombre_empleado = ?");

            pst.setString(1, txt_nombre.getText().trim());
            pst.executeUpdate();

            txt_nombre.setText("");
            txt_email.setText("");
            txt_telefono.setText("");
            txt_centro.setText("");
            txt_observaciones.setText("");
            txt_totalMoscosos.setText("");
            txt_moscososCogi.setText("");
            txt_extras.setText("");
            txt_sustituciones.setText("");
            txtArea_vaca1.setText("");
            txt_vaca1.setText("");
            txtArea_vaca2.setText("");
            txt_vaca2.setText("");
            txt_centroInformePdf.setText("");
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El empleado ha sido borrado correctamente.");
            alert1.showAndWait();
          
            //JOptionPane.showMessageDialog(null, "El empleado ha sido borrado correctamente ");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El empeado no ha sido eliminado.");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void ActualizarEmpleado(ActionEvent event) {
        
        try {

            int validacion = 0;
            String nombre, mail, telefono, centro, tmoscosos, cmoscosos, observaciones, horas, sustituciones, vacaciones, vacaciones1, mes, mes1, informepdf;

            nombre = txt_nombre.getText().trim();
            mail = txt_email.getText().trim();
            telefono = txt_telefono.getText().trim();
            centro = txt_centro.getText().trim();
            tmoscosos = txt_totalMoscosos.getText().trim();
            cmoscosos = txt_moscososCogi.getText().trim();
            horas = txt_extras.getText().trim();
            sustituciones = txt_sustituciones.getText().trim();
            observaciones = txt_observaciones.getText().trim();
            vacaciones = txt_vaca1.getText().trim();
            vacaciones1 = txtArea_vaca1.getText().trim();
            mes = txt_vaca2.getText().trim();
            mes1 = txtArea_vaca2.getText().trim();
            informepdf = txt_centroInformePdf.getText().trim();

            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "update empleados set nombre_empleado=?, email_empleado=?, telefono_empleado=?,centro_de_trab=?, observaciones_empleado=?, moscosos_total=?, moscosos_cogidos=?,horas_extras=?,sustituciones=?, vacaciones=?, vacaciones1=?, mes=?, mes1=?, centroinforme=? "
                    + "where nombre_empleado = '" + update_user + "'");

            pst2.setString(1, nombre);
            pst2.setString(2, mail);
            pst2.setString(3, telefono);
            pst2.setString(4, centro);
            pst2.setString(5, observaciones);
            pst2.setString(6, tmoscosos);
            pst2.setString(7, cmoscosos);
            pst2.setString(8, horas);
            pst2.setString(9, sustituciones);
            pst2.setString(10, vacaciones);
            pst2.setString(11, vacaciones1);
            pst2.setString(12, mes);
            pst2.setString(13, mes1);
            pst2.setString(14, informepdf);

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
    
    public void closeWindows(){
        
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
           
        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

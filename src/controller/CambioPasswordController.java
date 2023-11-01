package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class CambioPasswordController implements Initializable {
    
    String update_user = "";

    @FXML
    private PasswordField txt_cambiarPassword;
    @FXML
    private PasswordField txt_confirmarPassword;
    @FXML
    private Button btn_restaurarPassword;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RestaurarPasword(ActionEvent event) {
        
        update_user = UsuariosRegistradosController.update_user;

        String password, confirmacion_password;

        password = txt_cambiarPassword.getText().trim();
        confirmacion_password = txt_confirmarPassword.getText().trim();

        if (!password.equals("") && !confirmacion_password.equals("")) {

            if (password.equals(confirmacion_password)) {

                try {

                    Connection cn = Conexion.conectar();
                    PreparedStatement pst = cn.prepareStatement(
                            "update usuarios set password=? where username= '" + update_user + "'");

                    pst.setString(1, password);
                    pst.executeUpdate();
                    cn.close();

                    txt_cambiarPassword.setStyle("-fx-background-color: #6DF80C ;");
                    txt_confirmarPassword.setStyle("-fx-background-color: #6DF80C ;");

                    //JOptionPane.showMessageDialog(null, "Restauracion correcta. ");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Información");
                    alert1.setContentText("Restauración correcta.");
                    alert1.showAndWait();
                   
                    
                            
                    //this.dispose();

                } catch (SQLException e) {
                    System.out.println("Error al restaurar el password " + e);
                }

            } else {
                txt_confirmarPassword.setStyle("-fx-background-color: #F98854 ;");
                //JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. ");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Información");
                    alert1.setContentText("Las contraseñas no coinciden.");
                    alert1.showAndWait();
                
            }

        } else {
            txt_cambiarPassword.setStyle("-fx-background-color: #F98854 ;");
            txt_confirmarPassword.setStyle("-fx-background-color: #F98854 ;");
            //JOptionPane.showMessageDialog(null, "No se admiten contraseñas vacias. ");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Información");
                    alert1.setContentText("No se admiten contraseñas vacias.");
                    alert1.showAndWait();
            
        }
        
    }
    
}

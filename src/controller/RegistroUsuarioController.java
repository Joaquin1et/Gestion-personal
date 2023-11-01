package controller;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import static controller.LimpiezaElMolarController.user;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class RegistroUsuarioController implements Initializable {

    @FXML
    private ComboBox<String> cmb_permisos;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private Button btn_registro;
    @FXML
    private TextField txt_userRegistro;
    @FXML
    private PasswordField txt_passwordRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ArrayList<String> permisos = new ArrayList<>();
        Collections.addAll(permisos, new String[]{"Administrador", "Autorizado"});

        cmb_permisos.getItems().addAll(permisos);
        
        
    }    

    @FXML
    private void Registro(ActionEvent event) {
        
        user = LimpiezaElMolarController.user;

        int permisos_cmb, validacion = 0;
        String nombre, mail, telefono, username, pass, permisos_string = "";

        mail = txt_email.getText().trim();
        username = txt_userRegistro.getText().trim();
        pass = txt_passwordRegistro.getText().trim();
        nombre = txt_nombre.getText().trim();
        telefono = txt_telefono.getText().trim();
        permisos_cmb = cmb_permisos.getSelectionModel().getSelectedIndex() + 1;

        if (mail.equals("")) {
            txt_email.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (username.equals("")) {
            txt_userRegistro.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (pass.equals("")) {
            txt_passwordRegistro.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (nombre.equals("")) {
            txt_nombre.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (telefono.equals("")) {
            txt_telefono.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }

        if (permisos_cmb == 1) {
            permisos_string = "Administrador";
        } else if (permisos_cmb == 2) {
            permisos_string = "Autorizado";
        }

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select username from usuarios where username = '" + username + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_userRegistro.setStyle("-fx-background-color: #F64104 ;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Nombre de usuario no disponible.");
                alert.showAndWait();
                //JOptionPane.showMessageDialog(null, "Nombre de usuario no disponible. ");
                cn.close();
            } else {

                cn.close();

                if (validacion == 0) {
                    try {

                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into usuarios values (?,?,?,?,?,?,?,?,?)");
                        pst2.setInt(1, 0);
                        pst2.setString(2, nombre);
                        pst2.setString(3, mail);
                        pst2.setString(4, telefono);
                        pst2.setString(5, username);
                        pst2.setString(6, pass);
                        pst2.setString(7, permisos_string);
                        pst2.setString(8, "Activo");
                        pst2.setString(9, user);

                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        txt_email.setStyle("-fx-background-color: #6DF80C ;");
                        txt_userRegistro.setStyle("-fx-background-color: #6DF80C ;");
                        txt_passwordRegistro.setStyle("-fx-background-color: #6DF80C ;");
                        txt_nombre.setStyle("-fx-background-color: #6DF80C ;");
                        txt_telefono.setStyle("-fx-background-color: #6DF80C ;");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Usuario registrado correctamente.");
                        alert.showAndWait();

                        //JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                        dispose();

                    } catch (SQLException e) {
                       
                        System.err.println("Error al registrar usuario. ");
                        JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar!! Contacte con el administrador.");
                    }
                } else {
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();
                    //JOptionPane.showMessageDialog(null, "Debes de rellenar todos los campos.");
                }

            }

        } catch (Exception e) {
            System.err.println("Error al validar el nombre de usuario.");
            JOptionPane.showMessageDialog(null, "¡¡Error al comparar usuario!! Contacte con el administrador.");

        }
        
    }
    
    public void Limpiar() {
        txt_email.setText("");
        txt_nombre.setText("");
        txt_passwordRegistro.setText("");
        txt_telefono.setText("");
        txt_userRegistro.setText("");
        cmb_permisos.getSelectionModel().getSelectedIndex();

    }
    
}

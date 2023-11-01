package controller;

import static controller.LimpiezaElMolarController.user;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class RegistroEmpleadoAutorizadoController implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private ComboBox<String> cmb_vaca1;
    @FXML
    private TextArea txt_vacaciones1;
    @FXML
    private ComboBox<String> cmb_vaca2;
    @FXML
    private TextArea txt_vacaciones2;
    @FXML
    private ComboBox<String> cmb_centros;
    @FXML
    private ComboBox<String> cmb_centrosInformes;
    @FXML
    private TextField txt_totalMoscosos;
    @FXML
    private TextArea txt_moscososCogidos;
    @FXML
    private TextArea txt_horasExtras;
    @FXML
    private TextArea txt_sustituciones;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_registrarEmpleOk;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ArrayList<String> listaVaca1 = new ArrayList<>();
        Collections.addAll(listaVaca1, new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"});

        cmb_vaca1.getItems().addAll(listaVaca1);

        ArrayList<String> listaVaca2 = new ArrayList<>();
        Collections.addAll(listaVaca2, new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"});

        cmb_vaca2.getItems().addAll(listaVaca2);

        ArrayList<String> listaCentros = new ArrayList<>();
        Collections.addAll(listaCentros, new String[]{"Ayuntamiento", "Turismo y Servicios Sociales", "Recaudación", "Sala Villa", "Sala Pintura", "Sala Vip y Campo de Fútbol",
            "Frontones", "Colegio Remolino", "Colegio Arco de La Sierra", "Antiguas Escuelas", "Multiusos", "C.E.P.A.", "Juventud", "Policía", "Protección Civil"});

        cmb_centros.getItems().addAll(listaCentros);
        
        ArrayList<String> listaCentrosInformes = new ArrayList<>();
        Collections.addAll(listaCentrosInformes, new String[]{"Colegios", "Personal"});

        cmb_centrosInformes.getItems().addAll(listaCentrosInformes);
        
    }    

    @FXML
    private void RegistrarEmpleadoOk(ActionEvent event) {
        
        int vacaciones1_cmb, vacaciones2_cmb, centros_cmb,centrosInforme_cmb, validacion = 0;
        String nombre, email, telefono, vacaciones, vacaciones1, observaciones, t_moscosos, c_moscosos, horas, sustituciones, centros_string = "", centrosInforme_string = "", vacaciones1_string = "", vacaciones2_string = "";

        email = txt_email.getText().trim();
        nombre = txt_nombre.getText().trim();
        telefono = txt_telefono.getText().trim();
        vacaciones = txt_vacaciones1.getText().trim();
        vacaciones1 = txt_vacaciones2.getText().trim();
        observaciones = txt_observaciones.getText().trim();
        t_moscosos = txt_totalMoscosos.getText().trim();
        c_moscosos = txt_moscososCogidos.getText().trim();
        horas = txt_horasExtras.getText().trim();
        sustituciones = txt_sustituciones.getText().trim();
        centros_cmb = cmb_centros.getSelectionModel().getSelectedIndex() + 1;
        centrosInforme_cmb = cmb_centrosInformes.getSelectionModel().getSelectedIndex() + 1;
        vacaciones1_cmb = cmb_vaca1.getSelectionModel().getSelectedIndex() + 1;
        vacaciones2_cmb = cmb_vaca2.getSelectionModel().getSelectedIndex() + 1;

        if (email.equals("")) {
            txt_email.setStyle("-fx-background-color: #F64104 ;");
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
        if (t_moscosos.equals("")) {
            txt_totalMoscosos.setStyle("-fx-background-color: #F64104 ;");;
            validacion++;
        }
        /*if(c_moscosos.equals("")){
            txt_moscososCogidos.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }*/

        if (centros_cmb == 1) {
            centros_string = "Ayuntamiento";
        } else if (centros_cmb == 2) {
            centros_string = "Turismo y servicios Sociales";
        } else if (centros_cmb == 3) {
            centros_string = "Recaudación";
        } else if (centros_cmb == 4) {
            centros_string = "Sala Villa";
        } else if (centros_cmb == 5) {
            centros_string = "Sala Pintura";
        } else if (centros_cmb == 6) {
            centros_string = "Sala Vip y Campo de Fútbol";
        } else if (centros_cmb == 7) {
            centros_string = "Frontones";
        } else if (centros_cmb == 8) {
            centros_string = "Colegio Remolino";
        } else if (centros_cmb == 9) {
            centros_string = "Colegio Arco de La Sierra";
        } else if (centros_cmb == 10) {
            centros_string = "Colegio Viejo";
        } else if (centros_cmb == 11) {
            centros_string = "Multiusos";
        } else if (centros_cmb == 12) {
            centros_string = "Cepa";
        } else if (centros_cmb == 13) {
            centros_string = "Juventud";
        } else if (centros_cmb == 14) {
            centros_string = "Policía y P.Civil";
        }
        
        //combo box de cmb_centrosInforme 
        
        if (centrosInforme_cmb == 1) {
            centrosInforme_string = "Colegios";
        } else if (centrosInforme_cmb == 2) {
            centrosInforme_string = "Personal";
        }
        
        //combo box de las vacaciones 1

        if (vacaciones1_cmb == 1) {
            vacaciones1_string = "ENERO";
        } else if (vacaciones1_cmb == 2) {
            vacaciones1_string = "FEBRERO";
        } else if (vacaciones1_cmb == 3) {
            vacaciones1_string = "MARZO";
        } else if (vacaciones1_cmb == 4) {
            vacaciones1_string = "ABRIL";
        } else if (vacaciones1_cmb == 5) {
            vacaciones1_string = "MAYO";
        } else if (vacaciones1_cmb == 6) {
            vacaciones1_string = "JUNIO";
        } else if (vacaciones1_cmb == 7) {
            vacaciones1_string = "JULIO";
        } else if (vacaciones1_cmb == 8) {
            vacaciones1_string = "AGOSTO";
        } else if (vacaciones1_cmb == 9) {
            vacaciones1_string = "SEPTIEMBRE";
        } else if (vacaciones1_cmb == 10) {
            vacaciones1_string = "OCTUBRE";
        } else if (vacaciones1_cmb == 11) {
            vacaciones1_string = "NOVIEMBRE";
        } else if (vacaciones1_cmb == 12) {
            vacaciones1_string = "DICIEMBRE";
        }

        // Combo Box de las Vacaciones 2
        if (vacaciones2_cmb == 1) {
            vacaciones2_string = "ENERO";
        } else if (vacaciones2_cmb == 2) {
            vacaciones2_string = "FEBRERO";
        } else if (vacaciones2_cmb == 3) {
            vacaciones2_string = "MARZO";
        } else if (vacaciones2_cmb == 4) {
            vacaciones2_string = "ABRIL";
        } else if (vacaciones2_cmb == 5) {
            vacaciones2_string = "MAYO";
        } else if (vacaciones2_cmb == 6) {
            vacaciones2_string = "JUNIO";
        } else if (vacaciones2_cmb == 7) {
            vacaciones2_string = "JULIO";
        } else if (vacaciones2_cmb == 8) {
            vacaciones2_string = "AGOSTO";
        } else if (vacaciones2_cmb == 9) {
            vacaciones2_string = "SEPTIEMBRE";
        } else if (vacaciones2_cmb == 10) {
            vacaciones2_string = "OCTUBRE";
        } else if (vacaciones2_cmb == 11) {
            vacaciones2_string = "NOVIEMBRE";
        } else if (vacaciones2_cmb == 12) {
            vacaciones2_string = "DICIEMBRE";
        }

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select nombre_empleado from empleados where nombre_empleado = '" + nombre + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_nombre.setStyle("-fx-background-color: #F0591E;");
                JOptionPane.showMessageDialog(null, "Empleado ya registrado");
                cn.close();
            } else {

                cn.close();

                if (validacion == 0) {
                    try {

                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into empleados values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        pst2.setInt(1, 0);
                        pst2.setString(2, nombre);
                        pst2.setString(3, email);
                        pst2.setString(4, telefono);
                        pst2.setString(5, centros_string);
                        pst2.setString(6, observaciones);
                        pst2.setString(7, t_moscosos);
                        pst2.setString(8, c_moscosos);
                        pst2.setString(9, horas);
                        pst2.setString(10, sustituciones);
                        pst2.setString(11, vacaciones1_string);
                        pst2.setString(12, vacaciones);
                        pst2.setString(13, vacaciones2_string);
                        pst2.setString(14, vacaciones1);
                        pst2.setString(15, centrosInforme_string);

                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        txt_email.setStyle("-fx-background-color: #55F604 ;");
                        txt_nombre.setStyle("-fx-background-color: #55F604 ;");
                        txt_telefono.setStyle("-fx-background-color: #55F604 ;");
                        txt_vacaciones1.setStyle("-fx-background-color: #55F604 ;");
                        txt_vacaciones2.setStyle("-fx-background-color: #55F604 ;");
                        txt_observaciones.setStyle("-fx-background-color: #55F604 ;");
                        txt_totalMoscosos.setStyle("-fx-background-color: #55F604 ;");
                        //txt_moscososCogidos.setStyle("-fx-background-color: #55F604 ;");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Registrado correctamente.");
                        alert.showAndWait();

                        //JOptionPane.showMessageDialog(null, "Registrado correctamente. ");
                        this.dispose();

                    } catch (SQLException e) {
                        System.err.println("Error al registrar empleado. " + e);
                        JOptionPane.showMessageDialog(null, "¡¡ERROR al registrar!! Contacte con el administrador. ");
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();

                    //JOptionPane.showMessageDialog(null, "Debes de rellenar todos los campos. ");
                }

            }

        } catch (SQLException e) {
            System.err.println("Error al validar el nombre de empleado" + e);
            JOptionPane.showMessageDialog(null, "¡¡Error al comparar empleado!! Contacte con el administrador. ");
        }
        
    }
    
    public void Limpiar() {

        txt_email.setText("");
        txt_nombre.setText("");
        txt_telefono.setText("");
        txt_totalMoscosos.setText("");
        txt_moscososCogidos.setText("");
        txt_observaciones.setText("");
        txt_vacaciones1.setText("");
        txt_vacaciones2.setText("");
        txt_horasExtras.setText("");
        txt_sustituciones.setText("");
        cmb_centros.getSelectionModel().getSelectedIndex();
        cmb_centrosInformes.getSelectionModel().getSelectedIndex();

    }

    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void closeWindows(){
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PrincipalAutorizado.fxml"));

            Parent root = loader.load();
            PrincipalAutorizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Panel Principal - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
            
            stage.show();
            
           Stage myStage = (Stage) this.btn_registrarEmpleOk.getScene().getWindow();
            
            myStage.close(); 
    
        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoAutorizadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

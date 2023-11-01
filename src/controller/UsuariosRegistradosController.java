package controller;

import static controller.LimpiezaElMolarController.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Conexion;
import model.Usuario;

/**
 * @author Joaquín
 */
public class UsuariosRegistradosController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private TableView<Usuario> tbl_usuarios;
    @FXML
    private TableColumn<Usuario, Integer> colum_id;
    @FXML
    private TableColumn<Usuario, String> colum_nombre;
    @FXML
    private TableColumn<Usuario, String> colum_usuario;
    @FXML
    private TableColumn<Usuario, String> colum_permiso;
    @FXML
    private TableColumn<Usuario, String> colum_estatus;
    
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaUsuarios = FXCollections.observableArrayList();

        Usuario.llenarInformacionUsuarios(Conexion.conectar(), listaUsuarios);

        colum_id.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("Id"));
        colum_nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nombre"));
        colum_usuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Usuario"));
        colum_permiso.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Permiso"));
        colum_estatus.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Estatus"));

        tbl_usuarios.setItems(listaUsuarios);

        gestionarEventos();
        
    }    

    @FXML
    private void FichaUsuario(MouseEvent event) {
        
        user = LimpiezaElMolarController.user;
         
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InformacionUsuario.fxml"));

            Parent root = loader.load();
            InformacionUsuarioController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del usuario - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(InformacionUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void gestionarEventos() {
        tbl_usuarios.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario valorAnterior, Usuario valorSeleccionado) {

                update_user = valorSeleccionado.getNombre();

                System.out.println("Nombre del Usuario seleccionado " + update_user);

            }

        });
    }
    
}

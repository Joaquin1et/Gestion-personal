package controller;

import static controller.LimpiezaElMolarController.user;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Conexion;
import model.Pedidos;
import model.Productos;

/**
 * @author Joaquín
 */
public class MdPrincipalController implements Initializable {

    @FXML
    private Label Label_usuario;
    @FXML
    private Label Label_fecha;
    @FXML
    private Label Label_hora;
    @FXML
    private Tab tab_almacen;
    @FXML
    private TextField txt_fecha;
    @FXML
    private TextField txt_proveedor;
    @FXML
    private TextArea txt_pedido;
    @FXML
    private Button btn_hacerPedido;
    @FXML
    private TableView<Pedidos> tbl_pedidos;
    @FXML
    private TableColumn<Pedidos, Integer> colum_id;
    @FXML
    private TableColumn<Pedidos, String> colum_fecha;
    @FXML
    private TableColumn<Pedidos, String> colum_nombre;
    @FXML
    private TableColumn<Pedidos, String> colum_pedido;
    
    private ObservableList<Pedidos> listaPedidos;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Productos> filtrarProducto;
    
    public static String Pedido ;
    
    public static String articulo;
    
    @FXML
    private Tab tab_precios;
    @FXML
    private Button btn_guardarProducto;
    @FXML
    private TextField txt_nombreProvee;
    @FXML
    private TextField txt_precioProducto;
    @FXML
    private TextArea txt_articulo;
    @FXML
    private TableView<Productos> tbl_listaPrecios;
    @FXML
    private TableColumn<Productos, Integer> colum_idPrecio;
    @FXML
    private TableColumn<Productos, String> colum_nombreProvee;
    @FXML
    private TableColumn<Productos, String> colum_articulo;
    @FXML
    private TableColumn<Productos, String> colum_precio;
    @FXML
    private TextField txt_buscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        user = LimpiezaElMolarController.user;

        Label_usuario.setText(user);

        //INSERTAR FECHA Y HORA
        Date fecha = new Date();
        DateFormat formatofecha = new SimpleDateFormat("dd/MM/yyyy");
        Label_fecha.setText(formatofecha.format(fecha));

        DateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
        Label_hora.setText(formatohora.format(fecha));

        txt_fecha.setText(formatofecha.format(fecha));
        //txt_fecha.setText(dia+" / "+(mes+1)+" / "+aa);

        //RELLENAR TABLEVIEW--------------------
        listaPedidos = FXCollections.observableArrayList();

        Pedidos.llenarInformacionPedidos(Conexion.conectar(), listaPedidos);

        colum_id.setCellValueFactory(new PropertyValueFactory<Pedidos, Integer>("Id"));
        colum_fecha.setCellValueFactory(new PropertyValueFactory<Pedidos, String>("Fecha"));
        colum_nombre.setCellValueFactory(new PropertyValueFactory<Pedidos, String>("Nombre"));
        colum_pedido.setCellValueFactory(new PropertyValueFactory<Pedidos, String>("Pedido"));

        tbl_pedidos.setItems(listaPedidos);

        gestionarEventos();
        
        
        //RELLENAR TABLEVIEW PRODUCTOS--------------------
        
        listaProductos = FXCollections.observableArrayList();
        filtrarProducto = FXCollections.observableArrayList();

        Productos.llenarInformacionArticulos(Conexion.conectar(), listaProductos);

        colum_idPrecio.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("Id"));
        colum_nombreProvee.setCellValueFactory(new PropertyValueFactory<Productos, String>("Nombre"));
        colum_articulo.setCellValueFactory(new PropertyValueFactory<Productos, String>("Articulo"));
        colum_precio.setCellValueFactory(new PropertyValueFactory<Productos, String>("Precio"));

        tbl_listaPrecios.setItems(listaProductos);

        gestionarEventosProductos();
        
    }    

    @FXML
    private void HacerPedido(ActionEvent event) {
        
        int validacion = 0;
        String fecha, proveedor, pedido;

        fecha = txt_fecha.getText().trim();
        proveedor = txt_proveedor.getText().trim();
        pedido = txt_pedido.getText().trim();

        if (fecha.equals("")) {
            txt_fecha.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (proveedor.equals("")) {
            txt_proveedor.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (pedido.equals("")) {
            txt_pedido.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select nombre from proveedores where nombre = '" + pedido + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_proveedor.setStyle("-fx-background-color: #F64104 ;");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Pedido ya registrado.");
                alert.showAndWait();

                cn.close();
            } else {

                cn.close();

                if (validacion == 0) {
                    try {

                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into pedidos_provee values (?,?,?,?)");
                        pst2.setInt(1, 0);
                        pst2.setString(2, fecha);
                        pst2.setString(3, proveedor);
                        pst2.setString(4, pedido);

                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        txt_fecha.setStyle("-fx-background-color: #6DF80C ;");
                        txt_proveedor.setStyle("-fx-background-color: #6DF80C ;");
                        txt_pedido.setStyle("-fx-background-color: #6DF80C ;");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Guardado correctamente.");
                        alert.showAndWait();

                        //this.dispose();
                        txt_fecha.setStyle("-fx-background-color: #FFFFFF ;");
                        txt_proveedor.setStyle("-fx-background-color: #FFFFFF ;");
                        txt_proveedor.setStyle("-fx-background-color: #FFFFFF ;");

                    } catch (SQLException e) {
                        System.err.println("Error al guardar producto. " + e);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("¡¡ERROR al guardar!! Contacte con el administrador.");
                        alert.showAndWait();

                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();

                }

            }

        } catch (SQLException e) {
            System.err.println("Error al validar el nombre de empleado" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("¡¡Error al validar el pedido!! Contacte con el administrador.");
            alert.showAndWait();
        }

        Limpiar();
        
    }

    @FXML
    private void FichaPedidos(MouseEvent event) {
        
        user = LimpiezaElMolarController.user;
 
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaPedido.fxml"));

            Parent root = loader.load();
            FichaPedidoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar o Eliminar Pedido - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FichaPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void GuardarProducto(ActionEvent event) {
        
        int validacion = 0;
        String nombreProvee, articulo, precio;

        nombreProvee = txt_nombreProvee.getText().trim();
        articulo = txt_articulo.getText().trim();
        precio = txt_precioProducto.getText().trim();

        if (nombreProvee.equals("")) {
            txt_nombreProvee.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (articulo.equals("")) {
            txt_articulo.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (precio.equals("")) {
            txt_precioProducto.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select articulo from precios where articulo = '" + articulo + "'");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txt_articulo.setStyle("-fx-background-color: #F64104 ;");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Producto ya registrado.");
                alert.showAndWait();

                cn.close();
            } else {

                cn.close();

                if (validacion == 0) {
                    try {

                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into precios values (?,?,?,?)");
                        pst2.setInt(1, 0);
                        pst2.setString(2, nombreProvee);
                        pst2.setString(3, articulo);
                        pst2.setString(4, precio);

                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        txt_nombreProvee.setStyle("-fx-background-color: #6DF80C ;");
                        txt_articulo.setStyle("-fx-background-color: #6DF80C ;");
                        txt_precioProducto.setStyle("-fx-background-color: #6DF80C ;");

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Guardado correctamente.");
                        alert.showAndWait();

                        //this.dispose();
                        txt_nombreProvee.setStyle("-fx-background-color: #FFFFFF ;");
                        txt_articulo.setStyle("-fx-background-color: #FFFFFF ;");
                        txt_precioProducto.setStyle("-fx-background-color: #FFFFFF ;");

                    } catch (SQLException e) {
                        System.err.println("Error al guardar producto. " + e);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("¡¡ERROR al guardar!! Contacte con el administrador.");
                        alert.showAndWait();

                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();

                }

            }

        } catch (SQLException e) {
            System.err.println("Error al validar el nombre del producto" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("¡¡Error al validar el artículo!! Contacte con el administrador.");
            alert.showAndWait();
        }

        Limpiar1();
        
    }

    @FXML
    private void FichaProductos(MouseEvent event) {
        
        user = LimpiezaElMolarController.user;
 
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaProducto.fxml"));

            Parent root = loader.load();
            FichaProductoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modificar o Eliminar Producto - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FichaPedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void FiltrarProducto(KeyEvent event) {
        
        String filtroBuscar = this.txt_buscar.getText();
       
       
       
       if(filtroBuscar.isEmpty()){
           
           this.tbl_listaPrecios.setItems(listaProductos);
           
       }else{
           this.filtrarProducto.clear();
           
           
           for(Productos p:this.listaProductos){
               
               if(p.getArticulo().toLowerCase().contains(filtroBuscar.toLowerCase())){
                   
                   this.filtrarProducto.add(p);
                   
               }
               
           }
           
           this.tbl_listaPrecios.setItems(filtrarProducto);
       }
        
    }
    
    
    public void gestionarEventos() {
        tbl_pedidos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Pedidos>() {
            @Override
            public void changed(ObservableValue<? extends Pedidos> observable, Pedidos valorAnterior, Pedidos valorSeleccionado) {

                Pedido = valorSeleccionado.getPedido();

                //System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }
    public void gestionarEventosProductos() {
        tbl_listaPrecios.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Productos>() {
            @Override
            public void changed(ObservableValue<? extends Productos> observable, Productos valorAnterior, Productos valorSeleccionado) {

                articulo = valorSeleccionado.getArticulo();

                //System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }
    
    public void Limpiar() {

        txt_fecha.setText("");
        txt_proveedor.setText("");
        txt_pedido.setText("");

    }
    public void Limpiar1() {

        txt_nombreProvee.setText("");
        txt_articulo.setText("");
        txt_precioProducto.setText("");

    }
    
}

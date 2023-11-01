package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static controller.LimpiezaElMolarController.user;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;
import model.Empleados;

/**
 * @author Joaquín
 */
public class EmpleadosRegistradosController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private TableView<Empleados> tbl_empleados;
    @FXML
    private TableColumn<Empleados, Integer> id_empleado;
    @FXML
    private TableColumn<Empleados, String> tbl_nombre;
    @FXML
    private TableColumn<Empleados, String> tbl_telefono;
    @FXML
    private TableColumn<Empleados, String> tbl_centro;
    
    private ObservableList<Empleados> listaEmpleados;
    @FXML
    private Button btn_pdfEmpleados;
    @FXML
    private Button btn_pdfColegios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaEmpleados = FXCollections.observableArrayList(); 
         
         Empleados.llenarInformacionEmpleados(Conexion.conectar(), listaEmpleados);
         
         id_empleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("Id"));
         tbl_nombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Nombre"));
         tbl_telefono.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Telefono"));
         tbl_centro.setCellValueFactory(new PropertyValueFactory<Empleados, String>("Centro"));
         
        
         
        tbl_empleados.setItems(listaEmpleados);
        
        gestionarEventos();
        
    } 
    
    @FXML
    private void FichaEmpleado(MouseEvent event) {
        
        user = LimpiezaElMolarController.user;
         
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InformacionEmpleado.fxml"));

            Parent root = loader.load();
            InformacionEmpleadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del empleado - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void PdfEmpleados(ActionEvent event) {
        
        Document documento = new Document();
        
        try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Informe_Limpieza.pdf"));
            
            documento.open();
           
            Font fuente= new Font();
            fuente.setSize(24);
            
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            //table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("INFORMACIÓN DEL PERSONAL DE LIMPIEZA", PdfPCell.ALIGN_CENTER));
            //table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
            documento.add(table);
                   
            //documento.add(new Paragraph("Informacion de los empleados.", fuente));
            documento.add(new Paragraph(" ", fuente));
            
            PdfPTable tabla = new PdfPTable(6);
    
            tabla.setWidthPercentage(100);
            
    Paragraph columna1 = new Paragraph("NOMBRE");
    columna1.getFont().setStyle(Font.BOLD);
    columna1.getFont().setSize(7);
    columna1.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna1);
    
    /*Paragraph columna2 = new Paragraph("TELÉFONO");
    columna2.getFont().setStyle(Font.BOLD);
    columna2.getFont().setSize(7);
    tabla.addCell(columna2);*/
    
    Paragraph columna2 = new Paragraph("CENTRO");
    columna2.getFont().setStyle(Font.BOLD);
    columna2.getFont().setSize(7);
    columna2.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna2);
    
    Paragraph columna3 = new Paragraph("MOSCOSOS COGIDOS");
    columna3.getFont().setStyle(Font.BOLD);
    columna3.getFont().setSize(7);
    columna3.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna3);
    
    Paragraph columna4 = new Paragraph("EXTRAS");
    columna4.getFont().setStyle(Font.BOLD);
    columna4.getFont().setSize(7);
    columna4.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna4);
    
    Paragraph columna5 = new Paragraph("SUSTITUCIONES");
    columna5.getFont().setStyle(Font.BOLD);
    columna5.getFont().setSize(7);
    columna5.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna5);
    
    Paragraph columna6 = new Paragraph("VACACIONES 1ª");
    columna6.getFont().setStyle(Font.BOLD);
    columna6.getFont().setSize(7);
    columna6.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna6);
    
    /*Paragraph columna8 = new Paragraph("VACACIONES 2ª");
    columna8.getFont().setStyle(Font.BOLD);
    columna8.getFont().setSize(7);
    tabla.addCell(columna8);*/
            
           
            //tabla.addCell("Codigo");
            // tabla.addCell("Nombre");
            //tabla.addCell("Email");
            //tabla.addCell("Teléfono");
            //tabla.addCell("Centro");
            //tabla.addCell("Observaciones");
            //tabla.addCell("Moscosos");
            //tabla.addCell("Moscosos Cogidos");
            //tabla.addCell("Extras");
            //tabla.addCell("Sustituciones");
            //tabla.addCell("Vacaciones");
            //tabla.addCell("Vacaciones1");
            //tabla.addCell("Mes");
            //tabla.addCell("Mes1");
            
            try{
               Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "select * from empleados");
                
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    
                    do{
                        
                        Paragraph columna9 = new Paragraph(rs.getString(2));
                        columna9.getFont().setStyle(Font.ITALIC);
                        columna9.getFont().setSize(8);
                        tabla.addCell(columna9);
                               
                        /*Paragraph columna10 = new Paragraph(rs.getString(4));
                        columna10.getFont().setStyle(Font.BOLD);
                        columna10.getFont().setSize(8);
                        tabla.addCell(columna10);*/
                        
                        Paragraph columna11 = new Paragraph(rs.getString(5));
                        columna11.getFont().setStyle(Font.ITALIC);
                        columna11.getFont().setSize(8);
                        tabla.addCell(columna11);
                        
                        Paragraph columna12 = new Paragraph(rs.getString(8));
                        columna12.getFont().setStyle(Font.ITALIC);
                        columna12.getFont().setSize(8);
                        tabla.addCell(columna12);
                        
                        Paragraph columna13 = new Paragraph(rs.getString(9));
                        columna13.getFont().setStyle(Font.ITALIC);
                        columna13.getFont().setSize(8);
                        tabla.addCell(columna13);
                        
                        Paragraph columna14 = new Paragraph(rs.getString(10));
                        columna14.getFont().setStyle(Font.ITALIC);
                        columna14.getFont().setSize(8);
                        tabla.addCell(columna14);
                        
                        Paragraph columna15 = new Paragraph(rs.getString(11));
                        columna15.getFont().setStyle(Font.ITALIC);
                        columna15.getFont().setSize(8);
                        tabla.addCell(columna15);
                        
                        /*Paragraph columna16 = new Paragraph(rs.getString(12));
                        columna16.getFont().setStyle(Font.BOLD);
                        columna16.getFont().setSize(8);
                        tabla.addCell(columna16);*/
                        
                       //tabla.addCell(rs.getString(1));
                       //tabla.addCell(rs.getString(2));
                       //tabla.addCell(rs.getString(3));
                       //tabla.addCell(rs.getString(4));
                       //tabla.addCell(rs.getString(5));
                       //tabla.addCell(rs.getString(6));
                       //tabla.addCell(rs.getString(7));
                       //tabla.addCell(rs.getString(8));
                       //tabla.addCell(rs.getString(9));
                       //tabla.addCell(rs.getString(10));
                       //tabla.addCell(rs.getString(11));
                       //tabla.addCell(rs.getString(12));
                       //tabla.addCell(rs.getString(13));
                       //tabla.addCell(rs.getString(14));
                    }while(rs.next());
                    documento.add(tabla);
                }
                
            }catch(SQLException | DocumentException e){
                
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "El informe se creó con éxito.");
        }catch(FileNotFoundException | DocumentException | HeadlessException e){
            
        }
        
    }

    @FXML
    private void PdfColegios(ActionEvent event) {
        
        Document documento = new Document();
        
        try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Informe_Personal_Colegios.pdf"));
            
            documento.open();
           
            Font fuente= new Font();
            fuente.setSize(24);
            
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            //table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("INFORMACIÓN DEL PERSONAL DE LIMPIEZA \n \n COLEGIOS", PdfPCell.ALIGN_CENTER));
            //table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
            documento.add(table);
                
            //documento.add(new Paragraph("Informacion de los empleados.", fuente));
            documento.add(new Paragraph(" ", fuente));
            
            PdfPTable tabla = new PdfPTable(6);
    
            tabla.setWidthPercentage(100);
            
    Paragraph columna1 = new Paragraph("NOMBRE");
    columna1.getFont().setStyle(Font.BOLD);
    columna1.getFont().setSize(7);
    columna1.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna1);
    
    Paragraph columna2 = new Paragraph("TELÉFONO");
    columna2.getFont().setStyle(Font.BOLD);
    columna2.getFont().setSize(7);
    columna2.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna2);
    
    Paragraph columna3 = new Paragraph("CENTRO");
    columna3.getFont().setStyle(Font.BOLD);
    columna3.getFont().setSize(7);
    columna3.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna3);
    
    Paragraph columna4 = new Paragraph("MOSCOSOS COGIDOS");
    columna4.getFont().setStyle(Font.BOLD);
    columna4.getFont().setSize(7);
    columna4.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna4);
    
    /*Paragraph columna4 = new Paragraph("EXTRAS");
    columna4.getFont().setStyle(Font.BOLD);
    columna4.getFont().setSize(7);
    columna4.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna4);*/
    
    /*Paragraph columna5 = new Paragraph("SUSTITUCIONES");
    columna5.getFont().setStyle(Font.BOLD);
    columna5.getFont().setSize(7);
    columna5.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna5);*/
    
    Paragraph columna5 = new Paragraph("VACACIONES 1ª");
    columna5.getFont().setStyle(Font.BOLD);
    columna5.getFont().setSize(7);
    columna5.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna5);
    
    Paragraph columna6 = new Paragraph("VACACIONES 2ª");
    columna6.getFont().setStyle(Font.BOLD);
    columna6.getFont().setSize(7);
    columna6.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna6);
            
           
            //tabla.addCell("Codigo");
            // tabla.addCell("Nombre");
            //tabla.addCell("Email");
            //tabla.addCell("Teléfono");
            //tabla.addCell("Centro");
            //tabla.addCell("Observaciones");
            //tabla.addCell("Moscosos");
            //tabla.addCell("Moscosos Cogidos");
            //tabla.addCell("Extras");
            //tabla.addCell("Sustituciones");
            //tabla.addCell("Vacaciones");
            //tabla.addCell("Vacaciones1");
            //tabla.addCell("Mes");
            //tabla.addCell("Mes1");
            
            try{
               Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "select * from empleados where centroinforme = 'Colegios'");
                                
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    
                    do{
                        
                        Paragraph columna9 = new Paragraph(rs.getString(2));
                        columna9.getFont().setStyle(Font.ITALIC);
                        columna9.getFont().setSize(8);
                        tabla.addCell(columna9);
                               
                        Paragraph columna10 = new Paragraph(rs.getString(4));
                        columna10.getFont().setStyle(Font.BOLD);
                        columna10.getFont().setSize(8);
                        tabla.addCell(columna10);
                        
                        Paragraph columna11 = new Paragraph(rs.getString(5));
                        columna11.getFont().setStyle(Font.ITALIC);
                        columna11.getFont().setSize(8);
                        tabla.addCell(columna11);
                        
                        Paragraph columna12 = new Paragraph(rs.getString(8));
                        columna12.getFont().setStyle(Font.ITALIC);
                        columna12.getFont().setSize(8);
                        tabla.addCell(columna12);
                        
                        /*Paragraph columna13 = new Paragraph(rs.getString(9));
                        columna13.getFont().setStyle(Font.ITALIC);
                        columna13.getFont().setSize(8);
                        tabla.addCell(columna13);*/
                        
                       /* Paragraph columna14 = new Paragraph(rs.getString(10));
                        columna14.getFont().setStyle(Font.ITALIC);
                        columna14.getFont().setSize(8);
                        tabla.addCell(columna14);*/
                        
                        Paragraph columna15 = new Paragraph(rs.getString(11));
                        columna15.getFont().setStyle(Font.ITALIC);
                        columna15.getFont().setSize(8);
                        tabla.addCell(columna15);
                        
                        Paragraph columna16 = new Paragraph(rs.getString(12));
                        columna16.getFont().setStyle(Font.BOLD);
                        columna16.getFont().setSize(8);
                        tabla.addCell(columna16);
                        
                       //tabla.addCell(rs.getString(1));
                       //tabla.addCell(rs.getString(2));
                       //tabla.addCell(rs.getString(3));
                       //tabla.addCell(rs.getString(4));
                       //tabla.addCell(rs.getString(5));
                       //tabla.addCell(rs.getString(6));
                       //tabla.addCell(rs.getString(7));
                       //tabla.addCell(rs.getString(8));
                       //tabla.addCell(rs.getString(9));
                       //tabla.addCell(rs.getString(10));
                       //tabla.addCell(rs.getString(11));
                       //tabla.addCell(rs.getString(12));
                       //tabla.addCell(rs.getString(13));
                       //tabla.addCell(rs.getString(14));
                    }while(rs.next());
                    documento.add(tabla);
                }
                
            }catch(SQLException | DocumentException e){
                
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "El informe se creó con éxito.");
        }catch(FileNotFoundException | DocumentException | HeadlessException e){
            
        }
        
        
    }
    
    
    public PdfPCell getCell(String text, int alignment) {
    PdfPCell cell = new PdfPCell(new Phrase(text));
    cell.setPadding(0);
    cell.setHorizontalAlignment(alignment);
    cell.setBorder(PdfPCell.NO_BORDER);
    return cell;
}
    
    
    public void gestionarEventos(){
        tbl_empleados.getSelectionModel().selectedItemProperty().addListener (
                new ChangeListener<Empleados>(){
            @Override
            public void changed(ObservableValue<? extends Empleados> observable, Empleados valorAnterior, Empleados valorSeleccionado) {
                
                update_user = valorSeleccionado.getNombre();
                
                System.out.println("Nombre del alumno seleccionado "+ update_user);
                 
            }
            
        });
    }
    
    public void closeWindows(){
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Principal.fxml"));

            Parent root = loader.load();
            PrincipalController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Administrador - Sesión de "+user);
            stage.setResizable(false);
            stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
            
            stage.show();
            
             
        } catch (IOException ex) {
            Logger.getLogger(RegistroEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

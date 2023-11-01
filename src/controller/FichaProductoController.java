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
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín+
 */
public class FichaProductoController implements Initializable {
    
    String update_user = "",articulo = "";

    @FXML
    private TextField txt_nombreEmpresa;
    @FXML
    private TextArea txt_nombreArticulo;
    @FXML
    private TextField txt_precio;
    @FXML
    private Button btn_modificarArticulo;
    @FXML
    private Button btn_eliminarArticulo;
    @FXML
    private Button btn_pdfArticulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = UsuariosRegistradosController.update_user;
       articulo = MdPrincipalController.articulo;
   
        //MOSTRAR LA INFORMACIÓN DEL USUARIO------------
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select * from precios where articulo = '" +articulo + "'");
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);

            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_nombreEmpresa.setText(rs.getString("nombreRs"));
                txt_nombreArticulo.setText(rs.getString("articulo"));
                txt_precio.setText(rs.getString("precio"));
                    
            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar artículo." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
        
    }    

    @FXML
    private void ModificarArticulo(ActionEvent event) {
        
        try {

            int validacion = 0;
            String nombreEmpresa, articulo1, precio;

            nombreEmpresa = txt_nombreEmpresa.getText().trim();
            articulo1 = txt_nombreArticulo.getText().trim();
            precio = txt_precio.getText().trim();
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "update precios set nombreRs=?, articulo=?, precio=? "
                    + "where articulo = '" + articulo + "'");

            pst2.setString(1, nombreEmpresa);
            pst2.setString(2, articulo1);
            pst2.setString(3, precio);
           
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

    @FXML
    private void EliminarArticulo(ActionEvent event) {
        
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
                    "delete from precios where articulo = ?");

            pst.setString(1, txt_nombreArticulo.getText().trim());
            pst.executeUpdate();

            txt_nombreEmpresa.setText("");
            txt_nombreArticulo.setText("");
            txt_precio.setText("");
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El producto ha sido borrado correctamente.");
            alert1.showAndWait();
          
            //JOptionPane.showMessageDialog(null, "El empleado ha sido borrado correctamente ");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El producto no ha sido eliminado.");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void PdfArticulo(ActionEvent event) {
        
        Document documento = new Document();
        
        try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Listado_de_precios.pdf"));
            
            documento.open();
           
            Font fuente= new Font();
            fuente.setSize(24);
            
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            //table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(" LISTADO DE PRECIOS", PdfPCell.ALIGN_CENTER));
            //table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
            documento.add(table);
                   
            //documento.add(new Paragraph("Informacion de los empleados.", fuente));
            documento.add(new Paragraph(" ", fuente));
            
            PdfPTable tabla = new PdfPTable(3);
    
            tabla.setWidthPercentage(100);
            
    Paragraph columna1 = new Paragraph("NOMBRE R/S");
    columna1.getFont().setStyle(Font.BOLD);
    columna1.getFont().setSize(7);
    columna1.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna1);
    
    Paragraph columna2 = new Paragraph("ARTÍCULO");
    columna2.getFont().setStyle(Font.BOLD);
    columna2.getFont().setSize(7);
    columna2.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna2);
    
    Paragraph columna3 = new Paragraph("PRECIO");
    columna3.getFont().setStyle(Font.BOLD);
    columna3.getFont().setSize(7);
    columna3.getFont().setColor(BaseColor.BLUE);
    tabla.addCell(columna3);
    
    
            
           
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
                        "select * from precios");
                
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
                        
                        Paragraph columna11 = new Paragraph(rs.getString(3));
                        columna11.getFont().setStyle(Font.ITALIC);
                        columna11.getFont().setSize(8);
                        tabla.addCell(columna11);
                        
                        Paragraph columna12 = new Paragraph(rs.getString(4));
                        columna12.getFont().setStyle(Font.ITALIC);
                        columna12.getFont().setSize(8);
                        tabla.addCell(columna12);
                        
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
    
}

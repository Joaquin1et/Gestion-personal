package limpiezaelmolar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Joaquín
 */
public class LimpiezaElMolar extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
       
        Parent root = FXMLLoader.load(getClass().getResource("/view/LimpiezaElMolar.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Acceso al sistema");

        primaryStage.show();
           
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

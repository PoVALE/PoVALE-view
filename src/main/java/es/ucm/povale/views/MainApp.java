package es.ucm.povale.views;

import es.ucm.povale.assertInformation.AssertInformation;
import es.ucm.povale.environment.Environment;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    private FXMLController controller;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLDocument.fxml"));   
        
        Parent root = (Parent)fxmlLoader.load(); 
        
        //FXMLController controller = fxmlLoader.<FXMLController>getController();
        fxmlLoader.setController(controller);
        Scene scene = new Scene(root); 
        
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("PoVALE");
        stage.setScene(scene);
       
        controller.initializeVariables();
        controller.setStage(stage);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void setController(FXMLController controller) {
        this.controller = controller;
    }
    
}

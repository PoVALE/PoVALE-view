package es.ucm.povale.views;

import es.ucm.povale.reader.Var;
import es.ucm.povale.reader.XMLParser;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLDocument.fxml"));   
        
        Parent root = (Parent)fxmlLoader.load(); 
        
        FXMLController controller = fxmlLoader.<FXMLController>getController();
        
        //
        String XMLFile = "src/main/resources/existDocument.xml";  
        XMLParser parser = new XMLParser();
        parser.parseXMLFile(XMLFile);
        List<Var> myVars = parser.getMyVars();
        //
        
        controller.setVariables(myVars);
        
        Scene scene = new Scene(root); 
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("PoVALE");
        stage.setScene(scene);    

        stage.show(); 
        
        controller.initializeVariables();
        
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
    
    

}

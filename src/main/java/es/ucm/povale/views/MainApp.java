package es.ucm.povale.views;

import es.ucm.povale.environment.Environment;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.XMLParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    private Environment environment;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        File selectedFile = null;
     
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona el fichero de especificación");
        fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("XML Files", "*.xml"));
        selectedFile = fileChooser.showOpenDialog(stage);
        
        //el usuario ha pulsado la opción cancelar
        if (selectedFile == null) {
            Platform.exit();
            System.exit(0);
        }
        else{
            this.environment = new Environment(); 
            InputStream is = new FileInputStream(selectedFile);
            XMLParser parser = new XMLParser();
            parser.parseXMLFile(is);

            List<String> plugins = parser.getMyPlugins();
            if (!plugins.get(0).equalsIgnoreCase("")) {
                for (String a : plugins) {
                    Import plugin = new Import(a, this.environment);
                }
            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLDocument.fxml"));   
            FXMLController controller = new FXMLController();
            fxmlLoader.setController(controller);
            Parent root = (Parent)fxmlLoader.load(); 

            Scene scene = new Scene(root); 
            scene.getStylesheets().add("/styles/Styles.css");
            stage.setTitle("PoVALE");
            stage.setScene(scene);

            this.environment.addParamEditors();
            this.environment.addVariables(parser.getMyVars());
            controller.setStage(stage);
            controller.setEnvironment(this.environment);
            controller.setAssertions(parser.getMyAsserts());
            controller.setRequirements(parser.getMyRequirements());
            controller.initializeVariables();
            controller.initializeRequirements();
            controller.setStage(stage);

            stage.show();
        }
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

package es.ucm.povale.views;

import es.ucm.povale.environment.Environment;
import es.ucm.povale.example.Main;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.XMLParser;
import java.io.InputStream;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    
    private static Environment environment;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        InputStream is = Main.class.getClassLoader().getResourceAsStream("existDocument.xml");
        
        System.out.println(is);
        
        XMLParser parser = new XMLParser();
        parser.parseXMLFile(is);

        List<String> plugins = parser.getMyPlugins();
        if (!plugins.get(0).equalsIgnoreCase("")) {
            for (String a : plugins) {
                Import plugin = new Import(a, environment);
            }
        }

        environment.addParamEditors();

        environment.addVariables(parser.getMyVars());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLDocument.fxml"));   
        
        FXMLController controller = fxmlLoader.<FXMLController>getController();
        
        Parent root = (Parent)fxmlLoader.load(); 

        controller.setAssertions(parser.getMyAsserts());
        
        Scene scene = new Scene(root); 
        
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("PoVALE");
        stage.setScene(scene);
       
        controller.initializeVariables();
        controller.setStage(stage);
        
        System.out.println(controller);
        
        stage.show();
    }
    
    //scene povale que tenga como param de entrada un xml parser y haga un .show()

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
/*
    public void setController(FXMLController controller) {
        MainApp.controller = controller;
    }
    */
}

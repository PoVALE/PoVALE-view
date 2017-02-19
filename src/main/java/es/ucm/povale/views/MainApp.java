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
        
        //controller.setEnvironment(this.environment);
        
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
    
    public TreeItem<String> createBranch(AssertInformation node) {
        TreeItem<String> assertRoot;
        TreeItem<String> assertBranch;//sub arbol para descendientes
        //1. creamos raiz
        if (node.getResult()) {
            Node correctIcon = new ImageView(new Image("file:src/main/resources/correct.png"));
            assertRoot = new TreeItem<>(node.getMessage(), correctIcon);
        } else {
            Node incorrectIcon = new ImageView(new Image("file:src/main/resources/incorrect.png"));
            assertRoot = new TreeItem<>(node.getMessage(), incorrectIcon);
        }

        //2. si no es hoja crear hijos
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                assertBranch = createBranch(node.getChildren().get(i));
                assertRoot.getChildren().add(assertBranch);
            }
        }
        return assertRoot;
    }
    
    public void setController(FXMLController controller) {
        this.controller = controller;
    }
    
}

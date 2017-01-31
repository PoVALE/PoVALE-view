
package es.ucm.povale.views;

import es.ucm.povale.assertion.Assertion;
import es.ucm.povale.entity.IntegerEntity;
import es.ucm.povale.environment.Environment;
//import es.ucm.povale.parameter.FileEditor;
import es.ucm.povale.views.parameter.ParameterEditor;
import es.ucm.povale.views.parameter.StringEditor;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.Var;
import es.ucm.povale.reader.XMLParser;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author PoVALE Team
 */
public class PoVALEView extends Application {
    
    private Node correctIcon = new ImageView(new Image("file:src/main/resources/correct.png"));
    private Node incorrectIcon = new ImageView(new Image("file:src/main/resources/incorrect.png"));
    
    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        String XMLFile = "src/main/resources/existDocument.xml";  
        XMLParser parser = new XMLParser();
        parser.parseXMLFile(XMLFile);
        List<Var> myVars = parser.getMyVars();

        Environment env = new Environment();
        
        List<String> plugins = parser.getMyPlugins();
        if(!plugins.get(0).equalsIgnoreCase("")){
            for (String a : plugins){
                Import plugin = new Import(a);
            }
        }

        env.getValues().put("x", new IntegerEntity(3));

        List<Assertion> assertions = parser.getMyAsserts();
    
        HashMap<String,ParameterEditor> paramEditorMap = new HashMap<>();
        paramEditorMap.put("StringEntity",new StringEditor());
        
        TitledPane mainPane = new TitledPane();
        mainPane.setPrefHeight(400);
        mainPane.setPrefWidth(600);
        mainPane.setText("Requisitos");
        
        BorderPane contentPane = new BorderPane();
        contentPane.setPrefHeight(200);
        contentPane.setPrefWidth(200);
        
        GridPane variablePane = new GridPane();
        variablePane.setAlignment(Pos.CENTER);
        for (int i=0;i<myVars.size();i++){
            variablePane.add(new Label(myVars.get(i).getLabel()),1, i+1);
            Label description = new Label(myVars.get(i).getDescription());
            description.setTextFill(Color.GREY);
            variablePane.add(description,1, i+2);
            variablePane.add(paramEditorMap.get(myVars.get(i).getType()).getPane(), 2, i+1);
        }
        
        contentPane.setTop(variablePane);
        
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        Button button = new Button("Check");
        buttonPane.add(button, 1, 1);
        contentPane.setRight(buttonPane);
        
        FlowPane resultPanel = new FlowPane();
        resultPanel.setPrefHeight(200);
        resultPanel.setPrefWidth(200);
        resultPanel.setAlignment(Pos.CENTER);
        resultPanel.setPrefSize(200, 577);
        contentPane.setBottom(resultPanel);
        
        mainPane.setContent(contentPane);
        
        button.setOnAction((final ActionEvent e) -> {
            boolean result = true;
            TreeItem<String> rootNode = 
                new TreeItem<String>("", correctIcon);
            
            rootNode.setExpanded(false);
            
            for (Assertion a : assertions) {
                TreeItem<String> assertLeaf = null;
                if (!a.check(env).isPresent()) {
                    assertLeaf = new TreeItem<String>("OK", correctIcon);
                    rootNode.getChildren().add(assertLeaf);
                } 
                else 
                {
                    result = false;
                    //assertLeaf = new TreeItem<String>("Incorrect", incorrectIcon);
                    assertLeaf = new TreeItem<String>(a.getMessage(), incorrectIcon);
                    rootNode.getChildren().add(assertLeaf);
                }
               
        }
           if(!result)
               rootNode.setGraphic(incorrectIcon);
           
            TreeView<String> tree = new TreeView<String> (rootNode); 
            resultPanel.getChildren().clear();
            resultPanel.getChildren().add(tree);
            contentPane.setBottom(resultPanel);
            mainPane.setContent(contentPane);
        });                 
        stage.setScene(new Scene(mainPane));
        stage.show();
    }
    
    
}

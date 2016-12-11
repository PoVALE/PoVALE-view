/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.view;

import es.ucm.povale.assertion.Assertion;
import es.ucm.povale.entity.IntegerEntity;
import es.ucm.povale.environment.Environment;
import es.ucm.povale.parameter.FileEditor;
import es.ucm.povale.parameter.ParameterEditor;
import es.ucm.povale.parameter.StringEditor;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.Var;
import es.ucm.povale.reader.XMLParser;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author laurahernandoserrano
 */
public class PoVALEView extends Application {
    
   

    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String XMLFile = "src/main/resources/existPrueba1.xml";
        XMLParser parser = new XMLParser();
        parser.parseXMLFile(XMLFile);
        List<Var> myVars = parser.getMyVars();

        Environment env = new Environment();
        
        List<String> plugins = parser.getMyPlugins();
        
        for (String a : plugins){
            Import plugin = new Import(a);
        }

        env.getValues().put("x", new IntegerEntity(3));

        List<Assertion> assertions = parser.getMyAsserts();
        
    
        HashMap<String,ParameterEditor> paramEditorMap = new HashMap<>();
        paramEditorMap.put("String",new StringEditor());
        paramEditorMap.put("File",new FileEditor(stage));
        
        stage.setTitle("PoVALE");
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(500, 400);
        
        
        GridPane gridPane = new GridPane();
        
        for (int i=0;i<myVars.size();i++){
            
            VBox vBox = new VBox();
            vBox.getChildren().add(new Label(myVars.get(i).getName()));
            vBox.getChildren().add(new Label(myVars.get(i).getDescription()));
            gridPane.add(vBox, i+1, 1);
            //gridPane.add(new Label(myVars.get(i).getName()), i, 1);
            paramEditorMap.get(myVars.get(i).getType());
            gridPane.add(paramEditorMap.get(myVars.get(i).getType()).getPane(), i+1, 2);
            
        }
        borderPane.setTop(gridPane);
        
        GridPane gridPaneButton = new GridPane();
        Button button = new Button("Comprobar");
        gridPane.add(button, 1, 1);
        
        button.setOnAction((final ActionEvent e) -> {
           for (Assertion a : assertions) {
            if (!a.check(env).isPresent()) {
                System.out.println("true");
            } else {
                System.out.println("false");
                System.out.println(a.check(env).get().toString());
            }
        }
        });
        
        stage.setScene(new Scene(borderPane,500,400));
        stage.show();
    }
    
    
}

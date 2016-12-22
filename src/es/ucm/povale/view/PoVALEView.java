/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.view;

import es.ucm.povale.assertion.Assertion;
import es.ucm.povale.entity.IntegerEntity;
import es.ucm.povale.environment.Environment;
//import es.ucm.povale.parameter.FileEditor;
import es.ucm.povale.parameter.ParameterEditor;
import es.ucm.povale.parameter.StringEditor;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.Var;
import es.ucm.povale.reader.XMLParser;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        String XMLFile = "test/xmlExamples/existDocument.xml";
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
        //paramEditorMap.put("File",new FileEditor(stage));
        
        
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
        TextArea resultText = new TextArea();
        resultText.setPrefHeight(200);
        resultText.setPrefWidth(577);        
        resultPanel.getChildren().add(resultText);
        contentPane.setBottom(resultPanel);
        
        mainPane.setContent(contentPane);
        
         button.setOnAction((final ActionEvent e) -> {
           for (Assertion a : assertions) {
            if (!a.check(env).isPresent()) {
                resultText.setText("Se cumplen los requisitos");
            } else {
                resultText.setText("Los requisitos no se cumplen");
            }
        }
        });
                          
        stage.setScene(new Scene(mainPane));
        stage.show();
    }
    
    
}

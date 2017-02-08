/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.views;

import es.ucm.povale.reader.Var;
import es.ucm.povale.views.parameter.ParameterEditor;
import es.ucm.povale.views.parameter.StringEditor;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;

/**
 *
 * @author laurahernandoserrano
 */
public class FXMLController{
    
    @FXML private Label lblName1;
    @FXML private Label lblDesc1;
    @FXML private Label lblName2;
    @FXML private Label lblDesc2;
    @FXML private Label lblName3;
    @FXML private Label lblDesc3;
    @FXML private Label lblName4;
    @FXML private Label lblDesc4;
    @FXML private Label lblName5;
    @FXML private Label lblDesc5;
    @FXML private Label lblName6;
    @FXML private Label lblDesc6;
    @FXML private Label lblName7;
    @FXML private Label lblDesc7;
    @FXML private Label lblName8;
    @FXML private Label lblDesc8;
    @FXML private Separator line1;
    @FXML private Separator line2;
    @FXML private Separator line3;
    @FXML private Separator line4;
    @FXML private Separator line5;
    @FXML private Separator line6;
    @FXML private Separator line7;
    @FXML private Separator line8;
    
    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;
    @FXML private Pane pane5;
    @FXML private Pane pane6;
    @FXML private Pane pane7;
    @FXML private Pane pane8;
    
    
    List<Var> environmentVariables;
    List<Label> variableNames;
    List<Label> variableDescriptions;
    List<Separator> lines;
    List<Pane> panes;
    
    @FXML
    public void initialize() {
        createLists();
    }
    
    private void createLists(){
        variableNames = new LinkedList<>();
        variableDescriptions = new LinkedList<>();
        lines = new LinkedList<>();
        panes = new LinkedList<>();
        
        variableNames.add(lblName1);
        variableDescriptions.add(lblDesc1);
        variableNames.add(lblName2);
        variableDescriptions.add(lblDesc2);
        variableNames.add(lblName3);
        variableDescriptions.add(lblDesc3);
        variableNames.add(lblName4);
        variableDescriptions.add(lblDesc4);
        variableNames.add(lblName5);
        variableDescriptions.add(lblDesc5);
        variableNames.add(lblName6);
        variableDescriptions.add(lblDesc6);
        variableNames.add(lblName7);
        variableDescriptions.add(lblDesc7);
        variableNames.add(lblName8);
        variableDescriptions.add(lblDesc8);
        
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);
        lines.add(line5);
        lines.add(line6);
        lines.add(line7);
        lines.add(line8);
        
        panes.add(pane1);
        panes.add(pane2);
        panes.add(pane3);
        panes.add(pane4);
        panes.add(pane5);
        panes.add(pane6);
        panes.add(pane7);
        panes.add(pane8);
        
    }    
    
    public void setVariables(List<Var> variables){
        this.environmentVariables = variables;
    }
    
    public void initializeVariables(){
        
        if(this.environmentVariables.size()>9){
            //throw error
        }
       
        for(int i=0; i<environmentVariables.size();i++){
            variableNames.get(i).setText(environmentVariables.get(i).getLabel());
            variableDescriptions.get(i).setText(environmentVariables.get(i).getDescription());
            
            switch(environmentVariables.get(i).getType()){
                case "StringEntity":
                    panes.get(i).getChildren().add(new StringEditor().getPane());
                    break;
            }
                    
        }
        
        for(int j=environmentVariables.size(); j<8; j++){
            variableNames.get(j).setVisible(false);
            variableDescriptions.get(j).setVisible(false);
            lines.get(j).setVisible(false);
        }
        
    }
}

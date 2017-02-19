/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.views;

import es.ucm.povale.Var;
import es.ucm.povale.assertInformation.AssertInformation;
import es.ucm.povale.assertion.Assertion;
import es.ucm.povale.entity.Entity;
import es.ucm.povale.environment.Environment;
import es.ucm.povale.views.parameter.ParameterEditor;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author laurahernandoserrano
 */
public class FXMLController {

    @FXML
    private Label lblName1;
    @FXML
    private Label lblDesc1;
    @FXML
    private Label lblName2;
    @FXML
    private Label lblDesc2;
    @FXML
    private Label lblName3;
    @FXML
    private Label lblDesc3;
    @FXML
    private Label lblName4;
    @FXML
    private Label lblDesc4;
    @FXML
    private Label lblName5;
    @FXML
    private Label lblDesc5;
    @FXML
    private Label lblName6;
    @FXML
    private Label lblDesc6;
    @FXML
    private Label lblName7;
    @FXML
    private Label lblDesc7;
    @FXML
    private Label lblName8;
    @FXML
    private Label lblDesc8;
    @FXML
    private Separator line1;
    @FXML
    private Separator line2;
    @FXML
    private Separator line3;
    @FXML
    private Separator line4;
    @FXML
    private Separator line5;
    @FXML
    private Separator line6;
    @FXML
    private Separator line7;
    @FXML
    private Separator line8;

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;
    @FXML
    private Pane pane5;
    @FXML
    private Pane pane6;
    @FXML
    private Pane pane7;
    @FXML
    private Pane pane8;

    @FXML
    private TreeView tvRequisitos;
    @FXML
    private TreeView tvValidacion;

    @FXML
    private Button btnComprobar;

    private List<Var> environmentVariables;
    private List<Label> variableNames;
    private List<Label> variableDescriptions;
    private List<Separator> lines;
    private List<Pane> panes;
    private List<Assertion> assertions;

    private Stage stage;

    private Environment env;

    @FXML
    public void initialize() {
        createLists();
    }

    public void setStage(Stage mainStage) {
        this.stage = mainStage;
    }

    private void createLists() {
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

    public void setAssertions(List<Assertion> assertions) {
        this.assertions = assertions;
    }
    
    public void setEnvironment(Environment e) {
        this.env = e;
    }

    public void initializeVariables() {

        if (this.env.getVariables().size() > 9) {
            //throw error
        }

        List<Var> list = this.env.getVariables().stream().collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            variableNames.get(i).setText(list.get(i).getLabel());
            variableDescriptions.get(i).setText(list.get(i).getDescription());

            ParameterEditor<? extends Entity> editor = env.getParamEditor(list.get(i).getType()).getEditor(list.get(i).getType(), list.get(i).getParameters());
            editor.setStage(this.stage);
            panes.get(i).getChildren().add(editor.getPane());
        }

        for (int j = list.size(); j < 8; j++) {
            variableNames.get(j).setVisible(false);
            variableDescriptions.get(j).setVisible(false);
            lines.get(j).setVisible(false);
        }

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

    @FXML
    private void handleComprobarButtonAction(ActionEvent event) {
        boolean result = true;
        Node correctIcon = new ImageView(new Image("file:src/main/resources/correct.png"));
        Node incorrectIcon = new ImageView(new Image("file:src/main/resources/incorrect.png"));
        TreeItem<String> rootNode = new TreeItem<>("Se cumplen los siquientes requisitos:", correctIcon);

        rootNode.setExpanded(true);

        for (Assertion a : assertions) {
            TreeItem<String> assertBranch = null;
            AssertInformation assertInfo = a.check(env);
            if (!assertInfo.getResult()) {
                result = false;
            }
            assertBranch = createBranch(assertInfo);
            rootNode.getChildren().add(assertBranch);
        }
        if (!result) {
            rootNode.setGraphic(incorrectIcon);
        }

        tvValidacion = new TreeView<>(rootNode);
    }
}

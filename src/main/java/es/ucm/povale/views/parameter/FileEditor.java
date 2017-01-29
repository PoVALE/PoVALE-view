package es.ucm.povale.views.parameter;

import es.ucm.povaleFiles.entities.FSFile;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class FileEditor extends ParameterEditor<FSFile> {

    private HBox pane;
    private FileChooser fileChooser;
    private Button selectButton;
    private TextField path;
    private File selectedFile;

    public FileEditor(final Stage stage) {
        this.pane = new HBox();
        this.fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Java Files", "*.java"));

        this.selectButton = new Button("Open Resource File");
        this.path = new TextField();
        this.path.setPrefColumnCount(100);

        selectButton.setOnAction((final ActionEvent e) -> {
            selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                path.setText(selectedFile.getAbsolutePath());
            }
        });

        path.setOnAction((final ActionEvent e) -> {
            if (!new File(path.getText()).exists()) {
                //throw new FileNotFoundException
                path.setText("");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning ");
                alert.setHeaderText("File does not exist");
                alert.showAndWait();
            } else {
                selectedFile = new File(path.getText());
            }

        });
        pane.getChildren().addAll(path, selectButton);
    }

    @Override
    public Pane getPane() {
        return this.pane;
    }

    @Override
    public FSFile getEntity() {
        return new FSFile(selectedFile.toPath());
    }

    @Override
    public boolean isValid() {
        return (selectedFile == null);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.view;

import es.ucm.povale.assertion.Assertion;
import es.ucm.povale.entity.IntegerEntity;
import es.ucm.povale.environment.Environment;
import es.ucm.povale.plugin.Import;
import es.ucm.povale.reader.Var;
import es.ucm.povale.reader.XMLParser;
import static java.util.Collections.list;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author laurahernandoserrano
 */
public class PoVALEView extends Application {
    
   

    /**
     * @param args the command line arguments
     */
    entityMap.put("variable", this::createEntity);
    public static void main(String[] args) {
        String XMLFile = "src/main/resources/existPrueba1.xml";
        XMLParser parser = new XMLParser();
        parser.parseXMLFile(XMLFile);

        Environment env = new Environment();
        
         List<String> plugins = parser.getMyPlugins();
        
        for (String a : plugins){
            Import plugin = new Import(a);
        }

        env.getValues().put("x", new IntegerEntity(3));

        List<Assertion> assertions = parser.getMyAsserts();
        for (Assertion a : assertions) {
            
            if (!a.check(env).isPresent()) {
                System.out.println("true");
            } else {
                System.out.println("false");
                System.out.println(a.check(env).get().toString());
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public void builder(Stage stage, List<Var> myVars) throws Exception {
        /* Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        //stage.setTitle("");
        stage.setScene(scene);
        stage.show();
        */
        stage.setTitle("PoVALE's Interface");
        BorderPane bp = new BorderPane();
        bp.setPrefSize(200, 200);
        
        
        GridPane gp = new GridPane();
        for (Var v: myVars){
            gp.
            //crearlabel con nombre
                    //hashmap
            ParameterEditor p = new ParameterEditor<v.>
            //crear un parameterEditor()
        }
        bp.getChildren().add(gp);
        stage.setScene(new Scene(bp, 200, 200));
        //leer de una lista la lista de variables e ir metiendolas en la pantalla
    }
    
    
}

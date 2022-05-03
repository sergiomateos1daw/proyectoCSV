package es.sergio.mateos.proyectocsv;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    boolean depuracion = true;
    
    ImageView banner;
    
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);
        ///////// AÑADIMOS EL BANNER ///////////
        Image bannerImg = new Image(getClass().getResourceAsStream("/images/banner.png")); // CARGA LA IMAGEN DE FONDO
        banner = new ImageView(bannerImg);
        root.getChildren().add(banner);
        ////////////////////////////////////////
        HBox hBoxComboBox = new HBox(20);
        hBoxComboBox.setAlignment(Pos.CENTER);
        root.getChildren().add(hBoxComboBox);
        
        ////////////////////////////////////////
        HBox hBoxDatos = new HBox(20);
        hBoxDatos.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(hBoxDatos);
        
        ////////////////////////////////////////
        HBox hBoxMediaAtentados = new HBox(20);
        hBoxMediaAtentados.setAlignment(Pos.TOP_LEFT);
        root.getChildren().add(hBoxMediaAtentados);
        
        var scene = new Scene(root, 640, 480);
        
        stage.setScene(scene);
        stage.show();
        
        CapaOperaciones capaOperaciones = new CapaOperaciones();
        capaOperaciones.leerFichero(hBoxComboBox, hBoxDatos, hBoxMediaAtentados, root, depuracion);
        
    }
    
    public static void main(String[] args) {
        launch();
    }

}
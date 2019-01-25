package hamburguer;

/*
 * Nombre del programa:
 * Autor: Luis Ángel De Santiago Guerrero
 * Última fecha de modificación: 
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Angel
 */
public class App extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("PrincipalFXML.fxml"));

    Scene scene = new Scene(root);

    stage.setTitle("Petición de órdenes de hamgurguesas");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}

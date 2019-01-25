package hamburguer;

/*
 * Nombre del programa:
 * Autor: Luis Ángel De Santiago Guerrero
 * Última fecha de modificación: 
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Angel
 */
public class PrincipalController implements Initializable {

  // Componentes de la ventana principal
  @FXML
  private BorderPane borderpane;
  @FXML
  private Button btnChangeScene;
  @FXML
  private Label labelNotification;
  @FXML
  public ListView<String> listview;
  @FXML
  private Button btnProcess;

  Alert alert;

  ObservableList<String> cola = FXCollections.observableArrayList();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    System.out.println("Inicializando");

    // Inicializa la lista con la cola
    listview.setItems(cola);

    // Pone a la escucha el botón
    // que procesa los pedidos
    btnProcess.setOnAction(e -> {
      if (!cola.isEmpty()) {
        cola.remove(0);

        alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información sobre el pedido");
        alert.setHeaderText(null);
        alert.setContentText("Pedido despachado correctamente.");
        alert.showAndWait();

        listview.getSelectionModel().clearSelection();
      }
    });
  }

  /**
   * Lanza el formulario de pedidos
   *
   * @throws IOException
   */
  public void launchFormsPedidos() throws IOException {
    this.labelNotification.setText("Procesando: Cargando formulario de pedidos...");

    FXMLLoader loader = new FXMLLoader(getClass().getResource("PedidosFXML.fxml"));
    Parent root1 = loader.load();

    // Obtiene la instancia del controlador pedidos
    PedidosController pController = loader.getController();

    Scene scene = new Scene(root1);

    Stage formsWindow = new Stage();

    // Obtiene la instancia de la ventana principal a través de un botón
    Stage mainWindow = (Stage) this.btnChangeScene.getScene().getWindow();

    // Inicia la ventana en modo MODAL
    formsWindow.setTitle("Formulario de pedidos");
    formsWindow.initModality(Modality.WINDOW_MODAL);
    formsWindow.initOwner(mainWindow);
    formsWindow.setScene(scene);
    formsWindow.showAndWait();

    // Agrega los pedidos realizados a la cola principal
    if (!pController.data.isEmpty()) {
      cola.addAll(pController.data);
    }

    this.labelNotification.setText("Listo.");
  }

  /**
   * Lanza una ventana de información sobre el proyecto
   *
   * @throws IOException
   */
  public void launchInfo() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));

    Scene scene = new Scene(root);

    Stage window = new Stage();

    window.setTitle("Acerca de este programa");
    window.initModality(Modality.WINDOW_MODAL);
    window.initOwner(this.btnProcess.getScene().getWindow());
    window.setScene(scene);
    window.setResizable(false);
    window.showAndWait();

  }

  /**
   * Función para cerrar la aplicación
   */
  public void closeApp() {
    System.out.println("Aplicación terminada correctamente");
    Platform.exit();
  }

}

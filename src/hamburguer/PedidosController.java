package hamburguer;

/*
 * Nombre del programa:
 * Autor: Luis Ángel De Santiago Guerrero
 * Última fecha de modificación: 
 */
import java.net.URL;
import java.sql.Time;
import java.time.Instant;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Angel
 */
public class PedidosController implements Initializable {

  final double hamburguerPrice = 35.0;
  final double sodaPrice = 15.0;

  // Componentes de la ventana del formulario
  @FXML
  private Label nameClient;
  @FXML
  private TextField inputNameClient;
  @FXML
  private Label totalLabel;

  // Datos para la hamburguesa
  @FXML
  private Label hambTotalLabel;
  @FXML
  private CheckBox hambCheckB;
  @FXML
  private ComboBox<String> hambComboB;
  ObservableList<String> hambLimit;

  // Datos para la soda
  @FXML
  private Label sodaTotalLabel;
  @FXML
  private CheckBox sodaCheckB;
  @FXML
  private ComboBox<String> sodaComboB;
  ObservableList<String> sodaLimit;

  @FXML
  private Button btnComplete;

  Alert alert;

  public String mystring = "";
  ObservableList<String> data = FXCollections.observableArrayList();
  public static int counter = 1;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    hambLimit = FXCollections.observableArrayList();
    sodaLimit = FXCollections.observableArrayList();

    // Establece el límite para las 
    // opciones de compra para la hamburguesa
    // y el refresco
    for (int i = 1; i <= 10; i++) {
      hambLimit.add(Integer.toString(i));
      sodaLimit.add(Integer.toString(i));
    }
    hambComboB.setItems(hambLimit);
    hambComboB.setVisibleRowCount(5);

    sodaComboB.setItems(sodaLimit);
    sodaComboB.setVisibleRowCount(5);

    //
    // Pone a la escucha el ComboBox para la cantidad de hamburguesas
    //
    hambCheckB.setOnAction(e -> {
      if (hambCheckB.isSelected()) {
        // Es importante seleccionar el primer elemento
        // Si no, causará una exception tipo NullPointerException
        hambComboB.getSelectionModel().selectFirst();
        hambComboB.setDisable(false);
      } else {
        hambTotalLabel.setText("0");
        hambComboB.setDisable(true);
      }
      updateTotal();
    });

    hambComboB.setOnAction(e -> {
      hambTotalLabel.setText(hambComboB.getSelectionModel().getSelectedItem());
      updateTotal();
    });

    //
    // Pone a la escucha al ComboBox para la cantidad de refrescos
    //
    sodaCheckB.setOnAction(e -> {
      if (sodaCheckB.isSelected()) {
        sodaComboB.getSelectionModel().selectFirst();
        sodaComboB.setDisable(false);
      } else {
        sodaComboB.setDisable(true);
        sodaTotalLabel.setText("0");
      }
      updateTotal();
    });

    sodaComboB.setOnAction(e -> {
      sodaTotalLabel.setText(sodaComboB.getSelectionModel().getSelectedItem());
      updateTotal();
    });

    // Botón de completar
    btnComplete.setOnAction(e -> {
      complete();
    });
  }

  /**
   * Simplemente "bindea" la entrada de texto con la etiqueta
   */
  public void prepareDataName() {
    if (inputNameClient.getText().isEmpty()) {
      nameClient.setText("Anónimo");
    } else {
      nameClient.setText(inputNameClient.getText());
    }

  }

  /**
   * Actualiza el estado total de pedidos
   */
  public void updateTotal() {
    double total = 0.0;

    // Calcula la cantidad de hamburguesas
    if (!hambComboB.isDisabled()) {
      total += Double.parseDouble(hambComboB.getSelectionModel().getSelectedItem()) * hamburguerPrice;
    }

    // Calcula la cantidad de refrescos
    if (!sodaComboB.isDisabled()) {
      total += Double.parseDouble(sodaComboB.getSelectionModel().getSelectedItem()) * sodaPrice;
    }

    // Deshabilita o habilita botón completar
    if (total != 0.0) {
      btnComplete.setDisable(false);
    } else {
      btnComplete.setDisable(true);
    }

    totalLabel.setText(Double.toString(total));
  }

  public void complete() {
    String nc = nameClient.getText();
    String ham = hambTotalLabel.getText();
    String soda = sodaTotalLabel.getText();
    String total = totalLabel.getText();

    mystring = "Turno: " + counter + ". Cliente: " + nc + ", "
            + " hamburguesas: " + ham
            + " refrescos: " + soda
            + ", total: " + total + ". Fecha:";

    mystring += Time.from(Instant.now());

    data.add(mystring);
    counter++;

    alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Información del pedido");
    alert.setHeaderText(null);
    alert.setContentText("¡Pedido agregado correctamente!");
    alert.showAndWait();

    System.out.println(mystring);

    // Establece los datos por defecto
    inputNameClient.setText("");
    nameClient.setText("Anónimo");
    hambCheckB.setSelected(false);
    hambComboB.getSelectionModel().selectFirst();
    hambComboB.setDisable(true);
    sodaCheckB.setSelected(false);
    sodaComboB.getSelectionModel().selectFirst();
    sodaComboB.setDisable(true);
    hambTotalLabel.setText("0");
    sodaTotalLabel.setText("0");
    updateTotal();
  }

}

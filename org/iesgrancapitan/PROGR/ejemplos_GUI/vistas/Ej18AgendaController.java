package org.iesgrancapitan.PROGR.ejemplos_GUI.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.iesgrancapitan.PROGR.ejemplos_GUI.Ej18AgendaFicha;
import org.iesgrancapitan.PROGR.ejemplos_GUI.agenda.Agenda;
import org.iesgrancapitan.PROGR.ejemplos_GUI.agenda.ContactoException;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class Ej18AgendaController {
  @FXML
  private GridPane pane;
  @FXML
  private TextField textNombre;
  @FXML
  private TextField textDireccion;
  @FXML
  private TextField textTelefono;
  @FXML
  private ImageView imagen;

  private Ej18AgendaFicha mainApp;
  private int n = 0;  // elemento de la agenda a mostrar

  // Event Listener on Button.onAction
  @FXML
  public void irPrimero(ActionEvent event) {
    n = 0;
    actualizaFicha();
  }

  @FXML
  public void irAnterior(ActionEvent event) {
    if (n>0) {
      n--;
      actualizaFicha();
    } else {
      Alerta("Estamos en el primer contacto");
    }
  }

  // Event Listener on Button.onAction
  @FXML
  public void alta(ActionEvent event) {
    // TODO Autogenerated
  }

  // Event Listener on Button.onAction
  @FXML
  public void modificar(ActionEvent event) {
    // TODO Autogenerated
  }
  // Event Listener on Button.onAction
  @FXML
  public void borrar(ActionEvent event) {
    // TODO Autogenerated
  }

  @FXML
  public void irSiguiente(ActionEvent event) {
    if (n < mainApp.getAgenda().size() - 1) {
      n++;
      actualizaFicha();
    } else {
      Alerta("Estamos en el último contacto");
    }
  }

  @FXML
  public void irUltimo(ActionEvent event) {
    n = mainApp.getAgenda().size() - 1;
    actualizaFicha();
  }

  @FXML
  public void actualizaImagen() {
    if (mainApp.getAgenda().size()>0) { // estamos en un contacto "real"
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(   // filtramos por *.java
          new FileChooser.ExtensionFilter("Ficheros PNG", "*.png")   );
      File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
      // Si no han pulsado "Cancelar" procedemos...
      if (file == null) {
        return;
      }
      // Copiamos imagen
      try {
        String archivo = nombreImagen();
        System.out.println(archivo);
        File destino = new File(archivo + 
            "/" + textNombre.getText().replace(" ", "_") + ".png");
        //File destino = new File("borra.png");
        
        Files.copy(file.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
         actualizaFicha();
      } catch (Exception e) {
        e.printStackTrace();
        Alerta("Error al crear la imagen");
      }
    }
  }

  /**
   * Es llamado por la aplicación principal para devolverse una referencia a sí mismo.
   * 
   * @param mainApp
   */
  public void setMainApp(Ej18AgendaFicha mainApp) {
    this.mainApp = mainApp;
    actualizaFicha();
  }

  /**
   * Rellena los controles con los datos de la agenda
   */
  private void actualizaFicha() {
    Agenda agenda = mainApp.getAgenda();
    try {

      //Datos contacto
      textNombre.setText(agenda.get(n).getNombre());
      textTelefono.setText(String.valueOf(agenda.get(n).getTelefono()));
      textDireccion.setText(agenda.get(n).getEmail());

      //Imagen
      Image img = null;
      try {
        String archivo = "../img/" + textNombre.getText().replace(" ", "_") + ".png";
        img = new Image(getClass().getResource(archivo).toString());
      } catch (Exception e) {
        img = new Image(getClass().getResource("../img/contact.png").toString());
      }
      imagen.setImage(img);

    } catch (ContactoException e) {
      textNombre.setText("");
      textTelefono.setText("");
      textDireccion.setText("");
      Alerta("Agenda vacía");
    }
  }

  private void Alerta(String msg) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle("Atención");
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
  }
  
  private String nombreImagen() {
    String nombre = getClass().getResource("../img").toString();
    nombre.replace("%c3%b3", "ó");
    System.out.println(nombre);
    return nombre;
  }
}

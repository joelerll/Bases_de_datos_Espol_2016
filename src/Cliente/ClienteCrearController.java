
package Cliente;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import database.DBconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;


public class ClienteCrearController implements Initializable {
    //ATRIBUTOS PARA LA BASE DE DATOS
    private static final DBconnection database=new DBconnection();
    private static Connection con;
    //ATRIBUTOS DE LA VENTANA
    @FXML
    private DatePicker date;
    @FXML
    private JFXTextField tfNombres;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private JFXButton btnMenuPrincipal;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private JFXTextField tfConvencional;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXButton btnIngresar;
    @FXML
    private JFXTextField tfCedula;
    @FXML
    private JFXTextField tfCelular;
    //METODOS
    @FXML
    void ingresarCliente(ActionEvent event) {
        LocalDate localDate = date.getValue();
        String fecha = localDate.format(DateTimeFormatter.ISO_DATE);
        
        String cedula = tfCedula.getText();
        String nombre = tfNombres.getText();
        String apellido = tfApellidos.getText();
        
        if(cedula.equals("")||nombre.equals("")||apellido.equals("")||cedula.equals(null)||nombre.equals(null)||apellido.equals(null)){              //VALIDA QUE LOS CAMPOS OBLIGATORIOS ESTEN LLENOS
            AlertBox.alertBox.crearAlertBox("Information Dialog", null, "Debes ingresar los campos obligatorios");
        }
        else{
            if(!Cliente.revisarEmail(tfEmail.getText())){                           //VALIDA EL EMAIL
                AlertBox.alertBox.crearAlertBox("Warning Dialog", null,"No es un email valido");
            }
            else{
                Cliente c2 = new Cliente(cedula, "", "", "", "", "", "", "");
                if(Cliente.buscarCliente2(c2).isEmpty()){    //Si no existe ese cliente con esa cedula en la base de datos
                    Cliente cliente = new Cliente(tfCedula.getText(),fecha , tfNombres.getText(), tfApellidos.getText(), tfDireccion.getText(), tfCelular.getText(), tfConvencional.getText(), tfEmail.getText());
                    Cliente.ingresarCliente2(cliente);
                    AlertBox.alertBox.crearAlertBox("Information Dialog", null, "Cliente creado");
                    encerarTF();
                }
                else{
                    AlertBox.alertBox.crearAlertBox("Information Dialog", null, "Ya existe un cliente con esa cedula");
                }
            }
        }
    }

    @FXML
    void regresarMenuPrincipal(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MenuPrincipal/menuPrincipal.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show(); 
    }
    
    
    public void encerarTF(){
        tfCedula.setText("");
        tfNombres.setText("");
        tfApellidos.setText("");
        tfDireccion.setText("");
        tfCelular.setText("");
        tfConvencional.setText("");
        tfEmail.setText("");
        date.setValue(LocalDate.now());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIngresar.setDefaultButton(true);
        RequiredFieldValidator validatorCedula = new RequiredFieldValidator();
        RequiredFieldValidator validatorNombre = new RequiredFieldValidator();
        RequiredFieldValidator validatorApellido = new RequiredFieldValidator();
        
        tfCedula.getValidators().add(validatorCedula);
        validatorCedula.setMessage("Campo Obligatorio");
        tfCedula.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue){
                tfCedula.validate();
            }
        });
        
        tfNombres.getValidators().add(validatorNombre);
        validatorNombre.setMessage("Campo Obligatorio");
        tfNombres.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue){
                tfNombres.validate();
            }
        });
        
        tfApellidos.getValidators().add(validatorApellido);
        validatorApellido.setMessage("Campo Obligatorio");
        tfApellidos.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if(!newValue){
                tfApellidos.validate();
            }
        });
        
        date.setValue(LocalDate.now());
    }    
    
}

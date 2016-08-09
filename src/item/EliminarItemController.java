/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package item;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBconnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joelerll
 */
public class EliminarItemController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private JFXButton btnAtras;

    @FXML
    private JFXTextField TexfFielNombre;

    @FXML
    private JFXTextField textFieldPrecio;

    @FXML
    private DatePicker datePickerFecha;

    @FXML
    private JFXTextField textFieldBuscarNombre;

    @FXML
    private JFXTextField textFieldDescripcion;

    @FXML
    private DatePicker datePickerBuscarFecha;

    @FXML
    private JFXButton btnBuscar;
    
    @FXML
    private JFXTextField textFieldFecha;
    
    @FXML
    private Label labelMensaje;
    
    @FXML
    private ComboBox comboBoxNombre;
    
    public DBconnection database=new DBconnection();
    public Connection conexion;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    //atras de menu
    void atras(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("/item/itemOpciones.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show(); 
    }
    
    List <Item>  items=null;
    Item item = new Item();
    //busca item por nombre y campo
    public Item buscarItem(String campo,String nombreBuscar){
        try{
            conexion = database.conectar();
            String q ="SELECT * FROM item WHERE "+ campo +" LIKE ('%"+nombreBuscar+"%')"+"LIMIT 1";
            System.out.println(q);
            ps = conexion.prepareCall(q);
            rs = ps.executeQuery();
            rs.next();
            this.item.setId(rs.getInt(1));
            this.item.setPrecio(rs.getBigDecimal(2));
            this.item.setNombre(rs.getString(3));
            this.item.setDescripcion(rs.getString(4));
            this.item.setFecha(rs.getDate(5));
            ps.close();
            conexion.close();
            rs.close();
            System.out.println("Encontrado item\n"+item.toString());
            // limpiar combo box por cada busqueda
            if (comboBoxNombre != null){
                comboBoxNombre.getSelectionModel().clearSelection();
                comboBoxNombre.getItems().clear();
                System.out.println("dssa-----------------");
            }
        }catch(SQLException sql){
            System.out.println("Error en buscar item para eliminar");
        }
        return this.item;
    }
  
    public void buscarItemNombre(ActionEvent event){
        String campo = "nombre";
        String nombreBuscar= textFieldBuscarNombre.getCharacters().toString().toUpperCase();
        if ("".equals(nombreBuscar)){
            labelMensaje.setText("No ingreso nombre");
        }else{
            Item item= buscarItem(campo,nombreBuscar);
            setCamposEnTextField(item);
            this.items=Item.searchItem("nombre", nombreBuscar);
            comboBoxNombre.setValue(this.items.get(0));
            comboBoxNombre.getItems().addAll(this.items);
        }
    }

    //FXMLsss
    @FXML
    public void getComboBoxOpcion(ActionEvent event){
        this.item = null;
        this.item =(Item) comboBoxNombre.getValue();
        System.out.println(this.item);
        setCamposEnTextField(this.item);     
    }
    
    @FXML
    public void eliminarItem(ActionEvent event){
        Item.eliminarItemSQL(this.item);
    }

    ////Private 
    private void setCamposEnTextField(Item item)
    {
        TexfFielNombre.setText(item.getNombre());
        textFieldDescripcion.setText(item.getDescripcion());
        textFieldPrecio.setText(item.getPrecio().toString());
        textFieldFecha.setText(item.getFecha().toString());
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
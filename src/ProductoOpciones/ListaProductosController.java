/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductoOpciones;

import AlertBox.alertBox;
import Clases.ProductoVO;
import Utils.Colores;
import com.jfoenix.controls.JFXButton;
import factura.IngresarController;
import factura.ProductosCanasta;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author joelerll
 */
public class ListaProductosController implements Initializable {
    @FXML
    private TableView<ProductoVO> productos;
 
    @FXML
    private ImageView imagen;
    
    @FXML
    private JFXButton btnSalir;
 
    @FXML
    private JFXButton btnAgregar;
    
    public static List<ProductoVO> productosOB;

    private final ObservableList<ProductoVO> productosOBB = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       printTabla();
    }
    
    private void printTabla(){
        // Setear todos los productos en la tablewiew
       for (ProductoVO p : productosOB){   
            productosOBB.add(p);
       }
       
       // Verifica que celda esta escogiendo
       Callback<TableColumn, TableCell> integerCellFactory = new Callback<TableColumn, TableCell>(){
           @Override
           public TableCell call(TableColumn p){
               MyIntegerTableCell cell = new MyIntegerTableCell();
               cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
               return cell;
               
           }
        };
       Callback<TableColumn, TableCell> stringCellFactory = new Callback<TableColumn, TableCell>(){
           @Override
           public TableCell call(TableColumn p){
               MyStringTableCell cell = new MyStringTableCell();
               cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
               return cell;
               
           }
        };
       Callback<TableColumn, TableCell> bigDecimalCellFactory = new Callback<TableColumn, TableCell>(){
           @Override
           public TableCell call(TableColumn p){
               MyBigDecimalTableCell cell = new MyBigDecimalTableCell();
               cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
               return cell;
               
           }
        };
       
       // Anade celdad al tableview, no se hace directamente del fxml por error de parser del mismo
       TableColumn codigo = new TableColumn("Codigo"); 
       codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
       codigo.setCellFactory(stringCellFactory);
       
       TableColumn nombre = new TableColumn("Nombre");
       nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
       nombre.setCellFactory(stringCellFactory);
       
       TableColumn marca = new TableColumn("Marca");
       marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
       marca.setCellFactory(stringCellFactory);
       
       TableColumn precio = new TableColumn("Precio");
       precio.setCellValueFactory(new PropertyValueFactory<>("precio_venta"));
       precio.setCellFactory(bigDecimalCellFactory);
       
       TableColumn stock = new TableColumn("Stock");
       stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
       stock.setCellFactory(integerCellFactory);
       
       // Customizar celdas
        productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        codigo.setMaxWidth( 1f * Integer.MAX_VALUE * 15 );
        nombre.setMaxWidth( 1f * Integer.MAX_VALUE * 35 );
        marca.setMaxWidth( 1f * Integer.MAX_VALUE * 10 );
        precio.setMaxWidth( 1f * Integer.MAX_VALUE * 8 );
        stock.setMaxWidth( 1f * Integer.MAX_VALUE * 5 );
       
       // elimina la ultima celda por defecto
       productos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

       // Ingresa los datos en cada celda determinada
       productos.setItems(productosOBB);
       
        // Anade todas las columnas en orden
       productos.getColumns().addAll(codigo,nombre,marca,stock,precio);
    }
    
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        // do what you have to do
        IngresarController.productosCanasta.clear();
        stage.close();
    }
    
    class MyIntegerTableCell extends TableCell<ProductoVO,Integer>{
        @Override
        public void updateItem(Integer item, boolean empty){
            super.updateItem(item,empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }
        
        private String getString(){
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    class MyStringTableCell extends TableCell<ProductoVO,String>{
        @Override
        public void updateItem(String item, boolean empty){
            super.updateItem(item,empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }
        
        private String getString(){
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    class MyBigDecimalTableCell extends TableCell<ProductoVO,BigDecimal>{
        @Override
        public void updateItem(BigDecimal item, boolean empty){
            super.updateItem(item,empty);
            setText(empty ? null : getString());
            setGraphic(null);
        }
        
        private String getString(){
            return getItem() == null ? "" : getItem().toString();
        }
    }
    
    class MyEventHandler implements EventHandler<MouseEvent >{
        @Override
        public void handle(MouseEvent t){
            TableCell c = (TableCell) t.getSource();
            int index = c.getIndex();
            
            System.out.println("\n" + Colores.ANSI_GREEN + "Producto escogido");
            System.out.println(productosOB.get(index).getId());

            // Agregar un producto a carrito
            btnAgregar.setOnAction(new EventHandlerImpl(index));
            
            // Ingresar imagen y ver
            try{
                imagen.setVisible(true);
                Image im = new Image(productosOB.get(index).getImagen());
                imagen.setImage(im);
            }catch(Exception e){
                imagen.setVisible(false);
                System.out.println(Colores.ANSI_RED + "No se encontro imagen");
            }    
        }

        private class EventHandlerImpl implements EventHandler<ActionEvent> {
            
            private final int index;
            
            public EventHandlerImpl(int index) {
                this.index = index;
            }

            @Override
            public void handle(ActionEvent e) {
                ProductoVO p = new ProductoVO();
                p.setId(productosOB.get(index).getId());
                boolean b = true;
                for (ProductoVO pr : IngresarController.productosCanasta)
                {   
                    if (pr.getId().equals(p.getId()))
                        b = false;
                }
                if (b){
                    p.setNombre(productosOB.get(index).getNombre());
                    p.setMarca(productosOB.get(index).getMarca());
                    p.setStock(productosOB.get(index).getStock());
                    p.setPrecio_inicial(productosOB.get(index).getPrecio_inicial());
                    p.setPrecio_venta(productosOB.get(index).getPrecio_venta());
                    p.setImagen(productosOB.get(index).getImagen());
                    IngresarController.productosCanasta.add(p);
                    IngresarController.productosCanastaFactura.add(new ProductosCanasta(p));
                    alertBox.crearAlertBox(" ", "", "Producto Agregado a Carrito");
                }else{
                    alertBox.crearErrorBox(" ", "", "El producto ya ha sido agregado");
                }  
            }
        }
    }
    
}

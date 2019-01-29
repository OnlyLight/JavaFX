/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class OrderedItemController implements Initializable {

    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private Label itemQty;
    @FXML
    private Label subTotal;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton decreaseBtn;
    @FXML
    private JFXButton increaseBtn;
    @FXML
    private HBox editQtyHBox;
    @FXML
    private TextField qtyTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO\
        qtyTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                qtyTxt.setText(oldValue);
            }
        });
    }    

    @FXML
    private void mouseExitHandle(MouseEvent event) {
    }

    @FXML
    private void mouseEnterHandle(MouseEvent event) {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.BounceIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class MenuItemController implements Initializable {

    @FXML
    private JFXButton increaseQty;
    @FXML
    private JFXTextField itemQuantity;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        itemQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                itemQuantity.setText(oldValue);
            }
        });
    }    

    @FXML
    private void addQty(MouseEvent event) {
         itemQuantity.setText((String.valueOf((Integer.parseInt(itemQuantity.getText()) + 1))));
         new BounceIn(itemQuantity).setSpeed(2.0).setResetOnFinished(true).play();
    }
    
}
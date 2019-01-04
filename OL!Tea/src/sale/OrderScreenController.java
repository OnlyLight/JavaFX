/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.FadeInUp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import static sale.FXMLDocumentController.sdt;
import tqduy.bean.CusMember;
import tqduy.bean.LoaiMon;
import tqduy.bean.Member;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_LoaiMon;
import tqduy.connect.DBUtils_Member;
import tqduy.connect.DBUtils_Mon;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class OrderScreenController implements Initializable {

    @FXML
    private VBox orderScreen;
    @FXML
    private ScrollPane scrollMenu;
    @FXML
    private HBox listMenuType;
    @FXML
    private HBox mainHBox;
    @FXML
    private VBox listView;
    @FXML
    private Label menuItemName;
    @FXML
    private FlowPane menuList;
    @FXML
    private VBox CheckoutSection;
    @FXML
    private JFXTextField txtSdtCheck;
    @FXML
    private Label txtPay;
    @FXML
    private Label txtDiscount;
    @FXML
    private Label txtMoneyTotal;
    @FXML
    private JFXButton btnCheck;
    @FXML
    private JFXButton btnPay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         CheckoutSection.setEffect(new DropShadow(10, -3, 3, Color.rgb(34, 40, 49, 0.3)));
         showAcdMenu();
    }    
     
    private void showAcdMenu() {
//         Dynamic
//        acdMenu.getPanes().clear();
//        ArrayList<TitledPane> titles = new ArrayList<>();
        ArrayList<LoaiMon> listLoaiMon = DBUtils_LoaiMon.getList();
        listMenuType.getChildren().clear();
        for(int i = 0; i < listLoaiMon.size(); i++) {
            try {
                JFXButton btn = (JFXButton) FXMLLoader.load(getClass().getResource("menuType.fxml"));
                btn.setUserData(i);
                btn.setText(listLoaiMon.get(i).getLoaiMon());
                String url = "-fx-background-image: url('../images/bg-"+ i +".jpg');";
                System.out.println(url);
                btn.setStyle(url);
                listMenuType.getChildren().add(btn);
                btn.setOnAction((event) -> {
                   changeMenuItemList(btn);
                });
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        changeMenuItemList((JFXButton)listMenuType.getChildren().get(0));
//        acdMenu.getPanes().addAll(titles);
    }
    
    private void changeMenuItemList(JFXButton btn) {
        if (menuList.getUserData() != btn.getUserData()) {
            menuList.setUserData(btn.getUserData());
            menuList.getChildren().clear();
            ArrayList<LoaiMon> listMenu = DBUtils_LoaiMon.getList();
            menuItemName.setText(listMenu.get(Integer.parseInt(btn.getUserData().toString())).getLoaiMon());
            new FadeInUp(menuItemName).setSpeed(2.0).play();
            for (Mon m : DBUtils_Mon.getMon(Integer.parseInt(btn.getUserData().toString()) + 1)) {
                if(m.isIsActive()) {
                    try {
                        GridPane item = FXMLLoader.load(getClass().getResource("menuItem.fxml"));
                        ((Label) item.lookup("#menuItemName")).setText(m.getTenMon().toUpperCase());
                        ((Label) item.lookup("#menuItemPrice")).setText(((Integer)m.getDonGia()).toString() + " VND");
                        menuList.getChildren().add(item);
                        new FadeInUp(menuList.getChildren().get(menuList.getChildren().size() - 1)).setSpeed(2.0).play();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                }
            }
        }   
    }
    
}

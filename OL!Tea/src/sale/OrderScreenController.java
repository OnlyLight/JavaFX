/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.FadeInUp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static dialog.FXMLDialogController.monOrder;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
import tqduy.connect.DBUtils_MonOrder;

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
    private FlowPane listMenuType;
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
    @FXML
    private VBox orderedList;
    @FXML
    private ScrollPane orderedScroll;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         CheckoutSection.setEffect(new DropShadow(10, -3, 3, Color.rgb(34, 40, 49, 0.3)));
         DBUtils_MonOrder.deleteAll();
         showAcdMenu();
         getOrderedList();
         orderedList.heightProperty().addListener(observable -> {
             if (orderedList.getChildren().size() != 0) {
                orderedScroll.setVvalue(1.0);   
             };
         });
    }    
     
    private void getOrderedList() {
        orderedList.getChildren().clear();
        ArrayList<MonOrder> list = DBUtils_MonOrder.getList();
        int total = 0;
        for (MonOrder monOrder1 : list) {
            try {
                VBox itemOrdered = FXMLLoader.load(getClass().getResource("orderedItem.fxml"));
                ((Label) itemOrdered.lookup("#itemName")).setText(monOrder1.getTenMon().toUpperCase());
                ((Label) itemOrdered.lookup("#itemPrice")).setText(String.valueOf(monOrder1.getDonGia()));
                ((Label) itemOrdered.lookup("#itemQty")).setText("x" + monOrder1.getSoLuong());
                ((Label) itemOrdered.lookup("#subTotal")).setText(String.valueOf(monOrder1.getSoLuong()*monOrder1.getDonGia()));
                itemOrdered.setUserData(monOrder1);
                orderedList.getChildren().add(itemOrdered);
                total += monOrder1.getSoLuong()*monOrder1.getDonGia();
            } catch (IOException ex) {
                Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        String price = String.format(Locale.US, "%,d", total).replace(",", ".");
        txtMoneyTotal.setText(price);
    }
    
    private void showAcdMenu() {
//         Dynamic
//        acdMenu.getPanes().clear();
//        ArrayList<TitledPane> titles = new ArrayList<>();
        ArrayList<LoaiMon> listLoaiMon = DBUtils_LoaiMon.getList();
        listMenuType.getChildren().clear();
        for(int i = 0; i < listLoaiMon.size(); i++) {
            try {
                StackPane btnWrapper = FXMLLoader.load(getClass().getResource("menuType.fxml"));
                JFXButton btn = (JFXButton) btnWrapper.lookup("#btnMenuType");
                btn.setUserData(i);
                btn.setText(listLoaiMon.get(i).getLoaiMon().toUpperCase());
                int mainColor = 40 + i*15;
                HBox shadow = (HBox) btnWrapper.lookup("#shadowHbox");
                String mainHsl = "hsb("+ mainColor +", 70%, 80%)";
                String secondHsl = "hsb("+ mainColor +", 70%, 87%)";
                shadow.setStyle("-fx-background-color:" + mainHsl);
                btn.setStyle("-fx-background-color: linear-gradient(to left, "+ mainHsl +", "+ secondHsl +");");
                btn.setOnAction((event) -> {
                   changeMenuItemList(btn);
                });
                listMenuType.getChildren().add(btnWrapper);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        changeMenuItemList((JFXButton)listMenuType.getChildren().get(0).lookup("#btnMenuType"));
//        acdMenu.getPanes().addAll(titles);
    }
    
    private void changeMenuItemList(JFXButton btn) {
        if (menuList.getUserData() != btn.getUserData()) {
            menuList.setUserData(btn.getUserData());
            menuList.getChildren().clear();
            ArrayList<LoaiMon> listMenu = DBUtils_LoaiMon.getList();
            menuItemName.setText(listMenu.get(Integer.parseInt(btn.getUserData().toString())).getLoaiMon().toUpperCase());
            new FadeInUp(menuItemName).setSpeed(2.0).play();
            for (Mon m : DBUtils_Mon.getMon(Integer.parseInt(btn.getUserData().toString()) + 1)) {
                if(m.isIsActive()) {
                    try {
                        GridPane item = FXMLLoader.load(getClass().getResource("menuItem.fxml"));
                        ((Label) item.lookup("#menuItemName")).setText(m.getTenMon().toUpperCase());
                        String price = String.format(Locale.US, "%,d", m.getDonGia()).replace(",", ".");
                        ((Label) item.lookup("#menuItemPrice")).setText(price + " VND");
                        int mainColor = 40 + Integer.parseInt(btn.getUserData().toString())*15;
                        String mainHsl = "hsb("+ mainColor +", 70%, 80%)";
                        String secondHsl = "hsb("+ mainColor +", 70%, 87%)";
                        item.setStyle("-fx-background-color: "+ secondHsl);
                        item.setEffect(new DropShadow(10, 3, 3, Color.hsb(Float.intBitsToFloat(mainColor), 0.88, 0.75, 0.0)));
                        JFXButton addBtn = (JFXButton) item.lookup("#addToOrder");
                        addBtn.setOnAction((event) -> {
                            JFXTextField itemQty = (JFXTextField) item.lookup("#itemQuantity");
                            if (getQtyItemOrdered(m.getIdMon()) == -1) {
                                DBUtils_MonOrder.insert(m.getIdMon(), Integer.parseInt(itemQty.getText()));
                            } else {
                                DBUtils_MonOrder.update(m.getIdMon(), getQtyItemOrdered(m.getIdMon()) + Integer.parseInt(itemQty.getText()));
                            };
                            getOrderedList();
                        });
                        menuList.getChildren().add(item);
                        new FadeInUp(menuList.getChildren().get(menuList.getChildren().size() - 1)).setSpeed(2.0).play();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                }
            }
        }   
    }
    
    private int getQtyItemOrdered(int id) {
        ArrayList<MonOrder> list = DBUtils_MonOrder.getList();
        for (MonOrder monOrder1 : list) {
            if (monOrder1.getId() == id) {
                return monOrder1.getSoLuong();
            }
        };
        return -1;
    }
    
}

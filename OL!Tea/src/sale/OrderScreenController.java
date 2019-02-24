/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeft;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutDown;
import animatefx.animation.Pulse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import login.FXMLLoginController;
import tqduy.bean.CusMember;
import tqduy.bean.LoaiMon;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.connect.DBUtils_Bill;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_DK;
import tqduy.connect.DBUtils_LoaiMon;
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
    @FXML
    private StackPane mainOrderScreenStackPane;
    @FXML
    private VBox addMemberForm;
    @FXML
    private TextField txtTenCus;
    @FXML
    private TextField txtSDT;
    @FXML
    private ComboBox<?> cbLoaiMember;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnCloseAddMenu;
    public static String sdt = "";
    private CusMember customer = new CusMember();
    private int idMem = 0;
    @FXML
    private JFXDatePicker dpNgayDK;
    public static StackPane mainOrderScreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainOrderScreen = mainOrderScreenStackPane;
        CheckoutSection.setEffect(new DropShadow(10, -3, 3, Color.rgb(34, 40, 49, 0.3)));
        try {
            //         DBUtils_MonOrder.deleteAll();
            showAcdMenu();
        } catch (SQLException ex) {
            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            getOrderedList();
        } catch (SQLException ex) {
            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        orderedList.heightProperty().addListener(observable -> {
            if (orderedList.getChildren().size() != 0) {
                orderedScroll.setVvalue(1.0);
            };
        });
        txtSdtCheck.setText(sdt);
        txtSdtCheck.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                sdt = "";
                clearDiscountInfo();
            }
            if (newValue.matches("\\d*")) {
                try {
                    sdt = newValue;
                } catch (Exception e) {

                }
            } else {
                txtSdtCheck.setText(oldValue);
            }
        });
        btnCheck.setOnAction((event) -> {
            new BounceIn(btnCheck).setSpeed(2.0).play();
            ArrayList<CusMember> arrCus = null;
            try {
                arrCus = DBUtils_CusMember.getListForCheck();
            } catch (SQLException ex) {
                Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (check(arrCus, sdt)) {
                idMem = customer.getIdMember();
                System.out.println(customer);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                String content = "Telephone: " + customer.getSdt() + "\nMember Name: " + customer.getTenCus()
                        + "\nMember Type: " + customer.getLoaiMember() + "\nDiscount: " + customer.getDiscount() + "%"
                        + "\nCreated date: " + format.format(customer.getNgayLap()) + "\nCreated by: " + customer.getTenNhanVien();
                try {
                    creatDialog(content, "success");
                    txtDiscount.setText(String.valueOf(customer.getDiscount()) + "%");
                    txtDiscount.setUserData(customer.getDiscount());
                    try {
                        getOrderedList();
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Node screen = null;
                try {
                    screen = FXMLLoader.load(getClass().getResource("/signIn/FXMLSignIn.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                creatDialog(btnCheck, (Region) screen, mainOrderScreenStackPane);
                clearDiscountInfo();
                JFXDialog dialog = (JFXDialog) mainOrderScreen.getChildren().get(2);
                dialog.setOnDialogClosed((eventt) -> {
                    txtSdtCheck.setText(sdt);
                });
            }
        });
        btnPay.setOnAction((event) -> {
            new BounceIn(btnPay).setSpeed(2.0).play();
            try {
                creatDialog("Do you want to checkout ?", "warning");
                JFXButton btn = (JFXButton) mainOrderScreenStackPane.lookup("#btnExecute");
                btn.setOnAction((t) -> {
                    ((JFXDialog) mainOrderScreenStackPane.getChildren().get(2)).close();
                    int total = ((Integer) txtMoneyTotal.getUserData()).intValue();
                    if (total != 0) {
                        System.out.println("IDNV: " + FXMLLoginController.nvLogin.getIdNV() + " - idCUs: " + customer.getIdCus());
                        try {
                            DBUtils_Bill.insert(FXMLLoginController.nvLogin.getIdNV(), total, LocalDate.now());
                        } catch (SQLException ex) {
                            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        DBUtils_MonOrder.deleteAll();
                        clearDiscountInfo();
                        try {
                            creatDialog("Checkout successful !", "success");
                        } catch (IOException ex) {
                            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        try {
                            creatDialog("Nothing to checkout !", "danger");
                        } catch (IOException ex) {
                            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void clearDiscountInfo() {
        sdt = "";
        txtSdtCheck.setText("");
        txtDiscount.setUserData(null);
        txtDiscount.setText("0");
        txtPay.setText("0");
        try {
            getOrderedList();
        } catch (SQLException ex) {
            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeAddMemberForm() {
        JFXDialog dialog = (JFXDialog) mainOrderScreen.getChildren().get(2);
        dialog.close();
    }

    private void creatDialog(JFXButton btn, Region dialogContent, StackPane toStackPane) {
        JFXDialog dialog = new JFXDialog(toStackPane, dialogContent, JFXDialog.DialogTransition.NONE);
        dialog.setOverlayClose(false);
        dialog.show();
        JFXButton btnClose = (JFXButton) dialog.lookup("#btnCloseAddMenu");
        btnClose.setOnAction((eventt) -> {
            dialog.close();
        });
    }

    private boolean check(ArrayList<CusMember> arrCus, String sdtCheck) {
        for (CusMember arrCu : arrCus) {
            if (arrCu.getSdt().equals(sdtCheck)) {
                customer = arrCu;
                System.out.println(arrCu.getSdt() + " - " + sdtCheck);
                return true;
            }
        }
        return false;
    }

    private void getOrderedList() throws SQLException {
        orderedList.getChildren().clear();
        ArrayList<MonOrder> list = DBUtils_MonOrder.getList();
        int total = 0;
        for (MonOrder monOrder1 : list) {
            try {
                VBox itemOrdered = FXMLLoader.load(getClass().getResource("orderedItem.fxml"));
                ((Label) itemOrdered.lookup("#itemName")).setText(monOrder1.getTenMon().toUpperCase());
                ((Label) itemOrdered.lookup("#itemPrice")).setText(String.format(Locale.US, "%,d", monOrder1.getDonGia()).replace(",", "."));
                ((Label) itemOrdered.lookup("#itemQty")).setText("x" + monOrder1.getSoLuong());
                ((Label) itemOrdered.lookup("#subTotal")).setText(String.format(Locale.US, "%,d", monOrder1.getSoLuong() * monOrder1.getDonGia()).replace(",", "."));
                JFXButton deleteBtn = (JFXButton) itemOrdered.lookup("#deleteBtn");
                JFXButton decreaseBtn = (JFXButton) itemOrdered.lookup("#decreaseBtn");
                JFXButton increaseBtn = (JFXButton) itemOrdered.lookup("#increaseBtn");
                HBox editQtyHBox = (HBox) itemOrdered.lookup("#editQtyHBox");
                TextField qtyTxt = (TextField) itemOrdered.lookup("#qtyTxt");
                qtyTxt.setText(String.valueOf(monOrder1.getSoLuong()));
                editQtyHBox.setVisible(false);
                deleteBtn.setVisible(false);
                itemOrdered.setOnMouseEntered((event) -> {
                    editQtyHBox.setVisible(true);
                    qtyTxt.requestFocus();
                    deleteBtn.setVisible(true);
                });
                itemOrdered.setOnMouseExited((event) -> {
                    editQtyHBox.setVisible(false);
                    deleteBtn.setVisible(false);
                    int qtyEdit = Integer.parseInt(qtyTxt.getText());
                    if (qtyEdit != monOrder1.getSoLuong()) {
                        DBUtils_MonOrder.update(monOrder1.getId(), qtyEdit);
                        try {
                            getOrderedList();
                        } catch (SQLException ex) {
                            Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                deleteBtn.setOnAction((event) -> {
                    DBUtils_MonOrder.delete(monOrder1.getId());
                    try {
                        getOrderedList();
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                decreaseBtn.setOnAction((event) -> {
                    DBUtils_MonOrder.update(monOrder1.getId(), monOrder1.getSoLuong() - 1);
                    try {
                        getOrderedList();
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                increaseBtn.setOnAction((event) -> {
                    DBUtils_MonOrder.update(monOrder1.getId(), monOrder1.getSoLuong() + 1);
                    try {
                        getOrderedList();
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                itemOrdered.setUserData(monOrder1);
                orderedList.getChildren().add(itemOrdered);
                total += monOrder1.getSoLuong() * monOrder1.getDonGia();
            } catch (IOException ex) {
                Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        String price = String.format(Locale.US, "%,d", total).replace(",", ".");
        txtPay.setText(price);
        if (txtDiscount.getUserData() != null) {
            int discount = (int) txtDiscount.getUserData();
            total = total - total * discount / 100;
            price = String.format(Locale.US, "%,d", total).replace(",", ".");
        }
        txtMoneyTotal.setText(price);
        txtMoneyTotal.setUserData(total);
        new BounceIn(txtMoneyTotal).setSpeed(1.0).play();
    }

    private int getPosItemInOrderList(int id) {
        int pos = -1;
        for (int i = 0; i < orderedList.getChildren().size(); i++) {
            System.out.println("i: " + i);
            MonOrder item = (MonOrder) orderedList.getChildren().get(i).getUserData();
            System.out.println("item id: " + item.getId());
            System.out.println("id: " + id);
            if (item.getId() == id) {
                pos = i;
                break;
            }
        };
        return pos;
    }

    private void showAcdMenu() throws SQLException {
//         Dynamic
//        acdMenu.getPanes().clear();
//        ArrayList<TitledPane> titles = new ArrayList<>();
        ArrayList<LoaiMon> listLoaiMon = new ArrayList<>();
        ArrayList<LoaiMon> listLM = DBUtils_LoaiMon.getList(false);
        for (LoaiMon lm : listLM) {
            if (lm.isIsActive()) {
                listLoaiMon.add(lm);
            }
        }
        System.out.println("ListCheck: " + listLoaiMon);

//        ArrayList<LoaiMon> listLoaiMon = DBUtils_LoaiMon.getList();
        listMenuType.getChildren().clear();
        for (int i = 0; i < listLoaiMon.size(); i++) {
            try {
                StackPane btnWrapper = FXMLLoader.load(getClass().getResource("menuType.fxml"));
                JFXButton btn = (JFXButton) btnWrapper.lookup("#btnMenuType");
                int id = listLoaiMon.get(i).getId();
                btn.setUserData(id);
                btn.setText(listLoaiMon.get(i).getLoaiMon().toUpperCase());
                int mainColor = 40 + id * 15;
                HBox shadow = (HBox) btnWrapper.lookup("#shadowHbox");
                String mainHsl = "hsb(" + mainColor + ", 70%, 80%)";
                String secondHsl = "hsb(" + mainColor + ", 70%, 87%)";
                shadow.setStyle("-fx-background-color:" + mainHsl);
                btn.setStyle("-fx-background-color: linear-gradient(to left, " + mainHsl + ", " + secondHsl + ");");
                btn.setOnAction((event) -> {
                    new BounceIn(btn).setSpeed(2.0).play();
                    try {
                        changeMenuItemList(btn);
                    } catch (SQLException ex) {
                        Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                listMenuType.getChildren().add(btnWrapper);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        changeMenuItemList((JFXButton) listMenuType.getChildren().get(0).lookup("#btnMenuType"));
//        acdMenu.getPanes().addAll(titles);
    }

    private void changeMenuItemList(JFXButton btn) throws SQLException {
        if (menuList.getUserData() != btn.getUserData()) {
            menuList.setUserData(btn.getUserData());
            menuList.getChildren().clear();
            ArrayList<LoaiMon> listMenu = DBUtils_LoaiMon.getList(false);
            int index = -1;
            for (int i = 0; i < listMenu.size(); i++) {
                LoaiMon get = listMenu.get(i);
                if (get.getId() == Integer.parseInt(btn.getUserData().toString())) {
                    index = i;
                    break;
                }
            }
            menuItemName.setText(listMenu.get(index).getLoaiMon());
            new FadeInUp(menuItemName).setSpeed(2.0).play();
            for (Mon m : DBUtils_Mon.getMon(listMenu.get(index).getId())) {
                if (m.isIsActive()) {
                    try {
                        GridPane item = FXMLLoader.load(getClass().getResource("menuItem.fxml"));
                        ((Label) item.lookup("#menuItemName")).setText(m.getTenMon().toUpperCase());
                        String price = String.format(Locale.US, "%,d", m.getDonGia()).replace(",", ".");
                        ((Label) item.lookup("#menuItemPrice")).setText(price + " VND");
                        int mainColor = 40 + Integer.parseInt(btn.getUserData().toString()) * 15;
                        String mainHsl = "hsb(" + mainColor + ", 70%, 80%)";
                        String secondHsl = "hsb(" + mainColor + ", 70%, 87%)";
                        item.setStyle("-fx-background-color: " + secondHsl);
                        item.setEffect(new DropShadow(10, 3, 3, Color.hsb(Float.intBitsToFloat(mainColor), 0.88, 0.75, 0.0)));
                        JFXButton addBtn = (JFXButton) item.lookup("#addToOrder");
                        addBtn.setOnAction((event) -> {
                            try {
                                new BounceIn(addBtn).setSpeed(2.0).play();
                                JFXTextField itemQty = (JFXTextField) item.lookup("#itemQuantity");
                                int getQtyitem = getQtyItemOrdered(m.getIdMon());
                                if (getQtyitem == -1) {
                                    DBUtils_MonOrder.insert(m.getIdMon(), Integer.parseInt(itemQty.getText()));
                                } else {
                                    DBUtils_MonOrder.update(m.getIdMon(), getQtyitem + Integer.parseInt(itemQty.getText()));
                                };
                                try {
                                    getOrderedList();
                                } catch (SQLException ex) {
                                    Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    ArrayList<LoaiMon> list = DBUtils_LoaiMon.getList(false);
                                } catch (SQLException ex) {
                                    Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (getQtyitem == -1) {
                                    new FadeInLeft(orderedList.getChildren().get(orderedList.getChildren().size() - 1)).setSpeed(2.0).setResetOnFinished(true).play();
                                } else {
                                    System.out.println("size: " + orderedList.getChildren().size());
                                    new Pulse(orderedList.getChildren().get(getPosItemInOrderList(m.getIdMon()))).setSpeed(3.0).setResetOnFinished(true).play();
                                };
                                itemQty.setText("1");
                            } catch (SQLException ex) {
                                Logger.getLogger(OrderScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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

    private int getQtyItemOrdered(int id) throws SQLException {
        ArrayList<MonOrder> list = DBUtils_MonOrder.getList();
        for (MonOrder monOrder1 : list) {
            if (monOrder1.getId() == id) {
                return monOrder1.getSoLuong();
            }
        };
        return -1;
    }

    private void creatDialog(String text, String type) throws IOException {
        String url;
        switch (type) {
            case "success":
                url = "/dialog/popupSuccess.fxml";
                break;
            case "danger":
                url = "/dialog/popupDanger.fxml";
                break;
            default:
                url = "/dialog/popupWarning.fxml";
        }
        Region dialogContent = FXMLLoader.load(getClass().getResource(url));
        JFXDialog dialog = new JFXDialog(mainOrderScreenStackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
        dialog.setOverlayClose(false);
        JFXButton btnClose = (JFXButton) dialog.lookup("#btnClose");
        Label txtContent = (Label) dialog.lookup("#txtContent");
        txtContent.setText(text);
        btnClose.setOnAction((eventt) -> {
            new BounceIn(btnClose).setSpeed(2.0).play();
            dialog.close();
        });
        btnClose.defaultButtonProperty().bind(btnClose.focusedProperty());
        dialog.show();
    }

    public static void setSDT(String numb) {
        sdt = numb;
    }
}

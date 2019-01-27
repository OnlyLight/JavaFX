/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInRight;
import animatefx.animation.FadeOutLeft;
import com.jfoenix.controls.JFXButton;
import insidefx.undecorator.UndecoratorScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import signIn.FXMLSignInController;
import login.FXMLLoginController;
import login.Login;
import tqduy.bean.CusMember;
import tqduy.bean.Member;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_MonOrder;

/**
 *
 * @author QuangDuy
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private JFXButton mnThucDon, mnNhanVien, mnTonKho, mnHoaDon, mnLogout, mnCus;
    private TableColumn<MonOrder, String> tbColumnTenMon;
    private TableColumn<MonOrder, Integer> tbColumnDonGia;
    private TableColumn<MonOrder, String> tbColumnLoaiMon;
    private TableColumn<MonOrder, Integer> tbColumnAmount;

    public static Mon monInfo;
    public static String sdt = "";
    private CusMember customer = new CusMember();
    private NhanVien nvLogin = FXMLLoginController.nvLogin;
    private Member m = FXMLSignInController.m;
    private double memberDiscount = 0;
    private int idMem = 0;
    @FXML
    private VBox sideBar;
    @FXML
    private StackPane bigStackPane;
    @FXML
    private JFXButton orderBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        permission();
        openScreen("orderScreen.fxml", null);
        sideBar.setEffect(new DropShadow(10, 3, 0, Color.rgb(34, 40, 49, 0.7)));

//        for (int i = 0; i < 10; i++) {
//            try {
//                GridPane item = FXMLLoader.load(getClass().getResource("menuItem.fxml"));
//                menuList.getChildren().add(item);
//                JFXButton btn = (JFXButton) item.lookup("#increaseQty");
//                btn.setOnAction((event) -> {
//                    
//                });
//                System.out.println("item" + ((JFXTextField)item.lookup("#itemQuantity")).getText());
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        bigStackPane.widthProperty().addListener((observable, oldValue, newValue) -> {
//            bigStackPane.setClip(new Rectangle(bigStackPane.getWidth(), bigStackPane.getHeight()));
//        });
//        bigStackPane.heightProperty().addListener((observable, oldValue, newValue) -> {
//            bigStackPane.setClip(new Rectangle(bigStackPane.getWidth(), bigStackPane.getHeight()));
//        });
//        tbInfomation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        showTable();
//        setOnKeyPress();
//        payBill();
        setEventClick();

    }

    private void permission() {
        // Phân Quyền cho user đăng nhập
        menuAdmin(true);
        if (nvLogin.getIdRoleName() == 1) {
            // Admin
            menuAdmin(false);
        }
    }

    private void menuAdmin(boolean status) {
        mnNhanVien.setDisable(status);
        mnTonKho.setDisable(status);
        mnHoaDon.setDisable(status);
        mnCus.setDisable(status);
        mnThucDon.setDisable(status);
    }

    // Check xem user đã đăng ký thành viên chưa
    // Table
    // Định nghĩa cho table
    private void createTitle() {
        tbColumnTenMon.setText("Name");
        tbColumnDonGia.setText("Price");
        tbColumnLoaiMon.setText("Type");
        tbColumnAmount.setText("Quantity");

        tbColumnTenMon.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
        tbColumnDonGia.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbColumnLoaiMon.setCellValueFactory(new PropertyValueFactory<>("loaiMon"));
        tbColumnAmount.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbColumnAmount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tbColumnAmount.setOnEditStart((event) -> {
            try {
                int newValue = event.getNewValue();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        tbColumnAmount.setOnEditCommit((event) -> {
            try {
                TablePosition<MonOrder, Integer> pos = event.getTablePosition();
                int row = pos.getRow();
                System.out.println("Row: " + row);

                int idMon = getListMon().get(row).getId();

                int newValue = 1;
                try {
                    newValue = event.getNewValue();
                } catch (Exception e) {
                    newValue = event.getOldValue();
                    System.out.println(e.getMessage());
                }

                if (newValue > 0) {
                    System.out.println("IDROW: " + idMon);
                    System.out.println("New: " + newValue);

                    DBUtils_MonOrder.update(idMon, newValue);

                } else {
//                tbInfomation.refresh();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    // Hiển thị thông tin cho table
//    public void showTable() {
//        tbInfomation.setEditable(true);
//        
//        createTitle();
//        
//        ObservableList<MonOrder> list = getListMon();
//        if(!list.isEmpty()) {
//            tbInfomation.getColumns().clear();
//            System.out.println("List: " + list.get(0).getId());
//            tbInfomation.setItems(list);
//
//            System.out.println(tbInfomation.getItems().get(0).getTenMon());
//
//            tbInfomation.getColumns().addAll(tbColumnTenMon, tbColumnDonGia, tbColumnLoaiMon, tbColumnAmount);
//            System.out.println("showTable");
//        }
//    }
    private ObservableList<MonOrder> getListMon() throws SQLException {
        ObservableList<MonOrder> list = FXCollections.observableArrayList();
        list.addAll(DBUtils_MonOrder.getList());
        return list;
    }

//    private void setOnKeyPress() {
//        tbInfomation.setOnKeyPressed((event) -> {
//            if(event.getCode() == KeyCode.DELETE) {
//                deleteRowTable();
//            }
//        });
//    }
//    private void deleteRowTable() {
//        ObservableList<MonOrder> list = tbInfomation.getSelectionModel().getSelectedItems();
//        ArrayList<MonOrder> rows = new ArrayList<>(list);
//        rows.forEach((row) -> {
//            System.out.println("row.getIdMon(): " + row.getId());
//            DBUtils_MonOrder.delete(row.getId());
//        });
//        tbInfomation.getColumns().clear();
//        showTable();
//        showPay();
//    }
    // End Table
    // Pay Bill
//    private void payBill() {
//        btnPay.setOnAction((event) -> {
//            System.out.println("IDMember: "+idMem+" - IDNV: " + nvLogin.getIdNV() + " - " + nvLogin.getUserName() + " - " + txtPay.getText());
//            
//            // show alert thanh toán
//            double pay = showPay();
//            if(pay <= 0) {
//                createAlert("Chưa chọn món !!");
//            } else {
//                Optional<ButtonType> result = createAlert("Thanh toán: " + pay);
//
//                if(result.get() == ButtonType.OK) {
//                    clearAndPostData(pay);
//                }
//            }
//        });
//    }
//    private void clearAndPostData(double pay) {
//        // Insert Data for Table Bill
//        if(customer.getIdCus() > 0) {
//            System.out.println("IDNV: " + nvLogin.getIdNV() + " - idCUs: " + customer.getIdCus());
//            DBUtils_DK.insert(nvLogin.getIdNV(), customer.getIdCus());
//        }
//
//        System.out.println("IDNV: " + nvLogin.getIdNV() + " - pay: " + pay + " - " + LocalDate.now());
//        DBUtils_Bill.insert(nvLogin.getIdNV(), (int)pay, LocalDate.now());
//        
//        // Clear
//        DBUtils_MonOrder.deleteAll();
////        tbInfomation.getItems().clear();
//        showPay();
//        memberDiscount = 0;
//        txtDiscount.setText(String.valueOf(memberDiscount));
//        System.out.println("Clear");
//    }
    private Optional<ButtonType> createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        System.out.println(result.get().getText());
        return result;
    }

    // End Pay Bill
    private void openScreen(String url, Node node) {
        Node screen = node;
        if (screen == null) {
            try {
                screen = FXMLLoader.load(getClass().getResource(url));
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ObservableList<Node> listNode = bigStackPane.getChildren();
        if (listNode.size() == 1 && listNode.get(0).getUserData() == url) {
            return;
        };
        if (listNode.size() > 0) {
            listNode.remove(0);
        }
        screen.setUserData(url);
        listNode.add(screen);
        new FadeInRight(listNode.get(0)).setSpeed(3.0).play();

//            if (listNode.size() == 2) {
//               if (listNode.get(1).getUserData() == url) {
//                   return;
//               }
//               listNode.remove(1);
//            }
//            screen.setUserData(url);
//            listNode.add(screen);
//            mainStackPane.toFront();
//            FadeInLeft fadeInDown = new FadeInLeft();
//            fadeInDown.setNode(mainStackPane);
//            fadeInDown.setSpeed(3.0);
//            fadeInDown.play();
    }

    // Menu
    private void setEventClick() {
//        mnClose.setText("Exit");
//        mnClose.setOnAction((event) -> {
//            DBUtils_MonOrder.deleteAll();
//            if(DBUtils_LoaiMon.conn() != null) {
//                try {
//                    DBUtils_LoaiMon.conn().close();
//                } catch (SQLException ex) {
//                    System.out.println(ex.getMessage());
//                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            System.exit(0);
//        });
        orderBtn.setOnAction((event) -> {
            openScreen("orderScreen.fxml", null);
        });

        mnTonKho.setOnAction((event) -> {
//            try {
//                showDialog("/warehouse/FXMLWareHouse.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            openScreen("/warehouse/FXMLWareHouse.fxml", null);
        });

        mnThucDon.setOnAction((event) -> {
//            try {
//                showDialogMenu("/managerMenu/FXMLManagerMenu.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            closeStage(btnPay);
            openScreen("/managerMenu/FXMLManagerMenu.fxml", null);
        });

        mnNhanVien.setOnAction((event) -> {
//            try {
//                showDialog("/managerNV/FXMLNhanVien.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            openScreen("/managerNV/FXMLNhanVien.fxml", null);
        });

        mnCus.setOnAction((event) -> {
//            try {
//                showDialog("/managerCus/FXMLCustomer_1.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            openScreen("/managerCus/FXMLCustomer_1.fxml", null);
        });

        mnLogout.setOnAction((event) -> {
            nvLogin = null;
            DBUtils_MonOrder.deleteAll();
//            try {
//                showDialog("/login/FXMLLogin_1.fxml", StageStyle.UNDECORATED, Modality.NONE);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            closeStage(btnPay);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/login/FXMLLogin_1.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage stage = Login.getStage();
            stage.close();
            UndecoratorScene scene = new UndecoratorScene(stage, (Region) root);
            stage.setScene(scene);
            stage.show();
            Window win = scene.getWindow();
            win.setWidth(1092);
            win.setHeight(700);
            stage.centerOnScreen();
            new FadeIn(root).play();
        });

        mnHoaDon.setOnAction((event) -> {
//            try {
//                showDialog("/bill/FXMLBill.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            openScreen("/bill/FXMLBill.fxml", null);
        });

//        mnAbout.setOnAction((event) -> {
//            try {
//                showDialog("/about/FXMLAbout.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
//            } catch (IOException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
    }

    // End Menu
    // Accordion
    private void eventShow(Button btn, Mon mon) {
        btn.setOnAction((event) -> {
            System.out.println("IdMon: " + mon.getIdMon() + " - tenMon: " + mon.getTenMon() + " - Don gia: " + mon.getDonGia() + " - IdLoaiMon: " + mon.getIdLoaiMon());

            monInfo = mon;

            try {
                showDialogMenu("/dialog/FXMLDialog.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

            closeStage(btn);
        });
    }

    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        System.out.println("Close");
    }

    // End Accordion
    private void showDialogMenu(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);

        Stage stage = new Stage(style);
        stage.initModality(modal);

        stage.getIcons().add(new Image("/images/download.jpg"));

        stage.setOnCloseRequest((event) -> {
            try {
                loadMainDialog("/sale/FXMLDocument.fxml", StageStyle.DECORATED, Modality.NONE);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        stage.setTitle("OL! Tea");

        stage.setScene(scene);
        stage.show();
    }

    private void loadMainDialog(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);

        Stage stage = new Stage(style);
        stage.initModality(modal);

        stage.getIcons().add(new Image("/images/download.jpg"));

        stage.setOnCloseRequest((event) -> {
            System.out.println("Delete MonOrder");
            DBUtils_MonOrder.deleteAll();
        });

        stage.setTitle("OL! Tea");

        stage.setScene(scene);
        stage.show();
    }

    private void showDialog(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);

        Stage stage = new Stage(style);
        stage.initModality(modal);
        stage.getIcons().add(new Image("/images/download.jpg"));

        stage.setOnCloseRequest((event) -> {
            System.out.println("Hello Button Close");
        });

        stage.setTitle("OL! Tea");

        stage.setScene(scene);
        stage.show();
    }

    private void fadeOut(MouseEvent event) {
        Node parent = ((Node) event.getSource()).getParent();
        FadeOutLeft fadeOut = new FadeOutLeft();
        fadeOut.setNode(parent);
        fadeOut.setResetOnFinished(true);
        fadeOut.setSpeed(3.0);
        fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.toBack();
            }
        });
        fadeOut.play();
    }
}

/*
        acdMenu = new Accordion();
        ArrayList<TitledPane> titles = new ArrayList<>();
        ArrayList<Button> contents1 = new ArrayList<>();
        ArrayList<Button> contents2 = new ArrayList<>();
        ArrayList<Button> contents3 = new ArrayList<>();
        ArrayList<Button> contents4 = new ArrayList<>();
        for(LoaiMon lm : DBUtils_LoaiMon.getList()) {
            TitledPane title = new TitledPane();
            title.setText(lm.getLoaiMon());
            titles.add(title);
        }
        
        loadData(contents1, 1);
        loadData(contents2, 2);
        loadData(contents3, 3);
        loadData(contents4, 4);

        loadAccordion(title1, content1, titles, contents1, 0);
        loadAccordion(title2, content2, titles, contents2, 1);
        loadAccordion(title3, content3, titles, contents3, 2);
        loadAccordion(title4, content4, titles, contents4, 3);
        
        acdMenu.getPanes().addAll(title1, title2, title3, title4);


    private void loadAccordion(TitledPane title, VBox content, ArrayList<TitledPane> titles, ArrayList<Button> contents, int count) {
        title.setText(titles.get(count).getText());
        content.getChildren().addAll(contents);
        title.setContent(content);
    }
    
    private void loadData(ArrayList<Button> contents, int count) {
        for (Mon mon : DBUtils_Mon.getMon(count)) {
            Button btn = new Button(mon.getTenMon());
            btn.setPrefWidth(content1.getPrefWidth());
            
            eventShow(btn, mon);
            
            contents.add(btn);
        }
    }
 */

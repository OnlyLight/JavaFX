/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import animatefx.animation.AnimationFX;
import animatefx.animation.GlowBackground;
import animatefx.animation.Pulse;
import animatefx.animation.Tada;
import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import signIn.FXMLSignInController;
import login.FXMLLoginController;
import tqduy.bean.CusMember;
import tqduy.bean.LoaiMon;
import tqduy.bean.Member;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_Bill;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_DK;
import tqduy.connect.DBUtils_LoaiMon;
import tqduy.connect.DBUtils_Member;
import tqduy.connect.DBUtils_Mon;
import tqduy.connect.DBUtils_MonOrder;

/**
 *
 * @author QuangDuy
 */
public class FXMLDocumentController implements Initializable {
    @FXML private TextField txtPay, txtMoneyTotal, txtDiscount;
    @FXML private JFXTextField txtSdtCheck;
    @FXML private JFXButton btnPay, btnCheck, mnThucDon, mnNhanVien, mnTonKho, mnHoaDon, mnLogout, mnMember, mnCus;
    @FXML private TableView<MonOrder> tbInfomation;
    @FXML private TableColumn<MonOrder, String> tbColumnTenMon;
    @FXML private TableColumn<MonOrder, Integer> tbColumnDonGia;
    @FXML private TableColumn<MonOrder, String> tbColumnLoaiMon;
    @FXML private TableColumn<MonOrder, Integer> tbColumnAmount;
    
    public static Mon monInfo;
    public static String sdt = "";
    private CusMember customer = new CusMember();
    private NhanVien nvLogin = FXMLLoginController.nvLogin;
    private Member m = FXMLSignInController.m;
    private double memberDiscount= 0;
    private int idMem = 0;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox CheckoutSection;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Phân Quyền cho user đăng nhập
//        menuQuanTri.setDisable(true);
//        if(nvLogin.getIdRoleName() == 1) {
//            // Admin
//            System.out.println("idRoleName: " + nvLogin.getIdRoleName());
//            menuQuanTri.setDisable(false);
//        } else {
//            // Staff
//            System.out.println("idRoleName: " + nvLogin.getIdRoleName());
//            System.out.println("Ko Phai admin");
//        }
        sideBar.setEffect(new DropShadow(10, 3, 0, Color.rgb(34, 40, 49, 0.7)));
        CheckoutSection.setEffect(new DropShadow(10, 0, 3, Color.rgb(34, 40, 49, 0.5)));
        showAcdMenu();
        tbInfomation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        showTable();
        setOnKeyPress();
        payBill();
        showPay();
        setEventClick();
        checkSDT();
    }
    
    // Check xem user đã đăng ký thành viên chưa
    private boolean check(ArrayList<CusMember> arrCus, String sdtCheck) {
        for (CusMember arrCu : arrCus) {
            if(arrCu.getSdt().equals(sdtCheck)) {
                customer = arrCu;
                System.out.println(arrCu.getSdt() + " - " + sdtCheck);
                return true;
            }
        }
        return false;
    }
    
    private void checkSDT() {
        txtSdtCheck.setText(sdt);
        ArrayList<CusMember> arrCusFirst = DBUtils_CusMember.getListForCheck();
        if(check(arrCusFirst, sdt)) {
            idMem = customer.getIdMember();
            showPay();
        }
        
        txtSdtCheck.textProperty().addListener((observable, oldValue, newValue) -> {
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
            ArrayList<CusMember> arrCus = DBUtils_CusMember.getListForCheck();
            if(check(arrCus, sdt)) {
                idMem = customer.getIdMember();
                Optional<ButtonType> result = createAlert("Welcome " + customer.getTenCus());
            
                if(result.get() == ButtonType.OK) {
                    showPay();
                }
            } else {
                System.out.println("Sign in !!");
                System.out.println("SDTMain: " + sdt);
                try {
                    showDialogMenu("/signIn/FXMLSignIn.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                closeStage(btnCheck);
            }
        });
    }
    
    // Table
    // Định nghĩa cho table
    private void createTitle() {
        tbColumnTenMon.setText("Tên Món");
        tbColumnDonGia.setText("Đơn Giá");
        tbColumnLoaiMon.setText("Loại Món");
        tbColumnAmount.setText("Số Lượng");
        
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
            TablePosition<MonOrder, Integer> pos = event.getTablePosition();
            int row = pos.getRow();
            System.out.println("Row: "+row);
            
            int idMon = getListMon().get(row).getId();
            
            int newValue = 1;
            try {
                newValue = event.getNewValue();
            } catch (Exception e) {
                newValue = event.getOldValue();
                System.out.println(e.getMessage());
            }
            
            if(newValue > 0) {
                System.out.println("IDROW: " + idMon);
                System.out.println("New: " + newValue);

                DBUtils_MonOrder.update(idMon, newValue);

                showPay();
            } else {
                tbInfomation.refresh();
            }
        });
        
    }
    
    // Hiển thị thông tin cho table
    public void showTable() {
        tbInfomation.setEditable(true);
        
        createTitle();
        
        ObservableList<MonOrder> list = getListMon();
        if(!list.isEmpty()) {
            tbInfomation.getColumns().clear();
            System.out.println("List: " + list.get(0).getId());
            tbInfomation.setItems(list);

            System.out.println(tbInfomation.getItems().get(0).getTenMon());

            tbInfomation.getColumns().addAll(tbColumnTenMon, tbColumnDonGia, tbColumnLoaiMon, tbColumnAmount);
            System.out.println("showTable");
        }
    }
    
    private ObservableList<MonOrder> getListMon() {
        ObservableList<MonOrder> list = FXCollections.observableArrayList();
        list.addAll(DBUtils_MonOrder.getList());
        return list;
    }
    
    private void setOnKeyPress() {
        tbInfomation.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.DELETE) {
                deleteRowTable();
            }
        });
    }
    
    private void deleteRowTable() {
        ObservableList<MonOrder> list = tbInfomation.getSelectionModel().getSelectedItems();
        ArrayList<MonOrder> rows = new ArrayList<>(list);
        rows.forEach((row) -> {
            System.out.println("row.getIdMon(): " + row.getId());
            DBUtils_MonOrder.delete(row.getId());
        });
        tbInfomation.getColumns().clear();
        showTable();
        showPay();
    }
    // End Table
    
    // Pay Bill
    private double showPay() {
        double pay = 0;
        for (Member member : DBUtils_Member.getList()) {
            if(member.isActive()) {
                if(member.getIdMember() == idMem) {
                    memberDiscount = member.getDiscount();
                }
            }
        }
        
        txtDiscount.setText(String.valueOf(memberDiscount));
        
        ObservableList<MonOrder> list = getListMon();
        if(!list.isEmpty()) {
            int sumPay = 0;
            for (MonOrder monOrder : list) {
                sumPay += monOrder.getDonGia() * monOrder.getSoLuong();
            }
            double discount = sumPay * (memberDiscount/100);
            pay = sumPay - discount;
            if(pay < 0) pay = 0;
            System.out.println("sumPay: " + sumPay + " - discount: " + discount + " - pay: " + pay);

            txtMoneyTotal.setText(String.valueOf(sumPay));
            txtPay.setText(String.valueOf(pay));
        } else {
            txtMoneyTotal.setText("0");
            txtPay.setText("0");
        }
        System.out.println("Pay: " + pay);
        return pay;
    }
    
    private void payBill() {
        btnPay.setOnAction((event) -> {
            System.out.println("IDMember: "+idMem+" - IDNV: " + nvLogin.getIdNV() + " - " + nvLogin.getUserName() + " - " + txtPay.getText());
            
            // show alert thanh toán
            double pay = showPay();
            if(pay <= 0) {
                createAlert("Chưa chọn món !!");
            } else {
                Optional<ButtonType> result = createAlert("Thanh toán: " + pay);

                if(result.get() == ButtonType.OK) {
                    clearAndPostData(pay);
                }
            }
        });
    }
    
    private void clearAndPostData(double pay) {
        // Insert Data for Table Bill
        if(customer.getIdCus() > 0) {
            System.out.println("IDNV: " + nvLogin.getIdNV() + " - idCUs: " + customer.getIdCus());
            DBUtils_DK.insert(nvLogin.getIdNV(), customer.getIdCus());
        }

        System.out.println("IDNV: " + nvLogin.getIdNV() + " - pay: " + pay + " - " + LocalDate.now());
        DBUtils_Bill.insert(nvLogin.getIdNV(), (int)pay, LocalDate.now());
        
        // Clear
        DBUtils_MonOrder.deleteAll();
        tbInfomation.getItems().clear();
        showPay();
        memberDiscount = 0;
        txtDiscount.setText(String.valueOf(memberDiscount));
        System.out.println("Clear");
    }
    
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
        
        mnTonKho.setOnAction((event) -> {
            try {
                showDialog("/warehouse/FXMLWareHouse.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mnThucDon.setOnAction((event) -> {
            try {
                showDialogMenu("/managerMenu/FXMLManagerMenu.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            closeStage(btnPay);
        });
        
        mnNhanVien.setOnAction((event) -> {
            try {
                showDialog("/managerNV/FXMLNhanVien.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mnMember.setOnAction((event) -> {
            try {
                showDialog("/managerMember/FXMLMember.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mnCus.setOnAction((event) -> {
            try {
                showDialog("/managerCus/FXMLCustomer_1.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mnLogout.setOnAction((event) -> {
            nvLogin = null;
            DBUtils_MonOrder.deleteAll();
            try {
                showDialog("/login/FXMLLogin_1.fxml", StageStyle.UNDECORATED, Modality.NONE);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            closeStage(btnPay);
        });
        
        mnHoaDon.setOnAction((event) -> {
            try {
                showDialog("/bill/FXMLBill.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    private void showAcdMenu() {
        // Dynamic
//        acdMenu.getPanes().clear();
//        ArrayList<TitledPane> titles = new ArrayList<>();
//        ArrayList<LoaiMon> listLoaiMon = DBUtils_LoaiMon.getList();
//
//        for(int i = 0; i < listLoaiMon.size(); i++) {
//            TitledPane title = new TitledPane();
//            VBox content = new VBox();
//            if(listLoaiMon.get(i).isIsActive()) {
//                for (Mon m : DBUtils_Mon.getMon(i+1)) {
//                    if(m.isIsActive()) {
//                        Button btn = new Button(m.getTenMon());
//                        btn.setPrefWidth(layoutSale.getPrefWidth());
//
//                        eventShow(btn, m);
//                        content.getChildren().add(btn);
//                    }
//                }
//                title.setText(listLoaiMon.get(i).getLoaiMon());
//                title.setContent(content);
//                titles.add(title);
//            }
//        }
//        acdMenu.getPanes().addAll(titles);
    }
    
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

    @FXML
    private void mouseEnterHover(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        ScaleTransition scale = new ScaleTransition();
        new Pulse(btn).play();
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

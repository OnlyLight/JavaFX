/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;
import tqduy.bean.LoaiMon;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.connect.DBUtils_LoaiMon;
import tqduy.connect.DBUtils_Mon;
import tqduy.connect.DBUtils_MonOrder;

/**
 *
 * @author QuangDuy
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txtPay, txtMoneyTotal, txtDiscount;
    
    @FXML
    private BorderPane layoutSale;
    
    @FXML
    private Button btnPay;
    
    @FXML
    private MenuItem mnThucDon, mnClose, mnNhanVien, mnAbout, mnTonKho, mnHoaDon, mnQuanLy;
    
    @FXML
    private TableView<MonOrder> tbInfomation;
    
    @FXML
    private TableColumn<MonOrder, String> tbColumnTenMon;
    
    @FXML
    private TableColumn<MonOrder, Integer> tbColumnDonGia;
    
    @FXML
    private TableColumn<MonOrder, String> tbColumnLoaiMon;
    
    @FXML
    private TableColumn<MonOrder, Integer> tbColumnAmount;
    
    @FXML 
    private Accordion acdMenu;
    
    public static Mon monInfo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showAcdMenu();
        tbInfomation.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        showTable();
        setOnKeyPress();
        payBill();
        showPay();
        setEventClick();
    }
    
    // Table
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
        txtDiscount.setText("35%");
        double pay = 0;
        ObservableList<MonOrder> list = getListMon();
        if(!list.isEmpty()) {
            int sumPay = 0;
            for (MonOrder monOrder : list) {
                sumPay += monOrder.getDonGia() * monOrder.getSoLuong();
            }
            double discount = sumPay * (35.0/100);
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
            // show alert thanh toán
            createAlert("Thanh toán: " + showPay());
        });
    }
    
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK) {
            DBUtils_MonOrder.deleteAll();
            tbInfomation.getItems().clear();
            showPay();
            System.out.println("Clear");
        }

        System.out.println(result.get().getText());
    }
    
    // End Pay Bill
    
    // Menu
    private void setEventClick() {
        mnClose.setText("Exit");
        mnClose.setOnAction((event) -> {
            System.exit(0);
        });
        
        mnTonKho.setOnAction((event) -> {
            try {
                showDialog("/warehouse/FXMLWareHouse.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        mnThucDon.setOnAction((event) -> {
            try {
                showDialog("/managerMenu/FXMLManagerMenu.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    // End Menu
    
    // Accordion
    private void showAcdMenu() {
        // Dynamic
        acdMenu.getPanes().clear();
        ArrayList<TitledPane> titles = new ArrayList<>();
        ArrayList<LoaiMon> listLoaiMon = DBUtils_LoaiMon.getList();

        for(int i = 0; i < listLoaiMon.size(); i++) {
            TitledPane title = new TitledPane();
            VBox content = new VBox();
            for (Mon m : DBUtils_Mon.getMon(i+1)) {
                Button btn = new Button(m.getTenMon());
                btn.setPrefWidth(layoutSale.getPrefWidth());

                eventShow(btn, m);
                content.getChildren().add(btn);
            }
            title.setText(listLoaiMon.get(i).getLoaiMon());
            title.setContent(content);
            titles.add(title);
        }
        acdMenu.getPanes().addAll(titles);
    }
    
    private void eventShow(Button btn, Mon mon) {
        btn.setOnAction((event) -> {
            System.out.println("IdMon: " + mon.getIdMon() + " - tenMon: " + mon.getTenMon() + " - Don gia: " + mon.getDonGia() + " - IdLoaiMon: " + mon.getIdLoaiMon());
            
            monInfo = mon;
            
            try {
                showDialog("/dialog/FXMLDialog.fxml", StageStyle.UNDECORATED, Modality.APPLICATION_MODAL);
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
    private void showDialog(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage(style);
        stage.initModality(modal);
        
        stage.setTitle("OL! Tea");
        
        stage.setScene(scene);
        stage.show();
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

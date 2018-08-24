/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerCus;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tqduy.bean.CusMember;
import tqduy.connect.DBUtils_CusMember;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLCustomerController implements Initializable {
    @FXML private TableView<CusMember> tbCus;
    @FXML private TableColumn<CusMember, String> tbTenNVCusColumn;
    @FXML private TableColumn<CusMember, String> tbVaiTroCusColumn;
    @FXML private TableColumn<CusMember, String> tbTenCusColumn;
    @FXML private TableColumn<CusMember, String> tbSDTCusColumn;
    @FXML private TableColumn<CusMember, String> tbLoaiMemberCusColumn;
    @FXML private TableColumn<CusMember, Date> tbNgayLapCusColumn;
    @FXML private JFXButton btnInThongTin;
    
    private void setEvent() {
        btnInThongTin.setOnAction((event) -> {
            createAlert("Printing....");
        });
    }
    
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        System.out.println(result.get().getText());
    }
    
    private void showTBCusMem() {
        System.out.println("Hello ");
        tbCus.getColumns().clear();
        
        tbTenNVCusColumn.setText("Nhân Viên");
        tbVaiTroCusColumn.setText("Vai Trò");
        tbTenCusColumn.setText("Tên Khách hàng");
        tbSDTCusColumn.setText("SDT");
        tbLoaiMemberCusColumn.setText("Loại Thành Viên");
        tbNgayLapCusColumn.setText("Ngày Lập");
        
        tbTenNVCusColumn.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        tbVaiTroCusColumn.setCellValueFactory(new PropertyValueFactory<>("vaiTro"));
        tbTenCusColumn.setCellValueFactory(new PropertyValueFactory<>("tenCus"));
        tbSDTCusColumn.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        tbLoaiMemberCusColumn.setCellValueFactory(new PropertyValueFactory<>("loaiMember"));
        tbNgayLapCusColumn.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        
        tbNgayLapCusColumn.setCellFactory((column) -> {
            TableCell<CusMember, Date> cell = new TableCell<CusMember, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };
            return cell; //To change body of generated lambdas, choose Tools | Templates.
        });
        
        ObservableList<CusMember> list = FXCollections.observableArrayList(DBUtils_CusMember.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbCus.setItems(list);
            tbCus.getColumns().addAll(tbTenNVCusColumn, tbVaiTroCusColumn, tbTenCusColumn, tbSDTCusColumn, tbLoaiMemberCusColumn, tbNgayLapCusColumn);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTBCusMem();
        setEvent();
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerCus;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    }    
    
}

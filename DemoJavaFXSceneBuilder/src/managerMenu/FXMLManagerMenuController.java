/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tqduy.bean.DVT;
import tqduy.bean.InsertNX;
import tqduy.bean.LoaiMon;
import tqduy.bean.Menu;
import tqduy.connect.DBUtils_DVT;
import tqduy.connect.DBUtils_LoaiMon;
import tqduy.connect.DBUtils_LoaiNX;
import tqduy.connect.DBUtils_Mon;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLManagerMenuController implements Initializable {
    @FXML private TextField txtTenLoaiMon, txtTenMonMenu, txtDonGiaMenu, txtDVT, txtLoaiNhapXuat;
    @FXML private Button btnThemLoaiMon, btnThemMenu, btnThemDVT, btnThemLoaiNX;
    @FXML private ListView<LoaiMon> lvLoaiMon;
    @FXML private ListView<DVT> lvDVT;
    @FXML private ComboBox<String> cbLoaiMonMenu;
    @FXML private ComboBox<String> cbDVT;
    @FXML private TableView<Menu> tbMenu;
    @FXML private TableColumn<Menu, String> tbTenMonColumnMenu;
    @FXML private TableColumn<Menu, Integer> tbDonGiaColumnMenu;
    @FXML private TableColumn<Menu, String> tbLoaiMonColumnMenu;
    @FXML private TableView<InsertNX> tbLoaiNX;
    @FXML private TableColumn<InsertNX, String> tbLoaiNXColumn;
    @FXML private TableColumn<InsertNX, String> tbDVTColumn;
    @FXML private Tab tabLoaiMon, tabMenu, tabDVT, tabLoaiNX;
    
    private int loaiMenu = 0, donGiaMenu = 0, indexDVT = 0;
    
    private void loadTableMenu() {
        tbMenu.getColumns().clear();
        tbTenMonColumnMenu.setText("Tên Món");
        tbDonGiaColumnMenu.setText("Đơn Giá");
        tbLoaiMonColumnMenu.setText("Loại Món");
        
        tbTenMonColumnMenu.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
        tbDonGiaColumnMenu.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbLoaiMonColumnMenu.setCellValueFactory(new PropertyValueFactory<>("loaiMon"));
        
        ObservableList<Menu> listMon = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());
        System.out.println("List: " + listMon);
        if(!listMon.isEmpty()) {
            tbMenu.setItems(listMon);
            tbMenu.getColumns().addAll(tbTenMonColumnMenu, tbDonGiaColumnMenu, tbLoaiMonColumnMenu);
        }
    }
    
    private void loadTableNX() {
        tbLoaiNX.getColumns().clear();
        tbLoaiNXColumn.setText("Loại NX");
        tbDVTColumn.setText("Đơn vị tính");
        
        tbLoaiNXColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoaiNX"));
        tbDVTColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        
        ObservableList<InsertNX> listMon = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());
        System.out.println("List: " + listMon);
        if(!listMon.isEmpty()) {
            tbLoaiNX.setItems(listMon);
            tbLoaiNX.getColumns().addAll(tbLoaiNXColumn, tbDVTColumn);
        }
    }
    
    private void loadListView() {
        ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
        if(!listMon.isEmpty()) {
            lvLoaiMon.getItems().clear();
            lvLoaiMon.setItems(listMon);
        }
    }
    
    private void loadListViewDVT() {
        ObservableList<DVT> listDVT = FXCollections.observableArrayList(DBUtils_DVT.getList());
        if(!listDVT.isEmpty()) {
            lvDVT.getItems().clear();
            lvDVT.setItems(listDVT);
        }
    }
    
    private void setClick() {
        ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
        btnThemLoaiMon.setOnAction((event) -> {
            DBUtils_LoaiMon.insert(txtTenLoaiMon.getText().toString().trim());
            System.out.println("Insert !!");
            
            if(!listMon.isEmpty()) {
                lvLoaiMon.getItems().clear();
                lvLoaiMon.setItems(listMon);
            }
        });
        
        btnThemDVT.setOnAction((event) -> {
            ObservableList<DVT> listDVT = FXCollections.observableArrayList(DBUtils_DVT.getList());
            DBUtils_DVT.insert(txtDVT.getText().toString().trim());
            
            listDVT = FXCollections.observableArrayList(DBUtils_DVT.getList());
            if(!listDVT.isEmpty()) {
                lvDVT.getItems().clear();
                lvDVT.setItems(listDVT);
            }
        });
        
        cbLoaiMonMenu.setOnAction((event) -> {
            loaiMenu = cbLoaiMonMenu.getSelectionModel().getSelectedIndex();
            
            System.out.println("LoaiMenu: " + loaiMenu);
        });
        
        cbDVT.setOnAction((event) -> {
            indexDVT = cbDVT.getSelectionModel().getSelectedIndex();
            
            System.out.println("DVT: " + indexDVT);
        });
        
        btnThemLoaiNX.setOnAction((event) -> {
            String tenLoaiNX = txtLoaiNhapXuat.getText().toString().trim();
            
            if(!tenLoaiNX.isEmpty() || indexDVT > -1) {
                DBUtils_LoaiNX.insert(tenLoaiNX, (indexDVT + 1));

                ObservableList<InsertNX> listLoai = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());

                if(!listLoai.isEmpty()) {
                    tbLoaiNX.getItems().clear();
                    tbLoaiNX.setItems(listLoai);
                }
            }
        });
        
        btnThemMenu.setOnAction((event) -> {
            String tenMon = txtTenMonMenu.getText().toString().trim();
            
            txtDonGiaMenu.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.matches("\\d*") && !newValue.equals("0")) {
                    try {
                        donGiaMenu = Integer.parseInt(newValue);
                    } catch (Exception e) {

                    }
                } else {
                    txtDonGiaMenu.setText(oldValue);
                }
            });
            
            donGiaMenu = Integer.parseInt(txtDonGiaMenu.getText().toString().trim());
            
            if(!tenMon.isEmpty() || donGiaMenu > 0 || loaiMenu > -1) {
                DBUtils_Mon.insert(tenMon, donGiaMenu, (loaiMenu + 1));

                ObservableList<Menu> listLoai = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());

                if(!listLoai.isEmpty()) {
                    tbMenu.getItems().clear();
                    tbMenu.setItems(listLoai);
                }
            }
        });
    }
    
    private void loadComboBox() {
        ObservableList<String> listLoai = FXCollections.observableArrayList();
        ObservableList<String> arrDVT = FXCollections.observableArrayList();
        
        for (LoaiMon loaiMon : DBUtils_LoaiMon.getList()) {
            String tenLoai = loaiMon.getLoaiMon();
            listLoai.add(tenLoai);
        }
        
        for (DVT dvt : DBUtils_DVT.getList()) {
            String tenDVT = dvt.getDvt();
            arrDVT.add(tenDVT);
        }
        
        if(!listLoai.isEmpty())  cbLoaiMonMenu.setItems(listLoai);
        if(!arrDVT.isEmpty())  cbDVT.setItems(arrDVT);
    }
    
    private void chooseTab() {
        tabLoaiMon.setOnSelectionChanged((event) -> {
            loadListView();
        });
        
        tabMenu.setOnSelectionChanged((event) -> {
            loadTableMenu();
            loadComboBox();
        });
        
        tabDVT.setOnSelectionChanged((event) -> {
            loadListViewDVT();
        });
        
        tabLoaiNX.setOnSelectionChanged((event) -> {
            loadTableNX();
            loadComboBox();
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadListView();
        chooseTab();
        setClick();
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
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
    @FXML private JFXTextField txtTenLoaiMon, txtTenMonMenu, txtDonGiaMenu, txtDVT, txtLoaiNhapXuat;
    @FXML private JFXButton btnThemLoaiMon, btnThemMenu, btnThemDVT, btnThemLoaiNX;
    @FXML private ListView<DVT> lvDVT;
    @FXML private ComboBox<LoaiMon> cbLoaiMonMenu;
    @FXML private ComboBox<DVT> cbDVT;
    @FXML private TableView<LoaiMon> tbLoaiMon;
    @FXML private TableColumn<LoaiMon, Integer> tbIDColumnLoaiMon;
    @FXML private TableColumn<LoaiMon, String> tbTenLoaiMonColumn;
    @FXML private TableColumn<LoaiMon, Boolean> tbIsActiveLoaiMonColumn;
    @FXML private TableView<Menu> tbMenu;
    @FXML private TableColumn<Menu, String> tbTenMonColumnMenu;
    @FXML private TableColumn<Menu, Integer> tbDonGiaColumnMenu;
    @FXML private TableColumn<Menu, String> tbLoaiMonColumnMenu;
    @FXML private TableColumn<Menu, Boolean> tbIsActiveMenuColumn;
    @FXML private TableView<InsertNX> tbLoaiNX;
    @FXML private TableColumn<InsertNX, String> tbLoaiNXColumn;
    @FXML private TableColumn<InsertNX, String> tbDVTColumn;
    @FXML private Tab tabLoaiMon, tabMenu, tabDVT, tabLoaiNX;
    
    private int donGiaMenu = 0;
    private LoaiMon loaiMenu = new LoaiMon();
    private DVT dvtSelected = new DVT();
    
    private void loadTableLoaiMon() {
        tbLoaiMon.getColumns().clear();
        tbLoaiMon.setEditable(true);
        tbIDColumnLoaiMon.setText("STT");
        tbTenLoaiMonColumn.setText("Loại");
        tbIsActiveLoaiMonColumn.setText("Active");
        
        tbIDColumnLoaiMon.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbTenLoaiMonColumn.setCellValueFactory(new PropertyValueFactory<>("loaiMon"));
        tbIsActiveLoaiMonColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LoaiMon, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LoaiMon, Boolean> param) {
                LoaiMon loaiMon = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(loaiMon.isIsActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    loaiMon.setIsActive(newValue);
                    System.out.println(loaiMon.getId() + " - Ten: " + loaiMon.getLoaiMon() + " - Active: " + loaiMon.isIsActive());
                    DBUtils_LoaiMon.update(loaiMon.getId(), loaiMon.isIsActive());
                });
                return booleanProperty;
            }
        });
        
        tbIsActiveLoaiMonColumn.setCellFactory(new Callback<TableColumn<LoaiMon, Boolean>, TableCell<LoaiMon, Boolean>>() {
            @Override
            public TableCell<LoaiMon, Boolean> call(TableColumn<LoaiMon, Boolean> param) {
                CheckBoxTableCell<LoaiMon, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
        System.out.println("List: " + listMon);
        if(!listMon.isEmpty()) {
            tbLoaiMon.setItems(listMon);
            tbLoaiMon.getColumns().addAll(tbIDColumnLoaiMon, tbTenLoaiMonColumn, tbIsActiveLoaiMonColumn);
        }
    }
    
    private void loadTableMenu() {
        tbMenu.getColumns().clear();
        tbMenu.setEditable(true);
        tbTenMonColumnMenu.setText("Name");
        tbDonGiaColumnMenu.setText("Price");
        tbLoaiMonColumnMenu.setText("Type");
        tbIsActiveMenuColumn.setText("Active");
        
        tbTenMonColumnMenu.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
        tbDonGiaColumnMenu.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbLoaiMonColumnMenu.setCellValueFactory(new PropertyValueFactory<>("loaiMon"));
        
        tbIsActiveMenuColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Menu, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Menu, Boolean> param) {
                Menu menu = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(menu.isIsActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    menu.setIsActive(newValue);
                    System.out.println(menu);
                    DBUtils_Mon.update(menu.getIdMon(), menu.isIsActive());
                });
                return booleanProperty;
            }
        });
        
        tbIsActiveMenuColumn.setCellFactory(new Callback<TableColumn<Menu, Boolean>, TableCell<Menu, Boolean>>() {
            @Override
            public TableCell<Menu, Boolean> call(TableColumn<Menu, Boolean> param) {
                CheckBoxTableCell<Menu, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        ObservableList<Menu> listMon = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());
        System.out.println("List: " + listMon);
        if(!listMon.isEmpty()) {
            tbMenu.setItems(listMon);
            tbMenu.getColumns().addAll(tbTenMonColumnMenu, tbDonGiaColumnMenu, tbLoaiMonColumnMenu, tbIsActiveMenuColumn);
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
    
    private void loadListViewDVT() {
        ObservableList<DVT> listDVT = FXCollections.observableArrayList();
        
        for (DVT dvt : DBUtils_DVT.getList()) {
            listDVT.add(dvt);
        }
        
        if(!listDVT.isEmpty()) {
            lvDVT.getItems().clear();
            lvDVT.setItems(listDVT);
        }
    }
    
    private void setClick() {
        txtTenLoaiMon.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemLoaiMon.fire();
            }
        });
        btnThemLoaiMon.setDisable(true);
        txtTenLoaiMon.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemLoaiMon.setDisable(newValue.trim().isEmpty());
        });
        
        txtDVT.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemDVT.fire();
            }
        });
        btnThemDVT.setDisable(true);
        txtDVT.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemDVT.setDisable(newValue.trim().isEmpty());
        });
        
        btnThemLoaiMon.setOnAction((event) -> {
            if(!txtTenLoaiMon.getText().toString().trim().isEmpty()) {
                DBUtils_LoaiMon.insert(txtTenLoaiMon.getText().toString().trim());
            }
            
            ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
            if(!listMon.isEmpty()) {
                tbLoaiMon.getItems().clear();
                tbLoaiMon.setItems(listMon);
            }
        });
        
        btnThemDVT.setOnAction((event) -> {
            if(!txtDVT.getText().toString().trim().isEmpty()) {
                DBUtils_DVT.insert(txtDVT.getText().toString().trim());
            }
            
            ObservableList<DVT> listDVT = FXCollections.observableArrayList();
            
            for (DVT dvt : DBUtils_DVT.getList()) {
                listDVT.add(dvt);
            }
            
            if(!listDVT.isEmpty()) {
                lvDVT.getItems().clear();
                lvDVT.setItems(listDVT);
            }
        });
        
        cbLoaiMonMenu.setOnAction((event) -> {
            loaiMenu = cbLoaiMonMenu.getSelectionModel().getSelectedItem();
            System.out.println(loaiMenu);
        });
        
        cbDVT.setOnAction((event) -> {
            dvtSelected = cbDVT.getSelectionModel().getSelectedItem();
            System.out.println(dvtSelected);
        });
        
        btnThemLoaiNX.setOnAction((event) -> {
            String tenLoaiNX = txtLoaiNhapXuat.getText().toString().trim();
            
            if(!tenLoaiNX.isEmpty() && dvtSelected != null) {
                DBUtils_LoaiNX.insert(tenLoaiNX, dvtSelected.getIdDVT());

                ObservableList<InsertNX> listLoai = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());

                if(!listLoai.isEmpty()) {
                    tbLoaiNX.getItems().clear();
                    tbLoaiNX.setItems(listLoai);
                }
            } else {
                createAlert("Hãy nhập đầy đủ thông tin !!");
            }
        });
        
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
        
        btnThemLoaiNX.setDisable(true);
        txtLoaiNhapXuat.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemLoaiNX.setDisable(newValue.trim().isEmpty());
        });
        
        btnThemMenu.setDisable(true);
        txtTenMonMenu.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemMenu.setDisable(newValue.trim().isEmpty());
        });
        
        btnThemMenu.setOnAction((event) -> {
            String tenMon = txtTenMonMenu.getText().toString().trim();
            
            if(!tenMon.isEmpty() && donGiaMenu > 0 && loaiMenu != null) {
                DBUtils_Mon.insert(tenMon, donGiaMenu, loaiMenu.getId());

                ObservableList<Menu> listLoai = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());

                if(!listLoai.isEmpty()) {
                    tbMenu.getItems().clear();
                    tbMenu.setItems(listLoai);
                }
            } else {
                createAlert("Hãy nhập đầy đủ thông tin !!");
            }
        });
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
    
    private void loadComboBox() {
        ObservableList<LoaiMon> listLoai = FXCollections.observableArrayList();
        ObservableList<DVT> arrDVT = FXCollections.observableArrayList();
        
        for (LoaiMon loaiMon : DBUtils_LoaiMon.getList()) {
            if(loaiMon.isIsActive()) {
                listLoai.add(loaiMon);
            }
        }
        
        for (DVT dvt : DBUtils_DVT.getList()) {
            arrDVT.add(dvt);
        }
        
        if(!listLoai.isEmpty()) {
            cbLoaiMonMenu.getItems().clear();
            cbLoaiMonMenu.setItems(listLoai);
        }
        if(!arrDVT.isEmpty()) {
            cbDVT.getItems().clear();
            cbDVT.setItems(arrDVT);
        }
    }
    
    private void chooseTab() {
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
        loadTableLoaiMon();
        chooseTab();
        setClick();
    }    
    
}

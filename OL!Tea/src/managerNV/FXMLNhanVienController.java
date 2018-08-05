/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerNV;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tqduy.bean.Role;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_NhanVien;
import tqduy.connect.DBUtils_Role;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLNhanVienController implements Initializable {
    @FXML private Button btnThemNV, btnThemVaiTro;
    @FXML private TextField txtPassWord, txtTenVaiTro, txtUserName;
    @FXML private ComboBox<Role> cbVaiTro;
    @FXML private TableView<Role> tbRole;
    @FXML private TableColumn<Role, Integer> tbIDVaiTroColumn;
    @FXML private TableColumn<Role, String> tbTenVaiTroColumn;
    @FXML private TableColumn<Role, Boolean> tbIsActiveVaiTroColumn;
    @FXML private TableView<NhanVien> tbNhanVien;
    @FXML private TableColumn<NhanVien, String> tbUserNameNVColumn;
    @FXML private TableColumn<NhanVien, String> tbVaiTroNVColumn;
    @FXML private TableColumn<NhanVien, Boolean> tbIsActiveNVColumn;
    @FXML private Tab tabRole, tabNhanVien;
    
    private Role roleSelected = new Role();
    
    private void showTableRole() {
        tbRole.getColumns().clear();
        tbRole.setEditable(true);
        
        tbIDVaiTroColumn.setText("STT");
        tbTenVaiTroColumn.setText("Loại");
        tbIsActiveVaiTroColumn.setText("Active");
        
        tbIDVaiTroColumn.setCellValueFactory(new PropertyValueFactory<>("idRole"));
        tbTenVaiTroColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        tbIsActiveVaiTroColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Role, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Role, Boolean> param) {
                Role role = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(role.isActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    role.setActive(newValue);
                    System.out.println(role);
                    DBUtils_Role.update(role.getIdRole(), role.isActive());
                });
                return booleanProperty;
            }
        });
        
        tbIsActiveVaiTroColumn.setCellFactory(new Callback<TableColumn<Role, Boolean>, TableCell<Role, Boolean>>() {
            @Override
            public TableCell<Role, Boolean> call(TableColumn<Role, Boolean> param) {
                CheckBoxTableCell<Role, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        ObservableList<Role> list = FXCollections.observableArrayList(DBUtils_Role.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbRole.setItems(list);
            tbRole.getColumns().addAll(tbIDVaiTroColumn, tbTenVaiTroColumn, tbIsActiveVaiTroColumn);
        }
    }
    
    private void showTBNV() {
        tbNhanVien.getColumns().clear();
        tbNhanVien.setEditable(true);
        
        tbUserNameNVColumn.setText("User Name");
        tbVaiTroNVColumn.setText("Vai Trò");
        tbIsActiveNVColumn.setText("Active");
        
        tbUserNameNVColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tbVaiTroNVColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        tbIsActiveNVColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<NhanVien, Boolean> param) {
                NhanVien nv = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(nv.isActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    nv.setActive(newValue);
                    System.out.println(nv);
                    DBUtils_NhanVien.update(nv.getIdNV(), nv.isActive());
                });
                return booleanProperty;
            }
        });
        
        tbIsActiveNVColumn.setCellFactory(new Callback<TableColumn<NhanVien, Boolean>, TableCell<NhanVien, Boolean>>() {
            @Override
            public TableCell<NhanVien, Boolean> call(TableColumn<NhanVien, Boolean> param) {
                CheckBoxTableCell<NhanVien, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        
        ObservableList<NhanVien> list = FXCollections.observableArrayList(DBUtils_NhanVien.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbNhanVien.setItems(list);
            tbNhanVien.getColumns().addAll(tbUserNameNVColumn, tbVaiTroNVColumn, tbIsActiveNVColumn);
        }
    }
    
    private void loadComboBox() {
        ObservableList<Role> listLoai = FXCollections.observableArrayList();
        
        for (Role role : DBUtils_Role.getList()) {
            if(role.isActive()) {
                listLoai.add(role);
            }
        }
        
        if(!listLoai.isEmpty()) {
            cbVaiTro.getItems().clear();
            cbVaiTro.setItems(listLoai);
        }
    }
    
    private void setEventClick() {
        btnThemVaiTro.setDisable(true);
        txtTenVaiTro.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemVaiTro.setDisable(newValue.trim().isEmpty());
        });
        
        btnThemVaiTro.setOnAction((event) -> {
            String ten = txtTenVaiTro.getText().toString().trim();
            if(!ten.isEmpty()) {
                Optional<ButtonType> result = createAlert("Do you want add ?");
            
                if(result.get() == ButtonType.OK) {
                    DBUtils_Role.insert(ten);
                    ObservableList<Role> list = FXCollections.observableArrayList(DBUtils_Role.getList());
                    System.out.println("List: " + list);
                    if(!list.isEmpty()) {
                        tbRole.getItems().clear();
                        tbRole.setItems(list);
                    }
                }
            } else {
                createAlert("Hãy nhập thông tin !!");
            }
        });
        
        cbVaiTro.setOnAction((event) -> {
            roleSelected = cbVaiTro.getSelectionModel().getSelectedItem();
            System.out.println(roleSelected.getIdRole() + " - " + roleSelected.getRoleName());
        });
        
        btnThemNV.setOnAction((event) -> {
            String userName = txtUserName.getText().toString().trim();
            String passWord = txtPassWord.getText().toString().trim();
            
            if(!userName.isEmpty() && !passWord.isEmpty()) {
                Optional<ButtonType> result = createAlert("Do you want add ?");
            
                if(result.get() == ButtonType.OK) {
                    DBUtils_NhanVien.insert(userName, passWord, roleSelected.getIdRole());
                    System.out.println(userName + " - " + passWord + " - " + roleSelected.getIdRole());
                    
                    ObservableList<NhanVien> list = FXCollections.observableArrayList(DBUtils_NhanVien.getList());
                    System.out.println("List: " + list);
                    if(!list.isEmpty()) {
                        tbNhanVien.getItems().clear();
                        tbNhanVien.setItems(list);
                    }
                }
            } else {
                createAlert("Hãy nhập thông tin !!");
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
    
    private void chooseTab() {
        tabRole.setOnSelectionChanged((event) -> {
            showTableRole();
        });
        
        tabNhanVien.setOnSelectionChanged((event) -> {
            showTBNV();
            loadComboBox();
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTableRole();
        chooseTab();
        setEventClick();
    }    
    
}

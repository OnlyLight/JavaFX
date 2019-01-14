/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    @FXML private TextField txtTenLoaiMon, txtTenMonMenu, txtDonGiaMenu, txtDVT, txtLoaiNhapXuat;
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
    
    private int donGiaMenu = 0;
    private LoaiMon loaiMenu = new LoaiMon();
    private DVT dvtSelected = new DVT();
    @FXML
    private VBox addForm;
    @FXML
    private JFXButton btnOpenAddForm;
    @FXML
    private StackPane menuStackPane;
    @FXML
    private JFXButton btnCloseAddMenu;
    @FXML
    private VBox addTypeForm;
    @FXML
    private JFXButton btnOpenAddTypeForm;
    @FXML
    private StackPane typeMenuStackPane;
    @FXML
    private VBox addImportTypeForm;
    @FXML
    private JFXButton btnOpenImportTypeForm;
    @FXML
    private StackPane typeImportStackPane;
    @FXML
    private VBox addUntiForm;
    @FXML
    private JFXButton btnOpenAddUnitForm;
    @FXML
    private StackPane unitStackPane;
    @FXML
    private StackPane mainMenuStackPane;
    
    private void loadTableLoaiMon() {
        tbLoaiMon.getColumns().clear();
        tbLoaiMon.setEditable(true);
        tbIDColumnLoaiMon.setText("STT");
        tbTenLoaiMonColumn.setText("Loại");
        tbIsActiveLoaiMonColumn.setText("Active");
        tbIsActiveLoaiMonColumn.getStyleClass().add("align-center");
        
        tbIDColumnLoaiMon.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbTenLoaiMonColumn.setCellValueFactory(new PropertyValueFactory<>("loaiMon"));
        tbIsActiveLoaiMonColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LoaiMon, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<LoaiMon, Boolean> param) {
                LoaiMon loaiMon = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(loaiMon.isIsActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    loaiMon.setIsActive(newValue);
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
        
<<<<<<< HEAD
        Runnable tast = () -> {
            ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
            if(!listMon.isEmpty()) {
                tbLoaiMon.setItems(listMon);
                tbLoaiMon.getColumns().addAll(tbIDColumnLoaiMon, tbTenLoaiMonColumn, tbIsActiveLoaiMonColumn);
            }//To change body of generated methods, choose Tools | Templates.
=======
        Runnable tast = new Runnable() {
            @Override
            public void run() {
                try {
                    ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
                    if(!listMon.isEmpty()) {
                        tbLoaiMon.setItems(listMon);
                        tbLoaiMon.getColumns().addAll(tbIDColumnLoaiMon, tbTenLoaiMonColumn, tbIsActiveLoaiMonColumn);
                    }//To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
>>>>>>> 86f9ae8439804aefd60ef644ed5a051a003224e1
        };
        Thread backgroundThread = new Thread(tast);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    
    private void loadTableMenu() {
        tbMenu.getColumns().clear();
        tbMenu.setEditable(true);
        tbTenMonColumnMenu.setText("Name");
        tbDonGiaColumnMenu.setText("Price");
        tbLoaiMonColumnMenu.setText("Type");
        tbIsActiveMenuColumn.setText("Active");
        tbIsActiveMenuColumn.getStyleClass().add("align-center");
        
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
<<<<<<< HEAD
         Runnable tast = () -> {
             ObservableList<Menu> listMon = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());
             if(!listMon.isEmpty()) {
                 tbMenu.setItems(listMon);
                 tbMenu.getColumns().addAll(tbTenMonColumnMenu, tbDonGiaColumnMenu, tbLoaiMonColumnMenu, tbIsActiveMenuColumn);
             }
             //To change body of generated methods, choose Tools | Templates.
=======
         Runnable tast = new Runnable() {
            @Override
            public void run() {
                try {
                    ObservableList<Menu> listMon = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());
                    if(!listMon.isEmpty()) {
                        tbMenu.setItems(listMon);
                        tbMenu.getColumns().addAll(tbTenMonColumnMenu, tbDonGiaColumnMenu, tbLoaiMonColumnMenu, tbIsActiveMenuColumn);
                    }
                    //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
>>>>>>> 86f9ae8439804aefd60ef644ed5a051a003224e1
        };
        Thread backgroundThread = new Thread(tast);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    
    private void loadTableNX() {
        tbLoaiNX.getColumns().clear();
        tbLoaiNXColumn.setText("Loại NX");
        tbDVTColumn.setText("Đơn vị tính");
        
        tbLoaiNXColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoaiNX"));
        tbDVTColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        
<<<<<<< HEAD
         Runnable tast = () -> {
             ObservableList<InsertNX> listMon = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());
             if(!listMon.isEmpty()) {
                 tbLoaiNX.setItems(listMon);
                 tbLoaiNX.getColumns().addAll(tbLoaiNXColumn, tbDVTColumn);
             } //To change body of generated methods, choose Tools | Templates.
=======
         Runnable tast = new Runnable() {
            @Override
            public void run() {
                try {
                    ObservableList<InsertNX> listMon = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());
                    if(!listMon.isEmpty()) {
                        tbLoaiNX.setItems(listMon);
                        tbLoaiNX.getColumns().addAll(tbLoaiNXColumn, tbDVTColumn);
                    } //To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
>>>>>>> 86f9ae8439804aefd60ef644ed5a051a003224e1
        };
        Thread backgroundThread = new Thread(tast);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    
    private void loadListViewDVT() {
        Runnable tast = () -> {
            ObservableList<DVT> listDVT = FXCollections.observableArrayList();
            for (DVT dvt : DBUtils_DVT.getList()) {
                listDVT.add(dvt);
            }
            
            if(!listDVT.isEmpty()) {
                lvDVT.getItems().clear();
                lvDVT.setItems(listDVT);
            }
            //To change body of generated methods, choose Tools | Templates.
        };
        Thread backgroundThread = new Thread(tast);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    
    private void creatDialog(JFXButton btn, Region dialogContent, StackPane toStackPane) {
        btn.setDisable(true);
        JFXDialog dialog = new JFXDialog(toStackPane, dialogContent, JFXDialog.DialogTransition.NONE);
        dialog.setOverlayClose(false);
        dialog.show();
        JFXButton btnClose = (JFXButton) dialog.lookup("#btnCloseAddMenu");
        btnClose.setOnAction((eventt) -> {
            dialog.close();
            btn.setDisable(false);
        });
    }
    
    private void setClick() {
        btnOpenAddForm.setOnAction((event) -> {
            try {
                loadComboBox();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            creatDialog(btnOpenAddForm, addForm, mainMenuStackPane);
//            btnOpenAddForm.setDisable(true);
//            JFXDialog dialog = new JFXDialog(menuStackPane, addForm, JFXDialog.DialogTransition.NONE);
//            dialog.setOverlayClose(false);
//            dialog.show();
//            JFXButton btnClose = (JFXButton) dialog.lookup("#btnCloseAddMenu");
//            btnClose.setOnAction((eventt) -> {
//                dialog.close();
//                btnOpenAddForm.setDisable(false);
//            });
        });
        btnOpenAddTypeForm.setOnAction((event) -> {
            creatDialog(btnOpenAddTypeForm, addTypeForm, mainMenuStackPane);
//            btnOpenAddTypeForm.setDisable(true);
//            JFXDialog dialog = new JFXDialog(typeMenuStackPane, addTypeForm, JFXDialog.DialogTransition.NONE);
//            dialog.setOverlayClose(false);
//            dialog.show();
//            JFXButton btnClose = (JFXButton) dialog.lookup("#btnCloseAddMenu");
//            btnClose.setOnAction((eventt) -> {
//                dialog.close();
//                btnOpenAddTypeForm.setDisable(false);
//            });
        });
        btnOpenImportTypeForm.setOnAction((event) -> {
            try {
                loadComboBox();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            creatDialog(btnOpenImportTypeForm, addImportTypeForm, mainMenuStackPane);
        });
        btnOpenAddUnitForm.setOnAction((event) -> {
            creatDialog(btnOpenAddUnitForm, addUntiForm, mainMenuStackPane);
        });
        txtTenLoaiMon.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemLoaiMon.fire();
            }
        });
        
        txtDVT.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemDVT.fire();
            }
        });
        
        btnThemLoaiMon.setOnAction((event) -> {
            try {
                if(!txtTenLoaiMon.getText().toString().trim().isEmpty()) {
                    DBUtils_LoaiMon.insert(txtTenLoaiMon.getText().toString().trim());
                };
                
                ObservableList<LoaiMon> listMon = FXCollections.observableArrayList(DBUtils_LoaiMon.getList());
                if(!listMon.isEmpty()) {
                    tbLoaiMon.getItems().clear();
                    tbLoaiMon.setItems(listMon);
                };
                JFXDialog dialog = (JFXDialog) mainMenuStackPane.getChildren().get(2);
                btnOpenAddTypeForm.setDisable(false);
                dialog.close();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
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
            };
            JFXDialog dialog = (JFXDialog) mainMenuStackPane.getChildren().get(2);
            btnOpenAddUnitForm.setDisable(false);
            dialog.close();
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
                try {
                    DBUtils_LoaiNX.insert(tenLoaiNX, dvtSelected.getIdDVT());
                    
                    ObservableList<InsertNX> listLoai = FXCollections.observableArrayList(DBUtils_LoaiNX.getListNX());
                    
                    if(!listLoai.isEmpty()) {
                        tbLoaiNX.getItems().clear();
                        tbLoaiNX.setItems(listLoai);
                    };
                    JFXDialog dialog = (JFXDialog) mainMenuStackPane.getChildren().get(2);
                    btnOpenImportTypeForm.setDisable(false);
                    dialog.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        btnThemMenu.setOnAction((event) -> {
            String tenMon = txtTenMonMenu.getText().toString().trim();
            
            if(!tenMon.isEmpty() && donGiaMenu > 0 && loaiMenu != null) {
                try {
                    DBUtils_Mon.insert(tenMon, donGiaMenu, loaiMenu.getId());
                    txtTenLoaiMon.clear();
                    txtDonGiaMenu.clear();
                    
                    ObservableList<Menu> listLoai = FXCollections.observableArrayList(DBUtils_Mon.getListMenu());
                    
                    if(!listLoai.isEmpty()) {
                        tbMenu.getItems().clear();
                        tbMenu.setItems(listLoai);
                    }
                    
                    JFXDialog dialog = (JFXDialog) mainMenuStackPane.getChildren().get(2);
                    dialog.close();
                    btnOpenAddForm.setDisable(false);
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void loadComboBox() throws SQLException {
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
    
    private void chooseTab() throws SQLException {
        loadTableMenu();
        loadComboBox();
        loadListViewDVT();
        loadTableNX();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableLoaiMon();
        try {
            chooseTab();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setClick();
    }    
    
}

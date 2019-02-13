/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerNV;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutDown;
import animatefx.animation.Tada;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import managerMenu.FXMLManagerMenuController;
import tqduy.MD5.MD5Library;
import tqduy.bean.InsertNX;
import tqduy.bean.Role;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_LoaiNX;
import tqduy.connect.DBUtils_NhanVien;
import tqduy.connect.DBUtils_Role;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLNhanVienController implements Initializable {

    @FXML
    private JFXButton btnThemNV, btnThemVaiTro;
    @FXML
    private TextField txtTenVaiTro, txtUserName;
    @FXML
    private TextField txtPassWord;
    @FXML
    private ComboBox<Role> cbVaiTro;
    @FXML
    private TableView<Role> tbRole;
    @FXML
    private TableColumn<Role, Integer> tbIDVaiTroColumn;
    @FXML
    private TableColumn<Role, String> tbTenVaiTroColumn;
    @FXML
    private TableColumn<Role, Boolean> tbIsActiveVaiTroColumn;
    @FXML
    private TableView<NhanVien> tbNhanVien;
    @FXML
    private TableColumn<NhanVien, String> tbUserNameNVColumn;
    @FXML
    private TableColumn<NhanVien, String> tbVaiTroNVColumn;
    @FXML
    private TableColumn<NhanVien, Boolean> tbIsActiveNVColumn;

    private Role roleSelected = new Role();
    @FXML
    private VBox addStaffForm;
    @FXML
    private JFXButton btnCloseAddMenu;
    @FXML
    private VBox addRoleForm;
    @FXML
    private StackPane mainStackStaff;
    @FXML
    private JFXButton btnOpenAddStaffForm;
    @FXML
    private JFXButton btnOpenAddRoleForm;
    @FXML
    private StackPane deleteStaffStackPane;
    @FXML
    private JFXButton deleteStaffBtn;
    @FXML
    private StackPane deleteRoleStackPane;
    @FXML
    private JFXButton deleteRoleBtn;

    private void showTableRole() throws SQLException {
        deleteRoleStackPane.setVisible(false);
        tbRole.getColumns().clear();
        tbRole.setEditable(true);
        tbRole.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbIDVaiTroColumn.setText("STT");
        tbTenVaiTroColumn.setText("Loại");
        tbIsActiveVaiTroColumn.setText("Active");
        tbIsActiveVaiTroColumn.getStyleClass().add("align-center");

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
        tbRole.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            Role item = tbRole.getSelectionModel().getSelectedItem();

            if (item == null) {
//                deleteTypeStackPane.setVisible(false);
                toggleVisible(deleteRoleStackPane, false);
            } else {
                ObservableList<Role> items = tbRole.getSelectionModel().getSelectedItems();
                deleteRoleBtn.setOnAction((event) -> {
                    String count = items.size() > 1 ? items.size() + " selected items" : "selected item";
                    try {
                        creatDialog("Do you want to delete " + count + " ?", "warning");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JFXButton btn = (JFXButton) mainStackStaff.lookup("#btnExecute");
                    btn.setOnAction((eventt) -> {
                        items.forEach((t) -> {
                            DBUtils_Role.delete(t.getIdRole());
                        });
                        try {
                            showTableRole();
                            ((JFXDialog) mainStackStaff.getChildren().get(1)).close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                });
                tbNhanVien.getSelectionModel().clearSelection();
                toggleVisible(deleteRoleStackPane, true);
            }
        });
        ObservableList<Role> list = FXCollections.observableArrayList(DBUtils_Role.getList());
        System.out.println("List: " + list);
        if (!list.isEmpty()) {
            tbRole.setItems(list);
            tbRole.getColumns().addAll(tbIDVaiTroColumn, tbTenVaiTroColumn, tbIsActiveVaiTroColumn);
        }
    }
    
    private void toggleVisible(Node node, Boolean visible) {
        if (visible) {
            if (node.getUserData() != null) {
                ((FadeOutDown) node.getUserData()).stop();
            }
            if (node.visibleProperty().getValue() == false || (node.getUserData() != null)) {
                new FadeInUp(node).setSpeed(2.0).play();
                node.setVisible(true);
                node.setUserData(null);
            }
        } else {
            FadeOutDown down = new FadeOutDown();
            down.setNode(node);
            down.setSpeed(2.0);
            down.isResetOnFinished();
            down.setOnFinished((event) -> {
                node.setVisible(false);
            });
            down.play();
            node.setUserData(down);
        }
    }
    
    private void creatDialog(String text, String type) throws IOException {
        String url;
        switch (type) {
            case "succes":
                url = "/dialog/popupSuccess.fxml";
                break;
            case "danger":
                url = "/dialog/popupDanger.fxml";
                break;
            default:
                url = "/dialog/popupWarning.fxml";
        }
        Region dialogContent = FXMLLoader.load(getClass().getResource(url));
        JFXDialog dialog = new JFXDialog(mainStackStaff, dialogContent, JFXDialog.DialogTransition.CENTER);
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

    private void showTBNV() throws SQLException {
        deleteStaffStackPane.setVisible(false);
        tbNhanVien.getColumns().clear();
        tbNhanVien.setEditable(true);
        tbNhanVien.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbUserNameNVColumn.setText("User Name");
        tbVaiTroNVColumn.setText("Vai Trò");
        tbIsActiveNVColumn.setText("Active");
        tbIsActiveNVColumn.getStyleClass().add("align-center");

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
        tbNhanVien.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            NhanVien item = tbNhanVien.getSelectionModel().getSelectedItem();

            if (item == null) {
//                deleteTypeStackPane.setVisible(false);
                toggleVisible(deleteStaffStackPane, false);
            } else {
                ObservableList<NhanVien> items = tbNhanVien.getSelectionModel().getSelectedItems();
                deleteStaffBtn.setOnAction((event) -> {
                    String count = items.size() > 1 ? items.size() + " selected items" : "selected item";
                    try {
                        creatDialog("Do you want to delete " + count + " ?", "warning");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JFXButton btn = (JFXButton) mainStackStaff.lookup("#btnExecute");
                    btn.setOnAction((eventt) -> {
                        items.forEach((t) -> {
                            DBUtils_NhanVien.delete(t.getIdNV());
                        });
                        try {
                            showTBNV();
                            ((JFXDialog) mainStackStaff.getChildren().get(1)).close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                });
                tbRole.getSelectionModel().clearSelection();
                toggleVisible(deleteStaffStackPane, true);
            }
        });
        ObservableList<NhanVien> list = FXCollections.observableArrayList(DBUtils_NhanVien.getList());
        System.out.println("List: " + list);
        if (!list.isEmpty()) {
            tbNhanVien.setItems(list);
            tbNhanVien.getColumns().addAll(tbUserNameNVColumn, tbVaiTroNVColumn, tbIsActiveNVColumn);
        }
    }

    private void loadComboBox() throws SQLException {
        ObservableList<Role> listLoai = FXCollections.observableArrayList();

        for (Role role : DBUtils_Role.getList()) {
            if (role.isActive()) {
                listLoai.add(role);
            }
        }

        if (!listLoai.isEmpty()) {
            cbVaiTro.getItems().clear();
            cbVaiTro.setItems(listLoai);
        }
    }

    private void setEventClick() {
        btnOpenAddRoleForm.setOnAction((event) -> {
            creatDialog(btnOpenAddRoleForm, addRoleForm, mainStackStaff);
            if (addRoleForm.lookup("#errorText") != null) {
                    addRoleForm.getChildren().remove(addRoleForm.getChildren().size() - 2);
            }
        });

        btnOpenAddStaffForm.setOnAction((event) -> {
            creatDialog(btnOpenAddStaffForm, addStaffForm, mainStackStaff);
            if (addStaffForm.lookup("#errorText") != null) {
                    addStaffForm.getChildren().remove(addStaffForm.getChildren().size() - 2);
            }
        });

        txtTenVaiTro.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemVaiTro.fire();
            }
        });

        btnThemVaiTro.setOnAction((event) -> {
            String ten = txtTenVaiTro.getText().toString().trim();
            if (addRoleForm.lookup("#errorText") != null) {
                    addRoleForm.getChildren().remove(addRoleForm.getChildren().size() - 2);
            }
            if (!ten.isEmpty()) {
                Optional<ButtonType> result = createAlert("Do you want add ?");

                if (result.get() == ButtonType.OK) {
                    try {
                        DBUtils_Role.insert(ten);
                        ObservableList<Role> list = FXCollections.observableArrayList(DBUtils_Role.getList());
                        System.out.println("List: " + list);
                        if (!list.isEmpty()) {
                            tbRole.getItems().clear();
                            tbRole.setItems(list);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                JFXDialog dialog = (JFXDialog) mainStackStaff.getChildren().get(1);
                btnOpenAddRoleForm.setDisable(false);
                dialog.close();
            } else {
                addErrorText(addRoleForm, "All fields are required !");
            }
        });

        cbVaiTro.setOnAction((event) -> {
            roleSelected = cbVaiTro.getSelectionModel().getSelectedItem();
            System.out.println(roleSelected.getIdRole() + " - " + roleSelected.getRoleName());
        });

        btnThemNV.setOnAction((event) -> {
            String userName = txtUserName.getText().toString().trim();
            String passWord = txtPassWord.getText().toString().trim();
            if (addStaffForm.lookup("#errorText") != null) {
                    addStaffForm.getChildren().remove(addStaffForm.getChildren().size() - 2);
            }
            if (!userName.isEmpty() && !passWord.isEmpty() && roleSelected.getIdRole() != -1) {
                String pass = MD5Library.md5(passWord);
                try {
                    DBUtils_NhanVien.insert(userName, pass, roleSelected.getIdRole());
                    System.out.println(userName + " - " + passWord + " - " + roleSelected.getIdRole());

                    ObservableList<NhanVien> list = FXCollections.observableArrayList(DBUtils_NhanVien.getList());
                    System.out.println("List: " + list);
                    if (!list.isEmpty()) {
                        tbNhanVien.getItems().clear();
                        tbNhanVien.setItems(list);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
                JFXDialog dialog = (JFXDialog) mainStackStaff.getChildren().get(1);
                btnOpenAddStaffForm.setDisable(false);
                dialog.close();
            } else {
                addErrorText(addStaffForm, "All fields are required !");
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

    private void chooseTab() throws SQLException {
        showTableRole();
        showTBNV();
        loadComboBox();
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
    
    private void addErrorText(VBox vbox, String text) {
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: #e84545; -fx-font-weight: bold");
        HBox hbox = new HBox(textLabel);
        textLabel.setId("errorText");
        hbox.setUserData("error");
        vbox.getChildren().add(vbox.getChildren().size() - 1, hbox);
        new Tada(textLabel).setSpeed(1.5).play();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roleSelected.setIdRole(-1);
        try {
            // TODO
            showTableRole();
            chooseTab();
            setEventClick();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLNhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

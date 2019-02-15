/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerCus;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeInUp;
import animatefx.animation.FadeOutDown;
import animatefx.animation.Tada;
import bill.FXMLBillController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import login.Login;
import managerMenu.FXMLManagerMenuController;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tqduy.bean.Bill;
import tqduy.bean.CusMember;
import tqduy.bean.Member;
import tqduy.bean.Role;
import tqduy.connect.DBUtils_Bill;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_Member;
import tqduy.connect.DBUtils_Role;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLCustomerController implements Initializable {
    @FXML private TextField txtTenLoaiMember, txtDiscountMember;
    @FXML private JFXButton btnThemMember;
    @FXML private TableView<Member> tbMember;
    @FXML private TableColumn<Member, Integer> tbIDMemberColumn;
    @FXML private TableColumn<Member, String> tbLoaiMemberColumn;
    @FXML private TableColumn<Member, Integer> tbDiscountMemberColumn;
    @FXML private TableColumn<Member, Boolean> tbActiveMemberColumn;
    
    private int discount = 0;
    @FXML
    private VBox addTypeForm;
    @FXML
    private JFXButton btnCloseAddMenu;
    @FXML
    private StackPane memberTypeStackPane;
    @FXML
    private JFXButton btnOpenAddForm;
    private StackPane mainStackPane;
    @FXML
    private StackPane mainMemberTypeStackPane;
    @FXML private TableView<CusMember> tbCus;
    @FXML private TableColumn<CusMember, String> tbTenNVCusColumn;
    @FXML private TableColumn<CusMember, String> tbVaiTroCusColumn;
    @FXML private TableColumn<CusMember, String> tbTenCusColumn;
    @FXML private TableColumn<CusMember, String> tbSDTCusColumn;
    @FXML private TableColumn<CusMember, String> tbLoaiMemberCusColumn;
    @FXML private TableColumn<CusMember, Date> tbNgayLapCusColumn;
    @FXML private JFXButton btnInThongTin;
    @FXML private VBox mainCusScreenVBox;
    @FXML
    private StackPane mainCusStackPane;
    @FXML
    private StackPane deleteCusStackPane;
    @FXML
    private JFXButton deleteCusBtn;
    @FXML
    private StackPane deleteMemTypeStackPane;
    @FXML
    private JFXButton deleteMemTypeBtn;
    
    private void setEvent() {
        btnInThongTin.setOnAction((event) -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File f = chooser.showDialog(Login.getStage());
            if(f != null){
                System.out.println(f.getAbsolutePath());
                try {
                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet("Customer Statistical");
                    XSSFRow header = sheet.createRow(0);
                    header.createCell(0).setCellValue("Customer Name");
                    header.createCell(1).setCellValue("Phone Number");
                    header.createCell(2).setCellValue("Member Type");
                    header.createCell(3).setCellValue("Created Date");
                    header.createCell(4).setCellValue("Staff");
                    header.createCell(5).setCellValue("Role");
                    sheet.autoSizeColumn(0);
                    sheet.autoSizeColumn(1);
                    sheet.autoSizeColumn(2);
                    sheet.autoSizeColumn(3);
                    sheet.autoSizeColumn(4);
                    sheet.autoSizeColumn(5);
                    ArrayList<CusMember> list = DBUtils_CusMember.getList();
                    int index = 1;
                    for (int i = 0; i < list.size(); i++) {
                        CusMember get = list.get(i);
                        XSSFRow row = sheet.createRow(i+1);
                        row.createCell(0).setCellValue(get.getTenCus());
                        row.createCell(1).setCellValue(get.getSdt());
                        row.createCell(2).setCellValue(get.getLoaiMember());
                        row.createCell(3).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(get.getNgayLap()));
                        row.createCell(4).setCellValue(get.getTenNhanVien());
                        row.createCell(5).setCellValue(get.getVaiTro());
                    }
                    FileOutputStream fileOut = new FileOutputStream(f.getPath() + "/customer-static.xlsx");
                    wb.write(fileOut);
                    fileOut.close();
                    creatDialog("Export file successfull at :\n"+ f.getPath() + "\\" + "customer-static.xlsx", "success");
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnOpenAddForm.setOnAction((event) -> {
            creatDialog(btnOpenAddForm, addTypeForm, mainCusStackPane);
            if (addTypeForm.lookup("#errorText") != null) {
                    addTypeForm.getChildren().remove(addTypeForm.getChildren().size() - 2);
            }
        });
        txtDiscountMember.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) {
                try {
                    discount = Integer.parseInt(newValue);
                } catch (Exception e) {

                }
            } else {
                txtDiscountMember.setText(oldValue);
            }
        });
        
        txtDiscountMember.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnThemMember.fire();
            }
        });
        
        btnThemMember.setOnAction((event) -> {
            String tenLoai = txtTenLoaiMember.getText().toString().trim();
            if (addTypeForm.lookup("#errorText") != null) {
                    addTypeForm.getChildren().remove(addTypeForm.getChildren().size() - 2);
            }
            if (tenLoai.isEmpty() || discount == 0 || "".equals(txtDiscountMember.getText().trim())) {
                 addErrorText(addTypeForm, "All fields are required !");
            } else if (discount > 100) {
                addErrorText(addTypeForm, "Discount percent must be equal or smaller than 100 !");
            } else {
                System.out.println(tenLoai + " - " + discount);
                DBUtils_Member.insert(tenLoai, discount);
                ObservableList<Member> list = null;
                try {
                    list = FXCollections.observableArrayList(DBUtils_Member.getList());
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("List: " + list);
                if(!list.isEmpty()) {
                    tbMember.getItems().clear();
                    tbMember.setItems(list);
                }
                JFXDialog dialog = (JFXDialog) mainCusStackPane.getChildren().get(1);
                btnOpenAddForm.setDisable(false);
                dialog.close();
            }
        });
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
    
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        System.out.println(result.get().getText());
    }
    
    private void showTBMember() throws SQLException {
        deleteMemTypeStackPane.setVisible(false);
        tbMember.getColumns().clear();
        tbMember.setEditable(true);
        tbMember.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbIDMemberColumn.setText("No.");
        tbLoaiMemberColumn.setText("Type Name");
        tbDiscountMemberColumn.setText("Discount");
        tbActiveMemberColumn.setText("Active");
        tbActiveMemberColumn.getStyleClass().add("align-center");
        
        tbIDMemberColumn.setCellValueFactory(new PropertyValueFactory<>("idMember"));
        tbLoaiMemberColumn.setCellValueFactory(new PropertyValueFactory<>("loai"));
        tbDiscountMemberColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        tbActiveMemberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Member, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Member, Boolean> param) {
                Member member = param.getValue();
                
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(member.isActive());
                
                booleanProperty.addListener((observable, oldValue, newValue) -> {
                    member.setActive(newValue);
                    System.out.println(member.getIdMember() + " - " + member + " - " + member.isActive());
                    DBUtils_Member.update(member.getIdMember(), member.isActive());
                });
                return booleanProperty;
            }
        });
        
        tbActiveMemberColumn.setCellFactory(new Callback<TableColumn<Member, Boolean>, TableCell<Member, Boolean>>() {
            @Override
            public TableCell<Member, Boolean> call(TableColumn<Member, Boolean> param) {
                CheckBoxTableCell<Member, Boolean> cell = new CheckBoxTableCell<>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
        tbMember.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            Member item = tbMember.getSelectionModel().getSelectedItem();

            if (item == null) {
//                deleteTypeStackPane.setVisible(false);
                toggleVisible(deleteMemTypeStackPane, false);
            } else {
                ObservableList<Member> items = tbMember.getSelectionModel().getSelectedItems();
                deleteMemTypeBtn.setOnAction((event) -> {
                    String count = items.size() > 1 ? items.size() + " selected items" : "selected item";
                    try {
                        creatDialog("Do you want to delete " + count + " ?", "warning");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JFXButton btn = (JFXButton) mainCusStackPane.lookup("#btnExecute");
                    btn.setOnAction((eventt) -> {
                        items.forEach((t) -> {
                            DBUtils_Member.delete(t.getIdMember());
                        });
                        try {
                            showTBMember();
                            ((JFXDialog) mainCusStackPane.getChildren().get(1)).close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                });
                tbCus.getSelectionModel().clearSelection();
                toggleVisible(deleteMemTypeStackPane, true);
            }
        });
        ObservableList<Member> list = FXCollections.observableArrayList(DBUtils_Member.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbMember.setItems(list);
            tbMember.getColumns().addAll(tbIDMemberColumn, tbLoaiMemberColumn, tbDiscountMemberColumn, tbActiveMemberColumn);
        }
    }
    
    private void showTBCusMem() throws SQLException {
        deleteCusStackPane.setVisible(false);
        System.out.println("Hello ");
        tbCus.getColumns().clear();
        tbCus.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbTenNVCusColumn.setText("Staff");
        tbVaiTroCusColumn.setText("Role");
        tbTenCusColumn.setText("Customer Name");
        tbSDTCusColumn.setText("Phone Number");
        tbLoaiMemberCusColumn.setText("Member Type");
        tbNgayLapCusColumn.setText("Created Date");
        
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
        tbCus.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            CusMember item = tbCus.getSelectionModel().getSelectedItem();

            if (item == null) {
//                deleteTypeStackPane.setVisible(false);
                toggleVisible(deleteCusStackPane, false);
            } else {
                ObservableList<CusMember> items = tbCus.getSelectionModel().getSelectedItems();
                deleteCusBtn.setOnAction((event) -> {
                    String count = items.size() > 1 ? items.size() + " selected items" : "selected item";
                    try {
                        creatDialog("Do you want to delete " + count + " ?", "warning");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JFXButton btn = (JFXButton) mainCusStackPane.lookup("#btnExecute");
                    btn.setOnAction((eventt) -> {
                        items.forEach((t) -> {
                            System.out.println("idCus: "+ t.getIdCus());
                            DBUtils_CusMember.delete(t.getIdCus());
                        });
                        try {
                            showTBCusMem();
                            ((JFXDialog) mainCusStackPane.getChildren().get(1)).close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLManagerMenuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                });
                tbMember.getSelectionModel().clearSelection();
                toggleVisible(deleteCusStackPane, true);
            }
        });
        ObservableList<CusMember> list = FXCollections.observableArrayList(DBUtils_CusMember.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbCus.setItems(list);
            tbCus.getColumns().addAll(tbTenCusColumn, tbSDTCusColumn, tbLoaiMemberCusColumn, tbNgayLapCusColumn, tbTenNVCusColumn, tbVaiTroCusColumn);
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
            case "success":
                url = "/dialog/popupSuccess.fxml";
                break;
            case "danger":
                url = "/dialog/popupDanger.fxml";
                break;
            default:
                url = "/dialog/popupWarning.fxml";
        }
        Region dialogContent = FXMLLoader.load(getClass().getResource(url));
        JFXDialog dialog = new JFXDialog(mainCusStackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
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
        try {
            // TODO
            showTBMember();
            showTBCusMem();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            Node Member = FXMLLoader.load(getClass().getResource("/managerMember/FXMLMember.fxml"));
//            mainCusScreenVBox.getChildren().add(Member);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        setEvent();
    }    
    
}

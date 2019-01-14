/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managerMember;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import tqduy.bean.Member;
import tqduy.connect.DBUtils_Member;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLMemberController implements Initializable {
    @FXML private JFXTextField txtTenLoaiMember, txtDiscountMember;
    @FXML private JFXButton btnThemMember;
    @FXML private TableView<Member> tbMember;
    @FXML private TableColumn<Member, Integer> tbIDMemberColumn;
    @FXML private TableColumn<Member, String> tbLoaiMemberColumn;
    @FXML private TableColumn<Member, Integer> tbDiscountMemberColumn;
    @FXML private TableColumn<Member, Boolean> tbActiveMemberColumn;
    
    private int discount = 0;
    
    private void showTBMember() throws SQLException {
        tbMember.getColumns().clear();
        tbMember.setEditable(true);
        
        tbIDMemberColumn.setText("STT");
        tbLoaiMemberColumn.setText("Tên Loại");
        tbDiscountMemberColumn.setText("Giảm Giá");
        tbActiveMemberColumn.setText("Active");
        
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
        
        ObservableList<Member> list = FXCollections.observableArrayList(DBUtils_Member.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbMember.setItems(list);
            tbMember.getColumns().addAll(tbIDMemberColumn, tbLoaiMemberColumn, tbDiscountMemberColumn, tbActiveMemberColumn);
        }
    }
    
    private void setEventClicked() {
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
        
        btnThemMember.setDisable(true);
        txtDiscountMember.textProperty().addListener((observable, oldValue, newValue) -> {
            btnThemMember.setDisable(newValue.trim().isEmpty());
        });
        
        btnThemMember.setOnAction((event) -> {
            String tenLoai = txtTenLoaiMember.getText().toString().trim();
            if(!tenLoai.isEmpty() && discount < 100) {
                System.out.println(tenLoai + " - " + discount);
                DBUtils_Member.insert(tenLoai, discount);
                
                ObservableList<Member> list = null;
                try {
                    list = FXCollections.observableArrayList(DBUtils_Member.getList());
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLMemberController.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("List: " + list);
                if(!list.isEmpty()) {
                    tbMember.getItems().clear();
                    tbMember.setItems(list);
                }
            } else {
                createAlert("Hãy Nhập đầy đủ và giảm giá < 100");
            }
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showTBMember();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setEventClicked();
    }    
    
}

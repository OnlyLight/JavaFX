/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialog;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sale.FXMLDocumentController;
import tqduy.bean.Mon;
import tqduy.bean.MonOrder;
import tqduy.connect.DBUtils_LoaiMon;
import tqduy.connect.DBUtils_MonOrder;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLDialogController implements Initializable {
    @FXML
    private TextField txtID, txtPrice, txtCategory, txtProductName, txtAmount;
    
    @FXML
    private Button btnAdd, btnCancel;
    
    @FXML
    private Label lbThongBao;
    
    private int amount = 1;
    private Mon mon = FXMLDocumentController.monInfo;
    public static MonOrder monOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showInfo();
        checkInput();
        eventClick();
    }    
    
    private void showInfo() {
        System.out.println(mon);
        checkInput();
        
        txtID.setText(String.valueOf(mon.getIdMon()));
        txtPrice.setText(String.valueOf(mon.getDonGia()));
        txtProductName.setText(String.valueOf(mon.getTenMon()));
        txtAmount.setText(String.valueOf(amount));
        
        int idLoaiMon = mon.getIdLoaiMon();
        
        String tenLoai = DBUtils_LoaiMon.getMon(idLoaiMon).getLoaiMon();
        txtCategory.setText(String.valueOf(tenLoai));
    }
    
    private void checkInput() {
        txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                try {
                    amount = Integer.parseInt(newValue);
                } catch (Exception e) {
                    
                }
            } else {
                txtAmount.setText(oldValue);
            }
        });
    }
    
    private void getData() {
        int id = mon.getIdMon();
        String tenMon = mon.getTenMon();
        int donGia = mon.getDonGia();
        String loaiMon = txtCategory.getText().toString().trim();
        int soLuong = Integer.parseInt(txtAmount.getText().toString().trim());

        monOrder = new MonOrder(id, tenMon, donGia, loaiMon, soLuong);
        System.out.println(monOrder);
    }
    
    private void eventClick(){
        btnAdd.setOnAction((event) -> {
            getData();
            
            closeStage(btnAdd);
            MonOrder m = new MonOrder();
            
            if(!checkID()) {
                DBUtils_MonOrder.insert(mon.getIdMon(), amount);
            }
            else {
                createAlert("Sản phẩm đã tồn tại");
            }
            
            try {
                showDialog();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnCancel.setOnAction((event) -> {
            closeStage(btnCancel);
            try {
                showDialog();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDialogController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private boolean checkID() {
        for (MonOrder mOrder : DBUtils_MonOrder.getList()) {
            if(mOrder.getId() == mon.getIdMon()) {
                return true;
            }
        }
        return false;
    }
    
    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        System.out.println("Close");
    }
    
    private void showDialog() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sale/FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Hi! Coffee");
        
        stage.setScene(scene);
        stage.show();
    }
}

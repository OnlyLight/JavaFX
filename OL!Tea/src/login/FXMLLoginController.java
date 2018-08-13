/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_MonOrder;
import tqduy.connect.DBUtils_NhanVien;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLLoginController implements Initializable {
    @FXML private JFXTextField txtUserName;
    @FXML private JFXPasswordField txtPassWord;
    @FXML private JFXButton btnLogin, btnExit;
    
    public static NhanVien nvLogin;
    
    private void setEventClick() {
        txtPassWord.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                System.out.println("Hello Enter");
                btnLogin.fire();
            }
        });
        btnLogin.setDisable(true);
        txtUserName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnLogin.setDisable(newValue.trim().isEmpty());
        });
        
        ArrayList<NhanVien> nvs = DBUtils_NhanVien.getListForCheck();
        btnLogin.setOnAction((event) -> {
            String user = txtUserName.getText().toString().trim();
            String pass = txtPassWord.getText().toString().trim();
            nvLogin = new NhanVien();
            
            if(checkUser(nvs, user, pass)) {
                try {
                    showDialog("/sale/FXMLDocument.fxml", StageStyle.DECORATED, Modality.NONE);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                closeStage(btnLogin);
            } else {
                createAlert("User hoặc passWord không đúng");
            }
        });
        
        btnExit.setOnAction((event) -> {
            System.exit(0);
        });
    }
    
    private boolean checkUser(ArrayList<NhanVien> nvs, String user, String pass) {
        for (NhanVien nv : nvs) {
            if(user.equals(nv.getUserName()) && pass.equals(nv.getPassWord())) {
                nvLogin.setIdNV(nv.getIdNV());
                nvLogin.setUserName(nv.getUserName());
                nvLogin.setPassWord(nv.getPassWord());
                nvLogin.setIdRoleName(nv.getIdRoleName());
                return true;
            }
        }
        return false;
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
    
    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        System.out.println("Close");
    }
    
    private void showDialog(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage(style);
        stage.initModality(modal);
        
        stage.getIcons().add(new Image("/images/icon.jpg"));
        
        stage.setOnCloseRequest((event) -> {
            System.out.println("Delete MonOrder");
            DBUtils_MonOrder.deleteAll();
        });
        
        stage.setTitle("OL! Tea");
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setEventClick();
    }    
    
}

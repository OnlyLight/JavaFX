/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_NhanVien;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLLoginController implements Initializable {
    @FXML private TextField txtUserName;
    @FXML private PasswordField txtPassWord;
    @FXML private Button btnLogin;
    
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
            
            for (NhanVien nv : nvs) {
                if(user.equals(nv.getUserName()) && pass.equals(nv.getPassWord())) {
                    nvLogin.setUserName(nv.getUserName());
                    nvLogin.setPassWord(nv.getPassWord());
                    nvLogin.setIdNV(nv.getIdNV());
                    try {
                        showDialog("/sale/FXMLDocument.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    closeStage(btnLogin);
                    break;
                }
            }
        });
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

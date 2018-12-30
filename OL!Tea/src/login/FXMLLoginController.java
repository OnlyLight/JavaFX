/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import insidefx.undecorator.UndecoratorScene;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.ScaleTransition;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tqduy.MD5.MD5Library;
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
    @FXML private JFXButton btnLogin;
    double x,y;
    
    public static NhanVien nvLogin;
    @FXML
    private StackPane mainLoginScreen;
    
    private void showDialog(String text) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Infomation"));
        content.setBody(new Text(text));
        JFXDialog dialog = new JFXDialog(mainLoginScreen, content, JFXDialog.DialogTransition.CENTER);
        JFXButton btnConfirm = new JFXButton("OK");
        btnConfirm.setStyle("-fx-background-color: linear-gradient(to left, #00adb5, #00ccd3);\n" +
        "-fx-cursor: hand; -fx-text-fill: #fff; -fx-font-weight: bold;");
        btnConfirm.setOnAction((eventt) -> {
            dialog.close();
        });
        btnConfirm.defaultButtonProperty().bind(btnConfirm.focusedProperty());
        content.setActions(btnConfirm);
        dialog.show();
    }
    
    private void setEventClick() {
        txtUserName.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
        txtPassWord.setOnKeyPressed((event) -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
//        btnLogin.setDisable(true);
//        txtUserName.textProperty().addListener((observable, oldValue, newValue) -> {
//            btnLogin.setDisable(newValue.trim().isEmpty());
//        });
//        
        ArrayList<NhanVien> nvs = DBUtils_NhanVien.getListForCheck();
        btnLogin.setOnAction((event) -> {
            if (mainLoginScreen.getChildren().size() == 1) {
                String user = txtUserName.getText().toString().trim();
                String pass = MD5Library.md5(txtPassWord.getText().toString().trim());
                nvLogin = new NhanVien();
                if (user.length() == 0 || txtPassWord.getText().toString().trim().length() == 0) {
                    showDialog("Please enter username, password!");
                } else if(checkUser(nvs, user, pass)) {
                    closeStage();
                } else {
                    showDialog("Wrong username or password!");
                }
            } else {
                ((JFXDialog) mainLoginScreen.getChildren().get(1)).close();
            }
        });
    }
    
    private boolean checkUser(ArrayList<NhanVien> nvs, String user, String pass) {
        for (NhanVien nv : nvs) {
            if(nv.isActive()) {
                if(user.equals(nv.getUserName()) && pass.equals(nv.getPassWord())) {
                    nvLogin.setIdNV(nv.getIdNV());
                    nvLogin.setUserName(nv.getUserName());
                    nvLogin.setPassWord(nv.getPassWord());
                    nvLogin.setIdRoleName(nv.getIdRoleName());
                    return true;
                }
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
    
    private void closeStage() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sale/FXMLDocument.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage stage = Login.getStage();
        stage.close();
        UndecoratorScene scene = new UndecoratorScene(stage, (Region) root);
        stage.setScene(scene);
        stage.show();
        new FadeIn(root).play();
    }
    
    private void showDialog(String url, StageStyle style, Modality modal) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        
        Stage stage = new Stage(style);
        stage.initModality(modal);
        
        stage.getIcons().add(new Image("/images/download.jpg"));
        
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setEventClick();
        btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());
    }    
    
}

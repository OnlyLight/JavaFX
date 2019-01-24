/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import insidefx.undecorator.UndecoratorScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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

    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXPasswordField txtPassWord;
    @FXML
    private JFXButton btnLogin;
    double x, y;

    public static NhanVien nvLogin;
    @FXML
    private StackPane mainLoginScreen;

    private void showDialog(String text) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Infomation"));
        content.setBody(new Text(text));
        JFXDialog dialog = new JFXDialog(mainLoginScreen, content, JFXDialog.DialogTransition.NONE);
        JFXButton btnConfirm = new JFXButton("OK");
        btnConfirm.setStyle("-fx-background-color: linear-gradient(to left, #00adb5, #00ccd3);\n"
                + "-fx-cursor: hand; -fx-text-fill: #fff; -fx-font-weight: bold;");
        btnConfirm.setOnAction((eventt) -> {
            dialog.close();
        });
        btnConfirm.defaultButtonProperty().bind(btnConfirm.focusedProperty());
        content.setActions(btnConfirm);
        dialog.show();
    }

    private void creatDialog(String text, String type) throws IOException {
        String url = "success".equals(type) ? "/dialog/popupSuccess.fxml" : "/dialog/popupDanger.fxml";
        Region dialogContent = FXMLLoader.load(getClass().getResource(url));
        JFXDialog dialog = new JFXDialog(mainLoginScreen, dialogContent, JFXDialog.DialogTransition.CENTER);
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

    private void setEventClick() throws SQLException {
        txtUserName.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });

        txtPassWord.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
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
                new BounceIn(btnLogin).setSpeed(2.0).play();
                String user = txtUserName.getText().toString().trim();
                String pass = MD5Library.md5(txtPassWord.getText().toString().trim());
                nvLogin = new NhanVien();
                if (user.length() == 0 || txtPassWord.getText().toString().trim().length() == 0) {
                    try {
                        creatDialog("Please enter username, password!", "danger");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (checkUser(nvs, user, pass)) {
                    closeStage();
                } else {
                    try {
                        creatDialog("Wrong username or password!", "danger");
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JFXButton btnClose = (JFXButton) ((JFXDialog) mainLoginScreen.getChildren().get(1)).lookup("#btnClose");
                btnClose.fire();
            }
        });
    }

    private boolean checkUser(ArrayList<NhanVien> nvs, String user, String pass) {
        for (NhanVien nv : nvs) {
            if (nv.isActive()) {
                if (user.equals(nv.getUserName()) && pass.equals(nv.getPassWord())) {
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
        scene.getStylesheets().add(getClass().getResource("/css/global.css").toExternalForm());
        stage.setScene(scene);
        stage.setMaximized(true);
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            setEventClick();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import login.FXMLLoginController;
import sale.FXMLDocumentController;
import sale.OrderScreenController;
import tqduy.bean.CusMember;
import tqduy.bean.Member;
import tqduy.bean.NhanVien;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_DK;
import tqduy.connect.DBUtils_Member;
import tqduy.connect.DBUtils_MonOrder;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLSignInController implements Initializable {

    @FXML
    private JFXButton btnAdd;
    @FXML
    private ComboBox<Member> cbLoaiMember;
    @FXML
    private TextField txtSDT, txtTenCus;

    private LocalDate dateDK = LocalDate.now();
    private String sdt = OrderScreenController.sdt;
    private NhanVien nvLogin = FXMLLoginController.nvLogin;
    public static Member m = new Member();
    @FXML
    private VBox addMemberForm;
    @FXML
    private JFXButton btnCloseAddMenu;

    private void inputData() throws SQLException {
        ObservableList<Member> listLoai = FXCollections.observableArrayList();

        for (Member member : DBUtils_Member.getList()) {
            if (member.isActive()) {
                listLoai.add(member);
            }
        }

        if (!listLoai.isEmpty()) {
            cbLoaiMember.setItems(listLoai);
        }

        txtSDT.setText(sdt);
        txtSDT.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) {
                try {
                    sdt = newValue;
                } catch (Exception e) {

                }
            } else {
                txtSDT.setText(oldValue);
            }
        });
        cbLoaiMember.setOnAction((event) -> {
            m = cbLoaiMember.getSelectionModel().getSelectedItem();
        });
    }

    private void setClickBtn() {
        btnAdd.setDisable(true);
        txtTenCus.textProperty().addListener((observable, oldValue, newValue) -> {
            btnAdd.setDisable(newValue.trim().isEmpty());
        });

        btnAdd.setOnAction((event) -> {
            String ten = txtTenCus.getText().toString().trim();

            System.out.println("Ten: " + ten + " - sdt: " + sdt + " - NGay: " + dateDK + " - idMember: " + m.getIdMember() + " - " + m.getLoai());
            if (ten.isEmpty() || sdt.isEmpty() || dateDK == null || m == null) {
                createAlert("Hãy nhập đầy đủ thông tin !!");
            } else {
                
                try {
                    DBUtils_CusMember.insert(ten, sdt, m.getIdMember(), dateDK);
                    
                    CusMember cus = DBUtils_CusMember.getCusAddNew();
//                    System.out.println("IDNV: " + nvLogin.getIdNV() + " - " + cus.getIdCus());
                    DBUtils_DK.insert(nvLogin.getIdNV(), cus.getIdCus());
                    OrderScreenController.setSDT(sdt);
                    OrderScreenController.closeAddMemberForm();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        System.out.println("Close");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            inputData();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setClickBtn();
    }

}

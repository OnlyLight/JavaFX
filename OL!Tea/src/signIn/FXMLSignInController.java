/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signIn;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import sale.FXMLDocumentController;
import tqduy.bean.Member;
import tqduy.connect.DBUtils_CusMember;
import tqduy.connect.DBUtils_Member;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLSignInController implements Initializable {
    @FXML private Button btnAdd;
    @FXML private ComboBox<Member> cbLoaiMember;
    @FXML private DatePicker dpNgayDK;
    @FXML private TextField txtSDT, txtTenCus;
    
    private LocalDate dateDK = LocalDate.now();
    private String sdt = FXMLDocumentController.sdt;
    public static Member m = new Member();
            
    private void inputData() {
        ObservableList<Member> listLoai = FXCollections.observableArrayList();
        
        for (Member member : DBUtils_Member.getList()) {
            if(member.isActive()) {
                listLoai.add(member);
            }
        }
        
        if(!listLoai.isEmpty()) {
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
        
        setDayForDP();
        cbLoaiMember.setOnAction((event) -> {
            m = cbLoaiMember.getSelectionModel().getSelectedItem();
        });
    }
    
    private void setClickBtn() {
        btnAdd.setOnAction((event) -> {
            String ten = txtTenCus.getText().toString().trim();
            
            System.out.println("Ten: " + ten + " - sdt: " + sdt + " - NGay: " + dateDK + " - idMember: " + m.getIdMember() + " - " + m.getLoai());
            DBUtils_CusMember.insert(ten, sdt, m.getIdMember(), dateDK);
            try {
                showDialog("/sale/FXMLDocument.fxml", StageStyle.DECORATED, Modality.APPLICATION_MODAL);
            } catch (IOException ex) {
                Logger.getLogger(FXMLSignInController.class.getName()).log(Level.SEVERE, null, ex);
            }
            closeStage(btnAdd);
        });
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
    
    private void closeStage(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        System.out.println("Close");
    }
    
    private void setDayForDP() {
        dpNgayDK.setValue(dateDK);
        fomatterForDatePicker(dpNgayDK);
    }
    
    private void fomatterForDatePicker(DatePicker datePicker) {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        
        datePicker.setConverter(converter);
        datePicker.setPromptText("dd-MM-yyyy");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inputData();
        setClickBtn();
    }    
    
}

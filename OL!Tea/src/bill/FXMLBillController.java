/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bill;

import animatefx.animation.BounceIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import tqduy.bean.Bill;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import login.Login;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tqduy.connect.DBUtils_Bill;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLBillController implements Initializable {

    @FXML
    private AreaChart<String, Number> areaChart;
    @FXML
    private TableView<Bill> tbBill;
    @FXML
    private TableColumn<Bill, String> tbTenNVColumn;
    @FXML
    private TableColumn<Bill, Integer> tbTongTienColumn;
    @FXML
    private TableColumn<Bill, Date> tbNgayLapColumn;
    @FXML
    private JFXButton btnInThongTin;
    @FXML
    private StackPane mainStackPane;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;

    // EVENT FOR IN THONG TIN THONG KE
    private void setEvent() {
        btnInThongTin.setOnAction((event) -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File f = chooser.showDialog(Login.getStage());
            if(f != null){
                System.out.println(f.getAbsolutePath());
                try {
                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet("Bill Statistical");
                    XSSFRow header = sheet.createRow(0);
                    header.createCell(0).setCellValue("Staff");
                    header.createCell(1).setCellValue("Amount");
                    header.createCell(2).setCellValue("Created Date");
                    sheet.autoSizeColumn(0);
                    sheet.autoSizeColumn(1);
                    sheet.autoSizeColumn(2);
                    ArrayList<Bill> list = DBUtils_Bill.getList();
                    int index = 1;
                    for (int i = 0; i < list.size(); i++) {
                        Bill get = list.get(i);
                        XSSFRow row = sheet.createRow(i+1);
                        row.createCell(0).setCellValue(get.getTenNV());
                        row.createCell(1).setCellValue(get.getTongTien());
                        row.createCell(2).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(get.getNgayLap()));
                    }
                    FileOutputStream fileOut = new FileOutputStream(f.getPath() + "/bill-static.xlsx");
                    wb.write(fileOut);
                    fileOut.close();
                    creatDialog("Export file successfull at :\n"+ f.getPath() + "\\" + "bill-static.xlsx", "success");
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        JFXDialog dialog = new JFXDialog(mainStackPane, dialogContent, JFXDialog.DialogTransition.CENTER);
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

    // CREATE ALERT FOR NOTIFICATION
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
        }

        System.out.println(result.get().getText());
    }

    // SHOW DATA FOR TABLE
    private void showTable() throws SQLException {
        tbBill.getColumns().clear(); // CLEAR TABLE

        // SET NAME FOR COLUMN
        tbTenNVColumn.setText("Nhân Viên");
        tbTongTienColumn.setText("Tổng tiền");
        tbNgayLapColumn.setText("Ngày Lập");

        // SET POPERTY FOR COLUMN
        tbTenNVColumn.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        tbTongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        tbNgayLapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));

        // FORMAT DISPLAY FOR DATE
        tbNgayLapColumn.setCellFactory((column) -> {
            TableCell<Bill, Date> cell = new TableCell<Bill, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell; //To change body of generated lambdas, choose Tools | Templates.
        });

        // SET DATA FOR TABLE
        ObservableList<Bill> list = FXCollections.observableArrayList(DBUtils_Bill.getList());
        System.out.println("List: " + list);
        if (!list.isEmpty()) {
            tbBill.setItems(list);
            tbBill.getColumns().addAll(tbTenNVColumn, tbTongTienColumn, tbNgayLapColumn);
        }
    }

    // DISPLAY AREACHART
    private void showAreaChart() throws SQLException {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        // SET TITLE
        areaChart.setLegendSide(Side.LEFT);
        System.out.println("AreaChart");

        // SET DATA DISPLAY FOR LINE LAST YEAR
        XYChart.Series<String, Number> series2017 = new XYChart.Series<String, Number>();
        series2017.setName("" + (year - 1) + "");

        // SET DATA DISPLAY FOR LINE CURRENT YEAR
        XYChart.Series<String, Number> series2018 = new XYChart.Series<String, Number>();
        series2018.setName("" + year + "");

        for (int i = 1; i <= 12; i++) {
            series2017.getData().add(new XYChart.Data<String, Number>("" + i + "", DBUtils_Bill.getSumPirceForMonth(i, (year - 1))));
            series2018.getData().add(new XYChart.Data<String, Number>("" + i + "", DBUtils_Bill.getSumPirceForMonth(i, year)));
        }

        areaChart.getData().addAll(series2017, series2018);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showAreaChart();
            showTable();
            setEvent();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBillController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

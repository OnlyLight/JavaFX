/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bill;

import com.jfoenix.controls.JFXButton;
import tqduy.bean.Bill;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tqduy.connect.DBUtils_Bill;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLBillController implements Initializable {
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private AreaChart<String, Number> areaChart;
    @FXML private TableView<Bill> tbBill;
    @FXML private TableColumn<Bill, String> tbTenNVColumn;
    @FXML private TableColumn<Bill, Integer> tbTongTienColumn;
    @FXML private TableColumn<Bill, Date> tbNgayLapColumn;
    @FXML private JFXButton btnInThongTin;
    
    // EVENT FOR IN THONG TIN THONG KE
    private void setEvent() {
        btnInThongTin.setOnAction((event) -> {
            createAlert("Printing...");
        });
    }
    
    // CREATE ALERT FOR NOTIFICATION
    private void createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Infomation");
        alert.setContentText(content);

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK) {
        }

        System.out.println(result.get().getText());
    }
    
    // SHOW DATA FOR TABLE
    private void showTable() {
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
        
        // SET DATA FOR TABLE
        ObservableList<Bill> list = FXCollections.observableArrayList(DBUtils_Bill.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbBill.setItems(list);
            tbBill.getColumns().addAll(tbTenNVColumn, tbTongTienColumn, tbNgayLapColumn);
        }
    }
    
    // DISPLAY AREACHART
    private void showAreaChart() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        
        // SET TITLE
        areaChart.setTitle("Thông Kê Hóa Đơn");
        areaChart.setLegendSide(Side.LEFT);
        System.out.println("AreaChart");
        
        // SET DATA DISPLAY FOR LINE LAST YEAR
        XYChart.Series<String, Number> series2017 = new XYChart.Series<String, Number>();
        series2017.setName(""+(year-1)+"");
        
        // SET DATA DISPLAY FOR LINE CURRENT YEAR
        XYChart.Series<String, Number> series2018 = new XYChart.Series<String, Number>();
        series2018.setName(""+year+"");
        
        for(int i = 1; i <= 12; i++) {
            series2017.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Bill.getSumPirceForMonth(i, (year-1))));
            series2018.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Bill.getSumPirceForMonth(i, year)));
        }

        areaChart.getData().addAll(series2017, series2018);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showAreaChart();
        showTable();
        setEvent();
    }    
    
}

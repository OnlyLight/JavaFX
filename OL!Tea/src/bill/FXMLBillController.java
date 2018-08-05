/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bill;

import tqduy.bean.Bill;

import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
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
    @FXML private Button btnInThongTin;
    
    private void setEvent() {
        btnInThongTin.setOnAction((event) -> {
            createAlert("Printing...");
        });
    }
    
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
    
    private void showTable() {
        tbBill.getColumns().clear();
        tbTenNVColumn.setText("Nhân Viên");
        tbTongTienColumn.setText("Tổng tiền");
        tbNgayLapColumn.setText("Ngày Lập");
        
        tbTenNVColumn.setCellValueFactory(new PropertyValueFactory<>("tenNV"));
        tbTongTienColumn.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        tbNgayLapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayLap"));
        
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
        
        ObservableList<Bill> list = FXCollections.observableArrayList(DBUtils_Bill.getList());
        System.out.println("List: " + list);
        if(!list.isEmpty()) {
            tbBill.setItems(list);
            tbBill.getColumns().addAll(tbTenNVColumn, tbTongTienColumn, tbNgayLapColumn);
        }
    }
    
    private void showAreaChart() {
        areaChart.setTitle("Revenue");
        
        areaChart.setLegendSide(Side.LEFT);
        System.out.println("AreaChart");
        // Chuỗi dữ liệu của năm 2014
        XYChart.Series<String, Number> series2017 = new XYChart.Series<String, Number>();
        series2017.setName("2017");
        
        XYChart.Series<String, Number> series2018 = new XYChart.Series<String, Number>();
        series2018.setName("2018");
        
        for(int i = 1; i <= 12; i++) {
            series2017.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Bill.getSumPirceForMonth(i, 2017)));
            series2018.getData().add(new XYChart.Data<String, Number>(""+i+"", DBUtils_Bill.getSumPirceForMonth(i, 2018)));
        }

//        series2014.getData().add(new XYChart.Data<String, Number>("1", 400));
//        series2014.getData().add(new XYChart.Data<String, Number>("3", 1000));
//        series2014.getData().add(new XYChart.Data<String, Number>("4", 1500));
//        series2014.getData().add(new XYChart.Data<String, Number>("5", 800));
//        series2014.getData().add(new XYChart.Data<String, Number>("7", 500));
//        series2014.getData().add(new XYChart.Data<String, Number>("8", 1800));
//        series2014.getData().add(new XYChart.Data<String, Number>("10", 1500));
//        series2014.getData().add(new XYChart.Data<String, Number>("12", 1300));

        // Chuỗi dữ liệu của năm 2015
        
//        series2015.getData().add(new XYChart.Data<String, Number>("1", 2000));
//        series2015.getData().add(new XYChart.Data<String, Number>("3", 1500));
//        series2015.getData().add(new XYChart.Data<String, Number>("4", 1300));
//        series2015.getData().add(new XYChart.Data<String, Number>("5", 1200));
//        series2015.getData().add(new XYChart.Data<String, Number>("7", 1400));
//        series2015.getData().add(new XYChart.Data<String, Number>("8", 1080));
//        series2015.getData().add(new XYChart.Data<String, Number>("10", 2050));
//        series2015.getData().add(new XYChart.Data<String, Number>("12", 2005));

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
    }    
    
}

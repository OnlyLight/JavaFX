/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import animatefx.animation.BounceIn;
import animatefx.animation.Tada;
import bill.FXMLBillController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import login.Login;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tqduy.bean.Bill;
import tqduy.bean.DVT;
import tqduy.bean.LoaiNX;
import tqduy.bean.Nhap;
import tqduy.bean.NhapTable;
import tqduy.bean.TKTable;
import tqduy.bean.Xuat;
import tqduy.bean.XuatTable;
import tqduy.connect.DBUtils_Bill;
import tqduy.connect.DBUtils_DVT;
import tqduy.connect.DBUtils_LoaiNX;
import tqduy.connect.DBUtils_Nhap;
import tqduy.connect.DBUtils_TK;
import tqduy.connect.DBUtils_Xuat;

/**
 * FXML Controller class
 *
 * @author QuangDuy
 */
public class FXMLWareHouseController implements Initializable {

    @FXML
    private TableView tbNhap, tbXuat;
    @FXML
    private TableColumn<NhapTable, String> tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDVTXuatColumn, tbTenLoaiXuatColumn;
    @FXML
    private TableColumn<NhapTable, String> tbTenSpNhapColumn;
    @FXML
    private TableColumn<NhapTable, Integer> tbDonGiaNhapColumn, tbSoLuongNhapColumn;
    @FXML
    private TableColumn<NhapTable, Date> tbNgayNhapColumn;
    @FXML
    private TableColumn<XuatTable, String> tbTenSpXuatColumn;
    @FXML
    private TableColumn<XuatTable, Integer> tbSoLuongXuatColumn;
    @FXML
    private TableColumn<XuatTable, Date> tbNgayXuatColumn;
    @FXML
    private TextField txtTenSpNhap, txtSoLuongNhap, txtDonGiaNhap;
    @FXML
    private DatePicker dpNgayNhap, dpDayStartNhap, dpDayFinishNhap;
    @FXML
    private ComboBox<LoaiNX> cbTenLoaiNhap, cbLoaiThongKe;
    @FXML
    private ComboBox<String> cbTenSpXuat;
    @FXML
    private Button btnNhap, btnFind, btnPrintInfo, btnReset;
    @FXML
    private TableView<TKTable> tbThongKe;
    @FXML
    private TableColumn<TKTable, String> tbTenSpTKColumn, tbLoaiTKColumn;
    @FXML
    private TableColumn<TKTable, Integer> tbDonGiaTKColumn, tbSoLuongNhapTKColumn, tbSoLuongXuatTKColumn;
    @FXML
    private TableColumn<TKTable, Date> tbNgayNhapTKColumn, tbNgayXuatTKColumn;
    @FXML
    private Tab tabTK;
    @FXML
    private BarChart<String, Number> bcThongKe;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ArrayList<LoaiNX> arrLoai = DBUtils_LoaiNX.getList();
    private ArrayList<DVT> arrDVT = DBUtils_DVT.getList();
    private LocalDate dateNhap = LocalDate.now();
    private LocalDate dateXuat = LocalDate.now();
    private LocalDate dayStartNhap, dayFinishNhap, dayStartXuat, dayFinishXuat;
    private int soLuongNhap = 1, donGiaNhap = 1, countNhap = 0, countTK = 0, soLuongXuat = 1, countXuat = 0;
    private LoaiNX tenLoaiNhap = new LoaiNX(), tenLoaiXuat = new LoaiNX(), loaiTK = new LoaiNX();
    private String tenSpXuat = "";
    @FXML
    private StackPane mainStackPane;
    @FXML
    private VBox filterPopup;
    @FXML
    private JFXDatePicker dpDayStartXuat;
    @FXML
    private JFXDatePicker dpDayFinishXuat;
    @FXML
    private VBox rightSideMng;
    @FXML
    private JFXButton btnOpenFind;
    @FXML
    private JFXRadioButton importRadio;
    @FXML
    private ToggleGroup addType;
    @FXML
    private JFXRadioButton exportRadio;
    @FXML
    private JFXButton btnCloseAddMenu;
    @FXML
    private StackPane prdNameStackPane;
    @FXML
    private VBox importExportForm;

    // Bản thông kê
    private void displayBarChart() throws SQLException {
        int year = Calendar.getInstance().get(Calendar.YEAR); // GET CURRENT YEAR
        System.out.println("year: " + year);

        tabTK.setOnSelectionChanged((event) -> {
            try {
                ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.getList());
                if (!list.isEmpty()) {
                    tbThongKe.getItems().clear();
                    tbThongKe.setItems(list);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<>();
        dataSeries1.setName("Import");

        XYChart.Series<String, Number> dataSeries2 = new XYChart.Series<>();
        dataSeries2.setName("Export");

        for (int i = year - 2; i <= year; i++) {
            dataSeries1.getData().add(new XYChart.Data<String, Number>("" + i + "", DBUtils_Nhap.getNhap(i)));
            dataSeries2.getData().add(new XYChart.Data<String, Number>("" + i + "", DBUtils_Xuat.getXuat(i)));
        }

        bcThongKe.getData().add(dataSeries1);
        bcThongKe.getData().add(dataSeries2);

        System.out.println("BarChart");
    }

    // Set event for Tab Thong ke
    private void eventClickTK() {
        btnReset.setOnAction((event) -> {
            try {
                ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.getList());
                if (!list.isEmpty()) {
                    tbThongKe.getItems().clear();
                    tbThongKe.setItems(list);
                };
                JFXDialog dialog = (JFXDialog) mainStackPane.getChildren().get(1);
                dialog.close();
                btnOpenFind.setDisable(false);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnPrintInfo.setOnAction((event) -> {
            DirectoryChooser chooser = new DirectoryChooser();
            File f = chooser.showDialog(Login.getStage());
            if(f != null){
                System.out.println(f.getAbsolutePath());
                try {
                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet("Import - Export Statistical");
                    XSSFRow header = sheet.createRow(0);
                    header.createCell(0).setCellValue("Product Name");
                    header.createCell(1).setCellValue("Price");
                    header.createCell(2).setCellValue("Import Qty");
                    header.createCell(3).setCellValue("Import Date");
                    header.createCell(4).setCellValue("Export Qty");
                    header.createCell(5).setCellValue("Export Date");
                    header.createCell(6).setCellValue("Type");              
                    ArrayList<TKTable> list = DBUtils_TK.getList();
                    int index = 1;
                    for (int i = 0; i < list.size(); i++) {
                        TKTable get = list.get(i);
                        XSSFRow row = sheet.createRow(i+1);
                        System.out.println("gia: " + get.getDonGia());
                        row.createCell(0).setCellValue(get.getTenSp());
                        row.createCell(1).setCellValue(get.getDonGia());
                        row.createCell(2).setCellValue(get.getSoLuongNhap());
                        row.createCell(3).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(get.getNgayNhap()));
                        row.createCell(4).setCellValue(get.getSoLuongXuat());
                        row.createCell(5).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(get.getNgayXuat()));
                        row.createCell(6).setCellValue(get.getTenLoai());
                    }
                    for (int i = 0; i < 7; i++) {
                        sheet.autoSizeColumn(i);
                    }   
                    FileOutputStream fileOut = new FileOutputStream(f.getPath() + "/import-export-static.xlsx");
                    wb.write(fileOut);
                    fileOut.close();
                    creatDialog("Export file successfull at :\n"+ f.getPath() + "\\" + "import-export-static.xlsx", "success");
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void eventSearch() {
        btnOpenFind.setOnAction((event) -> {
            btnOpenFind.setDisable(true);
            JFXDialog dialog = new JFXDialog(mainStackPane, filterPopup, JFXDialog.DialogTransition.NONE);
            dialog.setOverlayClose(false);
            JFXButton btnClose = (JFXButton) dialog.lookup("#btnCloseAddMenu");
            btnClose.setOnAction((eventt) -> {
                dialog.close();
                btnOpenFind.setDisable(false);
            });
            dialog.show();
        });

        cbLoaiThongKe.setOnAction((event) -> {
            loaiTK = cbLoaiThongKe.getSelectionModel().getSelectedItem();
            System.out.println(loaiTK);
        });

        btnFind.setOnAction((event) -> {
            System.out.println("Result[dayStartNhap: " + dayStartNhap + " - dayFinishNhap: " + dayFinishNhap + " - dayStartXuat: " + dayStartXuat + " - dayFinishXuat: " + dayFinishXuat);
            // Lấy list dữ liệu đã tìm kiếm đưa ra cho table hiển thị
            ObservableList<TKTable> list = FXCollections.observableArrayList(DBUtils_TK.searchTK(dayStartNhap, dayFinishNhap, dayStartXuat, dayFinishXuat, loaiTK.getTenLoaiNX()));
            tbThongKe.getItems().clear();
            if (!list.isEmpty()) {
                tbThongKe.setItems(list);
            };
            JFXDialog dialog = (JFXDialog) mainStackPane.getChildren().get(1);
            dialog.close();
            btnOpenFind.setDisable(false);
        });
    }
    // End Set event for Tab Thong ke

    // Combobox
    private void loadCombobox() throws SQLException {
        ArrayList<Nhap> arrNhap = DBUtils_Nhap.getList();
        ArrayList<String> copy = new ArrayList<>();
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Nhap nhap : arrNhap) {
            String ten = nhap.getTenSpNhap();
            copy.add(ten);
        }
        System.out.println("arrCopy1: " + copy.toString());

        // Hiển thị tên cho việc nhập xuất
        for (int i = 0; i < arrNhap.size(); i++) {
            int count = 0;
            for (int j = 0; j < copy.size(); j++) {
                if (arrNhap.get(i).getTenSpNhap().equals(copy.get(j))) {
                    count++;
                    if (count >= 2) {
                        copy.remove(j); // Nếu đếm được tên 2 lần thì xóa đi lấy 1 lần
                        break;
                    }
                }
            }
        }
        System.out.println("arrCopy2: " + copy.toString());

        list.addAll(copy);
        if (!list.isEmpty()) {
            cbTenSpXuat.setItems(list);
        }
    }

    private void showComboboxTenXuat() {
        cbTenSpXuat.setOnMouseClicked((event) -> {
            cbTenSpXuat.getItems().clear();
            try {
                loadCombobox();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cbTenSpXuat.setOnAction((event) -> {
            getTenSpXuat();
//            showBoxNhap();
        });
    }

    private void getTenSpXuat() {
        tenSpXuat = cbTenSpXuat.getSelectionModel().getSelectedItem();
        System.out.println("Ten sp Xuat: " + tenSpXuat);

        if (tenSpXuat != null) {
            try {
                ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
                for (LoaiNX loaiNX : DBUtils_LoaiNX.getListForName(tenSpXuat)) { // Bug Here
                    loai.add(loaiNX);
                }

                if (!loai.isEmpty()) {
                    cbTenLoaiNhap.getItems().clear();
                    cbTenLoaiNhap.setItems(loai);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void showBoxNhap() {
        ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
        for (LoaiNX loaiNX : arrLoai) {
            loai.add(loaiNX);
            System.out.println(loaiNX);
        }
        if (!loai.isEmpty()) {
            cbTenLoaiNhap.setItems(loai);
        }
    }

    private void showCombobox() {
        ObservableList<LoaiNX> loai = FXCollections.observableArrayList();
        ObservableList<String> listDVT = FXCollections.observableArrayList();
        for (LoaiNX loaiNX : arrLoai) {
            loai.add(loaiNX);
        }

        for (DVT dvt : arrDVT) {
            String tenDV = dvt.getDvt();
            listDVT.add(tenDV);
        }

        showBoxNhap();
//        cbTenLoaiXuat.setItems(loai);
        cbLoaiThongKe.setItems(loai);
    }

    private void checkInput() {
        // Box Nhap
        txtSoLuongNhap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) { // Lọc dữ liệu cho việt nhập vào chỉ lấy số
                try {
                    soLuongNhap = Integer.parseInt(newValue);
                } catch (Exception e) {

                }
            } else {
                txtSoLuongNhap.setText(oldValue);
            }
        });

        txtDonGiaNhap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.equals("0")) {
                try {
                    donGiaNhap = Integer.parseInt(newValue);
                } catch (Exception e) {

                }
            } else {
                txtDonGiaNhap.setText(oldValue);
            }
        });
        // End Box Nhap

        // Box Xuat
//        txtSoLuongXuat.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.matches("\\d*") && !newValue.equals("0")) {
//                try {
//                    soLuongXuat = Integer.parseInt(newValue);
//                } catch (Exception e) {
//                    
//                }
//            } else {
//                txtSoLuongXuat.setText(oldValue);
//            }
//        });
        // End Box Xuat
    }

    private void eventDatePicker() {
        dpNgayNhap.setValue(LocalDate.now());
//        dpNgayXuat.setValue(LocalDate.now());
        dpDayStartNhap.setValue(LocalDate.now());
        dpDayFinishNhap.setValue(LocalDate.now());
//        dpDayStartXuat.setValue(LocalDate.now());
//        dpDayFinishXuat.setValue(LocalDate.now());

        // fomat for Datepicker dd/MM/yyyy
        fomatterForDatePicker(dpNgayNhap);
//        fomatterForDatePicker(dpNgayXuat);
        fomatterForDatePicker(dpDayStartNhap);
        fomatterForDatePicker(dpDayFinishNhap);
//        fomatterForDatePicker(dpDayStartXuat);
//        fomatterForDatePicker(dpDayFinishXuat);

        // Set Limit for datepicker statistical
        setDayLimitForDatePicker(dpDayStartNhap, dpDayFinishNhap);
//        setDayLimitForDatePicker(dpDayStartXuat, dpDayFinishXuat);

        dpNgayNhap.setOnAction((event) -> {
            dateNhap = dpNgayNhap.getValue();
        });

//        dpNgayXuat.setOnAction((event) -> {
//            dateXuat = dpNgayXuat.getValue();
//        });
        dpDayStartNhap.setOnAction((event) -> {
            dayStartNhap = dpDayStartNhap.getValue();
        });

        dpDayFinishNhap.setOnAction((event) -> {
            dayFinishNhap = dpDayFinishNhap.getValue();
        });

//        dpDayStartXuat.setOnAction((event) -> {
//            dayStartXuat = dpDayStartXuat.getValue();
//        });
//        
//        dpDayFinishXuat.setOnAction((event) -> {
//            dayFinishXuat = dpDayFinishXuat.getValue();
//        });
    }

    private void setDayLimitForDatePicker(DatePicker dayStart, DatePicker dayFinish) {
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(dayStart.getValue())) { //Disable all dates after required date
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); //To set background on different color
                }
            }
        };

        dayFinish.setDayCellFactory(dayCellFactory);
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

    private void boxNhap() {
        // Box Nhap
        cbTenLoaiNhap.setOnAction((event) -> {
            tenLoaiNhap = cbTenLoaiNhap.getSelectionModel().getSelectedItem();
            System.out.println(tenLoaiNhap + " - " + tenLoaiNhap.getIdLoaiNX());
        });

        btnNhap.setOnAction((event) -> {
            new BounceIn(btnNhap).setSpeed(2.0).play();
            String type = ((JFXRadioButton) addType.getSelectedToggle()).getText();
            if (type.equals("Export")) {
                try {
                    submitXuat();
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                submitNhap();
            }
        });
        // End Box Nhap
    }

    private void submitNhap() {
        if (importExportForm.lookup("#errorText") != null) {
            importExportForm.getChildren().remove(importExportForm.getChildren().size() - 2);
        }
        String ten = txtTenSpNhap.getText().toString().trim();
        if (ten.isEmpty() || txtSoLuongNhap.getText().isEmpty() || txtDonGiaNhap.getText().isEmpty() || tenLoaiNhap.getIdLoaiNX() == -1) {
            addErrorText(importExportForm, "All fields are required !");
        } else {
            try {
                DBUtils_Nhap.insert(ten, tenLoaiNhap.getIdLoaiNX(), donGiaNhap, soLuongNhap, dateNhap);
                System.out.println("Ten: " + ten + " - Số lượng: " + soLuongNhap + " - Đơn giá: " + donGiaNhap + " - Date: " + dateNhap + " - Loai: " + tenLoaiNhap);
                txtTenSpNhap.setText("");
                txtSoLuongNhap.setText("");
                txtDonGiaNhap.setText("");

                ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(DBUtils_Nhap.getList());
                if (!listNhapDisplay.isEmpty()) {
                    tbNhap.getItems().clear();
                    tbNhap.setItems(listNhapDisplay);
                }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void submitXuat() throws SQLException {
        if (importExportForm.lookup("#errorText") != null) {
                    importExportForm.getChildren().remove(importExportForm.getChildren().size() - 2);
        }
        System.out.println("data: " + tenSpXuat + txtSoLuongNhap + tenLoaiXuat);
        if (tenSpXuat == null || tenSpXuat.isEmpty() || txtSoLuongNhap.getText().trim().isEmpty() || tenLoaiXuat.getIdLoaiNX() == -1) {
            addErrorText(importExportForm, "All fields are required !");
        } else {
//            System.out.println("tenSpXuat: " + tenSpXuat + " - tenLoaiXuat: " + tenLoaiNhap.getIdLoaiNX() + " So luog:" + soLuongXuat + " - "+ dateXuat);
            DBUtils_Xuat.insert(tenSpXuat, tenLoaiNhap.getIdLoaiNX(), soLuongXuat, dateXuat);
            txtSoLuongNhap.setText("");

            ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(DBUtils_Xuat.getList());
            if (!listXuatDisplay.isEmpty()) {
                tbXuat.getItems().clear();
                tbXuat.setItems(listXuatDisplay);
            }
        }
    }

//    private void boxXuat() {
//        // Box Xuat
//        cbTenLoaiXuat.setOnAction((event) -> {
//            tenLoaiXuat = cbTenLoaiXuat.getSelectionModel().getSelectedItem();
//            System.out.println(tenLoaiXuat);
//        });
//        
//        btnXuat.setOnAction((event) -> {
//        });
//        // End Box Xuat
//    }
    private void eventClickButtonADD() {
        checkInput();

        eventDatePicker();

        boxNhap();
//        boxXuat();
    }

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
    // END Combobox Nhap

    // Box Xuat
    // Table
    private void createNameColumn() {
        // Table Nhap
        tbTenSpNhapColumn.setText("Product Name");
        tbTenLoaiNhapColumn.setText("Type");
        tbDVTNhapColumn.setText("Unit");
        tbDonGiaNhapColumn.setText("Price");
        tbSoLuongNhapColumn.setText("Quantity");
        tbNgayNhapColumn.setText("Imported Date");

        // Table Xuat
        tbTenSpXuatColumn.setText("Product Name");
        tbTenLoaiXuatColumn.setText("Type");
        tbDVTXuatColumn.setText("Unit");
        tbSoLuongXuatColumn.setText("Quantity");
        tbNgayXuatColumn.setText("Exported Date");

        // Table Thong Ke
        tbTenSpTKColumn.setText("Product Name");
        tbDonGiaTKColumn.setText("Price");
        tbSoLuongNhapTKColumn.setText("Import Qty");
        tbNgayNhapTKColumn.setText("Imported Date");
        tbSoLuongXuatTKColumn.setText("Export Qty");
        tbNgayXuatTKColumn.setText("Exported Date");
        tbLoaiTKColumn.setText("Type");
    }

    private void setDataTable() {
        // Set data for table Nhap
        tbTenSpNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbTenLoaiNhapColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
        tbDVTNhapColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        tbDonGiaNhapColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbSoLuongNhapColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbNgayNhapColumn.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));

        // Set data for table Xuat
        tbTenSpXuatColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbTenLoaiXuatColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
        tbDVTXuatColumn.setCellValueFactory(new PropertyValueFactory<>("dvt"));
        tbSoLuongXuatColumn.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        tbNgayXuatColumn.setCellValueFactory(new PropertyValueFactory<>("ngayXuat"));

        // Set data for Table Thong Ke
        tbTenSpTKColumn.setCellValueFactory(new PropertyValueFactory<>("tenSp"));
        tbDonGiaTKColumn.setCellValueFactory(new PropertyValueFactory<>("donGia"));
        tbSoLuongNhapTKColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongNhap"));
        tbNgayNhapTKColumn.setCellValueFactory(new PropertyValueFactory<>("ngayNhap"));
        tbSoLuongXuatTKColumn.setCellValueFactory(new PropertyValueFactory<>("soLuongXuat"));
        tbNgayXuatTKColumn.setCellValueFactory(new PropertyValueFactory<>("ngayXuat"));
        tbLoaiTKColumn.setCellValueFactory(new PropertyValueFactory<>("tenLoai"));
    }

    private void createTable() {
        createNameColumn();

        setDataTable();

        tbNgayNhapColumn.setCellFactory((column) -> {
            TableCell<NhapTable, Date> cell = new TableCell<NhapTable, Date>() {
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

        tbNgayXuatColumn.setCellFactory((column) -> {
            TableCell<XuatTable, Date> cell = new TableCell<XuatTable, Date>() {
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

        tbNgayNhapTKColumn.setCellFactory((column) -> {
            TableCell<TKTable, Date> cell = new TableCell<TKTable, Date>() {
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

        tbNgayXuatTKColumn.setCellFactory((column) -> {
            TableCell<TKTable, Date> cell = new TableCell<TKTable, Date>() {
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
    }

    private void setEvent() throws SQLException {
        tbNhap.getColumns().clear();
        tbXuat.getColumns().clear();
        tbThongKe.getColumns().clear();
        tbNhap.setEditable(true);
        tbXuat.setEditable(true);

        createTable();

        ArrayList<Nhap> listNhap = DBUtils_Nhap.getList();
        ArrayList<Xuat> listXuat = DBUtils_Xuat.getList();

        ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(listNhap);
        ObservableList<XuatTable> listXuatDisplay = getListXuatDisplay(listXuat);

        if (!listXuatDisplay.isEmpty() && !listNhapDisplay.isEmpty()) {
            tbNhap.setItems(listNhapDisplay);
            tbNhap.getColumns().addAll(tbTenSpNhapColumn, tbTenLoaiNhapColumn, tbDVTNhapColumn, tbDonGiaNhapColumn, tbSoLuongNhapColumn, tbNgayNhapColumn);

            tbXuat.setItems(listXuatDisplay);
            tbXuat.getColumns().addAll(tbTenSpXuatColumn, tbTenLoaiXuatColumn, tbDVTXuatColumn, tbSoLuongXuatColumn, tbNgayXuatColumn);
        }

        tbThongKe.getColumns().addAll(tbTenSpTKColumn, tbDonGiaTKColumn, tbSoLuongNhapTKColumn, tbNgayNhapTKColumn, tbSoLuongXuatTKColumn, tbNgayXuatTKColumn, tbLoaiTKColumn);
    }

    private void setOnKeyPress() {
        tbNhap.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteRowTableNhap();
            }
        });

        tbXuat.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteRowTableXuat();
            }
        });
    }

    private void deleteRowTableXuat() {
        ObservableList<XuatTable> list = tbXuat.getSelectionModel().getSelectedItems();
        ArrayList<XuatTable> rows = new ArrayList<>(list);
        rows.forEach((row) -> {
            try {
                DBUtils_Xuat.delete(row.getId());

                ArrayList<Xuat> listXuat = DBUtils_Xuat.getList();
                ObservableList<XuatTable> listXuatpDisplay = getListXuatDisplay(listXuat);

                tbXuat.getItems().clear();
                tbXuat.setItems(listXuatpDisplay);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void deleteRowTableNhap() {
        ObservableList<NhapTable> list = tbNhap.getSelectionModel().getSelectedItems();
        ArrayList<NhapTable> rows = new ArrayList<>(list);
        rows.forEach((row) -> {
            try {
                DBUtils_Nhap.delete(row.getId());

                ArrayList<Nhap> listNhap = DBUtils_Nhap.getList();
                ObservableList<NhapTable> listNhapDisplay = getListNhapDisplay(listNhap);

                tbNhap.getItems().clear();
                tbNhap.setItems(listNhapDisplay);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private ObservableList<NhapTable> getListNhapDisplay(ArrayList<Nhap> listNhap) {
        ObservableList<NhapTable> list = FXCollections.observableArrayList();

        for (Nhap nhap : listNhap) {
            int id = nhap.getIdNhap();
            String tenSp = nhap.getTenSpNhap();
            int donGia = nhap.getDonGia();
            int soLuong = nhap.getSoLuong();
            Date ngayNhap = nhap.getNgayNhap();
            int idLoai = nhap.getIdLoaiNX();
            String tenLoai = "";
            String tenDV = "";
            for (LoaiNX loaiNX : arrLoai) {
                if (idLoai == loaiNX.getIdLoaiNX()) {
                    tenLoai = loaiNX.getTenLoaiNX();
                    int idDVT = loaiNX.getIdDVT();

                    for (DVT dvt : arrDVT) {
                        if (idDVT == dvt.getIdDVT()) {
                            tenDV = dvt.getDvt();
                            break;
                        }
                    }
                    break;
                }
            }
            NhapTable nb = new NhapTable(id, tenSp, tenLoai, tenDV, donGia, soLuong, ngayNhap);
            list.add(nb);
        }
        return list;
    }

    private ObservableList<XuatTable> getListXuatDisplay(ArrayList<Xuat> listXuat) {
        ObservableList<XuatTable> list = FXCollections.observableArrayList();

        for (Xuat xuat : listXuat) {
            int id = xuat.getIdXuat();
            String tenSp = xuat.getTenSpXuat();
            int soLuong = xuat.getSoLuong();
            Date ngayXuat = xuat.getNgayXuat();
            int idLoai = xuat.getIdLoaiNX();
            String tenLoai = "", tenDVT = "";
            for (LoaiNX loaiNX : arrLoai) {
                if (idLoai == loaiNX.getIdLoaiNX()) {
                    tenLoai = loaiNX.getTenLoaiNX();
                    int idDVT = loaiNX.getIdDVT();

                    for (DVT dvt : arrDVT) {
                        if (idDVT == dvt.getIdDVT()) {
                            tenDVT = dvt.getDvt();
                            break;
                        }
                    }
                    break;
                }
            }
            XuatTable xb = new XuatTable(id, tenSp, tenLoai, tenDVT, soLuong, ngayXuat);
            list.add(xb);
        }
        return list;
    }
    // End Table
    private void addErrorText(VBox vbox, String text) {
        System.out.println("size: " + vbox.getChildren().size());
        Label textLabel = new Label(text);
        textLabel.setStyle("-fx-text-fill: #e84545; -fx-font-weight: bold");
        HBox hbox = new HBox(textLabel);
        textLabel.setId("errorText");
        hbox.setUserData("error");
        vbox.getChildren().add(vbox.getChildren().size() - 1, hbox);
        new Tada(textLabel).setSpeed(1.5).play();
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tenLoaiNhap.setIdLoaiNX(-1);
            loadCombobox();
            addType.selectedToggleProperty().addListener((observable) -> {
                if (importExportForm.lookup("#errorText") != null) {
                    importExportForm.getChildren().remove(importExportForm.getChildren().size() - 2);
                }
                prdNameStackPane.getChildren().get(0).toFront();
                String type = ((JFXRadioButton) addType.getSelectedToggle()).getText();
                if (type.equals("Export")) {
                    getTenSpXuat();
                    txtDonGiaNhap.setText("");
                    txtDonGiaNhap.setDisable(true);
                } else {
                    showBoxNhap();
                    txtDonGiaNhap.setDisable(false);
                }
            });
            tbNhap.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tbXuat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            System.out.println("max: " + tbLoaiTKColumn.getMaxWidth());
            setEvent();
            showCombobox();
            eventClickButtonADD();
            setOnKeyPress();
            eventSearch();
            eventClickTK();
            displayBarChart();
            showComboboxTenXuat();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLWareHouseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
